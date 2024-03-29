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
            int i = 0;

            while (true){
                Command cmd = serverInstructions.receiveCommand();
                App app = new App(xmlFile, cmd.getCommandStr());
                if (i == 0) {
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        System.out.println("damn rip lol");
                        app.saveColl();
                        serverInstructions.sendResponse(Response.createResponse("Сервер недоступен"));
                    }));
                    i++;
                }

                System.out.println(cmd.getCommandStr());
                if (cmd.getCommandStr() == null || cmd.getCommandStr().equals("quit")){
                    disconnectClient(dos);
                    app.saveColl();
                    serverInstructions.sendResponse(Response.createResponse("Коллекция успешно сохранена"));
                    break;
                }else {
                    switch (cmd.getCommandStr()) {
                        case "import":
                            serverInstructions.sendResponse(Response.createResponse(app.parseXMLIO(cmd.getFileContent())));
                            break;
                        case "save":
                            serverInstructions.sendResponse(Response.createResponse(app.saveColl()));
                            break;
                        case "load":
                            serverInstructions.sendResponse(Response.createResponse(app.parseXML()));
                            break;
                        default:
                            String appResponse = app.cmdResponding(app.objectBuilder(cmd.getCommandStr()));
                            System.out.println(appResponse);
                            serverInstructions.sendResponse(Response.createResponse(appResponse));
                            break;
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
