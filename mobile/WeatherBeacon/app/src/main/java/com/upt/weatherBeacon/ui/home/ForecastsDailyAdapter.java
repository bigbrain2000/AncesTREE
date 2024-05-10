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
import com.upt.weatherBeacon.model.DailyWeatherData;

import java.time.LocalTime;
import java.util.List;

public class ForecastsDailyAdapter extends ArrayAdapter<DailyWeatherData> {
    private Context context;
    private List<DailyWeatherData> dataList;


    public ForecastsDailyAdapter(Context context, List<DailyWeatherData> dataList) {
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
        DailyWeatherData currentItem = dataList.get(position);

        // Bind data to the views in the list item layout
        TextView date = convertView.findViewById(R.id.date);
        TextView time = convertView.findViewById(R.id.time);
        TextView temperature = convertView.findViewById(R.id.tempMax);
        TextView temperatureMin = convertView.findViewById(R.id.tempMin);
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
        time.setVisibility(View.GONE);
        temperature.setText("Max. " +String.valueOf(currentItem.max_temperature)+ " °C");
        temperatureMin.setText("Min. "+String.valueOf(currentItem.min_temperature)+" °C");

        icon.setImageResource(currentItem.weatherCode);


        // Return the convertView
        return convertView;
    }


}
