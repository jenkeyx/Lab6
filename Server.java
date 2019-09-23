import App.App;

import java.io.*;
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
                Command cmd = serverInstructions.receiveCommand();
                System.out.println(cmd.getCommandStr());
                if (cmd.getCommandStr() == null || cmd.getCommandStr().equals("quit")){
                    disconnectClient(dos);
                    break;
                }else {
                    if (cmd.getCommandStr().equals("import")){
                        //todo содержимое файла приходит, но не записывается
                        FileWriter writer = new FileWriter("file.xml");
                        writer.write(cmd.getFileContent());
                        serverInstructions.sendResponse(Response.createResponse("Файл успешно импортирован"));
                    }else {
                        App app = new App(xmlFile, cmd.getCommandStr());
                        app.parseXML();
                        String appResponse = app.cmdResponding(app.objectBuilder(cmd.getCommandStr()));
                        System.out.println(appResponse);
                        serverInstructions.sendResponse(Response.createResponse(appResponse));
                    }
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
