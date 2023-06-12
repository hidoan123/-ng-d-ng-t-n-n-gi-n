package com.example.appdatdoan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appdatdoan.fragment.HomeFragment;
import com.example.appdatdoan.login.SignUp_Activity;
import com.example.appdatdoan.model.DetailCart;
import com.example.appdatdoan.model.DetailCartDatabase;
import com.example.appdatdoan.model.Food;
import com.example.appdatdoan.model.Order;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BottomSheetDetailOrder extends BottomSheetDialogFragment {
    private int SumPriceOrder;
    private List<DetailCart> mList;
    private ProgressDialog progressDialog;
    FirebaseUser userFb = FirebaseAuth.getInstance().getCurrentUser();
    String IdUser = userFb.getUid();
    private static final String KEY_SUM_PRICE_OREDR = "sum_sprice";
    private TextView tvListOrderFood,tvSumSpriceFood;
    private EditText edtUserName,edtUserAddress,edtUserPhoneNumber,edtUserPayMethods;
    private Button btnCannel,btnOrder;
    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static BottomSheetDetailOrder newInstance(int SumPrice)
    {
        BottomSheetDetailOrder bottomSheetDetailOrder = new BottomSheetDetailOrder();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_SUM_PRICE_OREDR,SumPrice);
        bottomSheetDetailOrder.setArguments(bundle);
        return bottomSheetDetailOrder;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundleReceive = getArguments();
        if(bundleReceive != null)
        {
            SumPriceOrder = (int) bundleReceive.get(KEY_SUM_PRICE_OREDR);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View viewdialog = LayoutInflater.from(getContext()).inflate(R.layout.order_layout,null);
        bottomSheetDialog.setContentView(viewdialog);

        initUiDiaLod(viewdialog);
        //setData
        SetData();
        //Listener
        onClickListener();
        return bottomSheetDialog;
    }
    //on click on fragment
    private void onClickListener() {
        btnCannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCannel();
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOrder();
            }
        });
    }
    //on click order
    private void onClickOrder() {
        int IdOrder = generateID();
        String strName = edtUserName.getText().toString();
        String strAddress = edtUserAddress.getText().toString();
        String strPhoneNumber = edtUserPhoneNumber.getText().toString().trim();
        String strPaymethods = edtUserPayMethods.getText().toString();
        String strTime = dateFormat.format(currentDate);
        String strStatus = "Waiting";
        if(strName == null|| strAddress == null|| strPhoneNumber == null)
        {
            new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.app_name))
                    .setMessage("You must enter enough information!")
                    .setPositiveButton("OK", null).show();
        }else{
            Order order = new Order(IdOrder,mList,IdUser,strName,strAddress,strPhoneNumber,strPaymethods,strStatus,SumPriceOrder,strTime);
            createOrderData(order);
            dismiss();
        }

    }
    //create order data
    private void createOrderData(Order order) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        progressDialog.show();
        DatabaseReference myRef = database.getReference("DataShop/Order");
        int tt = generateID();
        String path = String.valueOf(tt);
        myRef.child(path).setValue(order, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "You have successfully registered", Toast.LENGTH_SHORT).show();
                dismiss();
                // xu li data khi an chon
            DetailCartDatabase.getInstance(getContext()).detailCartDAO().deleteAllDetailCart(IdUser);
            }
        });
    }

    // click cannel
    private void onClickCannel() {
        dismiss();
    }

    //set data for order fragment
    private void SetData() {
        tvListOrderFood.setText(getStringListFood(mList));
        tvSumSpriceFood.setText(String.valueOf(SumPriceOrder));
    }

    // anh xa view
    private void initUiDiaLod(View viewdialog) {
        progressDialog = new ProgressDialog(getContext());
        tvListOrderFood = viewdialog.findViewById(R.id.tv_ListFood);
        tvSumSpriceFood = viewdialog.findViewById(R.id.tv_SumSprice);
        edtUserName = viewdialog.findViewById(R.id.edt_userName);
        edtUserAddress = viewdialog.findViewById(R.id.edt_userAddress);
        edtUserPhoneNumber = viewdialog.findViewById(R.id.edt_userPhoneNumber);
        edtUserPayMethods = viewdialog.findViewById(R.id.edt_userPayMethods);
        btnCannel = viewdialog.findViewById(R.id.btn_Cannel);
        btnOrder = viewdialog.findViewById(R.id.btn_Order);
        mList = DetailCartDatabase.getInstance(getContext()).detailCartDAO().getListDetailCart(IdUser);
        edtUserPayMethods.setEnabled(false);
    }
    private String getStringListFood(List<DetailCart> lstCart)
    {
        if(lstCart == null || lstCart.isEmpty())
        {
            return "";
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < lstCart.size(); i++)
            {
                DetailCart detailCart = lstCart.get(i);
                if(stringBuilder.length() > 0)
                {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(detailCart.getNameFoodTemp() + " x" + detailCart.getCount());
            }
            return stringBuilder.toString();
        }
    }
    int generateID() {
        long timestamp = System.currentTimeMillis();
        int truncatedTimestamp = (int) (timestamp % Integer.MAX_VALUE);
        return truncatedTimestamp;
    }
}
