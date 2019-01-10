package com.EliJosephHebdon.flashchatnewfirebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView mMassChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        mMassChat = (TextView) findViewById(R.id.mass_chat);


        mAuth = FirebaseAuth.getInstance();


        autoLogin();


    }

    private void autoLogin() {

        SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS, MODE_PRIVATE);


        String email = prefs.getString(RegisterActivity.EMAIL_KEY, null);
        String password = prefs.getString(RegisterActivity.PASSWORD_KEY, null);

        if (email == null && password == null){
            Intent intent2 = new Intent (WelcomeScreen.this, LoginActivity.class);
            finish();
            startActivity(intent2);
        }



        else {

            // TODO: Use FirebaseAuth to sign in with email & password
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    Log.d("FlashChat", "signInWithEmail() onComplete: " + task.isSuccessful());

                    if (!task.isSuccessful()) {
                        Log.d("FlashChat", "Problem signing in: " + task.getException());
                        showErrorDialog("There was a problem signing in");
                    } else {
                        Intent intent = new Intent(WelcomeScreen.this, MainChatActivity.class);
                        finish();
                        startActivity(intent);
                    }

                }


            });
        }
    }

    private void showErrorDialog(String message) {

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
