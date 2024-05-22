package com.upt.weatherBeacon.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.upt.weatherBeacon.AppState.GlobalState;
import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.data.local.UserSession;
import com.upt.weatherBeacon.data.local.UserSessionManager;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.Geocoding;
import com.upt.weatherBeacon.data.remote.WeatherRepository.Dto.GeocodingData;
import com.upt.weatherBeacon.databinding.FragmentHomemainBinding;
import com.upt.weatherBeacon.model.DailyWeatherData;
import com.upt.weatherBeacon.model.HourlyAirData;
import com.upt.weatherBeacon.model.HourlyWeatherData;
import com.upt.weatherBeacon.model.User;
import com.upt.weatherBeacon.model.WeatherData;
import com.upt.weatherBeacon.model.YearClimate;
import com.upt.weatherBeacon.model.YearGraphSeries;
import com.upt.weatherBeacon.ui.base.BaseFragment;
import com.upt.weatherBeacon.ui.base.Navigation;
import com.upt.weatherBeacon.ui.base.navigation.NavAttribs;
import com.upt.weatherBeacon.ui.base.navigation.Screen;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
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
        System.out.println("USER ::: jwt ::: " + appState.jwtToken.getValue());

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

        Button exit = binding.exitApp;
        Button logout = binding.logout;

        UserSessionManager sessionManager = new UserSessionManager(getContext());

        appState.getJwtToken().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                    System.out.println("USER jwt observer::: "+s);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.saveUserSession(new UserSession());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Navigation navigation = new Navigation(new NavAttribs(Screen.LoginScreen, null, true));
                viewModel.uiEventStream.setValue(navigation);
            }
        });


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

                ImageView currentWeatherCode = view.findViewById(R.id.imageWeather);
                TextView currentTemp = view.findViewById(R.id.curentTemp);
                TextView currentWeatherDesc = view.findViewById(R.id.currentWeatherDescription);



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

                        System.out.println("CURRENT WEATHER : " + weatherData.current.weatherCode );

                        currentTemp.setText(weatherData.current.temperature+" °C");
                        currentWeatherCode.setImageResource(weatherData.current.weatherCode);
                        currentWeatherDesc.setText(weatherData.current.weatherDescription);

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

                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

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

                        ImageView currentCode = view.findViewById(R.id.curentAirCode);
                        TextView currentAirDesc = view.findViewById(R.id.curentAirDescription);

                        currentCode.setImageResource(hourlyAir.get(currentHour).airCode);
                        currentAirDesc.setText(hourlyAir.get(currentHour).airDescription);

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

                List<YearGraphSeries> seriesVM = viewModel.getSeries(years);
                appState.updateGraphSeriesLiveData(seriesVM);
                appState.updateGraphSeriesLiveDataAll(viewModel.getSeries(years));

                Switch sw0 = view.findViewById(R.id.switchyear0);
                Switch sw1 = view.findViewById(R.id.switchyear1);
                Switch sw2 = view.findViewById(R.id.switchyear2);
                Switch sw3 = view.findViewById(R.id.switchyear3);
                Switch sw4 = view.findViewById(R.id.switchyear4);

                // Create a ColorStateList for thumb color
                ColorStateList thumbColorStateList0 = new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_checked},
                                new int[]{-android.R.attr.state_checked}
                        },
                        new int[]{
                                getResources().getColor(R.color.year0),
                                getResources().getColor(R.color.year0)
                        }
                );
                // Create a ColorStateList for thumb color
                ColorStateList thumbColorStateList1 = new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_checked},
                                new int[]{-android.R.attr.state_checked}
                        },
                        new int[]{
                                getResources().getColor(R.color.year1),
                                getResources().getColor(R.color.year1)
                        }
                );

                // Create a ColorStateList for thumb color
                ColorStateList thumbColorStateList2 = new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_checked},
                                new int[]{-android.R.attr.state_checked}
                        },
                        new int[]{
                                getResources().getColor(R.color.year2),
                                getResources().getColor(R.color.year2)
                        }
                );

                // Create a ColorStateList for thumb color
                ColorStateList thumbColorStateList3 = new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_checked},
                                new int[]{-android.R.attr.state_checked}
                        },
                        new int[]{
                                getResources().getColor(R.color.year3),
                                getResources().getColor(R.color.year3)
                        }
                );

                // Create a ColorStateList for thumb color
                ColorStateList thumbColorStateList4 = new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_checked},
                                new int[]{-android.R.attr.state_checked}
                        },
                        new int[]{
                                getResources().getColor(R.color.year4),
                                getResources().getColor(R.color.year4)
                        }
                );
                // Create a ColorStateList for thumb color
                ColorStateList trackColorStateList = new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_checked},
                                new int[]{-android.R.attr.state_checked}
                        },
                        new int[]{
                                getResources().getColor(R.color.trackPressed),
                                getResources().getColor(R.color.trackUnpressed),

                        }
                );

                sw0.setThumbTintList(thumbColorStateList0);
                sw0.setTrackTintList(trackColorStateList);
                sw1.setThumbTintList(thumbColorStateList1);
                sw1.setTrackTintList(trackColorStateList);
                sw2.setThumbTintList(thumbColorStateList2);
                sw2.setTrackTintList(trackColorStateList);
                sw3.setThumbTintList(thumbColorStateList3);
                sw3.setTrackTintList(trackColorStateList);
                sw4.setThumbTintList(thumbColorStateList4);
                sw4.setTrackTintList(trackColorStateList);

                sw0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<YearGraphSeries> yearGraphSeries = appState.graphSeries.getValue();
                        List<YearGraphSeries> yearGraphSeriesAll = appState.graphSeriesAll.getValue();
                        if (sw0.isChecked()) {
                            for (int i = 0; i < yearGraphSeries.size(); i++) {

                                if (yearGraphSeries.get(i).year == 2019) {
                                    yearGraphSeries.remove(i);
                                    i--;
                                    }
                            }
                        } else {
                            for (int i = 0; i < yearGraphSeriesAll.size(); i++) {
                                 if (yearGraphSeriesAll.get(i).year == 2019) {
                                    yearGraphSeries.add(yearGraphSeriesAll.get(i));
                                    }
                            }
                        }

                        appState.updateGraphSeriesLiveData(yearGraphSeries);

                    }
                });

                sw1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<YearGraphSeries> yearGraphSeries = appState.graphSeries.getValue();
                        List<YearGraphSeries> yearGraphSeriesAll = appState.graphSeriesAll.getValue();
                        if (sw1.isChecked()) {
                            for (int i = 0; i < yearGraphSeries.size(); i++) {
                                System.out.println("GRAPH AM AJUNS AICI LA STERGERE 2020 ::: "+yearGraphSeries.get(i).year);
                                if (yearGraphSeries.get(i).year == 2020) {
                                    yearGraphSeries.remove(i);
                                    i--;
                                      }
                            }
                        } else {
                             for (int i = 0; i < yearGraphSeriesAll.size(); i++) {
                                 System.out.println("GRAPH AM AJUNS AICI LA STERGERE 2020 ::: "+yearGraphSeriesAll.get(i).year);
                                 if (yearGraphSeriesAll.get(i).year == 2020) {
                                    yearGraphSeries.add(yearGraphSeriesAll.get(i));
                                    }
                            }
                        }

                        appState.updateGraphSeriesLiveData(yearGraphSeries);

                    }
                });

                sw2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<YearGraphSeries> yearGraphSeries = appState.graphSeries.getValue();
                        List<YearGraphSeries> yearGraphSeriesAll = appState.graphSeriesAll.getValue();
                        if (sw2.isChecked()) {
                            for (int i = 0; i < yearGraphSeries.size(); i++) {
                                if (yearGraphSeries.get(i).year == 2021) {
                                    yearGraphSeries.remove(i);
                                    i--;
                                   }
                            }
                        } else {
                             for (int i = 0; i < yearGraphSeriesAll.size(); i++) {
                                 if (yearGraphSeriesAll.get(i).year == 2021) {
                                    yearGraphSeries.add(yearGraphSeriesAll.get(i));
                                  }
                            }
                        }

                        appState.updateGraphSeriesLiveData(yearGraphSeries);

                    }
                });

                sw3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<YearGraphSeries> yearGraphSeries = appState.graphSeries.getValue();
                        List<YearGraphSeries> yearGraphSeriesAll = appState.graphSeriesAll.getValue();
                        if (sw3.isChecked()) {
                            for (int i = 0; i < yearGraphSeries.size(); i++) {
                                 if (yearGraphSeries.get(i).year == 2022) {
                                    yearGraphSeries.remove(i);
                                    i--;
                                      }
                            }
                        } else {
                            for (int i = 0; i < yearGraphSeriesAll.size(); i++) {
                                if (yearGraphSeriesAll.get(i).year == 2022) {
                                    yearGraphSeries.add(yearGraphSeriesAll.get(i));
                                   }
                            }
                        }

                        appState.updateGraphSeriesLiveData(yearGraphSeries);

                    }
                });

                sw4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<YearGraphSeries> yearGraphSeries = appState.graphSeries.getValue();
                        List<YearGraphSeries> yearGraphSeriesAll = appState.graphSeriesAll.getValue();
                        if (sw4.isChecked()) {
                            for (int i = 0; i < yearGraphSeries.size(); i++) {
                                  if (yearGraphSeries.get(i).year == 2023) {
                                    yearGraphSeries.remove(i);
                                    i--;
                                     }
                            }
                        } else {
                            for (int i = 0; i < yearGraphSeriesAll.size(); i++) {
                                if (yearGraphSeriesAll.get(i).year == 2023) {
                                    yearGraphSeries.add(yearGraphSeriesAll.get(i));
                                    }
                            }
                        }

                        appState.updateGraphSeriesLiveData(yearGraphSeries);

                    }
                });

                int[] colors = new int[]{getResources().getColor(R.color.year0), getResources().getColor(R.color.year1), getResources().getColor(R.color.year2), getResources().getColor(R.color.year3), getResources().getColor(R.color.year4), getResources().getColor(R.color.year5)};

                appState.graphSeries.observe(getViewLifecycleOwner(), new Observer<List<YearGraphSeries>>() {
                    @Override
                    public void onChanged(List<YearGraphSeries> series) {
                        graphMaxTemp.removeAllSeries();
                        graphMinTemp.removeAllSeries();
                        graphWind.removeAllSeries();
                        graphPrecipitation.removeAllSeries();

                        for (int i = 0; i < series.size(); i++) {
                            LineGraphSeries<DataPoint> seriesTMAX = seriesVM.get(i).series.get(0);
                            LineGraphSeries<DataPoint> seriesTMIN = seriesVM.get(i).series.get(1);
                            LineGraphSeries<DataPoint> seriesWind = seriesVM.get(i).series.get(2);
                            LineGraphSeries<DataPoint> seriesPrecipitation = seriesVM.get(i).series.get(3);

                            int colorIndex = seriesVM.get(i).year - 2019;
                            seriesTMAX.setAnimated(true);
                            seriesTMAX.setColor(colors[colorIndex]);
                            seriesTMIN.setAnimated(true);
                            seriesTMIN.setColor(colors[colorIndex]);
                            seriesWind.setAnimated(true);
                            seriesWind.setColor(colors[colorIndex]);
                            seriesPrecipitation.setAnimated(true);
                            seriesPrecipitation.setColor(colors[colorIndex]);
                            graphMaxTemp.addSeries(seriesTMAX);
                            graphMinTemp.addSeries(seriesTMIN);
                            graphWind.addSeries(seriesWind);
                            graphPrecipitation.addSeries(seriesPrecipitation);
                        }

                    }
                });


                for (int i = 0; i < seriesVM.size(); i++) {
                    LineGraphSeries<DataPoint> seriesTMAX = seriesVM.get(i).series.get(0);
                    LineGraphSeries<DataPoint> seriesTMIN = seriesVM.get(i).series.get(1);
                    LineGraphSeries<DataPoint> seriesWind = seriesVM.get(i).series.get(2);
                    LineGraphSeries<DataPoint> seriesPrecipitation = seriesVM.get(i).series.get(3);
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
                System.out.println("USER::: jwt token change manage account ::: "+ appState.getJwtToken().getValue());
                System.out.println("USER::: username change manage account ::: "+ appState.username);
                viewModel.getUserLoggedInData(appState.username);

                TextView username = view.findViewById(R.id.username);
                EditText firstName = view.findViewById(R.id.firstName);
                EditText lastName = view.findViewById(R.id.lastName);
                EditText email = view.findViewById(R.id.email);
                EditText address = view.findViewById(R.id.address);
//                EditText oldPassword = view.findViewById(R.id.oldPassword);
//                EditText password = view.findViewById(R.id.password);
//                EditText confirmPassword = view.findViewById(R.id.password2);

                TextView errorUsername = view.findViewById(R.id.errorUsername);
                TextView errorFirstName = view.findViewById(R.id.errorFirstName);
                TextView errorLastName = view.findViewById(R.id.errorLastName);
                TextView errorEmail = view.findViewById(R.id.errorEmail);
                TextView errorAddress = view.findViewById(R.id.errorAddress);
//                TextView errorOldPassword = view.findViewById(R.id.errorOldPassword);
//                TextView errorNewPassword = view.findViewById(R.id.errorPassword);
//                TextView errorConfirmPassword = view.findViewById(R.id.errorPassword2);
//
//                errorUsername.setVisibility(View.VISIBLE);
//                errorFirstName.setVisibility(View.VISIBLE);
//                errorLastName.setVisibility(View.VISIBLE);
//                errorEmail.setVisibility(View.VISIBLE);
//                errorAddress.setVisibility(View.VISIBLE);
//                errorOldPassword.setVisibility(View.VISIBLE);
//                errorNewPassword.setVisibility(View.VISIBLE);
//                errorConfirmPassword.setVisibility(View.VISIBLE);
//
//                errorUsername.setText("error");
//                errorFirstName.setText("error");
//                errorLastName.setText("error");
//                errorEmail.setText("error");
//                errorAddress.setText("error");
//                errorOldPassword.setText("error");
//                errorNewPassword.setText("error");
//                errorConfirmPassword.setText("error");

                appState.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        username.setText(user.username);
                        firstName.setText(user.firstName);
                        lastName.setText(user.lastName);
                        email.setText(user.email);
                        address.setText(user.address);

                    }
                });

                Button changeButton = view.findViewById(R.id.changeButton);

                Button deleteAccount = view.findViewById(R.id.delButton);

                deleteAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.deleteAccount(username.getText().toString(), appState.getJwtToken().getValue());
                        Navigation navigation = new Navigation(new NavAttribs(Screen.LoginScreen, null, true));
                        viewModel.uiEventStream.setValue(navigation);
                    }
                });
                changeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int ok =0;

                        if(username.getText().length() == 0){
                            ok = 1;
                            errorUsername.setVisibility(View.VISIBLE);
                            errorUsername.setText("Required");
                        }
                        if(firstName.getText().length() == 0){
                            ok=1;
                            errorFirstName.setVisibility(View.VISIBLE);
                            errorFirstName.setText("Required");
                        }
                        if(lastName.getText().length() == 0){
                            ok = 1;
                            errorLastName.setVisibility(View.VISIBLE);
                            errorLastName.setText("Required");
                        }
                        if(email.getText().length() == 0){
                            ok = 1;
                            errorEmail.setVisibility(View.VISIBLE);
                            errorEmail.setText("Required");
                        }
                        if(address.getText().length() == 0){
                            ok = 1;
                            errorAddress.setVisibility(View.VISIBLE);
                            errorAddress.setText("Required");
                        }
//                        if(password.getText().length() == 0){
//                            ok = 1;
//                            errorOldPassword.setText("Required");
//                            errorOldPassword.setVisibility(View.VISIBLE);
//                        }
//                        if(oldPassword.getText().length() == 0){
//                            ok = 1;
//                            errorOldPassword.setText("Required");
//                            errorOldPassword.setVisibility(View.VISIBLE);
//                        }
//                        if(password.getText().length() == 0){
//                            ok = 1;
//                            errorNewPassword.setText("Required");
//                            errorNewPassword.setVisibility(View.VISIBLE);
//                        }
//                        if(confirmPassword.getText().length() == 0){
//                            ok = 1;
//                            errorConfirmPassword.setText("Required");
//                            errorConfirmPassword.setVisibility(View.VISIBLE);
//                        }
//                        if(oldPassword.getText().toString().equals(appState.password)){
//                            ok = 1;
//                            errorOldPassword.setVisibility(View.VISIBLE);
//                            errorOldPassword.setText("Wrong old password");
//
//                        }
//                        if(password.getText().length() < 8){
//                            ok = 1;
//                            errorNewPassword.setVisibility(View.VISIBLE);
//                            errorNewPassword.setText("Minimum 8 characters");
//                        }
//                        if(password.getText().toString().equals(confirmPassword.getText().toString())){
//                            ok = 1;
//                            errorConfirmPassword.setVisibility(View.VISIBLE);
//                            errorConfirmPassword.setText("Passwords doesn't match");
//                        }
//
//                        if(password.getText().length() == 0 && confirmPassword.getText().length() == 0 && confirmPassword.getText().length() == 0){
//                            ok = 2;
//                            errorNewPassword.setVisibility(View.GONE);
//                            errorOldPassword.setVisibility(View.GONE);
//                            errorConfirmPassword.setVisibility(View.GONE);
//                        }

                        if(ok == 0){
                            viewModel.updateUser(username.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), address.getText().toString(), "");
                        }
                        if(ok == 2){
                            viewModel.updateUser(username.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), address.getText().toString(), appState.password);

                        }

                    }
                });
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

        View hourlyDialog = LayoutInflater.from(getContext()).inflate(R.layout.modal_hourly, null);
        ImageView weatherCode = hourlyDialog.findViewById(R.id.weatherCode);
        weatherCode.setImageResource(data.weatherCode);
        TextView imageDescription = hourlyDialog.findViewById(R.id.WeatherCodeDescription);
        TextView temperature = hourlyDialog.findViewById(R.id.temperature);
        TextView humidity = hourlyDialog.findViewById(R.id.humidity);
        TextView windSpeed = hourlyDialog.findViewById(R.id.windSpeed);
        TextView windDirection = hourlyDialog.findViewById(R.id.windDirection);

        imageDescription.setText(data.weatherDescription);
        temperature.setText(String.valueOf(data.temperature) + " °C");
        humidity.setText(String.valueOf(data.humidity) + " %");
        windSpeed.setText(String.valueOf(data.windSpeed) + " km/h");

        String windDirectionLabel = "N";
        HashMap<String, Double> directions = new HashMap<String, Double>();
        directions.put("N", Math.abs(data.windDirection));
        directions.put("E", Math.abs(data.windDirection - 90));
        directions.put("S", Math.abs(data.windDirection - 180));
        directions.put("W", Math.abs(data.windDirection - 270));
        directions.put("NE", Math.abs(data.windDirection - 45));
        directions.put("SE", Math.abs(data.windDirection - 135));
        directions.put("SW", Math.abs(data.windDirection - 225));
        directions.put("NW", Math.abs(data.windDirection - 315));
        directions.put("NNE", Math.abs(data.windDirection - 22.5));
        directions.put("NEE", Math.abs(data.windDirection - 67.5));
        directions.put("SEE", Math.abs(data.windDirection - 112.5));
        directions.put("SSE", Math.abs(data.windDirection - 157.5));
        directions.put("SSW", Math.abs(data.windDirection - 202.5));
        directions.put("SWW", Math.abs(data.windDirection - 247.5));
        directions.put("NWW", Math.abs(data.windDirection - 292.5));
        directions.put("NNW", Math.abs(data.windDirection - 337.5));

        String direction = "N";
        Double dif = directions.get("N");

        for (String i : directions.keySet()) {
            if (dif < directions.get(i)) {
                dif = directions.get(i);
                direction = i;
            }
        }

        windDirection.setText(String.valueOf(direction));
        // Create and configure AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(hourlyDialog);
        builder.setTitle("Additional Data");

        // Set additional data to dialog
        builder.setMessage("Additional hourly data: ");

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


        imageDescription.setText(data.weatherDescription);
        temperature.setText(String.valueOf(data.max_temperature) + " °C");
        temperature_min.setText(String.valueOf(data.min_temperature + " °C"));


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
        builder.setMessage("Additional daily data: ");

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
                        viewModel.getAirQualityForNewCity(data.latitude, data.longitude, data.name);
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
        builder.setMessage("Additional air data: ");

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
