package com.example.sharemeal.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharemeal.R;
import com.example.sharemeal.activity.MainActivity;
import com.example.sharemeal.activity.MainActivity2;
import com.example.sharemeal.activity.models.User;
import com.example.sharemeal.mainFragments.MyMealFragment;
import com.example.sharemeal.registration.RegisterActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.rpc.context.AttributeContext;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity{
    private static final int RC_SIGN_IN=123;
    private TextView register,forgotPass;
    Button log,signIn;
    EditText email, pass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleSignInClient mGoogleSignInClient;
    private final String TAG="aaa";
    //String userid;
    public static String userid;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            Intent intent= new Intent(getApplicationContext(), MainActivity2.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = (TextView) findViewById(R.id.tvRegister);
        forgotPass= (TextView)findViewById(R.id.tvForgotPassword);

        log = (Button) findViewById(R.id.btLogin);
        email = (EditText) findViewById(R.id.etEmailLogin);
        pass = (EditText) findViewById(R.id.etPasswordLogin);
        mAuth = FirebaseAuth.getInstance();



        createRequest();

        findViewById(R.id.buttonGoogleLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });


        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail= new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog= new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset password?");
                passwordResetDialog.setMessage("Enter your email to received reset link.");
                passwordResetDialog.setView(resetMail);


                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email= resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this,"Reset email sent to your mail",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,"Error! Link is not sent " + e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString().trim();
                String ps = pass.getText().toString().trim();

                if (em.isEmpty()) {
                    email.setError("Email is required!");
                    email.requestFocus();
                    return;
                }
                if (ps.isEmpty()) {
                    pass.setError("Password is required!");
                    pass.requestFocus();
                    return;
                }


                mAuth.signInWithEmailAndPassword(em, ps).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {



                            Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_LONG).show();
                              startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, "Log in error", Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });

    }

    private void createRequest() {
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient= GoogleSignIn.getClient(this, googleSignInOptions);
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent= new Intent(getApplicationContext(), MainActivity2.class);
                            intent.putExtra("id",userid);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }
}



