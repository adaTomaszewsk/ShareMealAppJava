package com.example.sharemeal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharemeal.R;
import com.example.sharemeal.activity.models.MealModel;
import com.example.sharemeal.mainFragments.MapFragment;
import com.example.sharemeal.mainFragments.MyMealFragment;
import com.example.sharemeal.mainFragments.OrdersFragment;
import com.example.sharemeal.mainFragments.SettingsFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private final String COLL_MEAL = "meal";
    private final String KEY_MEAL_ITEM_ID = "idMeal";
    private final String KEY_MEAL_NAME = "name";
    private final String KEY_MEAL_DATA = "data";
    private final String KEY_MEAL_HOURS = "hours";
    private final String KEY_MEAL_STATUS = "status";
    FirebaseAuth mAuth;


//    public final String userid = mAuth.getCurrentUser().getUid();


    private RecyclerView recyclerMeal;
    private MealAdapter mealAdapter;
//    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvNoInternetText;
//    private FloatingActionButton btnAddItem;
    private MealModel mealItem;
    private ArrayList<MealModel> mealList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


//        String ID_user;
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if(extras == null) {
//                ID_user= null;
//            } else {
//                ID_user= extras.getString("STRING_I_NEED");
//            }
//        } else {
//            ID_user= (String) savedInstanceState.getSerializable("STRING_I_NEED");
//        }



        GoogleSignInAccount googleSignInAccount= GoogleSignIn.getLastSignedInAccount(this);
        if(googleSignInAccount!= null){
            String userName=googleSignInAccount.getDisplayName();
//            Toast.makeText(this,"Hello "+userName,Toast.LENGTH_LONG).show();
        }



        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);


    }
    private void setUpRecyclerView() {
        mealList = new ArrayList<>();
        //mealAdapter = new MealAdapter(mealList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerMeal.setHasFixedSize(true);
        recyclerMeal.setLayoutManager(linearLayoutManager);
        recyclerMeal.setAdapter(mealAdapter);
        recyclerMeal.setItemAnimator(new DefaultItemAnimator());
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;
                    switch (item.getItemId()){
                        case R.id.meals:
                            selectedFragment = new MyMealFragment();
                            break;
                        case R.id.orders:
                            selectedFragment = new OrdersFragment();
                            break;
                        case R.id.search:
                            selectedFragment = new MapFragment();
                            break;
                        case R.id.settings:
                            selectedFragment = new SettingsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fr, selectedFragment).commit();
                    return true;
                }
            };
}

