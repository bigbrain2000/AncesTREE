package com.upt.weatherBeacon.ui.base;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.upt.weatherBeacon.ui.base.navigation.UiEvent;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<T extends BaseViewModel> extends Fragment {
    public BaseViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        viewModel.uiEventStream.observe(this, uiEvent -> processUiEvent(uiEvent));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        initUi();
        setObservers();
    }

    protected abstract void setObservers();

    protected abstract void initUi();

    private Class<BaseViewModel> getViewModelClass(){
        Type genericSuperclass = getClass().getGenericSuperclass();

        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();

            if (actualTypeArguments.length > 0 && actualTypeArguments[0] instanceof Class) {
                return (Class<BaseViewModel>) actualTypeArguments[0];
            }
        }

        // Handle the case where the generic type cannot be extracted
        throw new IllegalStateException("Unable to extract generic type");
    }

    protected void processUiEvent(UiEvent uiEvent){
        if (this.getActivity() instanceof BaseActivity) {
            ((BaseActivity<T>) this.getActivity()).processUiEvent(uiEvent);
        }
    }
}

