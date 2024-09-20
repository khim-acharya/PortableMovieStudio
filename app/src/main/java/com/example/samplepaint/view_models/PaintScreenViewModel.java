package com.example.samplepaint.view_models;


import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.samplepaint.db.User;
import com.example.samplepaint.entities.LineList;

import java.util.ArrayList;

public class PaintScreenViewModel extends ViewModel{

//    private static PaintScreenViewModel instance;
//    PaintScreenViewModel(){}
//
//    public static PaintScreenViewModel getInstance(){
//        if(instance == null){
//            instance = new PaintScreenViewModel();
//        }
//        return instance;
//    }
    public ArrayList<LineList> lineList = new ArrayList<>();
    //private final MutableLiveData<UIState> _uiState = new MutableLiveData<>();
    //LiveData<UIState> uiState = _uiState;
    public Paint paintState = setPaintState(true, Color.BLACK,10);
    public boolean isAppInitialized = false;
    public User user = new User();
    private final MutableLiveData<User> _userLiveData = new MutableLiveData<>();
    public LiveData<User> userLiveData = _userLiveData;


//    public void updateUIState(boolean undoBtn, boolean saveBtn, boolean colorBtn, boolean brushBtn) {
//        _uiState.setValue(new UIState(
//                !undoBtn,
//                !saveBtn,
//                !colorBtn,
//                !brushBtn
//        ));
//    }
    public void updateUser(User user) {
        _userLiveData.setValue(user);
    }
    public Paint setPaintState(boolean reset, int color, int size) {
        Paint defaultPaintState = new Paint();
        if(reset){
            defaultPaintState.setStyle(Paint.Style.STROKE);
            defaultPaintState.setStrokeJoin(Paint.Join.ROUND);
            defaultPaintState.setStrokeCap(Paint.Cap.ROUND);
            defaultPaintState.setAntiAlias(true);
            defaultPaintState.setColor(color);
            defaultPaintState.setStrokeWidth(size);
        }else {
            paintState = new Paint();
            paintState.setStyle(Paint.Style.STROKE);
            paintState.setStrokeJoin(Paint.Join.ROUND);
            paintState.setStrokeCap(Paint.Cap.ROUND);
            paintState.setAntiAlias(true);
            paintState.setColor(color);
            paintState.setStrokeWidth(size);
        }
        return defaultPaintState;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("MyViewModel", "ViewModel is cleared");
    }
}



