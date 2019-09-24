import App.Character;

import java.io.Serializable;

public class Command implements Serializable {
    private String commandStr;
    private Character character;
    private String fileContent;
    Command(String commandStr, Character character, String fileContent){
        this.commandStr = commandStr;
        this.character = character;
        this.fileContent = fileContent;
    }

    public String getCommandStr() {
        return commandStr;
    }

    public void setCommandStr(String commandStr) {
        this.commandStr = commandStr;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    Command() {
    }
}
