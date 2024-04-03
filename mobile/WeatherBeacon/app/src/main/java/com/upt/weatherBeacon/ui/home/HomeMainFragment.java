package com.upt.weatherBeacon.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Toast;

import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.databinding.FragmentHomemainBinding;
import com.upt.weatherBeacon.ui.base.BaseFragment;
import com.upt.weatherBeacon.ui.utilities.ScreenUtils;

import java.util.Objects;

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
        Context context = getContext().getApplicationContext();


        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifiedSpace.getLayoutParams().height = ScreenUtils.getScreenHeightInPixels(context)/2 + ScreenUtils.getScreenHeightInPixels(context)/8;
                toggleMenu();
            }
        });

        btnWeatherForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Weather Forecast clicked ", Toast.LENGTH_SHORT).show(); //TODO functionality
                menuLayout.setVisibility(View.GONE);
            }
        });

        btnElevation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Elevation clicked", Toast.LENGTH_SHORT).show();//TODO functionality
                menuLayout.setVisibility(View.GONE);
            }
        });

        btnGeocoding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Geocoding clicked", Toast.LENGTH_SHORT).show();//TODO functionality
                menuLayout.setVisibility(View.GONE);
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
