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
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by Developer on 26/12/17.
 */

public class MyToolbar extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView ivProfile, ivBack, ivNotification;
    protected TextView tvTitle;

    protected void setupToolbar(final Context context, boolean showProfile, boolean showBack, boolean showNotification) {
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

    public void setTutorial(final IShowcaseListener iShowcaseListener){


        new MaterialShowcaseView.Builder(this) // instantiate the material showcase view using Builder
                .setTarget(ivNotification) // set what view will be pointed or highlighted
                .setTitleText("Single") // set the title of the tutorial
                .setDismissText("GOT IT") // set the dismiss text
                .setContentText("This is the choose option button") // set the content or detail text
                .setDelay(500) // set delay in milliseconds to show the tutor
                // set the single use so it is shown only once using our create SHOWCASE_ID constant
                .setListener(new IShowcaseListener() {
                    @Override
                    public void onShowcaseDisplayed(MaterialShowcaseView materialShowcaseView) {

                    }

                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView materialShowcaseView) {
                        new MaterialShowcaseView.Builder(MyToolbar.this) // instantiate the material showcase view using Builder
                                .setTarget(ivProfile) // set what view will be pointed or highlighted
                                .setTitleText("Single") // set the title of the tutorial
                                .setDismissText("GOT IT") // set the dismiss text
                                .setContentText("This is the choose option button") // set the content or detail text
                                .setDelay(500) // set delay in milliseconds to show the tutor
                                .setListener(iShowcaseListener)
                                .show();
                    }
                })
                .show();
    }
}
