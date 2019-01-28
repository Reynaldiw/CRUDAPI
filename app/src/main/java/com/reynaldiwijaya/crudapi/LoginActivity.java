package com.reynaldiwijaya.crudapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.reynaldiwijaya.crudapi.api.ApiClient;
import com.reynaldiwijaya.crudapi.api.ApiInterface;
import com.reynaldiwijaya.crudapi.model.login.LoginBody;
import com.reynaldiwijaya.crudapi.model.login.LoginResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    // Membuat variable untuk animasi loading menggunakan ProsesDialog
    private LoginBody loginBody;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        showProgress();
        getData();
        login();
    }

    private void login() {
//        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading ...", "Get Data");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<LoginResponse> call = apiInterface.postLogin(loginBody);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();


                    // Menampilkan response API berupa token ke dalam toast
                    Toast.makeText(LoginActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
                    // Berpindah halaman ke Main Activity
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();


                // Menampilkan response message
                Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(retrofit2.Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getData() {
        // Buat object LoginBody
        loginBody = new LoginBody();
        loginBody.setEmail(edtEmail.getText().toString());
        loginBody.setPassword(edtPassword.getText().toString());

    }

    private void showProgress() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading ...");
        progressDialog.setMessage("Get Data ...");
        progressDialog.show();

    }
}
