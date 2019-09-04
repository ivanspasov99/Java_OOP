package bg.sofia.uni.fmi.mjt.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ClientIncomingMessages implements Runnable {
    Socket socket;

    public ClientIncomingMessages(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                if (socket.isClosed()) {
                    System.out.println("client socket is closed, stop waiting for server messages");
                    return;
                }
                String message = bufferedReader.readLine();
                if (message.equals("You have been disconnected")) {
                    System.out.println("Server confirmed disconnect");
                    // socket.close(); // second way to handle server response
                    return;
                }
                System.out.println(message);
            }
        } catch (SocketException se) {
            System.out.println("Socket closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
