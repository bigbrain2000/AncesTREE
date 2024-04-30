package com.upt.weatherBeacon.ui.home;

import android.content.Context;
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

import java.util.List;

public class ForecastsHourlyAdapter extends ArrayAdapter<HourlyWeatherData> {
    private Context context;
    private List<HourlyWeatherData> dataList;

    public ForecastsHourlyAdapter(Context context, List<HourlyWeatherData> dataList) {
        super(context, 0, dataList);
        this.context = context;
        this.dataList = dataList;
    }

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
        ImageView icon = convertView.findViewById(R.id.forecastIcon);


        date.setText(currentItem.date);
        time.setText(currentItem.time);
        temperature.setText(String.valueOf(currentItem.temperature));
        icon.setImageResource(currentItem.weatherCode);


        // Return the convertView
        return convertView;
    }


}
