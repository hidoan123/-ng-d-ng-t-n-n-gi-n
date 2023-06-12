package com.example.appdatdoan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appdatdoan.model.DetailCart;
import com.example.appdatdoan.model.DetailCartDatabase;
import com.example.appdatdoan.model.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgDetailFood;
    private TextView tvDetailNameFood;
    private TextView tvDetailPriceFood;
    private TextView tvDetailDescribeFood;
    private TextView tvAddToCart;
    private Food mFood;
    FirebaseUser userFb = FirebaseAuth.getInstance().getCurrentUser();
    String IdUser = userFb.getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mFood =(Food) getIntent().getExtras().get("object_Food");
        initUI();
        detailFood(mFood);
        setButtonAdd(mFood);

        tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddtoCart();
            }
        });
    }
        // ham show len fragment
    private void onClickAddtoCart() {
        MyBottomSheetDialogFragment sheetDialogFragment =  MyBottomSheetDialogFragment.newInstance(mFood);
        sheetDialogFragment.show(getSupportFragmentManager(),sheetDialogFragment.getTag());
    }

    private void detailFood(Food mFood) {

        if(mFood != null)
        {
            Glide.with(this)
                    .load(mFood.getImageUrl())
                    .into(imgDetailFood);
            tvDetailNameFood.setText(mFood.getFoodName());
            tvDetailPriceFood.setText(String.valueOf(mFood.getPrice()) + "VND");
            tvDetailDescribeFood.setText(mFood.getDescribe());
        }
    }

    private void initUI() {
        imgDetailFood = findViewById(R.id.img_detailImgFood);
        tvDetailNameFood = findViewById(R.id.tv_detailNameFood);
        tvDetailPriceFood = findViewById(R.id.tv_detailPrice);
        tvDetailDescribeFood = findViewById(R.id.tv_detailDescribe);
        tvAddToCart = findViewById(R.id.tv_addToCart);
    }
    //set background button add
    private void setButtonAdd(Food mFood)
    {
        List<DetailCart> mList = DetailCartDatabase.getInstance(this).detailCartDAO().getListDetailCart(IdUser);
        for(DetailCart db : mList)
        {
            if(db.getIdFoodTemp() == mFood.getIdFood())
            {
                tvAddToCart.setBackgroundColor(getResources().getColor(R.color.button));
                tvAddToCart.setEnabled(false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setButtonAdd(mFood);
    }
}