import App.App;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;


public class Server extends Thread {
    private static Socket socket;
    private static int clientNumber;
    private static File xmlFile;



    @Override
    public void run(){
        try {
            System.out.println("Клиент " + clientNumber + " подключился");
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            ServerInstructions serverInstructions = new ServerInstructions(dis, dos);

            serverInstructions.sendResponse(Response.createResponse("Соединение с сервером установлено успешно"));
            System.out.println("Клиент подключился к серверу");
            while (true){
                String cmdString = serverInstructions.receiveCommand().getCommandStr();
                System.out.println(cmdString);
                if (cmdString == null || cmdString.equals("quit")){
                    disconnectClient(dos);
                    break;
                }else {
                    App app = new App(xmlFile,cmdString);
                    app.parseXML();
                    String appResponse = app.cmdResponding(app.objectBuilder(cmdString));
                    System.out.println(appResponse);
                    serverInstructions.sendResponse(Response.createResponse(appResponse));
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    void setSocket(Socket socket, int clientNumber, File xmlFile) {
        Server.socket = socket;
        Server.clientNumber = clientNumber;
        Server.xmlFile = xmlFile;
        start();
    }
    private void disconnectClient(DataOutputStream dos) {
        System.out.println("Клиент " + clientNumber + " отсоединился");
        new ServerInstructions(null,dos).sendResponse(Response.createResponse("Клиент отсоединился"));
        this.interrupt();
    }
}
