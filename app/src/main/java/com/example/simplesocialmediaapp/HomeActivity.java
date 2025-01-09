package com.example.simplesocialmediaapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.example.simplesocialmediaapp.Fragments.CreateFragment;
import com.example.simplesocialmediaapp.Fragments.HomeFragment;
import com.example.simplesocialmediaapp.Fragments.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TabLayout apptabs;

    EditText etToken;
    ViewPager2 pager;
    ViewPagerFragmentAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        etToken = findViewById(R.id.etToken);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {


                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        System.out.println(token);
                        Toast.makeText(HomeActivity.this, "Your device registration token is" + token
                                , Toast.LENGTH_SHORT).show();

                        Log.d("DeviceToken", "Your device registration token is " + token);

                        etToken.setText(token);
                    }
                });

        initvar();

        settabs();

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                apptabs.selectTab(apptabs.getTabAt(position));
            }
        });

    }

    void initvar()
    {
        mAuth = FirebaseAuth.getInstance();

        apptabs = findViewById(R.id.apptabs);
        pager = findViewById(R.id.pager);
        adapter = new ViewPagerFragmentAdapter(this,apptabs.getTabCount());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(5);
    }

    void settabs()
    {
        apptabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#4F78D0"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TabLayout.Tab tab = apptabs.getTabAt(0);
        tab.getIcon().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        apptabs.selectTab(tab);
        pager.setCurrentItem(0);
    }

    public static class ViewPagerFragmentAdapter extends FragmentStateAdapter{
        int size;
        ProfileFragment profileFragment;

        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity,int size) {
            super(fragmentActivity);
            this.size = size;
            profileFragment = new ProfileFragment();
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new HomeFragment();
                case 1:
                    return new CreateFragment();
                case 2:
                    return new ProfileFragment();
            }
            return new HomeFragment();
        }

        @Override
        public int getItemCount() {
            return size;
        }
    }
}