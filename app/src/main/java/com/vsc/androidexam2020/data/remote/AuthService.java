package com.vsc.androidexam2020.data.remote;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vsc.androidexam2020.data.local.User;

public class AuthService {

    private static final String TAG = "AuthService";
    private static final String SERVER_ID = "22814597940-8ss7crcb56ra579mojkrvbvq38ejm8if.apps.googleusercontent.com";
    private static AuthService instance;
    private final GoogleSignInOptions gso;
    private FirebaseAuth auth;

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    private AuthService() {
        auth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(SERVER_ID)
                .requestEmail()
                .build();
    }

    /**
     * Receive the currently logged user object
     *
     * @return the logged user or null if no user is logged
     */
    public User getLoggedUser() {
        return auth.getCurrentUser() != null ? new User(auth.getCurrentUser().getUid(),
                auth.getCurrentUser().getEmail(), auth.getCurrentUser().getDisplayName()) : null;
    }

    public Intent getSignInIntent(Context context) {
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        return mGoogleSignInClient.getSignInIntent();
    }

    public void handleLoginResult(Intent data, LoginListener loginListener) {
        if (data == null) {
            loginListener.onLoginFailed("Intent data was null.");
            return;
        }
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogle(account, loginListener);
        } catch (ApiException e) {
            loginListener.onLoginFailed(e.getLocalizedMessage());
        }
    }

    public void loginWithEmail(String email, String password, LoginListener loginListener) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                loginListener.onLoginSuccessful(getLoggedUser());
            }
        }).addOnFailureListener(e -> {
            if (e instanceof FirebaseAuthInvalidUserException) {
                createUserWithEmail(email, password, loginListener);
            } else {
                loginListener.onLoginFailed(e.getLocalizedMessage());
            }
        });
    }

    private void createUserWithEmail(String email, String password, LoginListener loginListener) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> { if (task.isSuccessful()) loginListener.onLoginSuccessful(getLoggedUser()); })
                .addOnFailureListener(e -> loginListener.onLoginFailed(e.getLocalizedMessage()));
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct, final LoginListener loginListener) {
        if (acct == null) {
            loginListener.onLoginFailed("Google account was null.");
            return;
        }
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && auth.getCurrentUser() != null) {
                        loginListener.onLoginSuccessful(getLoggedUser());
                    } else if (task.getException() != null) {
                        loginListener.onLoginFailed(task.getException().getLocalizedMessage());
                    } else {
                        loginListener.onLoginFailed("Firebase Auth failed");
                    }
                })
                .addOnFailureListener(e -> loginListener.onLoginFailed(e.getLocalizedMessage()));
    }

    public void logout() {
        auth.signOut();
    }

    public interface LoginListener {
        void onLoginSuccessful(User user);

        void onLoginFailed(String error);
    }
}
