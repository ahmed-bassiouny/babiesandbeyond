package tech.ntam.babiesandbeyond.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Article;
import tech.ntam.babiesandbeyond.model.UserHistory;

/**
 * Created by Developer on 22/12/17.
 */

public class ArticleItemAdapter extends RecyclerView.Adapter<ArticleItemAdapter.MyViewHolder> {

    private ParseObject parseObject;
    private Activity activity;
    private List<Article> articles;
    public ArticleItemAdapter(Activity activity, List<Article> articles) {
        this.parseObject = (ParseObject) activity;
        this.activity = activity;
        this.articles = articles;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView ivArticleName;
        private TextView tvArticleDate;

        public MyViewHolder(View view) {
            super(view);
            ivArticleName = view.findViewById(R.id.tv_article_name);
            tvArticleDate = view.findViewById(R.id.tv_article_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parseObject.getMyObject(articles.get(getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_article_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article item = articles.get(position);
        holder.ivArticleName.setText(item.getTitle());
        holder.tvArticleDate.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

}