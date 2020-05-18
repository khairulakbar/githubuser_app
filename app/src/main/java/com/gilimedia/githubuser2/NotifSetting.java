package com.gilimedia.githubuser2;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gilimedia.githubuser2.notification.NotificationReceiver;

public class NotifSetting extends AppCompatActivity implements View.OnClickListener {
    private NotificationReceiver notificationReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        Button btn_cancel = findViewById(R.id.btn_cancel_repeating_alarm);
        Button btn_set = findViewById(R.id.btn_set_repeating_alarm);
        btn_cancel.setOnClickListener(this);
        btn_set.setOnClickListener(this);


        notificationReceiver = new NotificationReceiver();

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
            Intent mIntent = new Intent(NotifSetting.this,FavoriteActivity.class);
            startActivity(mIntent);
        }
        if(item.getItemId() == R.id.action_notif_setting){
            Intent mIntent = new Intent(NotifSetting.this,NotifSetting.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel_repeating_alarm:
                notificationReceiver.cancelAlarm(this);
                break;
            case R.id.btn_set_repeating_alarm:
                notificationReceiver.setNotif(this);
                break;
        }
    }
}
