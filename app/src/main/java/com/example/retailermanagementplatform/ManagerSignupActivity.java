package com.example.retailermanagementplatform;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManagerSignupActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_signup);

        // Initialize UI elements
        usernameEditText = findViewById(R.id.editText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signUp_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the sign-up button click here
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // You can implement your sign-up logic here, e.g., send data to a server or save it locally.

                // For now, let's display a toast message with the entered username and password.
                String message = "Username: " + username + "\nPassword: " + password;
                Toast.makeText(ManagerSignupActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}