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
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.HourlyAirQuality;
import com.upt.weatherBeacon.model.HourlyAirData;

import java.time.LocalTime;
import java.util.List;

public class AirQualityAdapter extends ArrayAdapter<HourlyAirData> {
    private Context context;
    private List<HourlyAirData> dataList;


    public AirQualityAdapter(Context context, List<HourlyAirData> dataList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.air_item, parent, false);
        }

        // Get the data item for this position
       HourlyAirData currentItem = dataList.get(position);

        // Bind data to the views in the list item layout
        TextView time = convertView.findViewById(R.id.timeAir);
        TextView airDescription = convertView.findViewById(R.id.airCodeDescription);
        ImageView icon = convertView.findViewById(R.id.airCode);
        ImageView liveIcon = convertView.findViewById(R.id.liveIconAir);
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
//            convertView.setBackgroundColor(color);
        }


        System.out.println("AIR QUALITY TIME ::: "+currentItem.time);

        time.setText(currentItem.time);
        airDescription.setText(currentItem.airDescription);
        icon.setImageResource(currentItem.airCode);

        // Return the convertView
        return convertView;
    }

}
