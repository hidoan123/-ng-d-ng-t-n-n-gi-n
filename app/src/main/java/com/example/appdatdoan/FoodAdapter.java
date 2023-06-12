package com.example.appdatdoan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdatdoan.model.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    private List<Food> mListFood;
    private IClickListener iClickListener;

    public interface IClickListener{
        void onClickItem(Food food);
    }

    public FoodAdapter(List<Food> mListFood, IClickListener iClickListener) {
        this.mListFood = mListFood;
        this.iClickListener = iClickListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foods,parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = mListFood.get(position);
        holder.tvPrice.setText(String.valueOf(food.getPrice()) + " VND");
        holder.tvName.setText(food.getFoodName());
        Glide.with(holder.itemView.getContext())
                .load(food.getImageUrl())
                .into(holder.imgFood);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListener.onClickItem(food);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mListFood != null) return mListFood.size();
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private ImageView imgFood;
        private TextView tvPrice;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_FoodName);
            imgFood = itemView.findViewById(R.id.img_Food);
            tvPrice = itemView.findViewById(R.id.tv_foodPrice);
        }
    }
}
