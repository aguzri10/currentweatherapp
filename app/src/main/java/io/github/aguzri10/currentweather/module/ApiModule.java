package io.github.aguzri10.currentweather.module;

import java.util.List;

import io.github.aguzri10.currentweather.model.ResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiModule {

    @GET("weather")
    Call<ResponseModel> getWeatherByCoord(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String appid
    );

    @GET("weather")
    Call<ResponseModel> getWeatherbyCity(
            @Query("q") String city,
            @Query("appid") String appid
    );
}
