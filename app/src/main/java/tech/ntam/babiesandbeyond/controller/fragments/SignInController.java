package tech.ntam.babiesandbeyond.controller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.IntentCompat;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.activities.ActiveAccountActivity;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.babiesandbeyond.view.activities.NurseTasksHomeActivity;
import tech.ntam.babiesandbeyond.view.activities.UserHomeActivity;
import tech.ntam.mylibrary.MyDialog;

/**
 * Created by Developer on 17/12/17.
 */

public class SignInController {

    private Activity activity;
    private GoogleSignInOptions gso;

    public SignInController(Activity activity) {
        this.activity = activity;
    }

    public void SignIn(String email, final String password) {
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(activity);
        RequestAndResponse.login(activity, email, password, new BaseResponseInterface<User>() {
            @Override
            public void onSuccess(User user) {
                checkUserType(user, password);
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                myDialog.dismissMyDialog();
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private GoogleSignInOptions getGoogleSignInOptions() {
        if (gso == null)
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
        return gso;
    }

    public GoogleApiClient getGoogleApiClient() {
        return new GoogleApiClient.Builder(activity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, getGoogleSignInOptions())
                .build();
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount account, final MyDialog myDialog) {
     /*   final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    RequestAndResponse.loginWithSocial(activity, user.getEmail(), user.getDisplayName(), new BaseResponseInterface<User>() {
                                        @Override
                                        public void onSuccess(User user) {
                                            checkUserType(user, "");
                                            myDialog.dismissMyDialog();
                                        }

                                        @Override
                                        public void onFailed(String errorMessage) {
                                            Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                                            FirebaseAuth.getInstance().signOut();
                                            myDialog.dismissMyDialog();
                                        }
                                    });
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(activity, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    myDialog.dismissMyDialog();
                                }
                            }
                        }
                );*/
        RequestAndResponse.loginWithSocial(activity, account.getEmail(), account.getDisplayName(), new BaseResponseInterface<User>() {
            @Override
            public void onSuccess(User user) {
                checkUserType(user, "");
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                myDialog.dismissMyDialog();
            }
        });
    }

    private void checkUserType(User user, String password) {
        if (user.getUserTypeId().equals(User.USER)) {
            Intent intent;
            // save user information in sharedpref
            UserSharedPref.setUserInfo(activity, user.getUser_token(), user.getEmail(), user.getId(), user.getName(), user.getPhoto(), user.getPhone(), false, user.getVerificationCode(), password, user.getIsActive());
            if (user.getIsActive())
                intent = new Intent(activity, UserHomeActivity.class);
            else
                intent = new Intent(activity, ActiveAccountActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();
        } else if (user.getUserTypeId().equals(User.NURSE) || user.getUserTypeId().equals(User.MIDWIFE) || user.getUserTypeId().equals(User.BABYSITTER)) {
            UserSharedPref.setUserInfo(activity, user.getUser_token(), user.getEmail(), user.getId(), user.getName(), user.getPhoto(), user.getPhone(), true);
            activity.startActivity(new Intent(activity, NurseTasksHomeActivity.class));
            activity.finish();
            if (user.getUserTypeId().equals(User.MIDWIFE)) {
                UserSharedPref.setIamMidwife(activity);
            }
        } else {
            Toast.makeText(activity, R.string.user_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    public void handleFacebookAccessToken(AccessToken token, final LoginButton loginButton) {
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(activity);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            RequestAndResponse.loginWithSocial(activity, user.getEmail(), user.getDisplayName(), new BaseResponseInterface<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    checkUserType(user, "");
                                    dialog.dismissMyDialog();
                                }

                                @Override
                                public void onFailed(String errorMessage) {
                                    LoginManager.getInstance().logOut();
                                    loginButton.setVisibility(View.VISIBLE);
                                    dialog.dismissMyDialog();
                                    Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            LoginManager.getInstance().logOut();
                            loginButton.setVisibility(View.VISIBLE);
                            dialog.dismissMyDialog();
                            Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
