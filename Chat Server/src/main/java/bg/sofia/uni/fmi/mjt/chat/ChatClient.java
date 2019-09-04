package bg.sofia.uni.fmi.mjt.chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private final static int COMMAND_POSITION = 0;
    private final static int HOST_POSITION = 1;
    private final static int PORT_POSITION = 2;
    private final static int USERNAME_POSITION = 3;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    private boolean isConnected = false;

    Socket socket;
    Thread incomingMessagesThread;

    public static void main(String[] args) {
        try {
            new ChatClient().runClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runClient() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            String command = line.split("\\s+")[COMMAND_POSITION];
            if (command.equals("connect")) {
                // check if it is connected already
                connect(line);
                ClientIncomingMessages cMess = new ClientIncomingMessages(socket);
                incomingMessagesThread = new Thread(cMess);
                incomingMessagesThread.start();
            } else if (isConnected) {
                writeToServer(line);
                if(command.equals("disconnect")) {
                    try {
                        incomingMessagesThread.join(); // waiting for response then close
                        socket.close();
                        return;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void writeToServer(String line) {
        printWriter.println(line);
    }

    private void connect(String line) {
        try {
            String host = getCommandStringValue(HOST_POSITION, line);
            int port = Integer.parseInt(getCommandStringValue(PORT_POSITION, line));
            String username = getCommandStringValue(USERNAME_POSITION, line);

            socket = new Socket(host, port);

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(username);
            isConnected = true;

            System.out.println("Connected to the server");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String getCommandStringValue(int pos, String line) {
        return line.split("\\s+")[pos];
    }

}
