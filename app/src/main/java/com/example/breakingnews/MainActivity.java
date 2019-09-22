package com.example.breakingnews;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breakingnews.adapters.CategoriesAdapter;
import com.example.breakingnews.api.RetrofitClientInstance;
import com.example.breakingnews.api.response.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView categoriesList;
    private CategoriesAdapter categoriesAdapter;

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
    }
}
