package com.example.breakingnews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.breakingnews.R;
import com.example.breakingnews.api.response.Article;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CategoriesViewHolder> {

    private List<Article> dataList = new ArrayList<>();
    private Context context;

    public NewsAdapter(Context context){
        this.context = context;
    }

    public void setDataList(List<Article> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<Article> getDataList() {
        return dataList;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        holder.title.setText(dataList.get(position).getTitle());
        Glide.with(context).load(dataList.get(position).getUrlToImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class CategoriesViewHolder extends  RecyclerView.ViewHolder {

        ImageView image;
        TextView title;

        CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.category_image);
            title = itemView.findViewById(R.id.title);
        }
    }
}
