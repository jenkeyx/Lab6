package App;

import App.Character;

import java.util.Random;

public class Human extends Character {
    String ShortName;

    Human(String name, double health, boolean watching_you, boolean ignore, Location location) {
        super(name, health, watching_you, ignore, location);
    }

    void read_something(String Text) {
        System.out.println(getName() + "read some" + Text);
    }

    Random rand = new Random();
    double Probability = rand.nextDouble();
    final double default_damage = 0.5;
    final double bonus_damage = default_damage + Probability;

    protected void shoot(Character c) {
        String name = getName();
        System.out.println(name + " выстрелил в " + c.getName());
        if (Probability > 0.6) {
            if (Probability > 0.8) {
                c.setHealth(c.getHealth() - bonus_damage);
                System.out.println("Сегодня удачный день!," + name + " попал по " + c.getName() + " и убил их");
            } else {
                c.setHealth(c.getHealth() - default_damage);
                System.out.println(name + " попал в " + c.getName() + ", теперь у них осталось " + String.valueOf(c.getHealth()));
            }
        } else {
            System.out.println(name + " промазал!");
        }
    }
}