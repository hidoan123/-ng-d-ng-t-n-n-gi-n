package com.example.appdatdoan;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Database;

import com.bumptech.glide.Glide;
import com.example.appdatdoan.fragment.CartFragment;
import com.example.appdatdoan.model.DetailCart;
import com.example.appdatdoan.model.DetailCartDatabase;
import com.example.appdatdoan.model.Food;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private static final String KEY_FOOD_OBJECT = "food_object";
    private Food mFood;
    private ImageView imgDetailFood;
    private TextView tvDetailNameFood,tvDetailTotalPrice,tvQuanlity;
    private Button btnAddtoCart;
    private ImageView imgdecrease,imgincrease;
    private int quanlity = 1;
    private int SumPrice;
    private DetailCart detailCart;
    FirebaseUser userFb = FirebaseAuth.getInstance().getCurrentUser();
    String IdUser = userFb.getUid();

    public static MyBottomSheetDialogFragment newInstance(Food food)
    {
        MyBottomSheetDialogFragment myBottomSheetDialogFragment = new MyBottomSheetDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_FOOD_OBJECT,food);
        myBottomSheetDialogFragment.setArguments(bundle);
        return myBottomSheetDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundleReceive = getArguments();
        if(bundleReceive != null)
        {
            mFood = (Food) bundleReceive.get(KEY_FOOD_OBJECT);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View viewdialog = LayoutInflater.from(getContext()).inflate(R.layout.food_item_bottomsheet,null);
        bottomSheetDialog.setContentView(viewdialog);

        initUiDiaLod(viewdialog);
        setData();
        onClickListener();
        return bottomSheetDialog;
    }
        //Ham click
    private void onClickListener() {
        imgdecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDecrease();
            }
        });
        imgincrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIncrease();
            }
        });
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddtoCart();
            }
        });
    }
    private void onClickAddtoCart() {
         DetailCart detailCart = new DetailCart(mFood.getIdFood(),IdUser,mFood.getFoodName(),mFood.getImageUrl(),mFood.getPrice(),quanlity,SumPrice);
         if(detailCart == null)
         {
             Log.d("Data", "Danh sách rỗng");
         }
            else{
                DetailCartDatabase.getInstance(getContext()).detailCartDAO().insertDetailCart(detailCart);
             Toast.makeText(getContext(), "you have add succesfully", Toast.LENGTH_SHORT).show();
            dismiss();}
    }

    // khi bam tang
    private void onClickIncrease() {
        quanlity++;
        tvQuanlity.setText(String.valueOf(quanlity));
        getTotal();
    }
    //set tong
    private void getTotal() {
        int TotalPrice = mFood.getPrice() * quanlity;
        tvDetailTotalPrice.setText(String.valueOf(TotalPrice));
        SumPrice = TotalPrice;
    }

    // khi bam giam
    private void onClickDecrease() {
        if(quanlity > 0)
        {
            quanlity--;
            tvQuanlity.setText(String.valueOf(quanlity));
        }
        getTotal();
    }

    //xet data
    private void setData() {
        if(mFood == null)
        {
            Log.d(TAG,"food null" );
        }
        Glide.with(getContext())
                .load(mFood.getImageUrl())
                .into(imgDetailFood);
        tvDetailNameFood.setText(mFood.getFoodName());
        tvDetailTotalPrice.setText(String.valueOf(mFood.getPrice()));
    }

    //anh xa view
    private void initUiDiaLod(View viewdialog) {
        imgDetailFood = viewdialog.findViewById(R.id.img_CartFood);
        tvDetailNameFood = viewdialog.findViewById(R.id.tv_CartNameFood);
        tvDetailTotalPrice = viewdialog.findViewById(R.id.tv_TotalPrice);
        tvQuanlity = viewdialog.findViewById(R.id.tv_Quanlity);
        btnAddtoCart = viewdialog.findViewById(R.id.btn_Addtocart);
        imgdecrease = viewdialog.findViewById(R.id.img_decrease);
        imgincrease = viewdialog.findViewById(R.id.img_increase);
    }
}
