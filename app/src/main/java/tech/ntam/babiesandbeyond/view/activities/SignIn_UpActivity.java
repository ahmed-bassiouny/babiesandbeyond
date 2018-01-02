package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.fragments.SignInFragment;
import tech.ntam.babiesandbeyond.view.fragments.SignUpFragment;
import tech.ntam.mylibrary.Utils;

public class SignIn_UpActivity extends AppCompatActivity {


    private TextView tvSignIn;
    private TextView tvSignUp;
    private boolean showSignUpPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__up);
        findViewById();
        onClick();
        initObject();
    }

    private void initObject() {
        Utils.goToFragment(R.id.frame_fragment,this,SignInFragment.newInstance(),false,null);
    }

    private void onClick() {
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpPage = false;
                tvSignUp.setBackground(null);
                tvSignIn.setBackground(getResources().getDrawable(R.drawable.line));
                Utils.goToFragment(R.id.frame_fragment,SignIn_UpActivity.this,SignInFragment.newInstance(),false,null);
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpPage = true;
                tvSignIn.setBackground(null);
                tvSignUp.setBackground(getResources().getDrawable(R.drawable.line));
                Utils.goToFragment(R.id.frame_fragment,SignIn_UpActivity.this, SignUpFragment.newInstance(),false,null);
            }
        });
    }

    private void findViewById() {
        tvSignIn = findViewById(R.id.tv_sign_in);
        tvSignUp = findViewById(R.id.tv_sign_up);
    }

    @Override
    public void onBackPressed() {
        if(showSignUpPage){
            showSignUpPage = false;
            tvSignUp.setBackground(null);
            tvSignIn.setBackground(getResources().getDrawable(R.drawable.line));
            Utils.goToFragment(R.id.frame_fragment,SignIn_UpActivity.this,SignInFragment.newInstance(),false,null);
        }else {
            super.onBackPressed();
        }
    }
}
