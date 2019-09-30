package com.example.breakingnews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.breakingnews.R;

public class NewsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(NewsActivity.IMAGE_URL_KEY);
        String title = intent.getStringExtra(NewsActivity.TITLE_KEY);
        String description = intent.getStringExtra(NewsActivity.CONTENT_KEY);

        ImageView newsImage = findViewById(R.id.news_image);
        TextView newsTitle = findViewById(R.id.title);
        TextView newsContent = findViewById(R.id.description);

        Glide.with(this).load(imageUrl).into(newsImage);
        newsTitle.setText(title);
        newsContent.setText(description);
    }
}
