package io.github.aguzri10.currentweather.view;

import java.util.List;

import io.github.aguzri10.currentweather.model.ResponseModel;
import io.github.aguzri10.currentweather.model.Weather;

public interface WeatherView {

    void onShowProgress();
    void onHideProgress();
    void onGetResults(ResponseModel responseModel, List<Weather> weathers);
    void onErrorResults(String message);
}
