package com.upt.weatherBeacon.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.model.HourlyWeatherData;

import java.time.LocalTime;
import java.util.List;

public class ForecastsHourlyAdapter extends ArrayAdapter<HourlyWeatherData> {
    private Context context;
    private List<HourlyWeatherData> dataList;


    public ForecastsHourlyAdapter(Context context, List<HourlyWeatherData> dataList) {
        super(context, 0, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @SuppressLint("NewApi")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate the list item layout if convertView is null
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.forecast_item, parent, false);
        }

        // Get the data item for this position
        HourlyWeatherData currentItem = dataList.get(position);

        // Bind data to the views in the list item layout
        TextView date = convertView.findViewById(R.id.date);
        TextView time = convertView.findViewById(R.id.time);
        TextView temperature = convertView.findViewById(R.id.tempMax);
        TextView temperatureMin = convertView.findViewById(R.id.tempMin);
        temperatureMin.setText("       ");
        ImageView icon = convertView.findViewById(R.id.forecastIcon);
        ImageView liveIcon = convertView.findViewById(R.id.liveIcon);
        liveIcon.setVisibility(View.GONE);

        LocalTime currentTime = null;
        currentTime = LocalTime.now();
        // Extract the hour from the current time
        int currentHour = currentTime.getHour();

        if (position == currentHour) {
            // Convert hexadecimal color code to integer
            int color = Color.parseColor("#fabf14");
            liveIcon.setVisibility(View.VISIBLE);

            // Set background color of the view
            convertView.setBackgroundColor(color);
        }


        date.setText(currentItem.date);
        time.setText(currentItem.time);
        temperature.setText(String.valueOf(currentItem.temperature)+" °C");
        icon.setImageResource(currentItem.weatherCode);


        // Return the convertView
        return convertView;
    }


}
