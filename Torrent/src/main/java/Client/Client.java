package Client;

import Bytes.ByteProcess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

// addons:
// 1 - make function for input different kind of data
// 2 - better exception user output
// 3 - a lot of work for garbage collector
public class Client {
    private final static int BUFFER_SIZE = 20_000;
    private final static int COMMAND_POSITION = 0;
    private final static int USER_DOWNLOAD_FROM_POSITION = 1;
    private final static int USERNAME_POSITION = 1;
    private final static int FILE_TO_DOWNLOAD = 2;
    private final static int PATH_TO_DOWNLOAD = 3;
    private final static int IP_ADDRESS_POSITION = 0;

    private final static int CONNECTED_CLIENT_USERNAME = 0;
    private final static int CONNECTED_CLIENT_CONNECTION_INFO = 1;
    private final static int CONNECTED_CLIENT_IP = 1;
    private final static int CONNECTED_CLIENT_PORT = 1;


    private final static int SERVER_PORT = 8000;

    private final static int GET_AVAILABLE_CLIENTS_ESTIMATE_TIME = 5000;

    private String miniServerHost; // ip host of miniServer
    private String mainServerHost;


    private Selector selector;
    private SocketChannel mainSocketChannel;

    private int miniServerPort;
    private AvailableClients availableClients;
    private MiniServer miniServer;
    private ByteBuffer byteBuffer;
    private ByteProcess byteProcess = new ByteProcess();



    public static void main(String[] strings) {
        try {
            Client client = new Client();
            client.run();
        } catch (FileNotFoundException e) {
            //System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            //System.out.println("IO Exception");
            e.printStackTrace();
        }
    }

    public Client() {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("You need to choose port for your server: ");

                int port = scanner.nextInt();

                if (port >= 1024 && port <= 65_535) {
                    miniServerPort = port;
                    System.out.println("Success");
                    break;
                } else {
                    System.out.println("Pick port between 1024 and 65_535");
                }
            } catch (Exception e) {
                System.out.println("You have entered different input type");
            }
        }
    }

    // for test purposes is it better function return type to be String
    private void run() throws FileNotFoundException, IOException {
        selector = Selector.open();
        mainServerHost = enterMainSeverHost();
        configMainSocketChannel(mainServerHost);
        configMiniServer();
        configTrackingOnlineClientsThread(mainServerHost);

        String line;
        while (true) {
            Scanner scanner = new Scanner(System.in);

            line = scanner.nextLine().trim();
            String[] tokens = line.split("\\s+");

            if (line.getBytes().length > BUFFER_SIZE) {
                System.out.println("Please enter message with at most 20_000 symbols");
                continue;
            }

            switch (tokens[COMMAND_POSITION]) {
                case "download": {
                    File fileToDownload = new File(tokens[FILE_TO_DOWNLOAD]);

                    String userDownloadFrom = tokens[USER_DOWNLOAD_FROM_POSITION];
                    String pathToDownload = tokens[PATH_TO_DOWNLOAD];

                    SocketChannel peerToPeerSocketChannel = configPeerToPeerConnection(userDownloadFrom);
                    System.out.println(peerToPeerSocketChannel);

                    byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

                    byteBuffer.put(line.getBytes());
                    byteBuffer.flip();

                    peerToPeerSocketChannel.write(byteBuffer);

                    RandomAccessFile randomAccessFile = null;
                    try {
                        randomAccessFile = new RandomAccessFile(pathToDownload + "\\" + "DownloadedFile" + fileToDownload.getName(), "rw");
                    } catch (FileNotFoundException e) {
                        System.out.println("No such file, try again");
                        continue;
                    }
                    FileChannel fileChannel = randomAccessFile.getChannel();

                    while (peerToPeerSocketChannel.isConnected()) {
                        byteBuffer.clear();
                        // is connected maybe??? do not know
                        //socketChannel.read(byteBuffer);
                        int result = peerToPeerSocketChannel.read(byteBuffer);
                        byteBuffer.flip();
                        fileChannel.write(byteBuffer);
                        if (result == -1) {
                            break;
                        }
                    }
                    //socket.close();
                    fileChannel.close(); //// close the file
                    break;
                }
                case "register": {
                    if (tokens.length < 3) {
                        // when the command is ok, but following input is invalid
                        System.out.println("Invalid input command");
                        continue;
                    }
                    String message = byteProcess.readBytesFromServer(userCommandEncode(tokens), mainSocketChannel);
                    System.out.println(message);

                    break;
                }
                case "unregister": {
                    if (tokens.length < 3) {
                        // when the command is ok, but following input is invalid
                        System.out.println("Invalid input command");
                        continue;
                    }
                    String message = byteProcess.readBytesFromServer(line, mainSocketChannel);
                    System.out.println(message);

                    break;
                }
                case "list-files": {

                    if (!line.equals("list-files")) {
                        System.out.println("Do you mean only list-files?");
                        continue;
                    }
                    String message = byteProcess.readBytesFromServer(line, mainSocketChannel);
                    System.out.println(message);
                    break;
                }
                case "list-users": {
                    listClientPeerToPeerInformation();
                    break;
                }
                default: {
                    System.out.println("Error, shutting down in: ");

                }
            }
            // close socket maybe;
        }
    }

    private Map<String, String> getOnlineUserInfo() throws IOException {
        //try {
            availableClients.readClientsFromServer();
            // must join, not sure if it is always faster
           //availableClients.join(GET_AVAILABLE_CLIENTS_ESTIMATE_TIME);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Map<String, String> onlineUserInfo = new HashMap<>();

        String clients = availableClients.getOnlineClients();
        if (!clients.equals("No users")) {
            for (String client : clients.split(", ")) {
                String user = client.split(" - ")[CONNECTED_CLIENT_USERNAME];
                String userInfo = client.split(" - ")[CONNECTED_CLIENT_CONNECTION_INFO];
                onlineUserInfo.put(user, userInfo);
            }
            return onlineUserInfo;
        } else {
            return null;
        }
    }

    private void listClientPeerToPeerInformation() throws IOException {
        Map<String, String> onlineClients = getOnlineUserInfo();

        if(onlineClients != null) {
            onlineClients.forEach((key, value) -> System.out.println(key + " - " + value));
        } else {
            System.out.println("No clients");
        }
    }

    private String enterMainSeverHost() {
        System.out.println("Choose host connection: ");
        Scanner hostScanner = new Scanner(System.in);
        return hostScanner.nextLine();
    }

    private String getMiniServerHost() throws IOException {
        // list all IP-s and make user choose one of them
        SocketAddress socketAddress = mainSocketChannel.getLocalAddress();
        return socketAddress.toString().replace("/", "").split(":")[IP_ADDRESS_POSITION];
    }

    private void configMiniServer() {
        miniServer = new MiniServer(selector, miniServerPort);
        miniServer.setDaemon(true);
        miniServer.start();
    }

    private void configTrackingOnlineClientsThread(String host) {
        availableClients = new AvailableClients(host);
        availableClients.setDaemon(true);
        availableClients.start();
    }

    private void configMainSocketChannel(String host) throws IOException {
        mainSocketChannel = SocketChannel.open(new InetSocketAddress(host, SERVER_PORT));
        mainSocketChannel.configureBlocking(true);
    }

    private String userCommandEncode(String[] tokens) throws IOException {
        miniServerHost = getMiniServerHost();
        tokens[USERNAME_POSITION] = tokens[USERNAME_POSITION] + ":" + miniServerHost + ":" + this.miniServerPort;

        return String.join(" ", tokens);
    }

    private SocketChannel configPeerToPeerConnection(String userName) {
        SocketChannel peerToPeerChannel = null;
        try {
            Map<String, String> onlineUserInfo = getOnlineUserInfo();

            // final values
            // literal values change with static final values
            String ip = onlineUserInfo.get(userName).split(":")[0];
            int port = Integer.parseInt(onlineUserInfo.get(userName).split(":")[1]);
            peerToPeerChannel = SocketChannel.open(new InetSocketAddress(ip, port));
            peerToPeerChannel.configureBlocking(false); // !!! could be BLOCKING
            return peerToPeerChannel;

        } catch (IOException e) {
            System.out.println("Cannot connect, incorrect data or no users online");
        }
        return peerToPeerChannel;
    }

}
