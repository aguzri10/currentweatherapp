package io.github.aguzri10.currentweather.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.github.aguzri10.currentweather.R;
import io.github.aguzri10.currentweather.adapter.WeatherCityAdapter;
import io.github.aguzri10.currentweather.model.ResponseModel;
import io.github.aguzri10.currentweather.model.Weather;
import io.github.aguzri10.currentweather.presenter.WeatherPresenter;
import io.github.aguzri10.currentweather.view.WeatherView;

public class SearchActivity extends AppCompatActivity implements WeatherView {

    private ProgressBar progressBar;
    private static final String appid = "b6907d289e10d714a6e88b30761fae22";
    private ResponseModel responseModels;
    private List<Weather> weather;
    private WeatherCityAdapter adapter;
    private WeatherCityAdapter .ItemClickListener itemClickListener;
    private WeatherPresenter presenter;
    private RecyclerView recyclerView;

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
                String name = responseModels.getName();
                String country = responseModels.getSys().getCountry();
                String weather_desc = weather.get(i).getDescription();
                String main_temp = String.valueOf(responseModels.getMain().getTemp());
                String main_temp_min = String.valueOf(responseModels.getMain().getTempMin());
                String main_temp_max = String.valueOf(responseModels.getMain().getTempMax());
                String wind_speed = String.valueOf(responseModels.getWind().getSpeed());
                String clouds = String.valueOf(responseModels.getClouds().getAll());
                String pressure = String.valueOf(responseModels.getMain().getPressure());

                Intent intentToDetail = new Intent(SearchActivity.this, DetailActivity.class);
                intentToDetail.putExtra("name", name);
                intentToDetail.putExtra("country", country);
                intentToDetail.putExtra("weather_desc", weather_desc);
                intentToDetail.putExtra("main_temp", main_temp);
                intentToDetail.putExtra("main_temp_min", main_temp_min);
                intentToDetail.putExtra("main_temp_max", main_temp_max);
                intentToDetail.putExtra("wind_speed", wind_speed);
                intentToDetail.putExtra("clouds", clouds);
                intentToDetail.putExtra("pressure", pressure);
                startActivity(intentToDetail);
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
