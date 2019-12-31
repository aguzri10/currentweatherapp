package io.github.aguzri10.currentweather.presenter;

import io.github.aguzri10.currentweather.model.ResponseModel;
import io.github.aguzri10.currentweather.module.ApiModule;
import io.github.aguzri10.currentweather.module.AppModule;
import io.github.aguzri10.currentweather.view.WeatherView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherPresenter {

    private WeatherView view;

    public WeatherPresenter(WeatherView view) {
        this.view = view;
    }

    public void getWeatherByCoord(double lat, double lon, String appid) {
        view.onShowProgress();

        ApiModule apiModule = AppModule.getWeather().create(ApiModule.class);
        Call<ResponseModel> call = apiModule.getWeatherByCoord(lat, lon, appid);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                view.onHideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResults(response.body(), response.body().getWeather());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                view.onHideProgress();
                view.onErrorResults(t.getLocalizedMessage());
            }
        });
    }

    public void getWeatherByCity(String city, String appid) {
        view.onShowProgress();

        ApiModule apiModule = AppModule.getWeather().create(ApiModule.class);
        Call<ResponseModel> call = apiModule.getWeatherbyCity(city, appid);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                view.onHideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResults(response.body(), response.body().getWeather());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                view.onHideProgress();
                view.onErrorResults(t.getLocalizedMessage());
            }
        });
    }
}
