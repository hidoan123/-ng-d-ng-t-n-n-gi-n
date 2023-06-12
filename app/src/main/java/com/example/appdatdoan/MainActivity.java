package com.example.appdatdoan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.appdatdoan.login.SignIn_Activity;
import com.example.appdatdoan.model.DetailCartDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nvt_Menu);
        viewPager = findViewById(R.id.vpgData);
        setUpViewPager();
        listenerOnBottomClick();
    }

    private void listenerOnBottomClick() {
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId())
                {
                    case R.id.item_Menu:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.item_Cart:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.item_history:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.item_feedback:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.item_setting:
                    {
                        new AlertDialog.Builder(this)
                            .setTitle("Are you logout?")
                            .setMessage("Are you sure?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //xu li
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(MainActivity.this, SignIn_Activity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("NO",null)
                            .show();

                    }
                }
                return true;
            });

    }

    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
    }
}