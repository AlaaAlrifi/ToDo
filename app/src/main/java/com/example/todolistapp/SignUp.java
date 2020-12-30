package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText name, email, password;
    Button creat;
    FirebaseAuth Auth;
    TextView logIn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        logIn=findViewById(R.id.login);
        name = findViewById(R.id.create_name);
        email = findViewById(R.id.create_email);
        password = findViewById(R.id.create_password);
        creat = findViewById(R.id.creat_btn);
        Auth = FirebaseAuth.getInstance();
        logIn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(SignUp.this, LogIn.class);
        startActivity(intent);
    }
});
        if (Auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Lists.class));
            finish();


        }
        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameuser = name.getText().toString();
                String useremail = email.getText().toString();
                String userpassword = password.getText().toString();
                if (TextUtils.isEmpty(nameuser)) {
                    name.setError("Error In Name"); }
                if (TextUtils.isEmpty(useremail)) {
                    email.setError("Error In Email"); }
                if (TextUtils.isEmpty(userpassword)) {
                    email.setError("Error In Password"); }

                Auth.createUserWithEmailAndPassword(useremail, userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Sign successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, Lists.class));

                        } else {
                            Toast.makeText(SignUp.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }
                });

            }
        });
    }




}