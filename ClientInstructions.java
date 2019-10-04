import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientInstructions {
    private String host;
    private int port;
    private SocketChannel channel;
    private boolean isConnected;
    ClientInstructions(String host, int port, SocketChannel channel){
        this.host = host;
        this.port = port;
        this.channel = channel;
        isConnected = true;
    }
    void sendCommand(Command command){
        try {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10000);
        new ObjectOutputStream(byteArrayOutputStream).writeObject(command);
            ByteBuffer byteBuffer = ByteBuffer.allocate(byteArrayOutputStream.size());
            byteBuffer.put(byteArrayOutputStream.toByteArray());
            byteBuffer.flip();
            channel.write(byteBuffer);
        }
        catch (IOException ignore){
        }
    }
    Response reciveResponse(){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(10000);
            channel.read(buffer);
            Response resp = (Response) new ObjectInputStream(new ByteArrayInputStream(buffer.array())).readObject();
            return resp;
        }catch (IOException | ClassNotFoundException e){
            return Response.createResponse("");
        }

    }
}
