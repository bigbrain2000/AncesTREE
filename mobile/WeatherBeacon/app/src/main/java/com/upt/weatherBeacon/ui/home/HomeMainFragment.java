package com.upt.weatherBeacon.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.databinding.FragmentHomemainBinding;
import com.upt.weatherBeacon.ui.base.BaseFragment;
import com.upt.weatherBeacon.ui.utilities.ScreenUtils;

public class HomeMainFragment extends BaseFragment<HomeViewModel> {

    private FragmentHomemainBinding binding;
    private LinearLayout menuLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomemainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Button btnMenu = view.findViewById(R.id.btnMenu);
        menuLayout = view.findViewById(R.id.menuLayout);
        Button btnWeatherForecast = view.findViewById(R.id.btnWeatherForecast);
        Button btnElevation = view.findViewById(R.id.btnElevation);
        Button btnGeocoding = view.findViewById(R.id.btnGeocoding);
        Button btnAirQuality = view.findViewById(R.id.btnAirQuality);
        Button btnClimateChange = view.findViewById(R.id.btnClimateChange);
        Button btnManageAccount = view.findViewById(R.id.btnManageAccount);
        Space modifiedSpace = view.findViewById(R.id.modifiedSpace);
        ViewGroup parentDisplayLayout = view.findViewById(R.id.display_layout);
        Context context = requireContext().getApplicationContext();


        View weatherForecastContent = getLayoutInflater().inflate(R.layout.weather_forecast_content, null);
        View elevationContent = getLayoutInflater().inflate(R.layout.elevation_content, null);
        View geocodingContent = getLayoutInflater().inflate(R.layout.geocoding_content, null);

        Button btnGeocodingSearch = geocodingContent.findViewById(R.id.btnGeocodingSearch);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifiedSpace.getLayoutParams().height = ScreenUtils.getScreenHeightInPixels(context)/2 + ScreenUtils.getScreenHeightInPixels(context)/8;
                toggleMenu();
                parentDisplayLayout.removeView(weatherForecastContent);
                parentDisplayLayout.removeView(elevationContent);
                parentDisplayLayout.removeView(geocodingContent);
            }
        });

        btnWeatherForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLayout.setVisibility(View.GONE);
                parentDisplayLayout.addView(weatherForecastContent);
                //TODO functionality for weather forecast

            }
        });

        btnElevation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLayout.setVisibility(View.GONE);

                parentDisplayLayout.addView(elevationContent);
                TextView labelTextView = parentDisplayLayout.findViewById(R.id.textCenter);

                labelTextView.setText("Altitude: 80"); //TODO functionality

            }
        });

        btnGeocoding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLayout.setVisibility(View.GONE);
                parentDisplayLayout.addView(geocodingContent);
            }
        });

        btnGeocodingSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textField = parentDisplayLayout.findViewById(R.id.textSearchField);
                String cityName = textField.getText().toString();

                TextView detailsTextView = parentDisplayLayout.findViewById(R.id.resultTextField);
                detailsTextView.setText("City: "+cityName+"\nLatitude: 12345\nLongitude: 54321\nElevation: 100\nTimezone: GMT+2\nPopulation: 1000"); //TODO functionality
                textField.setText("");
            }
        });

        btnAirQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Air Quality clicked", Toast.LENGTH_SHORT).show();//TODO functionality
                menuLayout.setVisibility(View.GONE);
            }
        });

        btnClimateChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Climate change clicked", Toast.LENGTH_SHORT).show();//TODO functionality
                menuLayout.setVisibility(View.GONE);
            }
        });

        btnManageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Manage account clicked", Toast.LENGTH_SHORT).show();//TODO functionality
                menuLayout.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void toggleMenu() {
        if (menuLayout.getVisibility() == View.VISIBLE) {
            menuLayout.setVisibility(View.GONE);
        } else {
            menuLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setObservers() {
        // Implement as needed
    }

    @Override
    protected void initUi() {
        // Implement as needed
    }

    @Override
    protected int getContentView() {
        return 0; // Return the appropriate layout resource id
    }
}
