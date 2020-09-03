package com.shatytskyi.munchcounter.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.adapter.MainList;
import com.shatytskyi.munchcounter.data.Repo;
import com.shatytskyi.munchcounter.fragment.AddUnitFragment;
import com.shatytskyi.munchcounter.fragment.WarningFragment;

public class MainListActivity extends AppCompatActivity implements Repo.OnDataChangedListener {

    private FloatingActionButton mButtonAdd;
    private TextView mHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("tag", "omCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main_list);
        Repo.instance().subscribe(this);
        // TODO: 02.09.2020 unsubscribe

        setSupportActionBar(findViewById(R.id.a_main_list_tb));

        new MainList(findViewById(R.id.a_list_rv), this);
        mHint = findViewById(R.id.a_main_list_tv_hint);
        onDataChanged();

        mButtonAdd = findViewById(R.id.a_main_list_fab);
        mButtonAdd.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_from_down_right, R.animator.slide_out_to_down)
                    .replace(R.id.a_main_list_container, new AddUnitFragment(this))
                    .commit();
            mButtonAdd.hide();
            mHint.setVisibility(View.INVISIBLE);
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
                if (Repo.instance().getData().size() != 0) {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_in_from_up_right, R.animator.slide_out_to_down)
                            .replace(R.id.a_main_list_container, WarningFragment.newInstance(WarningFragment.TYPE_ALL_RESET))
                            .commit();
                } else {
                    Toast.makeText(this, "No players in list", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.act_remove_all:
                if (Repo.instance().getData().size() != 0) {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_in_from_up_right, R.animator.slide_out_to_down)
                            .replace(R.id.a_main_list_container, WarningFragment.newInstance(WarningFragment.TYPE_ALL_REMOVED))
                            .commit();
                    break;
                } else {
                    Toast.makeText(this, "No players in list", Toast.LENGTH_SHORT).show();
                }
        }
        return true;
    }

    @Override
    public void onDataChanged() {
        if (Repo.instance().getData().size() == 0) {
            mHint.setVisibility(View.VISIBLE);
        } else mHint.setVisibility(View.INVISIBLE);
    }
}