package com.company.newsinternapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.company.newsinternapp.R;
import com.company.newsinternapp.modals.NewsHeadlines;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<NewsHeadlines> newsHeadlinesList;

    public RecyclerAdapter(Context context, List<NewsHeadlines> newsHeadlinesList) {
        this.context = context;
        this.newsHeadlinesList = newsHeadlinesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.news_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.healines.setText(newsHeadlinesList.get(position).getTitle());
        holder.source.setText(newsHeadlinesList.get(position).getNewsSource().getName());

        if(newsHeadlinesList.get(position).getUrlToImage()!=null){

            Picasso.get().load(newsHeadlinesList.get(position).getUrlToImage()).into(holder.imageView);
        }


    }

    @Override
    public int getItemCount() {
        return newsHeadlinesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView healines, source;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            healines= itemView.findViewById(R.id.title);
            source = itemView.findViewById(R.id.textSource);

            imageView= itemView.findViewById(R.id.image);
            cardView= itemView.findViewById(R.id.card_news);
        }
    }
}
