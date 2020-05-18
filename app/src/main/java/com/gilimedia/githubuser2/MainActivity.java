package com.gilimedia.githubuser2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gilimedia.githubuser2.network.ApiServices;
import com.gilimedia.githubuser2.network.InitRetrofit;
import com.gilimedia.githubuser2.response.ItemsItem;
import com.gilimedia.githubuser2.response.ResponseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    Context mContext;
    private UserAdapter adapter;
    private SearchView search;

    private ProgressBar progressBar;
    public final static String LIST_STATE_KEY = "recycler_list_state";
    Parcelable listState;
    LinearLayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mContext = this;

        search = findViewById(R.id.search);
        search.setOnQueryTextListener(this);



    }


    private void tampilUser(final String query) {
        ApiServices api = InitRetrofit.getInstance();
        // Siapkan request
        Call<ResponseUser> userCall = api.req_show_user(query);
        // Kirim request
        userCall.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {

                // Pasikan response Sukses
                if (response.isSuccessful()) {
                    Log.d("response api", response.body().toString());
                    // tampung data response body ke variable
                    List<ItemsItem> data_user = response.body().getItems();

                    adapter = new UserAdapter(MainActivity.this, data_user);
                    recyclerView.setAdapter(adapter);

                    showLoading(false);

                    if (data_user.isEmpty()) {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.not_found), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.not_found), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }


        });
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        showLoading(true);
        tampilUser(query);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (TextUtils.isEmpty(query)) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.filling), Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        // Simpan state List
        listState = mLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, listState);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        // Kembalikan state List dan posisi item
        if (state != null)
            listState = state.getParcelable(LIST_STATE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listState != null) {
            mLayoutManager.onRestoreInstanceState(listState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }if(item.getItemId() == R.id.action_favorite){
            Intent mIntent = new Intent(MainActivity.this,FavoriteActivity.class);
            startActivity(mIntent);
        }
        if(item.getItemId() == R.id.action_notif_setting){
            Intent mIntent = new Intent(MainActivity.this,NotifSetting.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }




}
