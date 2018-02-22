package tech.ntam.babiesandbeyond.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.fragments.SignUpController;
import tech.ntam.mylibrary.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    private static SignUpFragment signUpFragment;
    private SignUpController signUpController;
    private TextInputEditText etName;
    private TextInputEditText etPhone;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;
    private Button btnSignIn;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        if (signUpFragment == null) {
            signUpFragment = new SignUpFragment();
        }
        return signUpFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etName = view.findViewById(R.id.et_name);
        etPhone = view.findViewById(R.id.et_phone);
        etEmail = view.findViewById(R.id.email);
        etPassword = view.findViewById(R.id.et_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        btnSignIn = view.findViewById(R.id.btn_sign_in);
        onClick();
    }

    private void onClick() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().length() < 6) {
                    etName.setError(getString(R.string.invalid_name));
                } else if (!Utils.validatePhone(etPhone.getText().toString())) {
                    etPhone.setError(getString(R.string.invalid_phone));
                } else if (etEmail.getText().toString().trim().isEmpty()) {
                    etEmail.setError(getString(R.string.invalid_email));
                }else if(etPassword.getText().toString().length() <6){
                    etPassword.setError(getString(R.string.invalid_password));
                } else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    etConfirmPassword.setError(getString(R.string.invalid_confirm_password));
                } else if (!Utils.validateEmail(etEmail.getText().toString())) {
                    etEmail.setError(getString(R.string.invalid_email));
                } else {
                    // valid name , phone , etEmail and password
                    // call sign up method Controller
                    getController().SignUp(etEmail.getText().toString(),
                            etName.getText().toString(),
                            etPhone.getText().toString(),
                            etPassword.getText().toString());
                }
            }
        });
    }

    private SignUpController getController() {
        if (signUpController == null) {
            signUpController = new SignUpController(getActivity());
        }
        return signUpController;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getView() != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }

}
