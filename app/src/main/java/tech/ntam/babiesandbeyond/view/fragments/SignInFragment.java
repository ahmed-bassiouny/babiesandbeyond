package tech.ntam.babiesandbeyond.view.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.fragments.SignInController;
import tech.ntam.babiesandbeyond.view.activities.UserForgetPasswordActivity;
import tech.ntam.mylibrary.MyDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {


    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private TextView tvForgetPassword;
    private Button btnSignIn;
    private SignInController controller;
    private static SignInFragment signInFragment;
    private int RC_SIGN_IN = 5;
    private MyDialog myDialog;
    private CallbackManager mCallbackManager;
    private LoginButton loginButton;

    public static SignInFragment newInstance() {
        if (signInFragment == null) {
            signInFragment = new SignInFragment();
        }
        return signInFragment;
    }

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btnSignIn = view.findViewById(R.id.btn_sign_in);
        tvForgetPassword = view.findViewById(R.id.tv_forget_password);
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        onClick(view);
    }

    private void onClick(View view) {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().trim().isEmpty()) {
                    etEmail.setError(getString(R.string.invalid_email));
                } else if (etPassword.getText().toString().trim().isEmpty()) {
                    etPassword.setError(getString(R.string.invalid_password));
                } else {
                    // valid email and password
                    // call sign in method Controller
                    getController().SignIn(etEmail.getText().toString()
                            , etPassword.getText().toString());
                }
            }
        });
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UserForgetPasswordActivity.class));
            }
        });

        view.findViewById(R.id.iv_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog = new MyDialog();
                myDialog.showMyDialog(getActivity());
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(getController().getGoogleApiClient());
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getController().handleFacebookAccessToken(loginResult.getAccessToken());
                Log.e( "onError: ",loginResult.toString() );
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

    private SignInController getController() {
        if (controller == null) {
            controller = new SignInController(getActivity());
        }
        return controller;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getView() != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
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
