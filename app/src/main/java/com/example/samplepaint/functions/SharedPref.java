package com.example.samplepaint.functions;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class SharedPref {
    Context context;
    public SharedPref(Context context) {
        this.context = context;
    }

    public void setDefaultColor() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getPackageName(),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("MyColorPickerDialog_COLOR", Color.BLACK);
        editor.putInt("MyColorPickerDialog_SLIDER_BRIGHTNESS", 0);
        editor.apply();
    }

}
