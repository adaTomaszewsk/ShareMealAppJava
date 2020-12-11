package com.example.sharemeal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sharemeal.R;
import com.example.sharemeal.activity.models.MealModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UdateItem extends AppCompatActivity {
    EditText etMealName,etPickupTimes,etPersonalData;
    Button update;
    String userid;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    MealAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udate_item);

        String position = getIntent().getExtras().getString("position");

        etMealName=(EditText)findViewById(R.id.etMealNameU);
        etPersonalData=(EditText)findViewById(R.id.etPersonalDataU);
        etPickupTimes=(EditText)findViewById(R.id.etPickupTimesU);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getCurrentUser().getUid();

        update=(Button)findViewById(R.id.btAddMealU);

//        Query query=firebaseFirestore.collection("meal").whereEqualTo("status","dostepny");
//        FirestoreRecyclerOptions<MealModel> options = new FirestoreRecyclerOptions.Builder<MealModel>()
//                .setQuery(query, MealModel.class)
//                .build();
//
//
//        adapter= new MealAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etMealName.getText().toString();
                String pdata=etPersonalData.getText().toString();
                String pickHour=etPickupTimes.getText().toString();
                String status="Dostępne";
                if(!name.isEmpty() && !pdata.isEmpty() && !pickHour.isEmpty()) {


                    Map<String, Object> meal = new HashMap<>();
                    meal.put("name", name);
                    meal.put("data", pdata);
                    meal.put("pickHour", pickHour);
                    meal.put("status", status);
                    meal.put("userid",userid);
                    meal.put("byWho","");

//
                }else{
                   // Toast.makeText(AddMealActivity.this, "Empty field.", Toast.LENGTH_LONG).show();
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