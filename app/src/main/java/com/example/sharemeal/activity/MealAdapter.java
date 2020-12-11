package com.example.sharemeal.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemeal.R;
import com.example.sharemeal.activity.models.MealModel;
import com.example.sharemeal.mainFragments.MapFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MealAdapter extends FirestoreRecyclerAdapter<MealModel, MealAdapter.MealHolder>{
    private OnItemButtonClickListener listener;


    public MealAdapter(@NonNull FirestoreRecyclerOptions<MealModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MealHolder mealHolder, int position, @NonNull MealModel mealModel) {
//         mealModel = mealsList.get(position);
//        if (mealHolder instanceof MealHolder) {
            MealHolder mealViewHolder = (MealHolder) mealHolder;
            mealViewHolder.tvMealName.setText(mealModel.getName());
            mealViewHolder.tvHours.setText(mealModel.getPickHour());
            mealViewHolder.tvInfo.setText(mealModel.getData());
            mealHolder.tvStatus.setText(mealModel.getStatus());
//            mealViewHolder.btnChangeName.setOnClickListener(view -> {
//                Log.d("COS", String.valueOf(position));
//
//
//            });
//        }
    }

    @NonNull
    @Override
    public MealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutcard, parent, false);
        return new MealHolder(view);
    }

    class MealHolder extends RecyclerView.ViewHolder{
            public Button btnChangeName;
            public TextView tvMealName, tvHours, tvInfo, tvStatus;
            public View view;

            public MealHolder(@NonNull View itemView) {
                super(itemView);

                tvMealName = itemView.findViewById(R.id.tv_meal_name);
                tvHours = itemView.findViewById(R.id.tv_pickup_h);
                tvInfo = itemView.findViewById(R.id.tv_restInformation);
                tvStatus = itemView.findViewById(R.id.tv_status);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION && listener != null){
                            listener.onItemClick(getSnapshots().getSnapshot(position), position);
                        }
                    }
                });
            }
        }

       public interface OnItemButtonClickListener{
        void onItemClick(DocumentSnapshot snapshot, int position);
       }
       public void setOnItemButtonClickListener(OnItemButtonClickListener listener){
            this.listener=listener;
       }

       public void updateDocument(int position){
        getSnapshots().getSnapshot(position).getReference();
       }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }


    public void changeStatus1(int position, String userId){
        getSnapshots().getSnapshot(position).getReference().update("byWho",userId);

    }
    public void changeStatus2(int position){
        getSnapshots().getSnapshot(position).getReference().update("status","Zarezerwowane");
    }

    public void cancelOrderByWho(int position){
        getSnapshots().getSnapshot(position).getReference().update("byWho","");

    }
    public void cancelOrderStatus(int position){
        getSnapshots().getSnapshot(position).getReference().update("status","Dostępne");
    }
    public void changeStatusDone(int position){
        getSnapshots().getSnapshot(position).getReference().update("status","Wykonane");
    }

    public void byWho(int position, String model){
        getSnapshots().getSnapshot(position).getReference().update("introduceYourself",model.getName());
    }

}
