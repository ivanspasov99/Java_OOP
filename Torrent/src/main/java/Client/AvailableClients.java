package Client;

import Bytes.ByteProcess;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class AvailableClients extends Thread {
    private final static String ONLINE_CLIENTS_COMMAND = "give-online-clients";
    private static final int SERVER_PORT = 8000;
    private String clientList;
    private SocketChannel availableClientChannel;
    private ByteProcess byteProcess = new ByteProcess();

    public AvailableClients(String host) {
        try {

            availableClientChannel = SocketChannel.open(new InetSocketAddress(host, SERVER_PORT));
            availableClientChannel.configureBlocking(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // not safe, throw exception if interrupted
                Thread.sleep(5_000);
                readClientsFromServer();
            }
        } catch (InterruptedException e) {
            System.out.println("Thread for tracking clients interrupted");
            e.printStackTrace();
        }
    }

    public String getOnlineClients() {
        return clientList;
    }

    public void readClientsFromServer() {
        clientList = byteProcess.readBytesFromServer(ONLINE_CLIENTS_COMMAND, availableClientChannel);
    }
}
