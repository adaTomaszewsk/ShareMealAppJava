package com.example.sharemeal.mainFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sharemeal.R;
import com.example.sharemeal.activity.AddMealActivity;
import com.example.sharemeal.activity.models.MealModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class MyMealFragment extends Fragment {
    String user;
    Button addNewMeal;
    String TAG="MyMealFragment";
    private RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    private FirebaseAuth mAuth;
    String userid,idMeal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_meal, container, false);
        addNewMeal=rootView.findViewById(R.id.idAddMeal);
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView = rootView.findViewById(R.id.rv_my_meal);
        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getCurrentUser().getUid();

        addNewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddMealActivity.class);
                startActivity(intent);
            }
        });

        Query query=firebaseFirestore.collection("meal").whereEqualTo("userid",userid.toString());
        FirestoreRecyclerOptions<MealModel> options= new FirestoreRecyclerOptions.Builder<MealModel>()
                .setQuery(query, MealModel.class)
                .build();



        adapter= new FirestoreRecyclerAdapter<MealModel, MealViewHolder>(options) {
            @NonNull
            @Override
            public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutcard,parent, false);
                return new MealViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MealViewHolder holder, int i, @NonNull MealModel mealModel) {
                holder.name.setText(mealModel.getName());
                holder.data.setText(mealModel.getData());
                holder.status.setText(mealModel.getStatus());
                holder.pickHour.setText(mealModel.getPickHour());
            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return rootView;
    }

    private class MealViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView data;
        private TextView status;
        private TextView pickHour;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_meal_name);
            data = itemView.findViewById(R.id.tv_restInformation);
            status = itemView.findViewById(R.id.tv_status);
            pickHour = itemView.findViewById(R.id.tv_pickup_h);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
