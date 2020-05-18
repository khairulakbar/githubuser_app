package com.gilimedia.githubuser2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.gilimedia.githubuser2.fragment.FragmentFollowers;
import com.gilimedia.githubuser2.fragment.FragmentFollowing;
import com.gilimedia.githubuser2.fragment.ViewPagerAdapter;
import com.gilimedia.githubuser2.network.ApiServices;
import com.gilimedia.githubuser2.network.InitRetrofit;
import com.gilimedia.githubuser2.response.ResponseDetailUser;
import com.gilimedia.githubuser2.room.AppDatabase;
import com.gilimedia.githubuser2.room.AppExecutors;
import com.gilimedia.githubuser2.room.Usergithub;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TextView tvUsername, tvNama, company, location, numfollowers, numfollowing, numrepo;
    private CircleImageView ImgAvatar;
    private AppDatabase mDb;
    private ProgressDialog loading;
    private String username,avatar,githubUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDb = AppDatabase.getInstance(getApplicationContext());

        ImgAvatar = findViewById(R.id.avatar_detail);
        tvNama = findViewById(R.id.tvName_detail);
        tvUsername = findViewById(R.id.tvUsername_detail);
        numfollowers = findViewById(R.id.num_followers);
        numfollowing = findViewById(R.id.num_following);
        numrepo = findViewById(R.id.num_repo);
        company = findViewById(R.id.tvCompany);
        location = findViewById(R.id.tvLocation);

        TabLayout tabLayout = findViewById(R.id.tablayout_id);
        ViewPager viewPager = findViewById(R.id.viewpager_id);

        FloatingActionButton favorit = findViewById(R.id.fab_favorite);

        loading = ProgressDialog.show(DetailActivity.this, null, "Loading...", true, false);
        loading.show();


        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");
        avatar = intent.getStringExtra("AVATAR");
        githubUrl = intent.getStringExtra("URL");


        //tampilkan detail user
        if(username != null){
            showDetail(username);
        }


        String follower = getResources().getString(R.string.followers);
        String following = getResources().getString(R.string.following);
        //setup viewpager followers dan following
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentFollowers(), follower);
        adapter.AddFragment(new FragmentFollowing(), following);

        viewPager.setTag(username); //masukkan username ke dalam viewpager
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        favorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Usergithub usergithub = new Usergithub(
                        username,
                        githubUrl,
                        avatar);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Usergithub usergithub1 = mDb.userDao().loadPersonById(username);
                        if(usergithub1 == null){
                            mDb.userDao().insertPerson(usergithub);
                            finish();
                        }


                    }
                });
            }
        });


    }

    private void showDetail(final String username) {
        ApiServices api = InitRetrofit.getInstance();
        // Siapkan request
        Call<ResponseDetailUser> detailUserCall = api.req_detail_user(username);
        // Kirim request
        detailUserCall.enqueue(new Callback<ResponseDetailUser>() {
            @Override
            public void onResponse(Call<ResponseDetailUser> call, Response<ResponseDetailUser> response) {

                // Pasikan response Sukses
                if (response.isSuccessful()) {

                    Log.d("response api", response.body().toString());

                    String urlAvatar = response.body().getAvatarUrl();
                    String nama = response.body().getName();
                    String nama_user = response.body().getLogin();
                    String comp = response.body().getCompany();
                    String loc = response.body().getLocation();
                    Integer follower = response.body().getFollowers();
                    Integer following = response.body().getFollowing();
                    Integer repo = response.body().getPublicRepos();

                    tvNama.setText(nama);
                    tvUsername.setText(nama_user);
                    company.setText(comp);
                    location.setText(loc);
                    numfollowers.setText(String.valueOf(follower));
                    numfollowing.setText(String.valueOf(following));
                    numrepo.setText(String.valueOf(repo));
                    Picasso.with(DetailActivity.this).load(urlAvatar).into(ImgAvatar);

                    loading.dismiss();


                } else {
                    Toast.makeText(DetailActivity.this, getResources().getString(R.string.not_found), Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailUser> call, Throwable t) {
                Toast.makeText(DetailActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }


        });
    }


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
        }if(item.getItemId() == R.id.action_favorite){
            Intent mIntent = new Intent(DetailActivity.this,FavoriteActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
