package com.gilimedia.githubuser2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gilimedia.githubuser2.response.ItemsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    Context context;
    List<ItemsItem> users;

    public UserAdapter(Context context, List<ItemsItem> data_user) {
        // Inisialisasi
        this.context = context;
        this.users = data_user;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Layout inflater
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);

        // Hubungkan dengan MyViewHolder
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, final int position) {
        final String urlAvatar = users.get(position).getAvatarUrl();
        final String GitUrl = users.get(position).getHtmlUrl();

        holder.tvUsername.setText(users.get(position).getLogin());
        holder.tvUrl.setText(String.valueOf(GitUrl));
        Picasso.with(context).load(urlAvatar).into(holder.ivAvatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("USER_ID", String.valueOf(users.get(position).getId()));
                intent.putExtra("USER_NAME", users.get(position).getLogin());
                intent.putExtra("AVATAR", users.get(position).getAvatarUrl());
                intent.putExtra("URL", users.get(position).getHtmlUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Deklarasi widget
        ImageView ivAvatar;
        TextView tvUsername, tvUrl;

        public MyViewHolder(View itemView) {
            super(itemView);
            // inisialisasi widget
            ivAvatar = itemView.findViewById(R.id.img_avatar);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvUrl = itemView.findViewById(R.id.tvUrl);
        }
    }


}
