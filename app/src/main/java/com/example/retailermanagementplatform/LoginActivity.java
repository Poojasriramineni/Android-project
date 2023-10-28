package com.example.retailermanagementplatform;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;

public class LoginActivity extends Activity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Make sure the XML layout file is named "activity_login.xml"

        // Initialize views
        emailEditText = findViewById(R.id.editText);
        passwordEditText = findViewById(R.id.editText2);
        loginButton = findViewById(R.id.loginButton);

        // Set click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the login button click here
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // You can add your login logic here
                // For example, you can validate the email and password and authenticate the user
            }
        });
    }
}

