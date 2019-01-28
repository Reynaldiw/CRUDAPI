package com.reynaldiwijaya.crudapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.reynaldiwijaya.crudapi.DetailActivity;
import com.reynaldiwijaya.crudapi.R;
import com.reynaldiwijaya.crudapi.model.Constant;
import com.reynaldiwijaya.crudapi.model.User.UserData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final Context context;
    private final List<UserData> userDataList;
    private Bundle bundle;

    public UserAdapter(Context context, List<UserData> userDataList) {
        this.context = context;
        this.userDataList = userDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final UserData userData = userDataList.get(i);

        viewHolder.tvFirstName.setText(userData.getFirstName());
        viewHolder.tvLastName.setText(userData.getLastName());

        // Membuat Object RequestOptions
        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);

        Glide.with(context)
                .load(userData.getGambar())
                .apply(options)
                .into(viewHolder.imgAvatar);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = userData.getId();
                Log.d(Constant.TAG, "id: " + id);
                bundle = new Bundle();
                bundle.putInt(Constant.EXTRA_OBJECT, id);
                context.startActivity(new Intent(context, DetailActivity.class).putExtras(bundle));
            }
        });

    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.tv_firstName)
        TextView tvFirstName;
        @BindView(R.id.tv_lastName)
        TextView tvLastName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
