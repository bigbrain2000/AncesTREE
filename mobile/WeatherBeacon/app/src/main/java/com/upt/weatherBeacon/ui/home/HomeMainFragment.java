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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.databinding.FragmentHomemainBinding;
import com.upt.weatherBeacon.ui.base.BaseFragment;
import com.upt.weatherBeacon.ui.utilities.ScreenUtils;

public class HomeMainFragment extends BaseFragment<HomeViewModel> {

    private FragmentHomemainBinding binding;
    private LinearLayout menuLayout;
    private GlobalState appState = GlobalState.getState();

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
        View airContent = getLayoutInflater().inflate(R.layout.air_content, null);
        View climateContent = getLayoutInflater().inflate(R.layout.climate_content, null);
        View manageContent = getLayoutInflater().inflate(R.layout.manage_account, null);

        Button btnGeocodingSearch = geocodingContent.findViewById(R.id.btnGeocodingSearch);
        GraphView graphT = (GraphView) climateContent.findViewById(R.id.graphT);
        GraphView graphP = (GraphView) climateContent.findViewById(R.id.graphP);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                modifiedSpace.getLayoutParams().height = ScreenUtils.getScreenHeightInPixels(context) / 2 + ScreenUtils.getScreenHeightInPixels(context) / 8;
                toggleMenu();
                parentDisplayLayout.removeView(weatherForecastContent);
                parentDisplayLayout.removeView(elevationContent);
                parentDisplayLayout.removeView(geocodingContent);
                parentDisplayLayout.removeView(airContent);
                parentDisplayLayout.removeView(climateContent);
                parentDisplayLayout.removeView(manageContent);
            }
        });

        btnWeatherForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLayout.setVisibility(View.GONE);
                parentDisplayLayout.addView(weatherForecastContent);
                //TODO functionality for weather forecast
                TextView cityName = view.findViewById(R.id.cityName);
                cityName.setText(appState.getCity());

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
                detailsTextView.setText("City: " + cityName + "\nLatitude: 12345\nLongitude: 54321\nElevation: 100\nTimezone: GMT+2\nPopulation: 1000"); //TODO functionality
                textField.setText("");
            }
        });

        btnAirQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentDisplayLayout.addView(airContent);
                menuLayout.setVisibility(View.GONE);
                //TODO air functionality
            }
        });

        btnClimateChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLayout.setVisibility(View.GONE);
                parentDisplayLayout.addView(climateContent);

                //TODO functionality climate

                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                        new DataPoint(0, 1),
                        new DataPoint(1, 5),
                        new DataPoint(2, 3),
                        new DataPoint(3, 2),
                        new DataPoint(4, 6)
                });
                graphT.addSeries(series);
                graphP.addSeries(series);
            }
        });

        btnManageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO managefunctionality
                parentDisplayLayout.addView(manageContent);
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
