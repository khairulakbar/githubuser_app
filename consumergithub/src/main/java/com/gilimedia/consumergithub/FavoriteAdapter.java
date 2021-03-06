package com.gilimedia.consumergithub;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gilimedia.consumergithub.room.Usergithub;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    private Context context;
    private Cursor mCursor;

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
    public void onBindViewHolder(@NonNull FavoriteAdapter.MyViewHolder myViewHolder, final int i) {

        if (mCursor.moveToPosition(i)) {
            myViewHolder.username.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Usergithub.COLUMN_NAME)));
            myViewHolder.url.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Usergithub.COLUMN_URL)));
            Picasso.with(context).load(mCursor.getString(mCursor.getColumnIndexOrThrow(Usergithub.AVATAR))).into(myViewHolder.avatar);

            myViewHolder.username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, String.valueOf(i), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public void setTask(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username, url;
        CircleImageView avatar;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tvUsername);
            url = itemView.findViewById(R.id.tvUrl);
            avatar = itemView.findViewById(R.id.img_avatar);

        }
    }
}
