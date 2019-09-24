package App;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

public class App {
    private static List<Character> obj_coll = new LinkedList<>();
    private static File xmlFile = new File("/Users/jenkeyx/Desktop/ITMO/Prog/src/file.xml");
    private static String commandLine = "";


    public App(File xmlFile, String commandLine) {
        App.commandLine = commandLine;
        App.xmlFile = xmlFile;
    }


    public Character objectBuilder(String commandLine) {
        Character json_obj = null;
        try {
            if (commandLine.contains("{")) {
                try {
                    json_obj = jsonToObj("{" + commandLine.split("\\{")[1] + "{" + commandLine.split("\\{")[2]);

                } catch (ArrayIndexOutOfBoundsException e) {
                    json_obj = jsonToObj("{" + commandLine.split("\\{")[1]);
                    if (json_obj != null) {
                        json_obj.setLocation(new Location(0, 0, 0));
                    }
                }
            }
            return json_obj;
        } catch (JsonSyntaxException | NullPointerException e) {
            return null;
        }
    } // вычленяет объект из ввода


    public String cmdResponding(Character json_obj) {
        String msg = "";

        switch (commandLine.split("\\{")[0].replaceAll(" ", "")) {
            case ("show"):
                msg = show();
                break;
            case ("info"):
                System.out.println("Программа принимает на вход объекты в формате json и сохраняет в переданный вами файл.\nПример: \n add{ \n{\"name\":\"misha\",\n\"health\":\"2\"}\nshow - показать текущее состояние коллекции\ninfo - перечень комманд\nremove{element} - удалить объект по значению\nadd{element} - добавить объект в коллекцию\nadd_if_max{element}- добавить наибольший по значению\nadd_if_min{element} - добавить наименьший по значению\nquit - завершение работы");
                break;
            case ("remove_greater"):
                msg = remove_greater(json_obj);
                break;
            case ("remove"):
                msg = remove(json_obj);
                break;
            case ("add"):
                msg = add(json_obj);
                break;
            case ("add_if_max"):
                msg = add_if_max(json_obj);
                break;
            case ("add_if_min"):
                msg = add_if_min(json_obj);
                break;
            case ("quit"):
                msg = "quit";
                break;
            case "import":
                msg = importXML(commandLine);
            default:
                msg = ("Вы ввели какую то кракозябру, полный перечень команд info");
        }
        return msg;
    } //действия исходя из переданной команды

    public void saveColl() {
        try {
            if (xmlFile.exists()) {
                try {
                    FileWriter fileWriter = new FileWriter(xmlFile, false);
                    fileWriter.append("<Characters>\n");
                    if (!obj_coll.isEmpty()) {
                        for (Character character : obj_coll) {
                            JAXBContext context = JAXBContext.newInstance(Character.class);
                            Marshaller marshaller = context.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                            marshaller.marshal(character, fileWriter);
                        }
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(xmlFile, true);
                    if (obj_coll.isEmpty()) {
                        fileOutputStream.write("<Characters>".getBytes());
                    }
                    fileOutputStream.write("\n</Characters>".getBytes());
                } catch (IOException ignore) {
                }
            }
        } catch (JAXBException e) {
            System.exit(0);
            System.err.println("Файл содержит ошибку");
        }
        obj_coll.clear();
    }

    public String parseXML() {
        String name;
        double health;
        boolean watching_you, ignore;
        int x, y, z;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            NodeList charactersElements = document.getDocumentElement().getElementsByTagName("character");

            for (int i = 0; i < charactersElements.getLength(); i++) {
                Node node = charactersElements.item(i);
                Element element = (Element) node;
                name = element.getElementsByTagName("name").item(0).getTextContent();
                health = Double.parseDouble(element.getElementsByTagName("health").item(0).getTextContent());
                watching_you = Boolean.parseBoolean(element.getElementsByTagName("watching_you").item(0).getTextContent());
                ignore = Boolean.parseBoolean(element.getElementsByTagName("ignore").item(0).getTextContent());

                NodeList locationList = element.getElementsByTagName("location").item(0).getChildNodes();
                Element coordinate = (Element) locationList;
                x = Integer.parseInt(coordinate.getElementsByTagName("x").item(0).getTextContent());
                y = Integer.parseInt(coordinate.getElementsByTagName("y").item(0).getTextContent());
                z = Integer.parseInt(coordinate.getElementsByTagName("z").item(0).getTextContent());
                obj_coll.add(new Character(name, health, watching_you, ignore, new Location(x, y, z)));
            }
            return "Файл прошел парсинг";
        } catch (Exception e) {
            return "Файл содержит данные с ошибкой в синтаксисе XML или к нему отсутствует доступ";
        }
    }
    public String parseXMLIO(String fileContent) {
        String name;
        double health;
        boolean watching_you, ignore;
        int x, y, z;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(fileContent.getBytes()));
            NodeList charactersElements = document.getDocumentElement().getElementsByTagName("character");

            for (int i = 0; i < charactersElements.getLength(); i++) {
                Node node = charactersElements.item(i);
                Element element = (Element) node;
                name = element.getElementsByTagName("name").item(0).getTextContent();
                health = Double.parseDouble(element.getElementsByTagName("health").item(0).getTextContent());
                watching_you = Boolean.parseBoolean(element.getElementsByTagName("watching_you").item(0).getTextContent());
                ignore = Boolean.parseBoolean(element.getElementsByTagName("ignore").item(0).getTextContent());

                NodeList locationList = element.getElementsByTagName("location").item(0).getChildNodes();
                Element coordinate = (Element) locationList;
                x = Integer.parseInt(coordinate.getElementsByTagName("x").item(0).getTextContent());
                y = Integer.parseInt(coordinate.getElementsByTagName("y").item(0).getTextContent());
                z = Integer.parseInt(coordinate.getElementsByTagName("z").item(0).getTextContent());
                obj_coll.add(new Character(name, health, watching_you, ignore, new Location(x, y, z)));
            }
            return "Файл прошел парсинг";
        } catch (Exception e) {
            return "указанный файл содержит ошибку";
        }
    }



    private static Character jsonToObj(String json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, Character.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    private static String show() {
        if (!obj_coll.isEmpty()) {
            return obj_coll.toString();
        } else {
            return "Коллекция пуста";
        }

    }


    private static String remove_greater(Character json_obj) {
        int size = obj_coll.size();
        if (!obj_coll.isEmpty()) {
            for (Character character : obj_coll) {
                if (json_obj.compareTo(character) < 0) {
                    obj_coll.remove(character);
                }
            }
            if (obj_coll.size() == size) {
                return "Таких объектов нет ";
            } else {
                return "Все объекты с меньшим, чем у " + json_obj.getName() + " здоровьем удалены";
            }

        } else {
            return "Нечего удалять. Коллекция пуста";
        }
    }

    private static String remove(Character json_obj) {
        if (obj_coll.contains(json_obj)) {
            obj_coll.remove(json_obj);
            if (obj_coll.contains(json_obj)) {
                return "Коллекция содержит еще один такой же объект";
            }
            return "объект " + json_obj.getName() + " успешно удален из коллекции";
        } else {
            return "коллекция не содержит такой объект";
        }

    }


    private static String add(Character json_obj) {
        if (json_obj != null) {
            if (json_obj.getName() != null) {
                obj_coll.add(json_obj);
                return "Объект " + json_obj.getName() + " успешно добавлен в коллекцию";
            } else {
                return "Безымянный объект в коллекцию добавить нельзя";
            }
        } else {
            return "Объект задан неправильно";
        }
    }


    private static String add_if_min(Character json_obj) {
        if (!obj_coll.isEmpty()) {
            if (json_obj.getName() != null) {
                Character min = Collections.min(obj_coll);
                if (json_obj.compareTo(min) == 0) {
                    return "Этот объект содержит здоровье, равное объекту с самым малеьнким здоровьем в коллекции";
                } else if (json_obj.compareTo(min) < 0) {
                    obj_coll.add(json_obj);
                    return "Объект " + json_obj.getName() + " успешно добавлен в коллекцию";
                } else if (json_obj.compareTo(min) > 0) {
                    return "У этого объекта не самое маленькое здоровье";
                }
            } else {
                return "Безымянный объект сравнивать нельзя";
            }
            return "ok";
        } else {
            obj_coll.add(json_obj);
            return "Объект " + json_obj.getName() + " успешно добавлен в коллекцию";
        }

    }


    private static String add_if_max(Character json_obj) {
        if (!obj_coll.isEmpty()) {
            Character max = Collections.max(obj_coll);
            try {
                if (json_obj.compareTo(max) == 0) {
                    return "Этот объект содержит здоровье, равное объекту с самым большим здоровьем в коллекции";
                } else if (json_obj.compareTo(max) < 0) {
                    return "У этого объекта не самое большое здоровье";
                } else if (json_obj.compareTo(max) > 0) {
                    obj_coll.add(json_obj);
                    return "Объект " + json_obj.getName() + " успешно добавлен в коллекцию";
                } else {
                    obj_coll.add(json_obj);
                    return "Объект " + json_obj.getName() + " успешно добавлен в коллекцию";
                }
            } catch (NullPointerException e) {
                return "Объект задан неправильно";
            }

        }
        return "ok";
    }

    private static String importXML(String fileContent) {
        try {
            FileWriter writer = new FileWriter(xmlFile, false);
            writer.write(fileContent);
            return "Запись в файл произведена успешно";
        } catch (IOException e) {
            return e.getMessage();
        }

    }
}

