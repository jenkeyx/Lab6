package App;

import java.util.Random;

public class Phone {
    static final String Password = "123";
    static boolean access;

    class App extends Weather {

        void weather_info()throws AppException {
            Random rand = new Random();
            double Probability = rand.nextDouble();
            try {
                if (access == true) {
                    if (Probability > 0.4) {
                        System.out.println("Погода на сегодня: " + String.valueOf(Weather.current_weather));
                    } else {
                        throw new AppException();
                    }
                } else {
                    throw new AppException();
                }
            }
            catch (AppException e){
                System.err.println("Произошла ошибка, приложение сломалось, попробуйте позже зайти");
            }
        }
    }
}
