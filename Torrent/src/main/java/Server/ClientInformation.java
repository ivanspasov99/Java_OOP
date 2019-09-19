package Server;

import java.net.InetAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.*;

public class ClientInformation {
    private String userName;
    private int PORT;
    private String IP_ADDRESS;
    private Set<String> filesList;
    private SocketChannel socketChannel;

    public ClientInformation(String userName, int PORT, String IP_ADDRESS, String files, SocketChannel socketChannel) {
        this.userName = userName;
        this.PORT = PORT;
        this.IP_ADDRESS = IP_ADDRESS;
        this.filesList = new HashSet<>();
        this.socketChannel = socketChannel;
        register(files);
    }

    void register(String files) {
        // files can not be with same names
        filesList.addAll(Arrays.asList(files.split(",\\s+")));
    }

    void unregisterFiles(String files) {
        filesList.removeAll(Arrays.asList(files.split(",\\s+")));
    }

    public String getClientInfo() {
        return userName + " - " + IP_ADDRESS + ":" + PORT;
    }

    public String getFileInfo() {
        return userName + filesList.toString();
    }

    public boolean isConnected() {
        return socketChannel.isConnected();
    }
}
