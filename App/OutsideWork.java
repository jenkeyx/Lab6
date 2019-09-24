package App;

import App.Weather;

interface OutsideWork {
   public enum TypeOfWalk {SLOWLY,FAST,RUSING}
    void put_on_cloths(Weather.TypeOfWeather type);
    void clean_up(Weather.TypeOfWeather type);
}
