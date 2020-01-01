package io.github.aguzri10.currentweather.module;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppModule {

    private static final String BASE_URL = "https://openweathermap.org/data/2.5/";
    public static final String URL_IMAGE = "http://openweathermap.org/img/wn/";
    public static final String URL_IMAGE_EX = "@2x.png";
    public static final String appid = "<YOUR_APPID_OPENWEATHERMAP>";
    private static Retrofit retrofit = null;

    public AppModule(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public static Retrofit getWeather() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
