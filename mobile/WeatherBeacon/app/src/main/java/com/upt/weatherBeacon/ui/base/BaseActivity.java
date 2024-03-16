package com.upt.weatherBeacon.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.upt.weatherBeacon.R;
import com.upt.weatherBeacon.ui.base.navigation.NavAttribs;
import com.upt.weatherBeacon.ui.base.navigation.Screen;
import com.upt.weatherBeacon.ui.base.navigation.UiEvent;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {
    public Context context;
    public VM viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getContentView());
        viewModel = new ViewModelProvider(this).get(getViewModelClass());

        setupUi();
        setupObservers();
    }

    private Class<VM> getViewModelClass() {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (Class<VM>) type;
    }

    public void processUiEvent(UiEvent event) {
        if (event instanceof Navigation) {
            onNavigationEvent(((Navigation) event).navAttirbs);
        } else if (event instanceof NavBack) {
            getOnBackPressed();
        } else if (event instanceof ShowToast) {
            showToast((((ShowToast) event).message));
        }
    }

    private void showToast(String message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getOnBackPressed() {
        int fragmentsNumber = this.getSupportFragmentManager().getBackStackEntryCount();
        if(fragmentsNumber == 0){
            finish();
        }else if(fragmentsNumber > 0){
            getSupportFragmentManager().popBackStackImmediate();
        }else{
            super.onBackPressed();
        }
    }


    private void onNavigationEvent(NavAttribs navAttirbs) {
        handleNavigationEvent(navAttirbs);
    }

    private void handleNavigationEvent(NavAttribs navAttirbs) {
        Screen screen = navAttirbs.screen;
        if (screen != null) {
            try {
                Fragment fragment = screen.fragmentClass.newInstance();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                fragmentTransaction.setCustomAnimations(
                        R.anim.fade_in, // enter animation
                        R.anim.fade_out, // exit animation
                        R.anim.slide_in_left, // pop-enter animation
                        R.anim.slide_out_right // pop-exit animation

                );
                fragmentTransaction.replace(R.id.frame, fragment, screen.name());
                if (navAttirbs.addToBackStack) {
                    fragmentTransaction.addToBackStack(screen.name());
                }
                fragmentTransaction.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        context = this;
    }

    private void setupObservers() {
    }

    public void setupUi() {

    }

    public abstract int getContentView();
}
