package com.example.newshub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newshub.Adapters.VIewPagerAdapter;
import com.example.newshub.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        binding.toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(binding.toolbar);

        VIewPagerAdapter va = new VIewPagerAdapter(getSupportFragmentManager());
        binding.viewPager.setAdapter(va);
        binding.tab.setupWithViewPager(binding.viewPager);

        FirebaseUser user = auth.getCurrentUser();
        if(user!=null)
        {
            Uri photoUri = user.getPhotoUrl();
            if(photoUri!=null)
            {
                Picasso.get().load(photoUri).into(binding.profileImage);
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==0) Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        else {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        }
        return true;
    }
}