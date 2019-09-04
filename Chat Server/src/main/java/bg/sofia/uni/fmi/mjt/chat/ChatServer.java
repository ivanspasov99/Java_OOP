package bg.sofia.uni.fmi.mjt.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {
    private static final int SERVER_PORT = 8080;

    static Map<String, Socket>  userList = new HashMap<>();

    public static void main(String[] arg) {
        try {
            // what is the host for this server????
            try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
                System.out.println("Server is running on: " + SERVER_PORT);

                Socket socket;
                while (true) {
                    socket = serverSocket.accept();
                    System.out.println("Client has connected:" + socket.getInetAddress());

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    // assuming input is correct
                    // when user names are equal???
                    String username = bufferedReader.readLine();
                    userList.put(username, socket);
                    System.out.println(username + " connected");

                    new Thread(new ClientMessageHandler(username, socket)).start();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static Socket getUserSocket(String username) {
        return userList.get(username);
    }
    static void disconnectUser(String username) {
        userList.remove(username);
    }
    static Map<String, Socket> getUsers() {
        return userList;
    }

}
