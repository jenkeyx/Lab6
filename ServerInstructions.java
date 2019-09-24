import java.io.*;

public class ServerInstructions {
    private DataInputStream dis;
    private DataOutputStream dos;

    ServerInstructions(DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
    }

    void sendResponse(Response response) {
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream(10000);
            ObjectOutputStream oos = new ObjectOutputStream(byteArray);
            oos.writeObject(response);
            oos.flush();
            dos.write(byteArray.toByteArray());
        } catch (IOException ignore) {}
    }
    Command receiveCommand() {
        try {
            return (Command) new ObjectInputStream(dis).readObject();
        } catch (Exception e) {
            Command cmd = new Command("Клиент отсоединился", null, null);
            System.exit(0);
            return cmd;
        }
    }
}
