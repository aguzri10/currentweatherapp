package io.github.aguzri10.currentweather.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.aguzri10.currentweather.R;
import io.github.aguzri10.currentweather.model.ResponseModel;
import io.github.aguzri10.currentweather.model.Weather;

public class WeatherCityAdapter extends RecyclerView.Adapter<WeatherCityAdapter.ViewHolder> {

    private static final String URL_IMAGE = "http://openweathermap.org/img/wn/";
    private static final String URL_IMAGE_EX = "@2x.png";
    private Context context;
    private ResponseModel responseModel;
    private List<Weather> weathers;
    private ItemClickListener itemClickListener;

    public WeatherCityAdapter(Context context, ResponseModel responseModel, List<Weather> weathers, ItemClickListener itemClickListener) {
        this.context = context;
        this.responseModel = responseModel;
        this.weathers = weathers;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.city_list, viewGroup, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Weather weather = weathers.get(i);

        String image = weather.getIcon();
        Picasso.get()
                .load(URL_IMAGE + image + URL_IMAGE_EX)
                .into(viewHolder.ivIcon);

        viewHolder.tvName.setText(responseModel.getName() + ",");
        viewHolder.tvCountry.setText(responseModel.getSys().getCountry());
        viewHolder.tvWeatherDesc.setText(weather.getDescription());
        viewHolder.tvMainTemp.setText(String.valueOf(responseModel.getMain().getTemp()) + " \u2103");

        double temp_min = responseModel.getMain().getTempMin();
        double temp_max = responseModel.getMain().getTempMax();
        double wind_speed = responseModel.getWind().getSpeed();
        int clouds = responseModel.getClouds().getAll();
        int pressure = responseModel.getMain().getPressure();

        viewHolder.tvMainDetail.setText("Temperature form " + temp_min + " to " +
                temp_max + " \u2103" + ", wind " + wind_speed + " m/s." + " Clouds " + clouds + "%," +
                pressure + "hpa");
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvName, tvCountry, tvWeatherDesc, tvMainTemp, tvMainDetail;
        ItemClickListener itemClickListener;
        CardView cvItem;
        ImageView ivIcon;

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);

            ivIcon = view.findViewById(R.id.iv_icon);
            tvName = view.findViewById(R.id.tv_name);
            tvCountry = view.findViewById(R.id.tv_country);
            tvWeatherDesc = view.findViewById(R.id.tv_waether_desc);
            tvMainTemp = view.findViewById(R.id.tv_main_temp);
            tvMainDetail = view.findViewById(R.id.tv_main_detail);
            cvItem = view.findViewById(R.id.cv_item);

            this.itemClickListener = itemClickListener;
            cvItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        public void onClick(View view, int i);
    }
}
