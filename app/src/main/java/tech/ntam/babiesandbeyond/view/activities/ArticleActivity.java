package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.AboutUs;
import tech.ntam.babiesandbeyond.model.Article;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

public class ArticleActivity extends MyToolbar {

    private WebView webView;
    private TextView articleName;
    private TextView articleDate;
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        setupToolbar(this,false,true,false);
        tvTitle.setText("Article");
        articleName = findViewById(R.id.article_name);
        articleDate = findViewById(R.id.article_date);
        webView = findViewById(R.id.web_view);
        setData();
    }

    private void setData() {
        article = getIntent().getParcelableExtra("Article");
        if(article == null)
            finish();
        articleName.setText(article.getTitle());
        articleDate.setText(article.getDate());
        webView.loadDataWithBaseURL("", article.getBody(), "text/html", "UTF-8", "");
    }
}
