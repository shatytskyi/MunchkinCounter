package com.shatytskyi.munchcounter.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.data.Repo;
import com.shatytskyi.munchcounter.data.Unit;
import com.shatytskyi.munchcounter.fragment.DiceFrag;
import com.shatytskyi.munchcounter.fragment.WarningFrag;

public class SoloAct extends AppCompatActivity implements WarningFrag.WarningDialogListener {

    private Unit mPlayer;
    private TextView mTVPlayerScore;
    private TextView mTVPlayerLvl;
    private TextView mTVPlayerName;

    private final String SOLO_PREF = "solo_pref";
    private final String PREF_LVL = "pref_lvl";
    private final String PREF_POWER = "pref_power";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main_solo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar(findViewById(R.id.a_main_solo_tb));

        mPlayer = new Unit("Munchkin", getSharedPreferences(SOLO_PREF, MODE_PRIVATE).getInt(PREF_LVL, 1),
                getSharedPreferences(SOLO_PREF, MODE_PRIVATE).getInt(PREF_POWER, 0));
        Repo.ins().addUnit(mPlayer);

        setupViews();
        bindViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m_main_solo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act_dice:
                getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(DiceFrag.FRAGMENT_TAG)
                        .add(R.id.a_main_solo_container, new DiceFrag(DiceFrag.TYPE_SIMPLE, null))
                        .commit();
                break;
            case R.id.act_info_solo:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.inf)
                        .setMessage(R.string.info)
                        .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                        })
                        .show();
                // TODO: 17.09.2020 create solo mode special info
                break;
            case R.id.act_group_mode:
                saveData();
                startActivity(new Intent(this, ListAct.class));
                finish();
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        Log.d("tag", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else {
            finish();
            System.exit(0);
        }
    }

    private void setupViews() {

        mTVPlayerLvl = findViewById(R.id.a_solo_tv_lvl);
        mTVPlayerScore = findViewById(R.id.a_solo_tv_score);
        mTVPlayerName = findViewById(R.id.a_solo_tv_name);
        //player buttons
        findViewById(R.id.a_solo_b_lvl_minus).setOnClickListener(v -> {
            mPlayer.lvl--;
            bindViews();
        });
        findViewById(R.id.a_solo_b_lvl_plus).setOnClickListener(v -> {
            mPlayer.lvl++;
            bindViews();
        });

        findViewById(R.id.a_solo_b_minus_1).setOnClickListener(v -> {
            mPlayer.power--;
            bindViews();
        });
        findViewById(R.id.a_solo_b_minus_2).setOnClickListener(v -> {
            mPlayer.power -= 2;
            bindViews();
        });
        findViewById(R.id.a_solo_b_minus_3).setOnClickListener(v -> {
            mPlayer.power -= 3;
            bindViews();
        });
        findViewById(R.id.a_solo_b_minus_4).setOnClickListener(v -> {
            mPlayer.power -= 4;
            bindViews();
        });
        findViewById(R.id.a_solo_b_minus_5).setOnClickListener(v -> {
            mPlayer.power -= 5;
            bindViews();
        });
        findViewById(R.id.a_solo_b_plus_1).setOnClickListener(v -> {
            mPlayer.power++;
            bindViews();
        });
        findViewById(R.id.a_solo_b_plus_2).setOnClickListener(v -> {
            mPlayer.power += 2;
            bindViews();
        });
        findViewById(R.id.a_solo_b_plus_3).setOnClickListener(v -> {
            mPlayer.power += 3;
            bindViews();
        });
        findViewById(R.id.a_solo_b_plus_4).setOnClickListener(v -> {
            mPlayer.power += 4;
            bindViews();
        });
        findViewById(R.id.a_solo_b_plus_5).setOnClickListener(v -> {
            mPlayer.power += 5;
            bindViews();
        });
    }

    private void bindViews() {
        mTVPlayerScore.setText(String.valueOf(mPlayer.getScore()));
        mTVPlayerLvl.setText(String.valueOf(mPlayer.lvl));
        mTVPlayerName.setText(mPlayer.name);
    }

    private void saveData() {
        SharedPreferences pref = getSharedPreferences(SOLO_PREF, MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.putInt(PREF_LVL, mPlayer.lvl);
        ed.putInt(PREF_POWER, mPlayer.power);
        ed.apply();
        Repo.ins().removeUnit(mPlayer.id);
    }

    @Override
    protected void onPause() {
        saveData();
        super.onPause();
    }

    @Override
    public void onDialogPositiveButtonClicked(String dialogType, long unitId) {
        Unit mTempPlayer = mPlayer.copy();
        mPlayer = new Unit("Player", 1, 0);
        bindViews();
        Snackbar.make(findViewById(R.id.a_main_solo_container), "Munchkin was reset", Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    mPlayer = mTempPlayer.copy();
                    bindViews();
                })
                .setBackgroundTint(getResources().getColor(R.color.primary, getTheme()))
                .setActionTextColor(getResources().getColor(R.color.white, getTheme()))
                .show();
    }
}