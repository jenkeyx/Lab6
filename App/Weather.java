package App;

public class Weather {
    static boolean is_slippery;
    static boolean is_cold;
    static boolean is_hot;
     public static TypeOfWeather current_weather;
    public  static void set_weather(TypeOfWeather type) {
        current_weather = type;
        switch (type){
            case RAINY:
                is_slippery = true;
                System.out.println("Шел дождь. На улице было сыро и мокро");
                break;
            case SNOWY:
                is_cold = true;
                is_slippery = true;
                System.out.println("Шел снег. На улице было холодно и скользко");
                break;
            case WINDY:
                is_cold = true;
                System.out.println("Дул сильный ветер. На улице было холодно");
                break;
            case SUNNY:
                is_hot = true;
                System.out.println("Жара-а-а. На улице ни облачка");
                break;
        }
    }

   static enum TypeOfWeather {RAINY, SNOWY, WINDY, SUNNY}

}