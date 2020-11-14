package com.vsc.androidexam2020.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.vsc.androidexam2020.data.remote.AuthService;
import com.vsc.androidexam2020.R;
import com.vsc.androidexam2020.data.local.User;
import com.vsc.androidexam2020.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 11;
    private ActivityLoginBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        dataBinding.btnLoginGoogle.setOnClickListener(v -> signInWithGoogle());
        // TODO Ex 1
    }

    //If user has logged in before, automatically redirect to next screen
    @Override
    public void onStart() {
        super.onStart();
        if(AuthService.getInstance().getLoggedUser() != null) {
            onSignInSuccessful();
        }
    }

    private void signInWithGoogle() {
        startActivityForResult(AuthService.getInstance().getSignInIntent(this), RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            AuthService.getInstance().handleLoginResult(data, new AuthService.LoginListener() {
                @Override
                public void onLoginSuccessful(User user) {
                    onSignInSuccessful();
                }

                @Override
                public void onLoginFailed(String error) {
                    onSignInFailure(error);
                }
            });
        }
    }

    public void onSignInSuccessful() {
        // TODO Ex 2
    }

    public void onSignInFailure(String error) {
        Toast.makeText(this, "Sign is failed: " + error, Toast.LENGTH_SHORT).show();
    }

}
