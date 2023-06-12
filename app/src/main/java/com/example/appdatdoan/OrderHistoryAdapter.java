package com.example.appdatdoan;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdatdoan.model.DetailCart;
import com.example.appdatdoan.model.Order;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    List<Order> mList;

    public OrderHistoryAdapter(List<Order> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_user,parent,false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
            Order order = mList.get(position);
            String strStatus = order.getStatus();
                holder.tvBillCode.setText(String.valueOf(order.getIdOrder()));
                holder.tvlstOrderFoods.setText(order.getListDetailCart());
                holder.tvDateOrder.setText(order.getTime());
                holder.tvStatusOrder.setText(order.getStatus());
                holder.tvSumPriceOrder.setText(String.valueOf(order.getSumPriceOrder()));
                if(strStatus.equals("Waiting"))
                {
                    holder.itemView.setBackgroundColor(Color.CYAN);
                }

    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvBillCode, tvlstOrderFoods, tvDateOrder,tvStatusOrder,tvSumPriceOrder;
        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBillCode = itemView.findViewById(R.id.tv_BillCode);
            tvlstOrderFoods = itemView.findViewById(R.id.tv_LstFood);
            tvDateOrder = itemView.findViewById(R.id.tv_DateOrder);
            tvStatusOrder = itemView.findViewById(R.id.tv_OrderStatus);
            tvSumPriceOrder = itemView.findViewById(R.id.tv_SumPriceOrder);
        }
    }

}
