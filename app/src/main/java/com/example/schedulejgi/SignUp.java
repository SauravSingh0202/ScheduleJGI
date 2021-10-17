package com.example.schedulejgi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    TextInputLayout name,email,mobile,password,address,usertype;
  FirebaseDatabase rootNode;
  DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        usertype = findViewById(R.id.VisterType);

    }
    public void signToUser(View view){
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    private boolean validateName(){
        String val = name.getEditText().getText().toString();
        if(val.isEmpty()){
            name.setError("Field cannot be empty");
            return false;
        }else{
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEmail(){
        String val = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            email.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)){
           email.setError("Invalid email id");
           return false;
        }
        else{
            email.setError(null);
            return true;
        }
    }
    private boolean validateMobile(){
        String val = mobile.getEditText().getText().toString();
        if(val.isEmpty()){
            mobile.setError("Field cannot be empty");
            return false;
        }else{
            mobile.setError(null);
            return true;
        }
    }
    private boolean validatePassword(){
        String val = password.getEditText().getText().toString();
        String passwordVal = "^"+
                ".{4,}"+"$";
        if(val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }else if (!val.matches(passwordVal)){
            password.setError("Password too weak");
            return false;
        }
        else{
            password.setError(null);
            return true;
        }
    }
    private boolean validateAddress(){
        String val = address.getEditText().getText().toString();
        if(val.isEmpty()){
            address.setError("Field cannot be empty");
            return false;
        }else{
            address.setError(null);
            return true;
        }
    }
    private boolean validateUserType(){
        String val = usertype.getEditText().getText().toString();
        if(val.isEmpty()){
            usertype.setError("Field cannot be empty");
            return false;
        }else{
            usertype.setError(null);
            return true;
        }
    }
    public void registerUser(View view){

        if (!validateName()| !validateEmail()| !validateMobile()| !validatePassword()| !validateAddress()| !validateUserType()){
            return;
        }
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        String Name = name.getEditText().getText().toString();
        String Mobile = mobile.getEditText().getText().toString();
        String Email = email.getEditText().getText().toString();
        String Password = password.getEditText().getText().toString();
        String Address = address.getEditText().getText().toString();
        String UserType = usertype.getEditText().getText().toString();
       UserHelper helper = new UserHelper(Name,Email,Mobile,Password,Address,UserType);
       reference.child(Mobile).setValue(helper);
        Intent intent = new Intent(getApplicationContext(),Verification.class);
        intent.putExtra("mobile",Mobile);
        startActivity(intent);

    }
}
