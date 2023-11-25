package com.example.retailermanagementplatform;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.retailermanagementplatform.ManagerMain;
import com.example.retailermanagementplatform.R;
import com.example.retailermanagementplatform.SalesManager;
import com.example.retailermanagementplatform.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

public class ManagerSignupActivity extends AppCompatActivity {

    private TextInputEditText name_field;
    private TextInputEditText email_field;
    private TextInputEditText num_field;
    private TextInputEditText org_field;
    private TextInputEditText password_field;
    private TextView login;
    private Button signUp_button;
    private DatabaseReference databaseRef;
    private FirebaseAuth auth;
    private int tmp = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_manager);

        auth = FirebaseAuth.getInstance();
        login=findViewById(R.id.login);
        signUp_button=findViewById(R.id.signUp_button);
        name_field = findViewById(R.id.name);
        email_field = findViewById(R.id.emailid);
        num_field = findViewById(R.id.mobile);
        org_field = findViewById(R.id.organisation_name);
        password_field = findViewById(R.id.password);


        signUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                write_data();
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerSignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // for underline purpose(UI)
        SpannableString text = new SpannableString("Login");
        text.setSpan(new UnderlineSpan(), 0, 5, 0);
        login.setText(text);
    }

    void write_data(){


        final String name = name_field.getText().toString();
        final String email = email_field.getText().toString();
        final String num = num_field.getText().toString();
        final String password = password_field.getText().toString();
        final String org = org_field.getText().toString();

        //Empty check
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)
                || TextUtils.isEmpty(num) || TextUtils.isEmpty(org)){

            Toast.makeText(getApplicationContext(), "All fields not filled !!", Toast.LENGTH_SHORT).show();
            return;
        }

        //password length check
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        // mobile number check
        if(!isValidMobile(num)){
            Toast.makeText(getApplicationContext(), "Invalid mobile number !!", Toast.LENGTH_SHORT).show();
            return;
        }
        // email check
        if(!isValidEmail(email)){
            Toast.makeText(getApplicationContext(), "Invalid email !!", Toast.LENGTH_SHORT).show();
            return;
        }


        databaseRef = FirebaseDatabase.getInstance().getReference("Manager");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    SalesManager sm = dataSnapshot1.getValue(SalesManager.class);
                    if(sm.getEmail().equals(email)){
                        Toast.makeText(getApplicationContext(),"This Email-id is already registered !!", Toast.LENGTH_LONG).show();
                        tmp = -1;
                        break;
                    }
                }
                if(tmp != -1){

                    SalesManager salesManager = new SalesManager(name, num, password, email, org);
                    //Added data to database
                    databaseRef = FirebaseDatabase.getInstance().getReference("Manager");
                    String key = databaseRef.push().getKey();
                    databaseRef.child(key).setValue(salesManager);

                    // session created using sharedPreference
                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                    sessionManager.createLoginSession(key,"Manager");

                    //register user on fireBase Authentication

                    //create user
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(ManagerSignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(ManagerSignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(ManagerSignupActivity.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();

                                    }
                                    else {

                                        startActivity(new Intent(ManagerSignupActivity.this, ManagerMain.class));
                                        finish();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidMobile(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ManagerSignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
