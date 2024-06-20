package com.example.newshub.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.newshub.Adapters.AllAdapter;
import com.example.newshub.Models.NewsPaper;
import com.example.newshub.R;
import com.example.newshub.databinding.FragmentAllBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AllFragment extends Fragment {

    private static final String TAG = "AllFragment";

    public AllFragment() {
        // Required empty public constructor
    }

    FragmentAllBinding binding;
    ArrayList<NewsPaper> list;
    AllAdapter adapter;
    GridLayoutManager lm;
    DatabaseReference ref;
    FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        list = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        // Load default newspapers
        loadDefaultNewspapers();

        // Load user-specific newspapers
        if (user != null) {
            loadUserNewspapers(user.getUid());
        }

        adapter = new AllAdapter(requireContext(), list);
        lm = new GridLayoutManager(requireContext(), 2);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(lm);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.add_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                Button b = dialog.findViewById(R.id.add_btn_d);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText nameEt = dialog.findViewById(R.id.name_et);
                        EditText linkEt = dialog.findViewById(R.id.link_et);
                        String name = nameEt.getText().toString();
                        String link = linkEt.getText().toString();

                        if (!name.isEmpty() && !link.isEmpty()) {
                            dialog.dismiss(); // Dismiss dialog after checking the input

                            // Add to Firestore
                            if (user != null) {
                                String userId = user.getUid();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("name", name);
                                map.put("link", link);
                                map.put("imageI", 0); // Default image value
                                FirebaseFirestore.getInstance()
                                        .collection("Users")
                                        .document(userId)
                                        .collection("newspapers")
                                        .add(map)
                                        .addOnSuccessListener(documentReference -> {
                                            // Successfully added
                                            addInTheList(new NewsPaper(name, link, 0));
                                            Toast.makeText(getContext(), "Newspaper added!", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            // Failed to add
                                            e.printStackTrace();
                                            Toast.makeText(getContext(), "Failed to add newspaper.", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Toast.makeText(getContext(), "User not authenticated.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Name and link cannot be empty.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }

    private void loadDefaultNewspapers() {
        ref = FirebaseDatabase.getInstance().getReference().child("Default");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                    if (map != null) {
                        String name = (String) map.get("name");
                        String link = (String) map.get("link");
                        Long image = (Long) map.get("imageI"); // Use Long to handle Integer values from Firebase
                        int imageInt = image.intValue(); // Convert Long to int

                        // Create a new NewsPaper object and add it to the list
                        list.add(new NewsPaper(name, link, imageInt));
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter about data changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "DatabaseError: " + error.getMessage());
            }
        });
    }

    private void loadUserNewspapers(String userId) {
        FirebaseFirestore.getInstance()
                .collection("Users")
                .document(userId)
                .collection("newspapers")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        HashMap<String, Object> map = (HashMap<String, Object>) documentSnapshot.getData();
                        if (map != null) {
                            String name = (String) map.get("name");
                            String link = (String) map.get("link");
                            Long image = (Long) map.get("imageI"); // Use Long to handle Integer values from Firestore
                            int imageInt = image.intValue(); // Convert Long to int

                            // Create a new NewsPaper object and add it to the list
                            list.add(new NewsPaper(name, link, imageInt));
                        }
                    }
                    adapter.notifyDataSetChanged(); // Notify adapter about data changes
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "FirestoreError: " + e.getMessage());
                });
    }

    private void addInTheList(NewsPaper newsPaper) {
        list.add(newsPaper);
        adapter.notifyDataSetChanged();
    }
}
