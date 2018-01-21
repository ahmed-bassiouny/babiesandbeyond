package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.Utils;

public class ViewImageActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        img = findViewById(R.id.img);
        String url = getIntent().getStringExtra(IntentDataKey.SHOW_IMAGE);
        if(url == null || url.isEmpty())
            finish();
        Utils.MyGlide(this, img,url );
    }
}
