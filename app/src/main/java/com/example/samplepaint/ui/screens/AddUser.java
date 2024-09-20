package com.example.samplepaint.ui.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samplepaint.R;
import com.example.samplepaint.databinding.FragmentAddUserBinding;
import com.example.samplepaint.db.AppDataBase;
import com.example.samplepaint.db.User;

import java.util.zip.Inflater;

public class AddUser extends Fragment {
    private FragmentAddUserBinding addUserBinding;
    private String userName = "";
    private String password = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addUserBinding = FragmentAddUserBinding.bind(inflater.inflate(R.layout.fragment_add_user, container, false));

        addUserBinding.userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                userName = editable.toString();
            }
        });
        addUserBinding.passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                password = editable.toString();
            }
        });

        addUserBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.uName = userName;
                user.age = 20;
                user.password = password;
                AppDataBase.getInstance(requireActivity()).userDao().insertAll(user);
                Toast.makeText(requireActivity(), "User Created!! Go to Login Page", Toast.LENGTH_SHORT).show();
            }
        });
        return addUserBinding.getRoot();
    }
}