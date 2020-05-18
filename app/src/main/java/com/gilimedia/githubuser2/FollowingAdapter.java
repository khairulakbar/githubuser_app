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

import com.gilimedia.githubuser2.response.ResponseFollowing;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.MyViewHolder> {

    private final Context context;
    private List<ResponseFollowing> followings;

    public FollowingAdapter(Context context, List<ResponseFollowing> data_followers) {
        // Inisialisasi
        this.context = context;
        this.followings = data_followers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Layout inflater
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);

        // Hubungkan dengan MyViewHolder
        //MyViewHolder holder = new MyViewHolder(view);
        //return holder;
        //dijadikan satu statement seperti di bawah
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingAdapter.MyViewHolder holder, final int position) {
        final String urlAvatar = followings.get(position).getAvatarUrl();
        final String GitUrl = followings.get(position).getHtmlUrl();

        holder.tvUsername.setText(followings.get(position).getLogin());
        holder.tvUrl.setText(String.valueOf(GitUrl));
        Picasso.with(context).load(urlAvatar).into(holder.ivAvatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Username :" + followings.get(position).getLogin(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("USER_NAME", followings.get(position).getLogin());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return followings.size();
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
