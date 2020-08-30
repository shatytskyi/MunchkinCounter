package com.shaty.gamecounter.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shaty.gamecounter.data.Repo;
import com.shaty.gamecounter.fragment.AddUnitFragment;
import com.shaty.gamecounter.adapter.MainList;
import com.shaty.gamecounter.R;
import com.shaty.gamecounter.fragment.WarningFragment;

public class MainListActivity extends AppCompatActivity {

    FloatingActionButton mButtonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("tag", "create called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main_list);

        setSupportActionBar(findViewById(R.id.a_main_list_tb));

        new MainList(findViewById(R.id.a_list_rv), this);

        TextView hint = findViewById(R.id.a_main_list_tv_hint);
        if (Repo.instance().getData().size() == 0) {
            hint.setVisibility(View.VISIBLE);
        }

        mButtonAdd = findViewById(R.id.a_main_list_fab);
        mButtonAdd.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_from_down_right, R.animator.slide_out_to_down)
                    .replace(R.id.a_main_list_container, new AddUnitFragment(this))
                    .commit();
            mButtonAdd.hide();
            hint.setVisibility(View.INVISIBLE);
            MainListActivity.this.onPause();
        });
    }

    public void setAddButtonVisible() {
        mButtonAdd.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m_main_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act_reset_all:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_from_up_right, R.animator.slide_out_to_down)
                        .replace(R.id.a_main_list_container, WarningFragment.newInstance(WarningFragment.TYPE_ALL_RESET))
                        .commit();
                break;
            case R.id.act_remove_all:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_from_up_right, R.animator.slide_out_to_down)
                        .replace(R.id.a_main_list_container, WarningFragment.newInstance(WarningFragment.TYPE_ALL_REMOVED))
                        .commit();
                break;
        }
        return true;
    }
}