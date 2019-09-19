package Client;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class MiniServer extends Thread {
    private final int PORT;
    private static final int FILE_NAME_POSITION = 2;
    private Selector selector;
    private String fileName;

    public MiniServer(Selector selector, int port) {
        this.selector = selector;
        this.PORT = port;
    }

    @Override
    public void run() {
        try {
            ServerSocketChannel serverSocketChannel;
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            int ops = serverSocketChannel.validOps();

            serverSocketChannel.register(selector, ops, null);
            startMiniServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startMiniServer() throws IOException {
        while (true) {
            int channels = selector.select();
            if (channels == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // is connectable
                // is writable
                if (key.isAcceptable()) {
                    createChannel(key);
                }
                if (key.isReadable()) {
                    read(key);
                }
                if (key.isWritable()) {
                    // write
                    write(key);
                }
                iterator.remove();
            }
        }
    }

    private void createChannel(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(50_000);

        // could be slow on network, maybe in while till -1 is reached;
        byteBuffer.clear();
        int result = socketChannel.read(byteBuffer);
        byteBuffer.flip();
        //byteBuffer.flip();
        fileName = Charset.forName("UTF-8").decode(byteBuffer).toString().split(" ")[FILE_NAME_POSITION];
        socketChannel.register(selector, SelectionKey.OP_WRITE);
    }

    private void write(SelectionKey key) {
        try {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(50_000);

            RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "r");
            FileChannel fileChannel = randomAccessFile.getChannel();
            // new method
            // fileChannel.transferTo(socketChannel);
            // fileChannel.read(byteBuffer) > 0
            while (true) {
                byteBuffer.clear();
                int result = fileChannel.read(byteBuffer);
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                if (result == -1) {
                    break;
                }
            }
            socketChannel.close();
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
