package com.example.breakingnews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breakingnews.BuildConfig;
import com.example.breakingnews.R;
import com.example.breakingnews.adapters.NewsAdapter;
import com.example.breakingnews.api.RetrofitClientInstance;
import com.example.breakingnews.api.response.Article;
import com.example.breakingnews.api.response.BaseResponse;
import com.example.breakingnews.util.RecyclerItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    static final String IMAGE_URL_KEY = "image_url";
    static final String TITLE_KEY = "title";
    static final String CONTENT_KEY = "content";

    private RecyclerView newsList;
    private NewsAdapter categoriesAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsList = findViewById(R.id.news_list);
        newsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        newsList.setLayoutManager(layoutManager);
        newsList.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        categoriesAdapter = new NewsAdapter(this);
        newsList.setAdapter(categoriesAdapter);

        progressBar = findViewById(R.id.progress_bar);

        String categoryName = getIntent().getStringExtra(MainActivity.CATEGORY_NAME);

        if (categoryName == null) {
            showError(getString(R.string.error_empty_category));
            return;
        }

        getNews(categoryName);

        setClickListener();
    }

    private void getNews(String categoryName) {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClientInstance.getNewsService()
                .getNewsByCategory(BuildConfig.API_KEY,
                        getString(R.string.country_russia), categoryName)
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        progressBar.setVisibility(View.GONE);
                        categoriesAdapter.setDataList(response.body().getArticles());
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(NewsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void showError(String errorString) {
        Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();
    }

    private void setClickListener() {
        newsList.addOnItemTouchListener(new RecyclerItemClickListener(this, newsList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
                List<Article> articles = categoriesAdapter.getDataList();
                intent.putExtra(IMAGE_URL_KEY, articles.get(position).getUrlToImage());
                intent.putExtra(TITLE_KEY, articles.get(position).getTitle());
                intent.putExtra(CONTENT_KEY, articles.get(position).getDescription());
                startActivity(intent);
            }
        }));
    }
}
