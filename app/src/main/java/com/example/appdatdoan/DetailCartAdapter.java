package com.example.appdatdoan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdatdoan.model.DetailCart;
import com.example.appdatdoan.model.DetailCartDatabase;

import java.util.List;

public class DetailCartAdapter extends RecyclerView.Adapter<DetailCartAdapter.DetailCartViewHolder>{
    private List<DetailCart> mListDetailCart;
    private IClickItemUser iClickItemUser;

    public DetailCartAdapter(List<DetailCart> mListDetailCart, IClickItemUser iClickItemUser) {
        this.mListDetailCart = mListDetailCart;
        this.iClickItemUser = iClickItemUser;
    }
    public interface IClickItemUser{
        void decrease(DetailCart detailCart);//
        void increase(DetailCart detailCart);//
        void deleteDetailCart(DetailCart detailCart);
    }
//    public void reloadList(List<DetailCart> newList) {
//        mListDetailCart.addAll(newList);
//        notifyDataSetChanged();
//    }
    public void setData(List<DetailCart> list)
    {
        this.mListDetailCart= list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetailCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detailorder,parent,false);
        return new DetailCartViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DetailCartViewHolder holder, int position) {
            final DetailCart detailCart = mListDetailCart.get(position);
            if(detailCart == null) return;
        Glide.with(holder.itemView.getContext())
                .load(detailCart.getImgFoodTemp())
                .into(holder.imgOrder);
        holder.tvOderNameFood.setText(detailCart.getNameFoodTemp());
        holder.tvToltalCart.setText(String.valueOf(detailCart.getTotalPrice()));
        holder.tvQuanlity.setText(String.valueOf(detailCart.getCount()));
        holder.btnDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.deleteDetailCart(detailCart);            }
        });
        holder.imgDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.decrease(detailCart);
            }
        });
        holder.imgIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.increase(detailCart);
            }
        });
        holder.btnDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.deleteDetailCart(detailCart);
            }
        });


    }


    @Override
    public int getItemCount() {
        if(mListDetailCart != null) return mListDetailCart.size();
        return 0;
    }

    public class DetailCartViewHolder  extends RecyclerView.ViewHolder{
        private ImageView imgOrder,imgDecrease,imgIncrease;
        private TextView tvOderNameFood,tvToltalCart,tvQuanlity;
        private Button btnDeleteCart;

        public DetailCartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOrder = itemView.findViewById(R.id.img_detailCart);
            imgDecrease = itemView.findViewById(R.id.img_cartdecrease);
            imgIncrease = itemView.findViewById(R.id.img_cartincrease);
            tvOderNameFood = itemView.findViewById(R.id.tv_DetailCartNameFood);
            tvToltalCart = itemView.findViewById(R.id.tv_DetailTotalPrice);
            tvQuanlity = itemView.findViewById(R.id.tv_cartQuanlity);
            btnDeleteCart = itemView.findViewById(R.id.btn_Deletetocart);

        }
    }
}
