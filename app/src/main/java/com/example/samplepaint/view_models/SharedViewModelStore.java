package com.example.samplepaint.view_models;

import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import kotlin.Suppress;


public class SharedViewModelStore extends ViewModelStore {
    private static SharedViewModelStore instance;

    private SharedViewModelStore() {
    }

    public static SharedViewModelStore getInstance() {
        if (instance == null) {
            synchronized (SharedViewModelStore.class) {
                if (instance == null) {
                    instance = new SharedViewModelStore();
                }
            }
        }
        return instance;
    }
}

