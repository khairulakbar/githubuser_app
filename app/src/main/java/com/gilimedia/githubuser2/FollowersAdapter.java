package com.gilimedia.githubuser2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gilimedia.githubuser2.response.ResponseFollowers;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.MyViewHolder> {

    private final Context context;
    private List<ResponseFollowers> followers;

    public FollowersAdapter(Context context, List<ResponseFollowers> data_followers) {
        // Inisialisasi
        this.context = context;
        this.followers = data_followers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);

        return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowersAdapter.MyViewHolder holder, final int position) {
        final String urlAvatar = followers.get(position).getAvatarUrl();
        final String GitUrl = followers.get(position).getHtmlUrl();

        holder.tvUsername.setText(followers.get(position).getLogin());
        holder.tvUrl.setText(String.valueOf(GitUrl));
        Picasso.with(context).load(urlAvatar).into(holder.ivAvatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Username :" + followers.get(position).getLogin(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("USER_NAME", followers.get(position).getLogin());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return followers.size();
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
