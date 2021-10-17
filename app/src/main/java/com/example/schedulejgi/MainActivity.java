package com.example.schedulejgi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button signup;
    TextInputLayout mobile,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        signup = findViewById(R.id.signup);
        signup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,SignUp.class);
            startActivity(intent);
        });
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
    }
    private boolean validateMobile(){
        String val = mobile.getEditText().getText().toString();
        if(val.isEmpty()){
            mobile.setError("Field cannot be empty");
            return false;
        }else{
            mobile.setError(null);
            mobile.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String val = password.getEditText().getText().toString();
        if(val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        if (!validateMobile() | !validatePassword()) {
            return;
        }
        String userEnteredMobile = mobile.getEditText().getText().toString().trim();
        String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("mobile").equalTo(userEnteredMobile);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    mobile.setError(null);
                    mobile.setErrorEnabled(false);
                    String passwordFromDB = snapshot.child(userEnteredMobile).child("password").getValue(String.class);
                    if (passwordFromDB!=null&&passwordFromDB.equals(userEnteredPassword)) {
                        password.setError(null);
                        password.setErrorEnabled(false);
                        Intent intent = new Intent(getApplicationContext(),AboutPage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                } else {
                    mobile.setError("Wrong credentials");
                    mobile.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
