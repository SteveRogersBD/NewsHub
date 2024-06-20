package com.example.newshub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newshub.Adapters.VIewPagerAdapter;
import com.example.newshub.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore fs;
    FirebaseDatabase db;
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

//        String[] name = {"BBC", "CNN", "Fox News", "New York Times", "USA Today", "The Daily Star", "Times of India"};
//        int[] images = {R.drawable.bbc, R.drawable.cnn, R.drawable.fox_new, R.drawable.the_ny_times,
//                R.drawable.usa_today, R.drawable.the_daily_star, R.drawable.times_of_india};
//        String[] link = {"https://www.bbc.com/news", "https://www.cnn.com/", "https://www.foxnews.com/",
//                "https://www.nytimes.com/", "https://www.usatoday.com/", "https://www.thedailystar.net/",
//                "https://timesofindia.indiatimes.com/us"};
//
//        db = FirebaseDatabase.getInstance();
//        DatabaseReference ref = db.getReference().child("Default");
//
//        // Clear the Default node before adding new data to avoid duplicates
//        ref.removeValue().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (int i = 0; i < name.length; i++) {
//                    NewsPaper newsPaper = new NewsPaper(name[i], link[i], images[i]);
//                    ref.child(name[i]).setValue(newsPaper);
//                }
//            } else {
//                Toast.makeText(MainActivity.this, "Failed to clear existing data.", Toast.LENGTH_SHORT).show();
//            }
//        });
        //db.getReference().child("Default").;





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==0) startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        else {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        }
        return true;
    }
}