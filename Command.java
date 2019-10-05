import App.Character;

import java.io.Serializable;

public class Command implements Serializable {
    private String commandStr;
    private Character character;
    private String fileContent;

    Command(String commandStr, Character character, String fileContent) {
        this.commandStr = commandStr;
        this.character = character;
        this.fileContent = fileContent;
    }

    public String getCommandStr() {
        return commandStr;
    }

    public String getFileContent() {
        return fileContent;
    }


    Command() {
    }
}
