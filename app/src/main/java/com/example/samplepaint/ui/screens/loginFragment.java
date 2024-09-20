package com.example.samplepaint.ui.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samplepaint.Login;
import com.example.samplepaint.MainActivity;
import com.example.samplepaint.R;
import com.example.samplepaint.databinding.FragmentLoginBinding;
import com.example.samplepaint.db.AppDataBase;
import com.example.samplepaint.db.User;
import com.example.samplepaint.db.UserDao;
import com.example.samplepaint.view_models.PaintScreenViewModel;
import com.example.samplepaint.view_models.SingletonViewModelFactory;

import java.util.List;
import java.util.Objects;

public class loginFragment extends Fragment {
    private FragmentLoginBinding loginBinding;
    private String userName = "";
    private String password = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginBinding = FragmentLoginBinding.bind(inflater.inflate( R.layout.fragment_login, container, false));
        PaintScreenViewModel paintScreenViewModel = new ViewModelProvider(this, new SingletonViewModelFactory()).get(PaintScreenViewModel.class);
        loginBinding.txtAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddUser();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.login_container, fragment).commit();
            }
        });

        loginBinding.userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                userName = editable.toString();
            }
        });
        loginBinding.passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                password = editable.toString();
            }
        });


        loginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(userName.isEmpty() || password.isEmpty())){
                    AppDataBase appDataBase = AppDataBase.getInstance(requireContext());
                    List<User> user = appDataBase.userDao().getAll();
                    boolean matchUser = false;
                    for(int i = 0; i < user.size(); i++){
                        if((Objects.equals(user.get(i).uName, userName))&&(Objects.equals(user.get(i).password, password))){
                            User tempUser = new User();
                            tempUser.uId = user.get(i).uId;
                            tempUser.uName = userName;
                            tempUser.password = password;
                            paintScreenViewModel.user = tempUser;
                            paintScreenViewModel.updateUser(tempUser);
                            matchUser = true;
                            break;
                        }
                    }
                    if(matchUser) {
                        Intent intent = new Intent(requireActivity(), MainActivity.class);
                        intent.putExtra("message", loginBinding.userName.getText().toString());
                        startActivity(intent);
                    }else {
                        Toast.makeText(requireActivity(), "User Name And PassWord Not Matched", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(requireActivity(), "Please Enter User Name And Pass", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return loginBinding.getRoot();
    }
}