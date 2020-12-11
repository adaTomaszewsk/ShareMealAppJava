package com.example.sharemeal.mainFragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.AudioEffect;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharemeal.R;
import com.example.sharemeal.activity.MainActivity;
import com.example.sharemeal.activity.MainActivity2;
import com.example.sharemeal.activity.MealAdapter;
import com.example.sharemeal.activity.models.MealModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class MapFragment extends Fragment {
    private RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    MealAdapter adapter;
    private FirebaseAuth mAuth;
    String userid;
    FirebaseFirestore db;
    FirebaseUser currentFirebaseUser;


    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = rootView.findViewById(R.id.rv_my_map);

        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getCurrentUser().getUid();
        String uId=userid;
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String name=mAuth.getCurrentUser().getDisplayName();

        Query query = firebaseFirestore.collection("meal").whereEqualTo("status", "Dostępne")
                .whereNotEqualTo("userid", userid.toString());
//        Query query=db.collection("meal");
        FirestoreRecyclerOptions<MealModel> options = new FirestoreRecyclerOptions.Builder<MealModel>()
                .setQuery(query, MealModel.class)
                .build();

        adapter= new MealAdapter(options);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
//                                  @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                //adapter.changeStatus1(,viewHolder.getAdapterPosition());
//               // adapter.changeStatus2(viewHolder.getAdapterPosition());
//            }
//        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemButtonClickListener(new MealAdapter.OnItemButtonClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot snapshot, int position) {
                MealModel model=snapshot.toObject(MealModel.class);
                userid = mAuth.getCurrentUser().getUid();
                Toast.makeText(getContext(),"UserId: "+userid,Toast.LENGTH_SHORT).show();


                adapter.changeStatus2(position);
                adapter.changeStatus1(position,userid);
              //  adapter.byWho(position,name);
//

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



//
}

//    private ArrayList<MealModel> createTestMealList() {
//        ArrayList<MealModel> list = new ArrayList<>();
//
//        MealModel mealModel1 = new MealModel();
//        mealModel1.setData("setData");
//        mealModel1.setName("setName");
//        mealModel1.setPickHour("setPickHour");
//        mealModel1.setStatus("setStatus");
//        mealModel1.setUserid("setUserid");
//
//        MealModel mealModel2 = new MealModel();
//        mealModel2.setData("setData");
//        mealModel2.setName("setName");
//        mealModel2.setPickHour("setPickHour");
//        mealModel2.setStatus("setStatus");
//        mealModel2.setUserid("setUserid");
//
//        list.add(mealModel1);
//        list.add(mealModel2);
//        return list;
//    }
