package com.shatytskyi.munchcounter.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.adapter.MainList;
import com.shatytskyi.munchcounter.data.Repo;
import com.shatytskyi.munchcounter.fragment.AddUnitFragment;
import com.shatytskyi.munchcounter.fragment.DiceFragment;
import com.shatytskyi.munchcounter.fragment.WarningDialogFragment;

public class MainListActivity extends AppCompatActivity implements Repo.OnDataChangedListener {

    // TODO: 08.09.2020 limit lvl n power
    // TODO: бонусы к смывке
    // TODO: 16.09.2020 death
    // TODO: 16.09.2020 change player

    private FloatingActionButton mButtonAdd;
    private View mHint;
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
                    Toast.makeText(this, R.string.toast_no_players, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.act_remove_all:
                if (Repo.ins().getData().size() != 0) {
                    WarningDialogFragment f = new WarningDialogFragment(WarningDialogFragment.TYPE_ALL_REMOVE);
                    f.show(getSupportFragmentManager(), null);
                    break;
                } else {
                    Toast.makeText(this, R.string.toast_no_players, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.act_dice:
                getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .add(R.id.a_main_list_container, new DiceFragment())
                        .commit();
                break;
            case R.id.act_info:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.inf)
                        .setMessage(R.string.info)
                        .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                        })
                        .show();
                break;
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