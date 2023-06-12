package com.example.appdatdoan.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.example.appdatdoan.BottomSheetDetailOrder;
import com.example.appdatdoan.DetailCartAdapter;
import com.example.appdatdoan.MyBottomSheetDialogFragment;
import com.example.appdatdoan.R;
import com.example.appdatdoan.model.DetailCart;
import com.example.appdatdoan.model.DetailCartDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment{

    private RecyclerView rvcDetailCart;
    private TextView tvTotalPrice;
    private Button btnThanhToan;
    private DetailCartAdapter adapter;
    private List<DetailCart> mList;
    private int Sum;
    FirebaseUser userFb = FirebaseAuth.getInstance().getCurrentUser();
    String IdUser = userFb.getUid();

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initUI(view);
        mList = new ArrayList<>();
        adapter = new DetailCartAdapter(mList, new DetailCartAdapter.IClickItemUser() {
            @Override
            public void decrease(DetailCart detailCart) {
                onClickDecrease(detailCart);
            }

            @Override
            public void increase(DetailCart detailCart) {
                onClickIncrease(detailCart);
            }

            @Override
            public void deleteDetailCart(DetailCart detailCart) {
                onClickDelete(detailCart);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvcDetailCart.setLayoutManager(linearLayoutManager);
        rvcDetailCart.setAdapter(adapter);
        loadData();
        loadSum(mList);
        ListenerOnClick();
        return view;
    }
        //su kien khi nguoi dung bam vao nut
    private void ListenerOnClick() {
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickThanhToan();
            }
        });
    }
    // khi bam vao nut order
    private void onClickThanhToan() {
        if(mList == null || mList.isEmpty())
        {
            new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.app_name))
                    .setMessage("There is no data in your list, please select a")
                    .setPositiveButton("OK", null)
                    .show();
        }else{
            BottomSheetDetailOrder bottomSheetDetailOrder =  BottomSheetDetailOrder.newInstance(Sum);
            bottomSheetDetailOrder.show(getFragmentManager(),bottomSheetDetailOrder.getTag());
        }

    }

    //onClick Increase
    private void onClickIncrease(DetailCart detailCart) {
        int quanlity = detailCart.getCount();
        quanlity++;
        detailCart.setCount(quanlity);
        getTotal(detailCart);
        loadSum(mList);
    }
        //Total
    private void getTotal(DetailCart detailCart) {
        int SumPrice = detailCart.getCount() * detailCart.getPriceTemp();
        detailCart.setTotalPrice(SumPrice);
        DetailCartDatabase.getInstance(getContext()).detailCartDAO().updateUser(detailCart);
        loadData();
    }

    //onclick decrease
    private void onClickDecrease(DetailCart detailCart) {
        int quanlity = detailCart.getCount();
        if(quanlity > 0)
        {
            quanlity--;
            detailCart.setCount(quanlity);
        }
        getTotal(detailCart);
        loadSum(mList);
    }

    //Sum Price
    private void loadSum(List<DetailCart> mList) {
        Sum = 0;
        for(DetailCart detailCart : mList)
        {
            Sum += detailCart.getTotalPrice();
        }
        tvTotalPrice.setText(String.valueOf(Sum));

    }

    //delete cart
    private void onClickDelete(DetailCart detailCart) {
        new AlertDialog.Builder(getContext())
                .setTitle("delete your food")
                .setMessage("Are you sure?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //xu li
                        DetailCartDatabase.getInstance(getContext()).detailCartDAO().deleteUser(detailCart);
                        Toast.makeText(getContext(), "delete successfully", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("NO",null)
                .show();
    }

    private void loadData() {
        mList.clear();
        mList = DetailCartDatabase.getInstance(getContext()).detailCartDAO().getListDetailCart(IdUser);
        if(mList == null || mList.isEmpty())
        {
            Log.d("Data", "Danh sách rỗng");
        }
        adapter.setData(mList);
    }
    private void initUI(View view) {
        rvcDetailCart = view.findViewById(R.id.rcv_detailCart);
        tvTotalPrice = view.findViewById(R.id.tv_totalCart);
        btnThanhToan = view.findViewById(R.id.btn_OrderFood);
    }
    @Override
    public void onResume() {
        super.onResume();
        loadData();
        loadSum(mList);
    }
}