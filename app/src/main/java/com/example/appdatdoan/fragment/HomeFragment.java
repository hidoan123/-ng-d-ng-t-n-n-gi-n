package com.example.appdatdoan.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appdatdoan.DetailActivity;
import com.example.appdatdoan.FoodAdapter;
import com.example.appdatdoan.PhotoSlider;
import com.example.appdatdoan.PhotoSliderAdapter;
import com.example.appdatdoan.R;
import com.example.appdatdoan.ZoomOutPageTransformer;
import com.example.appdatdoan.model.Food;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragment extends Fragment {
    private static final int MY_REQUEST_CODE = 10;
    private Context context = getContext();
    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private List<Food> mListFood;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<PhotoSlider> photoSliderList;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // khi ảnh chạy đến cuối thì sẽ quay lại
            if(viewPager2.getCurrentItem() == photoSliderList.size()-1)
            {
                viewPager2.setCurrentItem(0);
            }
            else{
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager2 = view.findViewById(R.id.vpg_slider);
        circleIndicator3 = view.findViewById(R.id.circle_indicator);
        //
        recyclerView = view.findViewById(R.id.rcv_foods);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        mListFood = new ArrayList<>();
        adapter = new FoodAdapter(mListFood, new FoodAdapter.IClickListener() {
            @Override
            public void onClickItem(Food food) {
                onClickItemData(food);
            }
        });
        recyclerView.setAdapter(adapter);
        SetAnh();
        SetDataFromRealTime();
        return view;
    }
        // click vao item data
    private void onClickItemData(Food food) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object_Food", food);
            intent.putExtras(bundle);
            startActivityForResult(intent,MY_REQUEST_CODE);

    }

    //set data from realtime
    private void SetDataFromRealTime() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DataShop/Foods");

        //
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                        Food food = dataSnapshot.getValue(Food.class);
                        mListFood.add(food);
                }
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "get list user fault", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //xat anh
    private void SetAnh() {
        photoSliderList = getListPhoto();
        PhotoSliderAdapter photoSliderAdapter = new PhotoSliderAdapter(photoSliderList);
        viewPager2.setAdapter(photoSliderAdapter);
        circleIndicator3.setViewPager(viewPager2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,2000);
                super.onPageSelected(position);
            }
        });
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
    }

    // lay ra src anh
    private List<PhotoSlider> getListPhoto()
    {
        List<PhotoSlider> list = new ArrayList<>();
        list.add(new PhotoSlider(R.drawable.bancuon));
        list.add(new PhotoSlider(R.drawable.banhkhot));
        list.add(new PhotoSlider(R.drawable.bundaumamtom));
        list.add(new PhotoSlider(R.drawable.buncha));
        return list;
    }
    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable,2000);
    }
}