package com.upt.weatherBeacon.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Space;
import android.widget.Switch;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.databinding.FragmentHomemainBinding;
import com.upt.weatherBeacon.model.HourlyWeatherData;
import com.upt.weatherBeacon.model.WeatherData;
import com.upt.weatherBeacon.ui.base.BaseFragment;

import java.time.LocalTime;
import java.util.List;

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

                Context ctx = getContext();

//                textViewDaily

                Switch switchButton2 = view.findViewById(R.id.switchButtonText);
                switchButton2.setText("Hourly");
                ListView listView = view.findViewById(R.id.listWeatherDetails);

                switchButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            // Switch is ON, set text to "Daily"
                            listView.setVisibility(View.GONE);
                            switchButton2.setText("Daily");
                        } else {
                            // Switch is OFF, set text to "Hourly"
                            listView.setVisibility(View.VISIBLE);
                            switchButton2.setText("Hourly");
                        }
                    }
                });


                System.out.println("SWITCH TEXT show ::: " + switchButton2.getShowText());
                System.out.println("SWITCH TEXT ::: " + switchButton2.getText());
                System.out.println("SWITCH TEXT on ::: " + switchButton2.getTextOn());

                if (switchButton2.getText() == "Hourly" || switchButton2.getText() == "") {
                    listView.setVisibility(View.VISIBLE);
                }
                else{
                    listView.setVisibility(View.GONE);
                }
                appState.getWeatherDataLiveData().observe(getViewLifecycleOwner(), new Observer<WeatherData>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onChanged(WeatherData weatherData) {
                        TextView text = view.findViewById(R.id.textWeatherDescription);
                        text.setText("ORICE " + weatherData.elevation);




                            List<HourlyWeatherData> hourly = weatherData.hourly;
                            ForecastsHourlyAdapter hourlyAdapter = new ForecastsHourlyAdapter(ctx, hourly);


                            LocalTime currentTime = null;
                            currentTime = LocalTime.now();
                            // Extract the hour from the current time
                            int currentHour = currentTime.getHour();
                            listView.setAdapter(hourlyAdapter);
                            listView.setSelection(currentHour);
                            // Set OnClickListener to ListView items
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    // Get the clicked item
                                    HourlyWeatherData selectedItem = hourly.get(position);

                                    // Show modal dialog with additional data
                                    showAdditionalDataDialog(selectedItem);
                                }
                            });


                    }
                });


            }
        });
        btnWeatherForecast.performClick();

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
    private void showAdditionalDataDialog(HourlyWeatherData data) {

        View hourlyDialog = LayoutInflater.from(getContext()).inflate(R.layout.modal_hourly,null);
        ImageView weatherCode = hourlyDialog.findViewById(R.id.weatherCode);
        weatherCode.setImageResource(data.weatherCode);
        TextView imageDescription = hourlyDialog.findViewById(R.id.WeatherCodeDescription);
        TextView temperature = hourlyDialog.findViewById(R.id.temperature);
        TextView humidity = hourlyDialog.findViewById(R.id.humidity);
        TextView windSpeed = hourlyDialog.findViewById(R.id.windSpeed);
        TextView windDirection = hourlyDialog.findViewById(R.id.windDirection);

        imageDescription.setText("ORICE");
        temperature.setText(String.valueOf(data.temperature));
        humidity.setText(String.valueOf(data.humidity));
        windSpeed.setText(String.valueOf(data.windSpeed));
        windDirection.setText(String.valueOf(data.windDirection));
        // Create and configure AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(hourlyDialog);
        builder.setTitle("Additional Data");

        // Set additional data to dialog
        builder.setMessage("Additional data: " );

        // Add any other configuration you need for the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss dialog if needed
                dialog.dismiss();
            }
        });

        // Show the dialog
        builder.show();
    }
}
