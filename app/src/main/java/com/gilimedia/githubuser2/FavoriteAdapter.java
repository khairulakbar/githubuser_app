package com.gilimedia.githubuser2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gilimedia.githubuser2.room.AppDatabase;
import com.gilimedia.githubuser2.room.Usergithub;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    private Context context;
    private List<Usergithub> mUsergithubList;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.username.setText(mUsergithubList.get(i).getUsername());
        myViewHolder.url.setText(mUsergithubList.get(i).getUrl());
        Picasso.with(context).load(mUsergithubList.get(i).getAvatar()).into(myViewHolder.avatar);
    }

    @Override
    public int getItemCount() {
        if (mUsergithubList == null) {
            return 0;
        }
        return mUsergithubList.size();

    }

    public void setTasks(List<Usergithub> usergithubList) {
        mUsergithubList = usergithubList;
        notifyDataSetChanged();
    }

    public List<Usergithub> getTasks() {

        return mUsergithubList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username, url;
        AppDatabase mDb;
        CircleImageView avatar;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDb = AppDatabase.getInstance(context);
            username = itemView.findViewById(R.id.tvUsername);
            url = itemView.findViewById(R.id.tvUrl);
            avatar = itemView.findViewById(R.id.img_avatar);

        }
    }
}
