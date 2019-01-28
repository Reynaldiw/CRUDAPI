package com.reynaldiwijaya.crudapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.reynaldiwijaya.crudapi.adapter.UserAdapter;
import com.reynaldiwijaya.crudapi.api.ApiClient;
import com.reynaldiwijaya.crudapi.api.ApiInterface;
import com.reynaldiwijaya.crudapi.model.User.UserData;
import com.reynaldiwijaya.crudapi.model.User.UserResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    /**
     * Membuat variable yang dibutukna
     */
    // Membuat Variable List
    private List<UserData> userDataList;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Mengambil Object List
        userDataList = new ArrayList<>();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        showProgress();

        // Mengambil data dari SERVER REST API
        getData();
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    private void getData() {
        // Membuat Object ApiInterface
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> call = apiInterface.getUser(12);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                // Respon sukses, hilangkan progress
                progressDialog.dismiss();
                //Menghilangkan Icon refresh
                swipeRefresh.setRefreshing(false);
                // Mengambil body object userresponse
                UserResponse userResponse = response.body();
                Log.d("MainActivity","data: " + response.body());
                // Mengambil JSON Array dengan nama data
                userDataList = userResponse.getDataList();

                setUpList();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setUpList() {
        rvMain.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvMain.setAdapter(new UserAdapter(this, userDataList));
    }


}
