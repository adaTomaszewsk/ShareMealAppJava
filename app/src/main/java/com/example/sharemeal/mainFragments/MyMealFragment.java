package com.example.sharemeal.mainFragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.example.sharemeal.activity.MealAdapter;
import com.example.sharemeal.activity.UdateItem;
import com.example.sharemeal.activity.models.MealModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class MyMealFragment extends Fragment {
    Button addNewMeal;
    private RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    MealAdapter adapter;
    private FirebaseAuth mAuth;
    String userid;
    FirebaseUser currentFirebaseUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
    }

    public MyMealFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_meal, container, false);
        addNewMeal=rootView.findViewById(R.id.idAddMeal);
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView = rootView.findViewById(R.id.rv_my_meal);
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

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
        FirestoreRecyclerOptions<MealModel> options = new FirestoreRecyclerOptions.Builder<MealModel>()
                .setQuery(query, MealModel.class)
                .build();


        adapter= new MealAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemButtonClickListener(new MealAdapter.OnItemButtonClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot snapshot, int position) {
                MealModel model=snapshot.toObject(MealModel.class);
                adapter.updateDocument(position);
                Intent intent = new Intent(getActivity(), UdateItem.class);
                intent.putExtra("position",String.valueOf(position));
                startActivity(intent);


            }
        });
        return rootView;
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
