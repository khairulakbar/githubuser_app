package com.gilimedia.githubuser2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gilimedia.githubuser2.room.AppDatabase;
import com.gilimedia.githubuser2.room.AppExecutors;
import com.gilimedia.githubuser2.room.Usergithub;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FavoriteAdapter mAdapter;
    private AppDatabase mDb;
    private static final int LOADER_USER = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Intent intent = getIntent();
        String alert = intent.getStringExtra("ALERT");

        if(alert != null) {
            Toast.makeText(FavoriteActivity.this, alert, Toast.LENGTH_SHORT).show();
        }

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new FavoriteAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mDb = AppDatabase.getInstance(getApplicationContext());

        LoaderManager.getInstance(this).initLoader(LOADER_USER, null, mLoaderCallbacks);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

                // Swipe to delete implementation
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        Cursor cursor = mAdapter.getTasks();

                        int col = 0;
                        if(cursor.moveToPosition(position)){
                            col = cursor.getColumnIndexOrThrow(Usergithub.COLUMN_ID);
                        }

                        mDb.userDao().deleteById(cursor.getLong(col));
                        retrieveTasks();
                    }
                });

            }

            @Override
            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(FavoriteActivity.this, R.color.bg_swipe_delete))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete_24dp)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(FavoriteActivity.this, R.color.bg_swipe_delete))
                        .addSwipeRightActionIcon(R.drawable.ic_delete_24dp)
                        .addSwipeRightLabel(getString(R.string.tx_delete))
                        .setSwipeRightLabelColor(Color.WHITE)
                        .addSwipeLeftLabel(getString(R.string.tx_delete))
                        .setSwipeLeftLabelColor(Color.WHITE)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        }).attachToRecyclerView(mRecyclerView);



    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final Cursor cursor = mDb.userDao().selectAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mAdapter.setTask(cursor);
                    }
                });
            }
        });


    }


    private final LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {

                @Override
                @NonNull
                public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
                    return new CursorLoader(getApplicationContext(),
                            Usergithub.URI_USER,
                            new String[]{Usergithub.COLUMN_NAME},
                            null, null, null);
                }

                @Override
                public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
                    mAdapter.setTask(data);
                }

                @Override
                public void onLoaderReset(@NonNull Loader<Cursor> loader) {
                    mAdapter.setTask(null);
                }

            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);

        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        if(item.getItemId() == R.id.action_notif_setting){
            Intent mIntent = new Intent(FavoriteActivity.this,NotifSetting.class);
            startActivity(mIntent);
        }if(item.getItemId() == R.id.action_favorite){
            Intent mIntent = new Intent(FavoriteActivity.this,FavoriteActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
