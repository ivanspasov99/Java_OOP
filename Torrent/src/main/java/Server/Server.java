package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class Server {

    private static final int BUFFER_SIZE = 200;
    private static final int PORT = 8000;

    private static final int COMMAND_POSITION = 0;
    private static final int USER_INFO_POSITION = 1;
    private static final int FILES_POSITION = 2;

    private static final int USERNAME_POSITION = 0;
    private static final int USER_IP_POSITION = 1;
    private static final int USER_PORT_POSITION = 2;

    private static final int MAX_INT_SIZE = 4;

    private Selector selector;
    private Map<String, ClientInformation> clientList = new HashMap<String, ClientInformation>();
    ByteBuffer byteBuffer;

    public static void main(String[] strings) {
        try {
            Server server = new Server();
            server.run();
        } catch (IOException e) {
            System.out.println("Input/Output error");
            e.printStackTrace();
        }
    }

    private void run() throws IOException {
        selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        configServerSocketChannel(serverSocketChannel);

        // shutdown the server maybe???
        // how???
        while (true) {
            int channels = selector.select();
            // not necessary
            if (channels == 0) {
                continue;
            }

            Set<SelectionKey> events = selector.selectedKeys();
            Iterator<SelectionKey> iterator = events.iterator();


            while (iterator.hasNext()) {
                SelectionKey event = (SelectionKey) iterator.next();

                if (event.isAcceptable()) {
                    createChannel(event);
                } else if (event.isReadable()) {
                    // user problem exceptions
                    try {
                        String message = readCommand(event, byteBuffer);
                        byte[] returnMessage = executeCommand(message, event);
                        writeToClient(event, returnMessage, byteBuffer);

                    } catch (IOException | NullPointerException e) {
                        event.channel().close();
                        filterOnlineUsers();
                        System.out.println("Connection has been lost with client");
                    }
                }
                iterator.remove();
            }
        }
    }

    private void filterOnlineUsers() {
        clientList = clientList.entrySet().stream().filter(entry -> entry.getValue().isConnected()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void createChannel(SelectionKey event) throws IOException {
        ServerSocketChannel incomingConnection = (ServerSocketChannel) event.channel();
        SocketChannel socketChannel = incomingConnection.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("Client Accepted: " + socketChannel.getRemoteAddress());
    }

    private void configServerSocketChannel(ServerSocketChannel serverSocketChannel) throws IOException {
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        serverSocketChannel.configureBlocking(false);
        int validOps = serverSocketChannel.validOps();
        serverSocketChannel.register(selector, validOps, null);
    }

    private String readCommand(SelectionKey event, ByteBuffer byteBuffer) throws IOException {
        SocketChannel socketChannel = (SocketChannel) event.channel();
        // could be more than 200 symbols
        byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        byteBuffer.clear();

        // info could be lost because slow network......!!!!,
        socketChannel.read(byteBuffer);
        byteBuffer.flip();

        return Charset.forName("UTF-8").decode(byteBuffer).toString();
    }

    private byte[] executeCommand(String message, SelectionKey event) throws IOException {

        String[] tokens = message.split(" ");

        switch (tokens[COMMAND_POSITION]) {
            case "register": {
                String[] userInformation = tokens[USER_INFO_POSITION].split(":");
                String[] filesArray = Arrays.copyOfRange(tokens, 2, tokens.length);
                String files = String.join(" ", filesArray);

                return addClientToMap(userInformation, files, event);
            }
            case "unregister": {
                // comes only username
                String userName = tokens[USER_INFO_POSITION];
                String[] filesArray = Arrays.copyOfRange(tokens, 2, tokens.length);
                String files = String.join(" ", filesArray);

                // could remove another user files
                if (!clientList.containsKey(userName)) {
                    return prepareMessage("No such user!");
                }
                clientList.get(userName).unregisterFiles(files);
                return prepareMessage("Files removed");
            }
            case "list-files": {
                String parsed = clientList.values().stream().map(v -> v.getFileInfo()).collect(Collectors.joining(", "));
                return prepareMessage(parsed);
            }
            case "give-online-clients": {
                String parsed = clientList.values().stream().map(v -> v.getClientInfo()).collect(Collectors.joining(", "));
                if(parsed.isEmpty()) {
                    return prepareMessage("No users");
                }
                return prepareMessage(parsed);
            }
            default: {
                return prepareMessage("No such command");
            }
        }
    }

    private byte[] addClientToMap(String[] userInfo, String files, SelectionKey event) throws IOException {
        SocketChannel socketChannel = (SocketChannel) event.channel();
        String userName = userInfo[USERNAME_POSITION];
        String userIP = userInfo[USER_IP_POSITION];
        int userPort = Integer.parseInt(userInfo[USER_PORT_POSITION]);

        if (!clientList.containsKey(userName)) {
            clientList.put(userName, new ClientInformation(userName, userPort, userIP, files, socketChannel));
            return prepareMessage("Successful created user: " + userName + " and uploaded files: " + files);
        } else {
            clientList.get(userName).register(files);
            return prepareMessage("Added files to user: " + userName + " and uploaded files: " + files);
        }
    }

    private void writeToClient(SelectionKey event, byte[] byteArray, ByteBuffer byteBuffer) throws IOException {
        SocketChannel socketChannel = (SocketChannel) event.channel();

        byteBuffer = ByteBuffer.allocate(byteArray.length);

        byteBuffer.clear();
        byteBuffer.put(byteArray);
        byteBuffer.flip();

        socketChannel.write(byteBuffer);

        System.out.println("Response sent");
    }

    public static int getPort() {
        return PORT;
    }

    private byte[] prepareMessage(String line) {
        int size = line.length();

        ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_INT_SIZE + size);

        byteBuffer.putInt(size);
        byteBuffer.put(line.getBytes());

        return byteBuffer.array();
    }
}
