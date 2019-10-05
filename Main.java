

import java.io.File;
        import java.io.IOException;
        import java.net.InetAddress;
        import java.net.ServerSocket;
        import java.net.Socket;

public class Main {
    private static File xmlFile = new File("file.xml");
    public static void main(String[] args) {

        System.out.println("Сервер сервер запущен...");
        try {
            int clientNumber = 0;
            InetAddress inetAddress = InetAddress.getByName("localhost");
            ServerSocket serverSocket = new ServerSocket(1135, 0, inetAddress);
            while (true) {
                Socket socket = serverSocket.accept();
                new Server().setSocket(socket, clientNumber++, xmlFile);
            }
        } catch (IOException e) {
            System.out.println("Хост не найден");
        }
    }

}
