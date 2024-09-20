package com.example.samplepaint;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.samplepaint.db.AppDataBase;
import com.example.samplepaint.db.User;
import com.example.samplepaint.ui.screens.HomeFragment;
import com.example.samplepaint.ui.screens.PaintViewFragment;
import com.example.samplepaint.view_models.PaintScreenViewModel;
import com.example.samplepaint.databinding.ActivityMainBinding;
import com.example.samplepaint.view_models.SingletonViewModelFactory;


public class MainActivity extends AppCompatActivity {

    // Initialize variables
    ActivityMainBinding activityMainBinding;
    Intent intent;

    @SuppressLint("SetTextI18n")
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();

        // bind main activity
        activityMainBinding =ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(activityMainBinding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        activityMainBinding.userName.setText("Hello, "+intent.getStringExtra("message"));
        PaintScreenViewModel paintScreenViewModel = new ViewModelProvider(this, new SingletonViewModelFactory()).get(PaintScreenViewModel.class);
        if(!paintScreenViewModel.isAppInitialized){
            setDefaultColor();
            paintScreenViewModel.isAppInitialized = true;
        }
        activityMainBinding.userSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.update_user_dialog);
                EditText userNameTxt = (EditText) dialog.findViewById(R.id.user_name);
                userNameTxt.setText(paintScreenViewModel.user.uName);
                EditText passwordText = (EditText) dialog.findViewById(R.id.passwordInput);
                passwordText.setText(paintScreenViewModel.user.password);
                Button updateButton = (Button) dialog.findViewById(R.id.btn_update);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        User user = new User();
                        user.uId = paintScreenViewModel.user.uId;
                        user.uName = userNameTxt.getText().toString();
                        user.password = passwordText.getText().toString();
                        AppDataBase.getInstance(MainActivity.this).userDao().updateAll(user);
                        paintScreenViewModel.user = user;
                        paintScreenViewModel.updateUser(user);
                        Toast.makeText(MainActivity.this, "User Updated!! Go to Login Page", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                TextView deleteText = (TextView) dialog.findViewById(R.id.txt_delete_user);

                deleteText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        User user = new User();
                        user.uId = paintScreenViewModel.user.uId;
                        user.uName = paintScreenViewModel.user.uName;
                        user.password = paintScreenViewModel.user.password;
                        AppDataBase.getInstance(MainActivity.this).userDao().deleteUser(user);
                        paintScreenViewModel.updateUser(new User());
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
        activityMainBinding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFragment(new HomeFragment());
            }
        });
        activityMainBinding.btnPaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFragment(new PaintViewFragment());
            }
        });

        paintScreenViewModel.userLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                activityMainBinding.userName.setText(user.uName);
            }
        });
    }

    private void gotoFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

    }

    public void setDefaultColor() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                getPackageName(),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("MyColorPickerDialog_COLOR", Color.BLACK);
        editor.putInt("MyColorPickerDialog_SLIDER_BRIGHTNESS", 0);
        editor.apply();
    }

//    @SuppressLint("MissingSuperCall")
//    @Override
//    public void onBackPressed() {
//        moveTaskToBack(true);
//    }
}