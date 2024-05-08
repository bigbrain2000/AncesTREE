package com.upt.weatherBeacon.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
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

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.Geocoding;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.GeocodingData;
import com.upt.weatherBeacon.databinding.FragmentHomemainBinding;
import com.upt.weatherBeacon.model.DailyWeatherData;
import com.upt.weatherBeacon.model.HourlyAirData;
import com.upt.weatherBeacon.model.HourlyWeatherData;
import com.upt.weatherBeacon.model.WeatherData;
import com.upt.weatherBeacon.model.YearClimate;
import com.upt.weatherBeacon.ui.base.BaseFragment;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

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
        GraphView graphMaxTemp = (GraphView) climateContent.findViewById(R.id.graphMaxTemp);
        GraphView graphMinTemp = (GraphView) climateContent.findViewById(R.id.graphMinTemp);
        GraphView graphWind = (GraphView) climateContent.findViewById(R.id.graphWindSpeed);
        GraphView graphPrecipitation = (GraphView) climateContent.findViewById(R.id.graphPrecipitation);


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
                TextView cityName = view.findViewById(R.id.cityName);
                cityName.setText(appState.getCity());

                Context ctx = getContext();

//                textViewDaily

                Switch switchButton2 = view.findViewById(R.id.switchButtonText);
                switchButton2.setText("Hourly");
                ListView listView = view.findViewById(R.id.listWeatherDetails);
                ListView listViewDaily = view.findViewById(R.id.listDailyWeatherDetails);
                listViewDaily.setVisibility(View.GONE);

                switchButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            // Switch is ON, set text to "Daily"
                            listView.setVisibility(View.GONE);
                            listViewDaily.setVisibility(View.VISIBLE);
                            switchButton2.setText("Daily");

                        } else {
                            // Switch is OFF, set text to "Hourly"
                            listView.setVisibility(View.VISIBLE);
                            listViewDaily.setVisibility(View.GONE);
                            switchButton2.setText("Hourly");
                        }
                    }
                });


                System.out.println("SWITCH TEXT show ::: " + switchButton2.getShowText());
                System.out.println("SWITCH TEXT ::: " + switchButton2.getText());
                System.out.println("SWITCH TEXT on ::: " + switchButton2.getTextOn());

                if (switchButton2.getText() == "Hourly" || switchButton2.getText() == "") {
                    listView.setVisibility(View.VISIBLE);
                    listViewDaily.setVisibility(View.GONE);
                }
                else{
                    listView.setVisibility(View.GONE);
                    listViewDaily.setVisibility(View.VISIBLE);
                }
                appState.getWeatherDataLiveData().observe(getViewLifecycleOwner(), new Observer<WeatherData>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onChanged(WeatherData weatherData) {
                        TextView text = view.findViewById(R.id.textWeatherDescription);
//                        text.setText("ORICE " + weatherData.elevation);


                        List<HourlyWeatherData> hourly = weatherData.hourly;
                        ForecastsHourlyAdapter hourlyAdapter = new ForecastsHourlyAdapter(ctx, hourly);
                        List<DailyWeatherData> daily = weatherData.daily;
                        System.out.println("DAILY ::: " + daily.size());
                        System.out.println("DAILY HOURLY ::: " + daily.size());
                        ForecastsDailyAdapter dailyAdapter = new ForecastsDailyAdapter(getContext(), daily);

                        LocalTime currentTime = null;
                        currentTime = LocalTime.now();
                        // Extract the hour from the current time
                        int currentHour = currentTime.getHour();
                        listView.setAdapter(hourlyAdapter);
                        listView.setSelection(currentHour);
                        listViewDaily.setAdapter(dailyAdapter);
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
                        listViewDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Get the clicked item
                                DailyWeatherData selectedItem = daily.get(position);

                                // Show modal dialog with additional data
                                showAdditionalDailyDataDialog(selectedItem);
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
                appState.getWeatherDataLiveData().observe(getViewLifecycleOwner(), new Observer<WeatherData>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onChanged(WeatherData weatherData) {

                        labelTextView.setText(String.valueOf(weatherData.elevation) + " m");

                    }
                });


            }
        });

        btnGeocoding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLayout.setVisibility(View.GONE);
                parentDisplayLayout.addView(geocodingContent);
                btnGeocodingSearch.performClick();
            }
        });

        btnGeocodingSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textField = parentDisplayLayout.findViewById(R.id.textSearchField);
                String cityName = textField.getText().toString();
                if (!cityName.isEmpty()) {
                    viewModel.getGeocodingData(cityName);
                }

                TextView error = parentDisplayLayout.findViewById(R.id.geocodingError);
                appState.errorGeocoding.observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if (s.length() > 0) {
                            error.setText(s);
                        } else error.setText("");
                    }
                });
                appState.getGeocodingLiveData().observe(getViewLifecycleOwner(), new Observer<Geocoding>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onChanged(Geocoding geocoding) {

                        ListView geocodingResults = view.findViewById(R.id.listGeocodingResult);
                        GeocodingAdapter gecodingAdapter = new GeocodingAdapter(getContext(), Arrays.asList(geocoding.results));
                        geocodingResults.setAdapter(gecodingAdapter);

                        geocodingResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Get the clicked item
//                                System.out.println("GEOCODING ITEM CLICK!");
                                GeocodingData selectedItem = geocoding.results[position];
//
//                                // Show modal dialog with additional data
                                showAdditionalGeocodingDialog(selectedItem);
                            }
                        });

                    }

                });
//
            }
        });

        btnAirQuality.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                parentDisplayLayout.addView(airContent);
                menuLayout.setVisibility(View.GONE);

                appState.getAirQualityLiveData().observe(getViewLifecycleOwner(), new Observer<List<HourlyAirData>>() {
                    @Override
                    public void onChanged(List<HourlyAirData> hourlyAir) {
                        ListView airList = view.findViewById(R.id.listAirDetails);
                        AirQualityAdapter airAdapter = new AirQualityAdapter(getContext(), hourlyAir );
                        airList.setAdapter(airAdapter);
                        LocalTime currentTime = null;
                        currentTime = LocalTime.now();

                        // Extract the hour from the current time
                        int currentHour = currentTime.getHour();
                        airList.setSelection(currentHour);
                        airList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Get the clicked item
                                HourlyAirData selectedItem = hourlyAir.get(position);

                                // Show modal dialog with additional data
                                showAdditionalAirDataDialog(selectedItem);
                            }
                        });

                    }
                });
            }
        });

        btnClimateChange.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                menuLayout.setVisibility(View.GONE);
                parentDisplayLayout.addView(climateContent);


                graphMaxTemp.setTitle("Evolution of max. temperature through last 5 years");
                graphMinTemp.setTitle("Evoulution of min. temperature through last 5 years");
                graphWind.setTitle("Evolution of wind speed through last 5 years");
                graphPrecipitation.setTitle("Evolution of precipitations through last 5 years");

                graphMaxTemp.computeScroll();
                graphMaxTemp.getViewport().setScalable(true);
                graphMaxTemp.getViewport().setScrollable(true);
              

                graphMaxTemp.animate();
                // Set initial visible range along the x-axis
                graphMaxTemp.getViewport().setXAxisBoundsManual(true);
                graphMaxTemp.getViewport().setMinX(0);
                graphMaxTemp.getViewport().setMaxX(366);

// Set initial visible range along the y-axis
                graphMaxTemp.getViewport().setYAxisBoundsManual(true);
                graphMaxTemp.getViewport().setMinY(-20);
                graphMaxTemp.getViewport().setMaxY(50);

                graphMinTemp.computeScroll();
                graphMinTemp.getViewport().setScalable(true);
                graphMinTemp.getViewport().setScrollable(true);


                graphMinTemp.animate();
                // Set initial visible range along the x-axis
                graphMinTemp.getViewport().setXAxisBoundsManual(true);
                graphMinTemp.getViewport().setMinX(0);
                graphMinTemp.getViewport().setMaxX(366);

// Set initial visible range along the y-axis
                graphMinTemp.getViewport().setYAxisBoundsManual(true);
                graphMinTemp.getViewport().setMinY(-35);
                graphMinTemp.getViewport().setMaxY(50);


                graphPrecipitation.computeScroll();
                graphPrecipitation.getViewport().setScalable(true);
                graphPrecipitation.getViewport().setScrollable(true);


                graphPrecipitation.animate();
                // Set initial visible range along the x-axis
                graphPrecipitation.getViewport().setXAxisBoundsManual(true);
                graphPrecipitation.getViewport().setMinX(0);
                graphPrecipitation.getViewport().setMaxX(366);

// Set initial visible range along the y-axis
                graphPrecipitation.getViewport().setYAxisBoundsManual(true);
                graphPrecipitation.getViewport().setMinY(0);
                graphPrecipitation.getViewport().setMaxY(100);

                graphWind.computeScroll();
                graphWind.getViewport().setScalable(true);
                graphWind.getViewport().setScrollable(true);


                graphWind.animate();
                // Set initial visible range along the x-axis
                graphWind.getViewport().setXAxisBoundsManual(true);
                graphWind.getViewport().setMinX(0);
                graphWind.getViewport().setMaxX(366);

// Set initial visible range along the y-axis
                graphWind.getViewport().setYAxisBoundsManual(true);
                graphWind.getViewport().setMinY(0);
                graphWind.getViewport().setMaxY(75);



                TextView max0 = view.findViewById(R.id.max0);
                TextView max1 = view.findViewById(R.id.max1);
                TextView max2 = view.findViewById(R.id.max2);
                TextView max3 = view.findViewById(R.id.max3);
                TextView max4 = view.findViewById(R.id.max4);
                
                TextView min0 = view.findViewById(R.id.min0);
                TextView min1 = view.findViewById(R.id.min1);
                TextView min2 = view.findViewById(R.id.min2);
                TextView min3 = view.findViewById(R.id.min3);
                TextView min4 = view.findViewById(R.id.min4);


                TextView humidity0 = view.findViewById(R.id.humidity0);
                TextView humidity1 = view.findViewById(R.id.humidity1);
                TextView humidity2 = view.findViewById(R.id.humidity2);
                TextView humidity3 = view.findViewById(R.id.humidity3);
                TextView humidity4 = view.findViewById(R.id.humidity4);


                TextView wind0 = view.findViewById(R.id.wind0);
                TextView wind1 = view.findViewById(R.id.wind1);
                TextView wind2 = view.findViewById(R.id.wind2);
                TextView wind3 = view.findViewById(R.id.wind3);
                TextView wind4 = view.findViewById(R.id.wind4);

                List<YearClimate> years = viewModel.getClimateChangeData();

                max0.setText(String.valueOf(years.get(0).maxTemp));
                max1.setText(String.valueOf(years.get(1).maxTemp));
                max2.setText(String.valueOf(years.get(2).maxTemp));
                max3.setText(String.valueOf(years.get(3).maxTemp));
                max4.setText(String.valueOf(years.get(4).maxTemp));

                min0.setText(String.valueOf(years.get(0).minTemp));
                min1.setText(String.valueOf(years.get(1).minTemp));
                min2.setText(String.valueOf(years.get(2).minTemp));
                min3.setText(String.valueOf(years.get(3).minTemp));
                min4.setText(String.valueOf(years.get(4).minTemp));

                humidity0.setText(String.valueOf(years.get(0).totalPrecipitaiton));
                humidity1.setText(String.valueOf(years.get(1).totalPrecipitaiton));
                humidity2.setText(String.valueOf(years.get(2).totalPrecipitaiton));
                humidity3.setText(String.valueOf(years.get(3).totalPrecipitaiton));
                humidity4.setText(String.valueOf(years.get(4).totalPrecipitaiton));

                wind0.setText(String.valueOf(years.get(0).maxWindSpeed));
                wind1.setText(String.valueOf(years.get(1).maxWindSpeed));
                wind2.setText(String.valueOf(years.get(2).maxWindSpeed));
                wind3.setText(String.valueOf(years.get(3).maxWindSpeed));
                wind4.setText(String.valueOf(years.get(4).maxWindSpeed));

                List<List<LineGraphSeries<DataPoint>>> seriesVM = viewModel.getSeries(years);

                 int[] colors = new int[]{getResources().getColor(R.color.year0), getResources().getColor(R.color.year1),getResources().getColor(R.color.year2), getResources().getColor(R.color.year3), getResources().getColor(R.color.year4),getResources().getColor(R.color.year5)};
                 for(int i = 0; i < seriesVM.size(); i++){
                     LineGraphSeries<DataPoint> seriesTMAX =seriesVM.get(i).get(0);
                     LineGraphSeries<DataPoint> seriesTMIN =seriesVM.get(i).get(1);
                     LineGraphSeries<DataPoint> seriesWind =seriesVM.get(i).get(2);
                     LineGraphSeries<DataPoint> seriesPrecipitation =seriesVM.get(i).get(3);
                     seriesTMAX.setAnimated(true);
                     seriesTMAX.setColor(colors[i]);
                     seriesTMIN.setAnimated(true);
                     seriesTMIN.setColor(colors[i]);
                     seriesWind.setAnimated(true);
                     seriesWind.setColor(colors[i]);
                     seriesPrecipitation.setAnimated(true);
                     seriesPrecipitation.setColor(colors[i]);
                     graphMaxTemp.addSeries(seriesTMAX);
                     graphMinTemp.addSeries(seriesTMIN);
                     graphWind.addSeries(seriesWind);
                     graphPrecipitation.addSeries(seriesPrecipitation);
                     
                 }


//                GraphView graph = (GraphView) climateContent.findViewById(R.id.graphMinTemp);



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
        builder.setMessage("Additional data: ");

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

    private void showAdditionalDailyDataDialog(DailyWeatherData data) {

        View dailyDialog = LayoutInflater.from(getContext()).inflate(R.layout.modal_daily, null);
        ImageView weatherCode = dailyDialog.findViewById(R.id.weatherCode);
        weatherCode.setImageResource(data.weatherCode);
        TextView imageDescription = dailyDialog.findViewById(R.id.WeatherCodeDescriptio_daily);
        TextView temperature = dailyDialog.findViewById(R.id.temperature_max);
        TextView temperature_min = dailyDialog.findViewById(R.id.temperature_min);
        TextView sunRise = dailyDialog.findViewById(R.id.sunrise);
        TextView sunSet = dailyDialog.findViewById(R.id.sunset);


        imageDescription.setText("ORICE");
        temperature.setText(String.valueOf(data.max_temperature));
        temperature_min.setText(String.valueOf(data.min_temperature));


        String[] sunRiseSplit = data.sunrise.split("T");
        String[] sunSetSplit = data.sunset.split("T");
        String[] hoursSunrise = sunRiseSplit[1].split(":");
        String[] hoursSunset = sunSetSplit[1].split(":");

        int sunriseHour = Integer.parseInt(hoursSunrise[0]);
        int sunsetHour = Integer.parseInt(hoursSunset[0]);

        // Get the default timezone
        TimeZone defaultTimeZone = TimeZone.getDefault();

        // Get the offset in milliseconds
        int offsetInMillis = defaultTimeZone.getRawOffset();

        // Convert milliseconds to hours
        int offsetInHours = offsetInMillis / (60 * 60 * 1000);

        sunriseHour += offsetInHours;
        sunsetHour += offsetInHours;

        String sunsetTime = "" + sunsetHour + ":" + hoursSunset[1];
        String sunRiseTime = "" + sunriseHour + ":" + hoursSunrise[1];

        System.out.println("Timezone Offset: " + offsetInHours + " hours");

        sunRise.setText(sunRiseTime);
        sunSet.setText(sunsetTime);


        // Create and configure AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dailyDialog);
        builder.setTitle("Additional Data");

        // Set additional data to dialog
        builder.setMessage("Additional data: ");

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

    private void showAdditionalGeocodingDialog(GeocodingData data) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Set the dialog title, message, and buttons
        builder.setTitle("Search for new city")
                .setMessage("Do you want to search forecasts for " + data.name + " " + data.admin1 + " ?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform your action when the "OK" button is clicked
                        // For example, you can close the dialog or perform any other action
                        viewModel.getForecastsForNewCity(data.latitude, data.longitude, data.name);
                        dialog.dismiss(); // Close the dialog
                    }
                }) // null listener to simply dismiss the dialog when "OK" is clicked
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform your action when the "Cancel" button is clicked
                        // For example, you can close the dialog or perform any other action
                        dialog.dismiss(); // Close the dialog
                    }
                });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDialog() {
        // Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Set the dialog title, message, and buttons
        // Set the dialog title, message, and buttons
        builder.setTitle("Alert Dialog")
                .setMessage("This is a simple alert dialog.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform your action when the "OK" button is clicked
                        // For example, you can close the dialog or perform any other action
                        dialog.dismiss(); // Close the dialog
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform your action when the "Cancel" button is clicked
                        // For example, you can close the dialog or perform any other action
                        dialog.dismiss(); // Close the dialog
                    }
                });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void showAdditionalAirDataDialog(HourlyAirData data) {

        View airDialog = LayoutInflater.from(getContext()).inflate(R.layout.modal_air, null);
        ImageView weatherCode = airDialog.findViewById(R.id.airCode);
        weatherCode.setImageResource(data.airCode);
        TextView airQualityDescription = airDialog.findViewById(R.id.airQualityDescription);
        TextView pm10 = airDialog.findViewById(R.id.pm10);
        TextView pm2_5 = airDialog.findViewById(R.id.pm2_5);
        TextView dust = airDialog.findViewById(R.id.dust);
        TextView co2 = airDialog.findViewById(R.id.co2);
        TextView no2 = airDialog.findViewById(R.id.no2);
        TextView so2 = airDialog.findViewById(R.id.so2);
        TextView uvIndex = airDialog.findViewById(R.id.uvIndex);

        airQualityDescription.setText(data.airDescription);
        pm10.setText(String.valueOf(data.pm10)+ " μg/m³");
        pm2_5.setText(String.valueOf(data.pm2_5)+ " μg/m³");
        dust.setText(String.valueOf(data.dust));
        co2.setText(String.valueOf(data.carbon_monoxide)+ " μg/m³");
        no2.setText(String.valueOf(data.nitrogen_dioxide)+ " μg/m³");
        so2.setText(String.valueOf(data.sulphur_dioxide)+ " μg/m³");
        uvIndex.setText(String.valueOf(data.uv_index));



        // Create and configure AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(airDialog);
        builder.setTitle("Additional Data");

        // Set additional data to dialog
        builder.setMessage("Additional data: ");

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
