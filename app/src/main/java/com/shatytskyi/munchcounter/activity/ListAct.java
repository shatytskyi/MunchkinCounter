package com.shatytskyi.munchcounter.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.adapter.ListAdapt;
import com.shatytskyi.munchcounter.data.Repo;
import com.shatytskyi.munchcounter.fragment.AddFrag;
import com.shatytskyi.munchcounter.fragment.DiceFrag;
import com.shatytskyi.munchcounter.fragment.WarningFrag;

public class ListAct extends AppCompatActivity implements Repo.OnDataChangedListener, WarningFrag.WarningDialogListener {

    private FloatingActionButton mButtonAdd;
    private View mHint;
    private ListAdapt mListAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main_list);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setSupportActionBar(findViewById(R.id.a_main_list_tb));

        mListAdapt = new ListAdapt(findViewById(R.id.a_list_rv), this);
        mHint = findViewById(R.id.a_main_list_tv_hint);

        mButtonAdd = findViewById(R.id.a_main_list_fab);
        mButtonAdd.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.a_main_list_container, new AddFrag(this))
                    .addToBackStack("add")
                    .commit();
            mButtonAdd.hide();
        });
        onDataChanged();
    }

    //Unsubscribe all
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Repo.ins().unsubscribe(this);
        Repo.ins().unsubscribe(mListAdapt.getAdapter());
    }

    //Subscribe all
    @Override
    protected void onResume() {
        super.onResume();
        Repo.ins().subscribe(this);
        Repo.ins().subscribe(mListAdapt.getAdapter());
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
                    WarningFrag f = new WarningFrag(WarningFrag.TYPE_ALL_RESET, this);
                    f.show(getSupportFragmentManager(), null);
                    break;
                } else {
                    Toast.makeText(this, R.string.toast_no_players, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.act_remove_all:
                if (Repo.ins().getData().size() != 0) {
                    WarningFrag f = new WarningFrag(WarningFrag.TYPE_ALL_REMOVE, this);
                    f.show(getSupportFragmentManager(), null);
                    break;
                } else {
                    Toast.makeText(this, R.string.toast_no_players, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.act_dice:
                getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .add(R.id.a_main_list_container, new DiceFrag(DiceFrag.TYPE_SIMPLE, null))
                        .addToBackStack(DiceFrag.FRAGMENT_TAG)
                        .commit();
                break;
            case R.id.act_info_main:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.inf)
                        .setMessage(R.string.info)
                        .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                        })
                        .show();
                break;
            case R.id.act_solo_mode:
                startActivity(new Intent(this, SoloAct.class));
                finish();
                break;
            case R.id.act_shuffle:
                Repo.ins().shuffleData();
                break;
        }
        return true;
    }

    //Control hint visibility
    @Override
    public void onDataChanged() {
        Log.d("taag", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
        if (Repo.ins().getData().size() == 0) {
            mHint.setVisibility(View.VISIBLE);
        } else {
            mHint.setVisibility(View.INVISIBLE);
        }
    }

    public void setButtonAddVisible() {
        mButtonAdd.show();
    }

    @Override
    public void onDialogPositiveButtonClicked(String dialogType, long unitId) {
        switch (dialogType) {
            case WarningFrag.TYPE_ALL_REMOVE:
                Repo.ins().createTempData();
                Repo.ins().removeAll();
                Snackbar.make(findViewById(R.id.a_main_list_container), R.string.all_was_removed, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getResources().getColor(R.color.primary, getTheme()))
                        .setActionTextColor(getResources().getColor(R.color.white, getTheme()))
                        .setAction(R.string.undo, v -> Repo.ins().restoreData())
                        .show();
                break;
            case WarningFrag.TYPE_ALL_RESET:
                Repo.ins().resetAll();
                break;
        }
    }

}