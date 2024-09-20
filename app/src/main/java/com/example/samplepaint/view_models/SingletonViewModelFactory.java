package com.example.samplepaint.view_models;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SingletonViewModelFactory implements ViewModelProvider.Factory {
    private static PaintScreenViewModel instance;

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PaintScreenViewModel.class)) {
            if (instance == null) {
                instance = new PaintScreenViewModel();
            }
            return (T) instance;
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
