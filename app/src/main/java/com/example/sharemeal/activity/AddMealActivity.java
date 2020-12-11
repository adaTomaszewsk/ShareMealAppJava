package com.example.sharemeal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sharemeal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class AddMealActivity extends AppCompatActivity {
    EditText etMealName,etPickupTimes,etPersonalData;
    Button create;
    String userid;
    private FirebaseAuth mAuth;
    DocumentReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        etMealName=(EditText)findViewById(R.id.etMealNameU);
        etPersonalData=(EditText)findViewById(R.id.etPersonalDataU);
        etPickupTimes=(EditText)findViewById(R.id.etPickupTimesU);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getCurrentUser().getUid();

        create=(Button)findViewById(R.id.btAddMealU);
        ref=db.collection("meal").document();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etMealName.getText().toString();
                String pdata=etPersonalData.getText().toString();
                String pickHour=etPickupTimes.getText().toString();
                String status="Dostępne";
                if(!name.isEmpty() && !pdata.isEmpty() && !pickHour.isEmpty()) {

                String randomId=generateString();

                    Map<String, Object> meal = new HashMap<>();
                    meal.put("name", name);
                    meal.put("data", pdata);
                    meal.put("pickHour", pickHour);
                    meal.put("status", status);
                    meal.put("userid",userid);
                    meal.put("byWho","");

                    db.collection("meal")
                            .add(meal)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    Log.d("Aa", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    String a=documentReference.getId();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }else{
                    Toast.makeText(AddMealActivity.this, "Empty field.", Toast.LENGTH_LONG).show();
                }
                etMealName.setText("");
                etPickupTimes.setText("");
                etPersonalData.setText("");
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName().toString();
            String email = user.getEmail();

        } else {
            // No user is signed in
        }

    }
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}