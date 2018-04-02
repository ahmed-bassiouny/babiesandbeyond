package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.Article;

/**
 * Created by bassiouny on 02/04/18.
 */

public class ArticleResponse extends ParentResponse{
    @SerializedName(DATA_KEY)
    private List<Article> articles;

    public List<Article> getArticles() {
        if(articles == null)
            articles = new ArrayList<>();
        return articles;
    }
}
