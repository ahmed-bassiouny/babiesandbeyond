package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.fragments.SignInController;
import tech.ntam.mylibrary.MyDialog;

public class LoginOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private int RC_SIGN_IN = 5;
    private CallbackManager mCallbackManager;
    private LoginButton loginButton;
    private SignInController controller;
    private MyDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);
        findViewById(R.id.iv_facebook).setOnClickListener(this);
        findViewById(R.id.iv_gmail).setOnClickListener(this);
        findViewById(R.id.iv_email).setOnClickListener(this);
        findViewById(R.id.tv_terms).setOnClickListener(this);
        initObject();
    }

    private void initObject() {
        loginButton = new LoginButton(this);
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.INVISIBLE);
                getController().handleFacebookAccessToken(loginResult.getAccessToken(),loginButton);
            }

            @Override
            public void onCancel() {
                Log.e( "onError: ","Eror");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e( "onError: ",error.toString() );
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_facebook:
                loginButton.performClick();
                break;
            case R.id.iv_gmail:
                myDialog = new MyDialog();
                myDialog.showMyDialog(this);
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(getController().getGoogleApiClient());
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
            case R.id.iv_email:
                startActivity(new Intent(LoginOptionsActivity.this, SignIn_UpActivity.class));
                break;
            case R.id.tv_terms:
                startActivity(new Intent(LoginOptionsActivity.this,TermsActivity.class));
        }
    }
    private SignInController getController() {
        if (controller == null) {
            controller = new SignInController(this);
        }
        return controller;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                getController().firebaseAuthWithGoogle(account,myDialog);
            }else {
                myDialog.dismissMyDialog();

            }
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
