package com.example.jsonactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jsonactivity.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new JsonConsumer(MainActivity.this, binding.listView)
                .execute("https://jsonplaceholder.typicode.com/posts");

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openView((Post) parent.getItemAtPosition(position));

            }
        });

    }

    private void openView(Post post){

        Intent intent = new Intent(getApplication(), PostActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);

    }

}