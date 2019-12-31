package io.github.aguzri10.currentweather.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.aguzri10.currentweather.R;
import io.github.aguzri10.currentweather.adapter.WeatherCityAdapter;
import io.github.aguzri10.currentweather.model.ResponseModel;
import io.github.aguzri10.currentweather.model.Weather;
import io.github.aguzri10.currentweather.presenter.WeatherPresenter;
import io.github.aguzri10.currentweather.view.WeatherView;

public class SearchActivity extends AppCompatActivity implements WeatherView {

    private ProgressBar progressBar;
    private static final String URL_IMAGE = "http://openweathermap.org/img/wn/";
    private static final String URL_IMAGE_EX = "@2x.png";
    private static final String appid = "b6907d289e10d714a6e88b30761fae22";
    private ResponseModel responseModels;
    private List<Weather> weather;
    private WeatherCityAdapter adapter;
    private WeatherCityAdapter .ItemClickListener itemClickListener;
    private WeatherPresenter presenter;
    private RecyclerView recyclerView;
    private ImageView ivIcon;
    private TextView tvName, tvWeatherMainDesc, tvMainTemp, tvMainTempMin, tvMainTempMax,
            tvWindSpeed, tvHumidity, tvPressure, tvCoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setElevation(0);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler_view);

        Intent intent = getIntent();
        String isiSearch = intent.getStringExtra("resultSearch");
        String city = isiSearch + ",id";

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new WeatherPresenter(this);
        presenter.getWeatherByCity(city, appid);

        itemClickListener = (new WeatherCityAdapter.ItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                String icon = weather.get(i).getIcon();
                String name = responseModels.getName();
                String country = responseModels.getSys().getCountry();
                String weather_main = weather.get(i).getMain();
                double main_temp = responseModels.getMain().getTemp();
                double main_temp_min = responseModels.getMain().getTempMin();
                double main_temp_max = responseModels.getMain().getTempMax();
                double wind_speed = responseModels.getWind().getSpeed();
                double lat = responseModels.getCoord().getLat();
                double lon = responseModels.getCoord().getLon();
                String pressure = String.valueOf(responseModels.getMain().getPressure());
                String humidity = String.valueOf(responseModels.getMain().getHumidity());

                final Dialog dialog = new Dialog(SearchActivity.this, R.style.CustomDialog);
                dialog.setTitle("Detail Weather");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.weather_detail);
                ivIcon = dialog.findViewById(R.id.iv_icon);
                tvName = dialog.findViewById(R.id.tv_name);
                tvWeatherMainDesc = dialog.findViewById(R.id.tv_weather_desc);
                tvMainTemp = dialog.findViewById(R.id.tv_main_temp);
                tvMainTempMin = dialog.findViewById(R.id.tv_main_temp_min);
                tvMainTempMax = dialog.findViewById(R.id.tv_main_temp_max);
                tvWindSpeed = dialog.findViewById(R.id.tv_wind_speed);
                tvHumidity = dialog.findViewById(R.id.tv_humidity);
                tvPressure = dialog.findViewById(R.id.tv_pressure);
                tvCoord = dialog.findViewById(R.id.tv_coord);
                Button btnOke = dialog.findViewById(R.id.btn_ok);
                Picasso.get()
                        .load(URL_IMAGE + icon + URL_IMAGE_EX)
                        .into(ivIcon);
                tvName.setText(name + ", " + country );
                tvWeatherMainDesc.setText(weather_main);
                tvMainTemp.setText(main_temp + " \u2103");
                tvMainTempMin.setText("Temperature minimal " + main_temp_min + " \u2103");
                tvMainTempMax.setText("Temperature maksimal " + main_temp_max + " \u2103");
                tvWindSpeed.setText("Wind Speed " + wind_speed + " m/s");
                tvHumidity.setText("Humidity " + humidity + "%");
                tvPressure.setText("Pressure " + pressure + " hpa");
                tvCoord.setText("Coord " + "Latitude "+ lat + ", Longitude " + lon);
                btnOke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void onShowProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onGetResults(ResponseModel responseModel, List<Weather> weathers) {
        adapter = new WeatherCityAdapter(this, responseModel, weathers, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        responseModels = responseModel;
        weather = weathers;
    }

    @Override
    public void onErrorResults(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
