package com.example.android.crypto.News;

/**
 * Created by K on 2018-04-02.
 */

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.android.crypto.ModelClasses.News;
import com.example.android.crypto.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by K on 2018-03-27.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> data;
    private final RequestManager glide;

    public NewsAdapter(RequestManager glide, List<News> data) {
        this.data = data;
        this.glide = glide;
    }

    public void setNewsListData(List<News> data) {
        this.data = data;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        holder.newsTitle.setText(data.get(position).getTitle());
        holder.newsBody.setText(data.get(position).getBody());
        holder.newsSource.setText(data.get(position).getSource());
        //holder.newsDatePublished.setText(data.get(position).getPublishedOn());

        glide.load(data.get(position).getImageurl()).into(holder.newsImage);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface OnItemClickListener {
        void onClick(Integer Item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_image)
        ImageView newsImage;
        @BindView(R.id.news_title) TextView newsTitle;
        @BindView(R.id.news_body) TextView newsBody;
        @BindView(R.id.news_source) TextView newsSource;
        @BindView(R.id.news_date_published) TextView newsDatePublished;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick
        void onClick(View view) {
            System.out.println(getAdapterPosition());
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(getAdapterPosition()).getUrl()));
            view.getContext().startActivity(browserIntent);
        }
    }


}
