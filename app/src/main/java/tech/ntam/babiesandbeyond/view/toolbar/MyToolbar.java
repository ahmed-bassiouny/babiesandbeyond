package tech.ntam.babiesandbeyond.view.toolbar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.activities.UserHistoryNotificationActivity;
import tech.ntam.babiesandbeyond.view.activities.UserProfileActivity;
import tech.ntam.mylibrary.IntentDataKey;

/**
 * Created by bassiouny on 26/12/17.
 */

public class MyToolbar extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView ivProfile, ivBack, ivNotification;
    protected TextView tvTitle;
    private Context context;

    protected void setupToolbar(final Context context, boolean showProfile, boolean showBack, boolean showNotification) {
        this.context = context;
        toolbar = findViewById(R.id.my_toolbar);
        ivProfile = findViewById(R.id.iv_profile);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.toolbar_title);
        ivNotification = findViewById(R.id.iv_notification);

        toolbar.setTitle("");
        if (showBack) {
            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }
        if (showProfile) {
            ivProfile.setVisibility(View.VISIBLE);
        } else {
            ivProfile.setVisibility(View.GONE);
        }
        if (showNotification) {
            ivNotification.setVisibility(View.VISIBLE);
        } else {
            ivNotification.setVisibility(View.GONE);
        }
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, UserProfileActivity.class));
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserHistoryNotificationActivity.class);
                intent.putExtra(IntentDataKey.SHOW_HISTORY_DATA_KEY, false);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
    }

    public interface TitleToolbar {
        //void setTitleToolbar(String title);
    }
}
