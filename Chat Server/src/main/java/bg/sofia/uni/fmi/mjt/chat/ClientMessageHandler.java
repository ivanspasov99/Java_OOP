package bg.sofia.uni.fmi.mjt.chat;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
// how to synchronized sending multiple messages to one client from multiple other
// userList is going to have special class value with properties (socket and Object Lock)
// and class will synchronize by write functions with synch at Lock
// synch(lock) { printWriter.println(message) }
// we will have additional helper filed in specific class to synchronize write operation

// could have same problem with input stream
public class ClientMessageHandler implements Runnable {

    private final static int COMMAND_POSITION = 0;
    private final static int SEND_TO_USERNAME_POSITION = 1;
    private final static int SEND_TO_USERNAME_MESSAGE_POSITION = 2;
    private final static int SEND_TO_ALL_MESSAGE_POSITION = 1;
    private final String username;
    private final Socket socket;

    public ClientMessageHandler(String username, Socket socket) {
        this.socket = socket;
        this.username = username;
    }

    @Override
    public void run() {
        try {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            ) {
                while (true) {
                    String line = null;
                    try {
                        line = bufferedReader.readLine();
                    } catch (NullPointerException | IOException e) {
                            // when client exist without disconnect
                        disconnectFromServer();
                        e.printStackTrace();
                        continue;
                    }
                    String command = line.split("\\s+")[COMMAND_POSITION];

                    switch (command) {
                        case "send": {
                            String[] tokens = line.split("\\s+");
                            String userNameTo = tokens[SEND_TO_USERNAME_POSITION];
                            String message = String.join(" ", Arrays.asList(line.split("\\s+")).subList(SEND_TO_USERNAME_MESSAGE_POSITION, tokens.length));
                            sendMessageToUserName(message, userNameTo, printWriter);
                            break;
                        }
                        case "send-all": {
                            String[] tokens = line.split("\\s+");
                            String message = String.join(" ", Arrays.asList(line.split("\\s+")).subList(SEND_TO_ALL_MESSAGE_POSITION, tokens.length));
                            sendMessageToAllUsers(message, printWriter);
                            break;
                        }
                        case "disconnect": {
                            // COMMAND : <message>
                            // MESSAGE : <message> | <RECEIVER>
                            // BAN : <message>
                            // DISCONNECT_CONFIRMED: You have been disconnected
                            printWriter.println("You have been disconnected");
                            disconnectFromServer();
                            return;
                        }
                        case "list-users": {
                            listAllOnlineUsers(printWriter);
                            break;
                        }
                        default: {
                            printWriter.println("No such command");
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendMessageToUserName(String message, String username, PrintWriter toSender) {
        try {
            if(username.equals(this.username)) {
                toSender.println("cannot send message to yourself");
                return;
            }
            Socket socketTo = ChatServer.getUserSocket(username);
            // not closed??
            // open second Writer think it is a problem
            if (socketTo == null) {
                toSender.println("User is offline");
                return;
            }
            // thread unsafe
            PrintWriter toReceiver = new PrintWriter(socketTo.getOutputStream(), true);
            toReceiver.println(this.username + " send you: " + message);
            System.out.println("successfully send message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToAllUsers(String message, PrintWriter toSender) {
        String username;
        for (Map.Entry<String, Socket> entry : ChatServer.userList.entrySet()) {
            username = entry.getKey();
            if(username.equals(this.username)) {
                continue;
            }
            sendMessageToUserName(message, entry.getKey(), toSender);
        }
    }

    private void listAllOnlineUsers(PrintWriter toSender) {
        final int SIZE_WHEN_ALONE_IN_SERVER = 1;
        if (ChatServer.userList.size() == SIZE_WHEN_ALONE_IN_SERVER) {
            toSender.println("No online users");
            return;
        }
        for (Map.Entry<String, Socket> entry : ChatServer.userList.entrySet()) {
            String username = entry.getKey();
            if(username.equals(this.username)) {
                continue;
            }
            toSender.println("Username: " + entry.getKey());
        }
    }

    private void disconnectFromServer() {
        ChatServer.disconnectUser(username);
    }
}
