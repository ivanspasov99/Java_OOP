package Bytes;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ByteProcess {
    // PROTOCOL: <size_of_message> <message>
    private static final int MAX_INT_BYTES = 4;
    ByteBuffer byteBuffer;

    public String readBytesFromServer(String clientCommand, SocketChannel channel) {
        String message = "";
        byteBuffer = ByteBuffer.allocate(clientCommand.length());
        byteBuffer.clear();
        try {

            writeToServer(clientCommand, channel);
            byteBuffer.clear();

            byteBuffer = ByteBuffer.allocate(getSizeOfMessage(clientCommand, channel));
            byteBuffer.clear();

            message = getMessage(clientCommand, channel);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    private void writeToServer(String clientCommand, SocketChannel channel) throws IOException {
        byteBuffer.put(clientCommand.getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
    }

    private int getSizeOfMessage(String clientCommand, SocketChannel channel) throws IOException {
        byteBuffer = ByteBuffer.allocate(MAX_INT_BYTES);

        channel.read(byteBuffer);
        byteBuffer.flip();
        return byteBuffer.getInt();
    }

    private String getMessage(String clientCommand, SocketChannel channel) throws IOException {
        channel.read(byteBuffer);
        byteBuffer.flip();
        return StandardCharsets.UTF_8.decode(byteBuffer).toString();
    }
}
