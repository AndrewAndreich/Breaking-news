package com.example.breakingnews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breakingnews.R;
import com.example.breakingnews.adapters.CategoriesAdapter;
import com.example.breakingnews.api.response.Category;
import com.example.breakingnews.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String CATEGORY_SPORTS = "sports";
    private static final String CATEGORY_HEALTH = "health";
    private static final String CATEGORY_SCIENCE = "science";
    private static final String CATEGORY_ENTERTAINMENT = "entertainment";
    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_BUSINESS = "business";
    private static final String CATEGORY_TECHNOLOGY = "technology";

    public static final String CATEGORY_NAME = "category_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView categoriesList = findViewById(R.id.categories_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        List<Category> categories = fillListWithData();
        final CategoriesAdapter adapter = new CategoriesAdapter(this, categories);
        categoriesList.addItemDecoration(new DividerItemDecoration(this,
                layoutManager.getOrientation()));
        categoriesList.setLayoutManager(layoutManager);
        categoriesList.setAdapter(adapter);

        categoriesList.addOnItemTouchListener(new RecyclerItemClickListener(this, categoriesList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra(CATEGORY_NAME, adapter.getCategoryNameByPosition(position));
                startActivity(intent);
            }
        }));

    }

    private List<Category> fillListWithData() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(CATEGORY_SPORTS));
        categories.add(new Category(CATEGORY_HEALTH));
        categories.add(new Category(CATEGORY_SCIENCE));
        categories.add(new Category(CATEGORY_ENTERTAINMENT));
        categories.add(new Category(CATEGORY_GENERAL));
        categories.add(new Category(CATEGORY_BUSINESS));
        categories.add(new Category(CATEGORY_TECHNOLOGY));
        return categories;
    }
}