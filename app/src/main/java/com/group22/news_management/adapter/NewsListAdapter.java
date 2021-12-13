package com.group22.news_management.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.group22.news_management.model.NewsModel;
import com.group22.news_management.view.activity.NewsDetailActivity;
import com.group22.newsmanagerment.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder>{
    private Context context;
    private List<NewsModel> newsModels;

    public NewsListAdapter(Context context, List<NewsModel> newsModels) {
        this.context = context;
        this.newsModels = newsModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_rv_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel newsModel = newsModels.get(position);
        holder.textViewTitle.setText(newsModel.getTitle());
        holder.textViewSubTitle.setText(newsModel.getDescription());
        Picasso.get().load(newsModel.getThumbnail()).into(holder.imageViewThumbnail);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("id", newsModel.getId());
            intent.putExtra("link", newsModel.getLink());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewSubTitle;
        ImageView imageViewThumbnail;
        /* anh xa views */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewSubTitle = itemView.findViewById(R.id.textViewSubTitle);
            imageViewThumbnail = itemView.findViewById(R.id.idIVNews);
        }

    }
}
