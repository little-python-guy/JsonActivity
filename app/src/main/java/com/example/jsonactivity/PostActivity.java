package com.example.jsonactivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jsonactivity.databinding.ActivityMainBinding;
import com.example.jsonactivity.databinding.ActivityPostBinding;

public class PostActivity extends AppCompatActivity {

    private ActivityPostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Post post = (Post) getIntent().getSerializableExtra("post");

        binding.edId.setText(String.valueOf(post.getId()));
        binding.edUserId.setText(String.valueOf(post.getUserId()));
        binding.edTitle.setText(String.valueOf(post.getTitle()));
        binding.edBody.setText(String.valueOf(post.getBody()));

    }
}