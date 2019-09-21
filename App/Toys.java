package App;

import App.Character;
import App.Location;
import App.OutsideActivity;
import App.SingerException;

public class Toys extends Character implements OutsideWork, OutsideActivity {
    Toys(String name, double health, boolean watching_you, boolean ignore, Location location) {
        super(name, health, watching_you, ignore, location);
    }

    @Override
    public final void put_on_cloths(Weather.TypeOfWeather type) {
        switch (type) {
            case RAINY:
                System.out.println("Сегодня на улице снег и " + getName() + " оделся потеплее");
                break;
            case SNOWY:
                System.out.println("Сеодня на улице жарко и " + getName() + " достал свои самые лучшие шорты!");
                break;
            case WINDY:
                System.out.println("Сегодня на улице плохая и " + getName() + " надел ветровку");
                break;
            case SUNNY:
                System.out.println("Сегодня на улице плохая и " + getName() + " надел ветровку");
                break;
        }

    }

    @Override
    public void clean_up(Weather.TypeOfWeather type) {
        if (type == Weather.TypeOfWeather.SNOWY) {
            System.out.println(getName() + " убирал снег");
        } else {
            System.out.println("Сегодня " + getName() + " не убирал снег, потому что его нет");
        }
    }

    void in_thoughts() {
        System.out.println(getName() + " так сильно думал о том, чтобы не поскользнуться, что не слышал никого");
        setWatching_you(false);
        setIgnore(true);
    }

    @Override
    public void walk_around(OutsideWork.TypeOfWalk type) {
        if (isIgnore()) {
            switch (type) {
                case SLOWLY:
                    System.out.println(getName() + "медленно гулял по парку");
                    break;
                case FAST:
                    System.out.println(getName() + "бежал по парку");
                    break;
                case RUSING:
                    System.out.println(getName() + "пробежал парк");
                    break;
            }
        } else {
            System.out.println(getName() + " гулял себе по парку, но тут же поймал на себе чей-то взгляд");
        }
    }

    void sing_the_song() throws SingerException {
        try {
            if (getName() == "Винни"){
            System.out.println("Трам пам пам");}
            else{
                throw new SingerException();
            }
        } catch (SingerException e) {
            e.printStackTrace();
        }

    }
}
