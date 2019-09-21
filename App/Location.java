package App;

import javax.xml.bind.annotation.XmlType;
import java.util.Objects;
@XmlType
public class Location {
    public Location(){setX(0);
        setY(0);
        setZ(0);}
    public Location(Integer x, Integer y, Integer z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "App.Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x &&
                y == location.y &&
                z == location.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    int x,y,z;
}

