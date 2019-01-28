package com.reynaldiwijaya.crudapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.reynaldiwijaya.crudapi.api.ApiClient;
import com.reynaldiwijaya.crudapi.api.ApiInterface;
import com.reynaldiwijaya.crudapi.model.Constant;
import com.reynaldiwijaya.crudapi.model.User.UserData;
import com.reynaldiwijaya.crudapi.model.User.UserResponse;
import com.reynaldiwijaya.crudapi.model.detail.Data;
import com.reynaldiwijaya.crudapi.model.detail.ResponseDetailUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.img_avatar_detail)
    ImageView imgAvatarDetail;
    @BindView(R.id.tv_firstName_detail)
    TextView tvFirstNameDetail;
    @BindView(R.id.tv_lastName_detail)
    TextView tvLastNameDetail;

    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private int id;
    private Bundle bundle;
    private Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        id = bundle.getInt(Constant.EXTRA_OBJECT);

        showProgress();
        showDetail();
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setTitle("Loading ...");
        progressDialog.setMessage("Get Data ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void showDetail() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseDetailUser> call = apiInterface.getDetailUser(id);
        call.enqueue(new Callback<ResponseDetailUser>() {
            @Override
            public void onResponse(Call<ResponseDetailUser> call, Response<ResponseDetailUser> response) {
                progressDialog.dismiss();

                ResponseDetailUser responseDetailUser = response.body();

                data = responseDetailUser.getData();

                setUpList();
            }

            @Override
            public void onFailure(Call<ResponseDetailUser> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setUpList() {
        Glide.with(this).load(data.getAvatar()).into(imgAvatarDetail);
        tvFirstNameDetail.setText(data.getFirstName());
        tvLastNameDetail.setText(data.getLastName());
    }
}
