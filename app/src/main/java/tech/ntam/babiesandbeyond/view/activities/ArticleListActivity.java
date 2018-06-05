package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Article;
import tech.ntam.babiesandbeyond.view.adapter.ArticleItemAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class ArticleListActivity extends MyToolbar implements ParseObject<Article>{

    private RecyclerView recyclerView;
    private ArticleItemAdapter articleItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        setupToolbar(this,false,true,false);
        tvTitle.setText("Article List");
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();
    }
    private void loadData(){
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(this);
        RequestAndResponse.getArticles(new BaseResponseInterface<List<Article>>() {
            @Override
            public void onSuccess(List<Article> articles) {
                articleItemAdapter = new ArticleItemAdapter(ArticleListActivity.this,articles);
                recyclerView.setAdapter(articleItemAdapter);
                dialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(ArticleListActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                dialog.dismissMyDialog();
            }
        });

    }

    @Override
    public void getMyObject(Article article) {
        Intent intent = new Intent(this,ArticleActivity.class);
        intent.putExtra("Article",article);
        startActivity(intent);
    }
}
