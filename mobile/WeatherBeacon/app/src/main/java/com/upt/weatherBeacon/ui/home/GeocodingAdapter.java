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
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.Geocoding;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.GeocodingData;
import com.upt.weatherBeacon.model.DailyWeatherData;
import com.upt.weatherBeacon.model.HourlyWeatherData;

import java.time.LocalTime;
import java.util.List;

public class GeocodingAdapter extends ArrayAdapter<GeocodingData> {
    private Context context;
    private List<GeocodingData> dataList;


    public GeocodingAdapter(Context context, List<GeocodingData> dataList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.geocoding_result_item, parent, false);
        }

        TextView  detailsTextView = convertView.findViewById(R.id.geocodingItemDetails);
        // Get the data item for this position

        GeocodingData data = dataList.get(position);
        String details = "City: " + data.name+"\n"
                + data.country+" "+data.admin1+"\n"
                + "Latitude: "+data.latitude+"\n"
                + "Longitude: "+ data.longitude+"\n"
                + "Elevation: "+ data.elevation+"\n"
                + "Timezone: "+ data.timezone+"\n"
                + "Population: "+data.population;
        detailsTextView.setText(details);

        // Return the convertView
        return convertView;
    }

}
