package com.gilimedia.githubuser2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gilimedia.githubuser2.FollowersAdapter;
import com.gilimedia.githubuser2.R;
import com.gilimedia.githubuser2.network.ApiServices;
import com.gilimedia.githubuser2.network.InitRetrofit;
import com.gilimedia.githubuser2.response.ResponseFollowers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentFollowers extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private FollowersAdapter fadapter;
    private ProgressBar progressBar;

    LinearLayoutManager mLayoutManager;
    String username;
    TextView txtStatus;

    public FragmentFollowers() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.follower_fragment, container, false);
        username = container.getTag().toString();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        txtStatus = view.findViewById(R.id.txtStatus);

        txtStatus.setVisibility(View.GONE);
        showLoading(true);
        if(username != null){
            tampilFollowers(username);
        }

    }

    private void tampilFollowers(String username) {

        ApiServices api = InitRetrofit.getInstance();
        // Siapkan request
        Call<List<ResponseFollowers>> userCall = api.req_followers(username);
        // Kirim request
        userCall.enqueue(new Callback<List<ResponseFollowers>>() {
            @Override
            public void onResponse(Call<List<ResponseFollowers>> call, Response<List<ResponseFollowers>> response) {

                // Pasikan response Sukses
                if (response.isSuccessful()) {

                    Log.d("response api", response.body().toString());
                    // tampung data response body ke variable
                    List<ResponseFollowers> data_followers = response.body();

                    fadapter = new FollowersAdapter(getActivity(), data_followers);
                    recyclerView.setAdapter(fadapter);
                    showLoading(false);

                    if (data_followers.isEmpty()) {
                        txtStatus.setText(getResources().getString(R.string.no_followers));
                        txtStatus.setVisibility(View.VISIBLE);
                    }

                } else {
                    txtStatus.setText(getResources().getString(R.string.not_found));
                    txtStatus.setVisibility(View.VISIBLE);
                    showLoading(false);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseFollowers>> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }


        });
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

}
