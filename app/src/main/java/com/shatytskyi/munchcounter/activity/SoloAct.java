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

import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.data.Repo;
import com.shatytskyi.munchcounter.data.Unit;
import com.shatytskyi.munchcounter.fragment.DiceFrag;
import com.shatytskyi.munchcounter.fragment.EditFrag;
import com.shatytskyi.munchcounter.fragment.WarningFrag;

public class SoloAct extends AppCompatActivity implements WarningFrag.WarningDialogListener, Repo.OnDataChangedListener {

    private Unit mPlayer;
    private TextView mTVPlayerScore;
    private TextView mTVPlayerLvl;
    private TextView mTVPlayerName;

    private final String SOLO_PREF = "solo_pref";
    private final String PREF_LVL = "pref_lvl";
    private final String PREF_POWER = "pref_power";
    private final String PREF_NAME = "pref_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main_solo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar(findViewById(R.id.a_main_solo_tb));
        Repo.ins().subscribe(this);
        mPlayer = new Unit(
                getSharedPreferences(SOLO_PREF, MODE_PRIVATE).getString(PREF_NAME, getString(R.string.munchkin)),
                getSharedPreferences(SOLO_PREF, MODE_PRIVATE).getInt(PREF_LVL, 1),
                getSharedPreferences(SOLO_PREF, MODE_PRIVATE).getInt(PREF_POWER, 0));

        setupViews();
        Repo.ins().addUnit(mPlayer);
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
            saveData();
            finish();
        }
    }

    private void setupViews() {

        findViewById(R.id.a_solo_b_edit).setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.a_main_solo_container, new EditFrag(mPlayer.id), EditFrag.FRAGMENT_TAG)
                    .addToBackStack(EditFrag.FRAGMENT_TAG)
                    .commit();
        });

        findViewById(R.id.a_solo_b_reset).setOnClickListener(v -> {
            WarningFrag f = new WarningFrag(WarningFrag.TYPE_RESET, mPlayer.id, this);
            f.show(getSupportFragmentManager(), null);
        });

        findViewById(R.id.a_solo_b_fight).setOnClickListener(v -> {
            Intent intent = new Intent(SoloAct.this, FightAct.class);
            intent.putExtra(FightAct.EXTRA_USER_ID, mPlayer.id);
            startActivity(intent);
        });


        mTVPlayerLvl = findViewById(R.id.a_solo_tv_lvl);
        mTVPlayerScore = findViewById(R.id.a_solo_tv_score);
        mTVPlayerName = findViewById(R.id.a_solo_tv_name);
        //player buttons
        findViewById(R.id.a_solo_b_lvl_minus).setOnClickListener(v -> {
            Repo.ins().changeLvl(mPlayer.id, -1);
            onDataChanged();
        });
        findViewById(R.id.a_solo_b_lvl_plus).setOnClickListener(v -> {
            Repo.ins().changeLvl(mPlayer.id, +1);
            onDataChanged();
        });

        findViewById(R.id.a_solo_b_minus_1).setOnClickListener(v -> {
            Repo.ins().changePower(mPlayer.id, -1);
            onDataChanged();
        });
        findViewById(R.id.a_solo_b_minus_2).setOnClickListener(v -> {
            Repo.ins().changePower(mPlayer.id, -2);
            onDataChanged();
        });
        findViewById(R.id.a_solo_b_minus_3).setOnClickListener(v -> {
            Repo.ins().changePower(mPlayer.id, -3);
            onDataChanged();
        });
        findViewById(R.id.a_solo_b_minus_4).setOnClickListener(v -> {
            Repo.ins().changePower(mPlayer.id, -4);
            onDataChanged();
        });
        findViewById(R.id.a_solo_b_minus_5).setOnClickListener(v -> {
            Repo.ins().changePower(mPlayer.id, -5);
            onDataChanged();
        });
        findViewById(R.id.a_solo_b_plus_1).setOnClickListener(v -> {
            Repo.ins().changePower(mPlayer.id, +1);
            onDataChanged();
        });
        findViewById(R.id.a_solo_b_plus_2).setOnClickListener(v -> {
            Repo.ins().changePower(mPlayer.id, +2);
            onDataChanged();
        });
        findViewById(R.id.a_solo_b_plus_3).setOnClickListener(v -> {
            Repo.ins().changePower(mPlayer.id, +3);
            onDataChanged();
        });
        findViewById(R.id.a_solo_b_plus_4).setOnClickListener(v -> {
            Repo.ins().changePower(mPlayer.id, +4);
            onDataChanged();
        });
        findViewById(R.id.a_solo_b_plus_5).setOnClickListener(v -> {
            Repo.ins().changePower(mPlayer.id, +5);
            onDataChanged();
        });
    }

    private void saveData() {
        SharedPreferences pref = getSharedPreferences(SOLO_PREF, MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.putString(PREF_NAME, mPlayer.name);
        ed.putInt(PREF_LVL, mPlayer.lvl);
        ed.putInt(PREF_POWER, mPlayer.power);
        ed.apply();
        Repo.ins().unsubscribe(this);
        Repo.ins().removeUnit(mPlayer.id);
    }

    @Override
    public void onDialogPositiveButtonClicked(String dialogType, long unitId) {
        switch (dialogType) {
            case WarningFrag.TYPE_RESET:
                Repo.ins().resetUnit(mPlayer.id);
                break;
        }
    }

    @Override
    public void onDataChanged() {
        mTVPlayerScore.setText(String.valueOf(mPlayer.getScore()));
        mTVPlayerLvl.setText(String.valueOf(mPlayer.lvl));
        mTVPlayerName.setText(mPlayer.name);
    }
}