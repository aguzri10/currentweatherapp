package io.github.aguzri10.currentweather.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.github.aguzri10.currentweather.R;
import io.github.aguzri10.currentweather.model.ResponseModel;
import io.github.aguzri10.currentweather.model.Weather;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private static final String URL_IMAGE = "http://openweathermap.org/img/wn/";
    private static final String URL_IMAGE_EX = "@2x.png";
    private Context context;
    private ResponseModel responseModels;
    private List<Weather> weathers;
    private Calendar calendar;

    public WeatherAdapter(Context context, ResponseModel responseModels, List<Weather> weathers) {
        this.context = context;
        this.responseModels = responseModels;
        this.weathers = weathers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_main, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        char temperature = 0x00B0;
        String celcius = " \u2103";

        Weather weather = weathers.get(i);

        String image = weather.getIcon();
        Picasso.get()
                .load(URL_IMAGE + image + URL_IMAGE_EX)
                .into(viewHolder.ivIcon);

        viewHolder.tvLocation.setText(responseModels.getName());

        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
        String currentDate = simpleDateFormat.format(calendar.getTime());
        String currentTime = simpleDateFormat2.format(calendar.getTime());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayName = simpleDateFormat1.format(date);
        viewHolder.tvDate.setText("" + dayName + ", " + currentDate + " at " + currentTime);

        viewHolder.tvWeatherMain.setText(weather.getMain());
        viewHolder.tvTemp.setText(String.valueOf(responseModels.getMain().getTemp()) + temperature);

        viewHolder.tvFeelsLike.setText(String.valueOf(responseModels.getMain().getFeelsLike()) + celcius);
        viewHolder.tvWindSpeed.setText(String.valueOf(responseModels.getWind().getSpeed()) + " m/s");
        viewHolder.tvHumidity.setText(String.valueOf(responseModels.getMain().getHumidity() + "%"));
        viewHolder.tvPressure.setText(String.valueOf(responseModels.getMain().getPressure() + " in"));

        double lat = responseModels.getCoord().getLat();
        double lon = responseModels.getCoord().getLon();
        viewHolder.tvCoord.setText(lat + ", " + lon);
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLocation, tvDate, tvWeatherMain, tvTemp, tvFeelsLike, tvWindSpeed, tvHumidity, tvPressure, tvCoord;
        ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvLocation = itemView.findViewById(R.id.tv_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvWeatherMain = itemView.findViewById(R.id.tv_weather_main);
            tvTemp = itemView.findViewById(R.id.tv_temp);
            tvFeelsLike = itemView.findViewById(R.id.tv_feels_like);
            tvWindSpeed = itemView.findViewById(R.id.tv_wind_speed);
            tvHumidity = itemView.findViewById(R.id.tv_humidity);
            tvPressure = itemView.findViewById(R.id.tv_pressure);
            tvCoord = itemView.findViewById(R.id.tv_coord);
        }
    }
}
