package com.example.schedulejgi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static com.google.android.gms.tasks.TaskExecutors.MAIN_THREAD;

public class Verification extends AppCompatActivity {
    String verificationcodeBysystem;
  Button verify_btn;
  EditText editText;
  ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        verify_btn = findViewById(R.id.verify_btn);
        editText = findViewById(R.id.verify_edit);
        bar = findViewById(R.id.bar);
        bar.setVisibility(View.GONE);
        String Mobile = getIntent().getStringExtra("mobile");
        sendVerificationCode(Mobile);
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editText.getText().toString();
                if(code.isEmpty() || code.length()<6){
                    editText.setError("Wrong Otp");
                    editText.requestFocus();
                    return;
                }
                bar.setVisibility(View.VISIBLE);
                verfiy_code(code);
            }
        });

    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                10,
                TimeUnit.SECONDS,
                this,
                mCallBacks //Object of OnVerificationStateChangedCallbacks
        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationcodeBysystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
          String code = phoneAuthCredential.getSmsCode();
          if(code!=null){
              bar.setVisibility(View.VISIBLE);
              verfiy_code(code);
          }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(Verification.this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verfiy_code(String verificationCode){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationcodeBysystem,verificationCode);
        signByCreditenial(credential);

    }

    private void signByCreditenial(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Verification.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(),AboutPage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Verification.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}