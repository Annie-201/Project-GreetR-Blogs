package com.example.greetr_blogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.greetr_blogs.Adapter.Post_Adapter;
import com.example.greetr_blogs.Model.Post_Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home_Activity extends AppCompatActivity {

    FirebaseAuth auth;
    RecyclerView recyclerView;
    Post_Adapter post_adapter;
    List<Post_Model> postModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(layoutManager);

        postModelList = new ArrayList<>();

        loadPosts();
    }

    private void loadPosts() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postModelList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Post_Model post_model = ds.getValue(Post_Model.class);
                    postModelList.add(post_model);
                    post_adapter = new Post_Adapter(Home_Activity.this, postModelList);
                    recyclerView.setAdapter(post_adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home_Activity.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_logout) {
            auth.signOut();
            startActivity(new Intent(Home_Activity.this, MainActivity.class));
        }
        if (item.getItemId() == R.id.action_add_post) {
            startActivity(new Intent(Home_Activity.this, Add_Post_Activity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void Search(MenuItem item) {
        Uri u = Uri.parse("http://www.blogsearchengine.org/");
        Intent i = new Intent(Intent.ACTION_VIEW, u);
        startActivity(i);
    }
}
