package com.arshiya.newsapp.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arshiya.newsapp.AppLifeCycleObserver;
import com.arshiya.newsapp.R;
import com.arshiya.newsapp.articlelist.ui.activities.ArticleListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements ILoginPresenter.LoginEventListener {

    private static final String TAG = "LoginActivity";
    private LoginPresenter loginPresenter;

    @BindView(R.id.username)
    EditText etUserName;

    @BindView(R.id.password)
    EditText etPassword;

    @BindView(R.id.login)
    Button btnLogin;

    @BindView(R.id.error)
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        getLifecycle().addObserver(new AppLifeCycleObserver());

        loginPresenter = new LoginPresenter(this);

        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
                loginPresenter.login(getApplicationContext(), etUserName.getText().toString(), etPassword.getText().toString());
                return true;
            }

            return false;
        });

        btnLogin.setOnClickListener(v -> loginPresenter.login(getApplicationContext(),
                etUserName.getText().toString(), etPassword.getText().toString()));
    }

    @Override
    public void onLoginFail(Exception error) {
        tvError.setText(error.getMessage());
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, ArticleListActivity.class);
        startActivity(intent);
        finish();
    }
}