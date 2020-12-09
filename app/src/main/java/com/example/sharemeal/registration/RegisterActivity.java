package com.example.sharemeal.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sharemeal.R;
import com.example.sharemeal.activity.MainActivity;
import com.example.sharemeal.activity.MainActivity2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    EditText name, surname, email, pass, reppass;
    Button register;
    ProgressBar progressBar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        register=(Button)findViewById(R.id.btnRegister);

        name=(EditText)findViewById(R.id.etName);
        surname=(EditText)findViewById(R.id.etSurname);
        email=(EditText)findViewById(R.id.etEmailRegister);
        pass=(EditText)findViewById(R.id.etPasswordRegister);
        reppass=(EditText)findViewById(R.id.etRepeatPasswordRegister);





        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            finish();
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }



        private void registerUser(){
        //trim usuwa puste znaki spacji z końca linii
            String nm=name.getText().toString().trim();
            String srnm=surname.getText().toString().trim();
            String em=email.getText().toString().trim();
            String ps=pass.getText().toString().trim();
            String ps2=reppass.getText().toString().trim();


            //|| srnm.isEmpty() || em.isEmpty() || ps.isEmpty() || ps2.isEmpty())
            if(nm.isEmpty()){
                name.setError("Name is required!");
                name.requestFocus();
                return;
            }if(srnm.isEmpty()){
                surname.setError("Surname is required!");
                surname.requestFocus();
                return;
            }if(em.isEmpty()){
                email.setError("Email is required!");
                email.requestFocus();
                return;
            }if(ps.isEmpty()){
                pass.setError("Password is required!");
                pass.requestFocus();
                return;
            }if(ps2.isEmpty()){
                reppass.setError("Password is required!");
                reppass.requestFocus();
                return;
            }
            if (!ps.equals(ps2)) {
                Toast.makeText(this,"Passwords are not the same!",Toast.LENGTH_LONG ).show();
                pass.setText("");
                reppass.setText("");
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(em).matches()){
                email.setText("Please provide valid email!");
                email.requestFocus();
                return;
            }if(pass.length()<6){
                pass.setError("Min password lenght should be 6 characters!");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(em, ps).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){


                        Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                        intent.putExtra("EXTRA", "MyMealFragment");
                        startActivity(intent);
                    }else{
                        Toast.makeText(RegisterActivity.this, "Register error", Toast.LENGTH_LONG).show();
                    }
                }

            });

        }
}
