
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Client {
    private static String commandLine = "";
    private static ByteBuffer bf = ByteBuffer.allocate(10000);

    public static void main(String[] args) {
        File file = new File("/Users/jenkeyx/Desktop/ITMO/Prog/file.xml");
        SocketChannel sc;
        while (true) {
            try {
                sc = SocketChannel.open();
                sc.connect(new InetSocketAddress("localhost", 1131));
                break;
            } catch (IOException | NullPointerException e){
                System.err.println("Не удалось подключиться к серверу, следующая попытка через 3 с.");
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException | IllegalMonitorStateException ignore){
            }
        } //Пытаемся подключиться к серверу
        ClientInstructions clientInstructions = new ClientInstructions("localhost",1131,sc);
        while (true) {
            commandLine = "";
            Response callback = clientInstructions.reciveResponse();
            System.out.println(callback.getMessage());
            if ((callback.getMessage()).equals("Клиент отсоединился")){
                clientInstructions.sendCommand(new Command());
                System.exit(0);
            }
            inputParser();//После -- считываем комманды пользователя
            if (commandLine.equals("import")){
                clientInstructions.sendCommand(new Command(commandLine,null,null));
            }
            clientInstructions.sendCommand(new Command(commandLine,null,null));
        }
    }
    private static void inputParser() {
        String command;
        Scanner scanner = new Scanner(System.in);
        int a = 0;
        int b = 0;
        int count;
        System.out.println("»Введите команду»");
        do {
            try {
                command = scanner.nextLine();
                commandLine = commandLine.concat(command);
                if (commandLine.contains("{")) {
                    count = command.length() - command.replaceAll("\\{", "").length();
                    commandLine = commandLine.replaceAll("\\{", "[");
                    a += count;
                }
                if (commandLine.contains("}")) {
                    count = command.length() - command.replaceAll("}", "").length();
                    commandLine = commandLine.replaceAll("}", "]");
                    b += count;
                }
            } catch (NoSuchElementException e) {
                System.exit(0);
            }
        } while (a > b);
        commandLine = commandLine.replace("[", "{");
        commandLine = commandLine.replace("]", "}");
        bf.put(commandLine.getBytes());
    }

}

