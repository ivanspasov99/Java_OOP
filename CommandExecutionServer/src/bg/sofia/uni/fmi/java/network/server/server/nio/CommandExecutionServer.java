package bg.sofia.uni.fmi.java.network.server.server.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;


// Пуснете вашия сървър и вижте дали на операционната система порт 4444 е зает, с командата: netstat -an
// with this command you can see all of your active ports (listening for specific apps)
public class CommandExecutionServer implements AutoCloseable {

    public static final int SERVER_PORT = 4444;
    private static final int BUFFER_SIZE = 1500;

    private Selector selector;
    private ByteBuffer commandBuffer;
    private ServerSocketChannel ssc;
    private boolean runServer = true;

    public CommandExecutionServer(int port) throws IOException {
        selector = Selector.open();
        // init selector
        commandBuffer = ByteBuffer.allocate(BUFFER_SIZE); // how much bytes we need

        // server channel config
        ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false); // to support more than one channel???
        // it need to be registered because it is waiting for connection
        // when it sees that connection is OP_CONNECT, it accept it and it making specific channel to communicate on with
    }

    /**
     * Start the server
     * @throws IOException
     */
    public void start() throws IOException {
        // register in selectors
        ssc.register(selector, SelectionKey.OP_ACCEPT); // check if connection is successful with OP_ACCEPT

        while (runServer) {
            // select is blocking until there is at least one event
            int readyChannels = selector.select();  // return channels which are ready for processing

            // won't come never here
            if (readyChannels == 0) {
                continue; // if no channels
            }


            // getting all channels
            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            // using iterator because we need to delete the channel
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                // there is possibility of upcoming channel which can not be read
                if (key.isReadable()) {
                    System.out.println("Ready for read!");
                    this.read(key);
                } else if (key.isAcceptable()) {
                    /// !!! FIRST IT IS COMING HERE BECAUSE IT IS NOT READY FOR READ, WE NEED TO GET CHANNEL READY FOR READ
                    System.out.println("new!");
                    this.accept(key);
                }
                // when we are getting connection we are in one big while which is making our SET to get bigger
                // when we read from it we have coupe with the event so we do not need it more
                // we need to remove it because it is going to resetting, or doing stuff with already couped events
                keyIterator.remove(); // removing it because  readyChannels = selector.select(); is responsible for caching the new "events"
            }

        }
        System.out.println(InetAddress.getLocalHost().getHostAddress()); // where our server is located
    }

    /**
     * Accept a new connection
     *
     * @param key The key for which an accept was received
     * @throws IOException In case of problems with the accept
     */
    private void accept(SelectionKey key) throws IOException {

        // when iterating you are finding that there is waiting connection
        // because you have registered ServerSocket, and it is checking non stop
        ServerSocketChannel incomingConnection = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = incomingConnection.accept();
        socketChannel.configureBlocking(false); // for read and write operations i thing
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * Read data from a connection
     *
     * @param key The key for which data was received
     */
    private void read(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        try {
            commandBuffer.clear(); // useless
            // reading what user send
            int r = socketChannel.read(commandBuffer); // request
            if (r == -1) {
                return;
                //throw new RuntimeException("Channel is broken");
            }

            // Flips this buffer.
            // The limit is set to the current position and then the position is set to zero.
            // If the mark is defined then it is discarded.
            commandBuffer.flip();


            // decode bytes into unicode array characters
            // we flipped it so bytes are only 6 (if input is ivan),
            // we are doing this because we want to get only the read bytes not the whole 1500
            String message = Charset.forName("UTF-8").decode(commandBuffer).toString();

            // format the message
            String result = executeCommand(message);

            // result for server
            System.out.println("message:" + message);
            System.out.println("result:" + result);

            // Clears this buffer. The position is set to zero, the limit is set to the capacity, and the mark is discarded.
            // doing this because we want to write into the buffer new input
            commandBuffer.clear();

            // putting into the buffer data
            //Writes the given byte into this buffer at the current position, and then increments the position.
            // the position is x, so again...
            commandBuffer.put((result + System.lineSeparator()).getBytes());

            // we want to get only taken bytes into the array( only the output we want client to see)
            commandBuffer.flip();

            // again there is byte[] array only with specific symbol we wrote
            socketChannel.write(commandBuffer);

            // getting it ready for next input(ready for read)
        } catch (IOException e) {
            this.stop();
            e.getMessage();
            e.printStackTrace();
        }
    }

    /**
     * Stop the server
     * @throws IOException
     */
    public void stop() {
        runServer = false;
    }

    /**
     * Validate and execute the received commands from the clients
     *
     * @param recvMsg
     * @return The result of the execution of the command
     */
    private String executeCommand(String recvMsg) {
        String[] cmdParts = recvMsg.split(":");

        if (cmdParts.length > 2) {
            return "Incorrect command syntax";
        }

        String command = cmdParts[0].trim();

        if (command.equalsIgnoreCase("echo")) {
            if (cmdParts.length <= 1) {
                return "Missing Argument";
            }
            return cmdParts[1].strip();
        } else if (command.equalsIgnoreCase("gethostname")) {
            try {
                return InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return "Could not get hostname";
            }
        } else {
            return "Unknown command";
        }
    }

    @Override
    public void close() throws Exception {
        ssc.close();
        selector.close();
    }

    public static void main(String args[]) throws Exception {
        try (CommandExecutionServer es = new CommandExecutionServer(SERVER_PORT)) {
            es.start();
        } catch (Exception e) {
            System.out.println("An error has occured");
            e.printStackTrace();
        }
    }
}
