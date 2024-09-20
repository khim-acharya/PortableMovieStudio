package com.example.samplepaint;

import static android.text.Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.samplepaint.databinding.ActivityLoginBinding;
import com.example.samplepaint.db.AppDataBase;
import com.example.samplepaint.db.User;
import com.example.samplepaint.db.UserDao;
import com.example.samplepaint.ui.screens.AddUser;
import com.example.samplepaint.ui.screens.loginFragment;
import com.example.samplepaint.view_models.PaintScreenViewModel;
import com.example.samplepaint.view_models.SingletonViewModelFactory;

import java.util.List;
import java.util.Objects;

public class Login extends AppCompatActivity {
    private ActivityLoginBinding activityLoginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(activityLoginBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Fragment mFragment = new loginFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.login_container, mFragment).commit();
    }
}