package App;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlType(name = "character")
@XmlRootElement
public class Character implements Comparable<Character> {
    Character(String name, double health, boolean watching_you, boolean ignore, Location location) {
        this.name = name;
        this.health = health;
        this.watching_you = watching_you;
        this.ignore = ignore;
        this.location = location;
    }

    public Character() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    double getHealth() {
        return health;
    }

    void setHealth(double health) {
        this.health = health;
    }

    void setWatching_you(boolean watching_you) {
        this.watching_you = watching_you;
    }
    boolean isWatching_you(){return watching_you;}

    boolean isIgnore() {
        return ignore;
    }

    void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    private String name;

    @XmlElement(name = "health")
    private double health;
    @XmlElement(name = "watching_you")
    private boolean watching_you;
    @XmlElement(name = "ignore")
    private boolean ignore;
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        String str_obj = "";
        str_obj += "name: " + this.name + "\n";
        str_obj += "health: " + this.health + "\n";
        str_obj += "watching_you: " + this.watching_you + "\n";
        str_obj += "ignore: " + this.ignore + "\n";
        if (location == null) {
            location = new Location();
        }
        str_obj += "App.Location: " + location.toString();
        return str_obj;
    }

    @Override
    public int compareTo(Character c) {
        return (int) (this.health - c.health);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character)) return false;
        Character character = (Character) o;
        return Double.compare(character.getHealth(), getHealth()) == 0 &&
                watching_you == character.watching_you &&
                isIgnore() == character.isIgnore() &&
                Objects.equals(getName(), character.getName()) &&
                Objects.equals(getLocation(), character.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getHealth(), isWatching_you(), isIgnore(), getLocation());
    }
}