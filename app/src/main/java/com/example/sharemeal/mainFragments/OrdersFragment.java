package com.example.sharemeal.mainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharemeal.R;
import com.example.sharemeal.activity.MealAdapter;
import com.example.sharemeal.activity.models.MealModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class OrdersFragment extends Fragment {
    private RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    private FirebaseAuth mAuth;
    String userid, idMeal;
    Button btn;
    FirebaseFirestore db;
    DocumentReference docRef;


    public OrdersFragment() {
    }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
            }
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_orders, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = rootView.findViewById(R.id.rv_my_order);
        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getCurrentUser().getUid();
        btn = getActivity().findViewById(R.id.btnChangeName);

        DocumentReference ref=FirebaseFirestore.getInstance().collection("meal").document();
//        ref.get().addOnSuccessListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot doc=task.getResult();
//
//                }
//            }
//        }){

 //       }


        Query query = firebaseFirestore.collection("meal")
                .whereEqualTo("byWho",userid.toString())
                .whereEqualTo("status","Zarezerwowane");

        FirestoreRecyclerOptions<MealModel> options = new FirestoreRecyclerOptions.Builder<MealModel>()
                .setQuery(query, MealModel.class)
                .build();

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                String idMealItem = firebaseFirestore.collection("meal").document().getId();
//                Log.d("Your key my Lord: ",idMealItem);
//                docRef=FirebaseFirestore.getInstance()
//                        .collection("meal")
//                        .document(idMealItem);
//
//                Toast.makeText(getContext(),"My Lord, your answer is: "+ idMealItem,Toast.LENGTH_SHORT ).show();
//
//                Map<String, Object> map= new HashMap<>();
//                map.put("status","Zarezerwowane");
//
//                docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getContext(),"Hurra!",Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//            }
//        });

        adapter = new FirestoreRecyclerAdapter<MealModel, OrdersFragment.MealViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull OrdersFragment.MealViewHolder holder, int i, @NonNull MealModel mealModel) {
                holder.name.setText(mealModel.getName());
                holder.data.setText(mealModel.getData());
                holder.status.setText(mealModel.getStatus());
                holder.pickHour.setText(mealModel.getPickHour());
                //  holder.idMeal.setText(mealModel.getIdMeal());
                holder.btn.setText("Wykonane");
            }

            @NonNull
            @Override
            public OrdersFragment.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutcard, parent, false);
                return new OrdersFragment.MealViewHolder(view);
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
        private Button btn;
        private TextView idMeal;


        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            idMeal=itemView.findViewById(R.id.tv_meal_name);
            name = itemView.findViewById(R.id.tv_meal_name);
            data = itemView.findViewById(R.id.tv_restInformation);
            status = itemView.findViewById(R.id.tv_status);
            pickHour = itemView.findViewById(R.id.tv_pickup_h);
            btn = itemView.findViewById(R.id.btnChangeName);
            idMeal=itemView.findViewById(R.id.tv_meal_name);



//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
//                    String idMealItem = firebaseFirestore.collection("meal").document().getId();
//                    Log.d("Your key my Lord: ",idMealItem);
//                    docRef=FirebaseFirestore.getInstance()
//                            .collection("meal")
//                            .document(idMealItem);
//
//                    Toast.makeText(getContext(),"My Lord, your answer is: ",Toast.LENGTH_SHORT ).show();
//
//                    Map<String, Object> map= new HashMap<>();
//                    map.put("status","Zarezerwowane");
//
//                    docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(getContext(),"Hurra!",Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                        }
//                    });
//                }
//            });
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
