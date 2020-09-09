package com.shatytskyi.munchcounter.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.adapter.MainList;
import com.shatytskyi.munchcounter.data.Repo;
import com.shatytskyi.munchcounter.fragment.AddUnitFragment;
import com.shatytskyi.munchcounter.fragment.WarningDialogFragment;

public class MainListActivity extends AppCompatActivity implements Repo.OnDataChangedListener {

    // TODO: 08.09.2020 dialog fragments
    // TODO: 08.09.2020 limit lvl n power
    // TODO: 08.09.2020 add gender, classes n races, colors
    // TODO: 08.09.2020 create icon
    // TODO: 08.09.2020 add munchkin font

    private FloatingActionButton mButtonAdd;
    private TextView mHint;
    private MainList mMainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main_list);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setSupportActionBar(findViewById(R.id.a_main_list_tb));

        mMainList = new MainList(findViewById(R.id.a_list_rv), this);
        mHint = findViewById(R.id.a_main_list_tv_hint);
        Repo.ins().subscribe(this);
        Repo.ins().subscribe(mMainList.getAdapter());
        onDataChanged();

        mButtonAdd = findViewById(R.id.a_main_list_fab);
        mButtonAdd.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.a_main_list_container, new AddUnitFragment(this))
                    .addToBackStack("add")
                    .commit();
            mButtonAdd.hide();
            mHint.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Repo.ins().unsubscribe(this);
        Repo.ins().unsubscribe(mMainList.getAdapter());
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
                if (Repo.ins().getData().size() != 0) {
                    WarningDialogFragment f = new WarningDialogFragment(WarningDialogFragment.TYPE_ALL_RESET);
                    f.show(getSupportFragmentManager(), null);
                    break;
                } else {
                    Toast.makeText(this, "No players in list", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.act_remove_all:
                if (Repo.ins().getData().size() != 0) {
                    WarningDialogFragment f = new WarningDialogFragment(WarningDialogFragment.TYPE_ALL_REMOVED);
                    f.show(getSupportFragmentManager(), null);
                    break;
                } else {
                    Toast.makeText(this, "No players in list", Toast.LENGTH_SHORT).show();
                }
        }
        return true;
    }

    @Override
    public void onDataChanged() {
        if (Repo.ins().getData().size() == 0) {
            mHint.setVisibility(View.VISIBLE);
        } else mHint.setVisibility(View.INVISIBLE);
    }
}