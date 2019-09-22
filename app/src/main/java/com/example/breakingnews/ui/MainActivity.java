package com.example.breakingnews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breakingnews.BuildConfig;
import com.example.breakingnews.R;
import com.example.breakingnews.adapters.CategoriesAdapter;
import com.example.breakingnews.api.RetrofitClientInstance;
import com.example.breakingnews.api.response.Article;
import com.example.breakingnews.api.response.BaseResponse;
import com.example.breakingnews.util.RecyclerItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView categoriesList;
    private CategoriesAdapter categoriesAdapter;

    static final String IMAGE_URL_KEY = "image_url";
    static final String TITLE_KEY = "title";
    static final String CONTENT_KEY = "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoriesList = findViewById(R.id.categories_list);
        categoriesList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        categoriesList.setLayoutManager(layoutManager);
        categoriesList.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        categoriesAdapter = new CategoriesAdapter(this);
        categoriesList.setAdapter(categoriesAdapter);

        RetrofitClientInstance.getNewsService()
                .getNewsByCategory(BuildConfig.API_KEY,
                        getString(R.string.country_russia), "sports")
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        categoriesAdapter.setDataList(response.body().getArticles());
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        setClickListener();
    }

    private void setClickListener() {
        categoriesList.addOnItemTouchListener(new RecyclerItemClickListener(this, categoriesList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, NewsDetailsActivity.class);
                List<Article> articles = categoriesAdapter.getDataList();
                intent.putExtra(IMAGE_URL_KEY, articles.get(position).getUrlToImage());
                intent.putExtra(TITLE_KEY, articles.get(position).getTitle());
                intent.putExtra(CONTENT_KEY, articles.get(position).getDescription());
                startActivity(intent);
            }
        }));
    }
}
