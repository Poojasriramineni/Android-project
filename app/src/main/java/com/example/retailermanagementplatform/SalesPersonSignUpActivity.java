package com.example.retailermanagementplatform;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class SalesPersonSignUpActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_person_sign_up);

        // Linking XML elements with Java
        nameEditText = findViewById(R.id.editText_name);
        signUpButton = findViewById(R.id.signUp_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform actions when the "Sign up" button is clicked
                String enteredName = nameEditText.getText().toString();

                // Example: Print the entered name
                if (!enteredName.isEmpty()) {
                    System.out.println("Entered Name: " + enteredName);
                    // You can add further functionality here, such as sending the data to a server or processing it.
                } else {
                    // If the name field is empty, you can prompt the user to enter a name.
                    nameEditText.setError("Please enter a name");
                }
            }
        });
    }
}