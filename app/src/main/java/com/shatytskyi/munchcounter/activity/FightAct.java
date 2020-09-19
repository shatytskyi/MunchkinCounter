package com.shatytskyi.munchcounter.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.adapter.HelpersAdapt;
import com.shatytskyi.munchcounter.data.Repo;
import com.shatytskyi.munchcounter.data.Unit;
import com.shatytskyi.munchcounter.fragment.DiceFrag;
import com.shatytskyi.munchcounter.fragment.HelpersFrag;

import java.util.Objects;

public class FightAct extends AppCompatActivity implements HelpersAdapt.HelperListener, DiceFrag.DiceListener {

    public static final String EXTRA_USER_ID = "extra_user_id";

    private long mUnitId;
    private Unit mPlayer;
    private Unit mHelper;
    private Unit mMonster;
    private int mInitPower;

    private TextView mTVPlayerName;
    private TextView mTVPlayerScore;
    private TextView mTVPlayerLvl;
    private TextView mTVMonsterScore;
    private TextView mTVHelperName;
    private TextView mTVResultValue;
    private TextView mTVGetHelp;
    private ImageView mButtonResult;
    private TextView mTVResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_fight);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupViews();
        mUnitId = getIntent().getLongExtra(EXTRA_USER_ID, -1);
        mInitPower = Repo.ins().findUnitById(mUnitId).power;
        mPlayer = Repo.ins().findUnitById(mUnitId).copy();
        mMonster = new Unit("Monster", 0, 0);
        bindViews();

    }

    private void setupViews() {

        //changeable text views
        mTVPlayerName = findViewById(R.id.a_fight_tv_table_player);
        mTVHelperName = findViewById(R.id.a_fight_tv_table_helper);
        mTVResultValue = findViewById(R.id.a_fight_tv_table_result);
        mTVPlayerLvl = findViewById(R.id.a_fight_tv_lvl);
        mTVPlayerScore = findViewById(R.id.a_fight_tv_score);
        mTVMonsterScore = findViewById(R.id.a_fight_tv_monster_score);
        mTVGetHelp = findViewById(R.id.a_fight_tv_get_help);
        mButtonResult = findViewById(R.id.a_fight_b_result);
        mTVResult = findViewById(R.id.a_fight_tv_result);

        //functional buttons
        findViewById(R.id.a_fight_b_finish).setOnClickListener(v -> finish());
        findViewById(R.id.a_fight_b_reset_fight).setOnClickListener(v -> {
            mMonster = new Unit("Monster", 0, 0);
            mPlayer = Repo.ins().findUnitById(mUnitId).copy();
            mHelper = null;
            bindViews();
            Toast.makeText(this, R.string.fight_was_reset, Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.a_fight_b_get_help).setOnClickListener(v -> getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.a_fight_container,
                        new HelpersFrag((mPlayer.getScore() - mMonster.getScore()), this),
                        HelpersFrag.FRAGMENT_TAG)
                .addToBackStack(HelpersFrag.FRAGMENT_TAG)
                .commit());

        findViewById(R.id.a_fight_tv_table_helper).setOnClickListener(v -> {
            mHelper = null;
            Toast.makeText(this, "Partner removed", Toast.LENGTH_SHORT).show();
            bindViews();
        });

        findViewById(R.id.a_fight_b_dice).setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(DiceFrag.FRAGMENT_TAG)
                    .add(R.id.a_fight_container, new DiceFrag(DiceFrag.TYPE_SIMPLE, null))
                    .commit();
        });

        findViewById(R.id.a_fight_b_info).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle(R.string.inf)
                    .setMessage(R.string.info)
                    .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    })
                    .show();
        });


        //player buttons
        findViewById(R.id.a_fight_b_lvl_minus).setOnClickListener(v -> {
            mPlayer.lvl--;
            Repo.ins().changeLvl(mUnitId, -1);
            bindViews();
        });
        findViewById(R.id.a_fight_b_lvl_plus).setOnClickListener(v -> {
            mPlayer.lvl++;
            Repo.ins().changeLvl(mUnitId, +1);
            bindViews();
        });

        findViewById(R.id.a_fight_b_minus_1).setOnClickListener(v -> {
            mPlayer.power--;
            bindViews();
        });
        findViewById(R.id.a_fight_b_minus_2).setOnClickListener(v -> {
            mPlayer.power -= 2;
            bindViews();
        });
        findViewById(R.id.a_fight_b_minus_3).setOnClickListener(v -> {
            mPlayer.power -= 3;
            bindViews();
        });
        findViewById(R.id.a_fight_b_minus_4).setOnClickListener(v -> {
            mPlayer.power -= 4;
            bindViews();
        });
        findViewById(R.id.a_fight_b_minus_5).setOnClickListener(v -> {
            mPlayer.power -= 5;
            bindViews();
        });
        findViewById(R.id.a_fight_b_plus_1).setOnClickListener(v -> {
            mPlayer.power++;
            bindViews();
        });
        findViewById(R.id.a_fight_b_plus_2).setOnClickListener(v -> {
            mPlayer.power += 2;
            bindViews();
        });
        findViewById(R.id.a_fight_b_plus_3).setOnClickListener(v -> {
            mPlayer.power += 3;
            bindViews();
        });
        findViewById(R.id.a_fight_b_plus_4).setOnClickListener(v -> {
            mPlayer.power += 4;
            bindViews();
        });
        findViewById(R.id.a_fight_b_plus_5).setOnClickListener(v -> {
            mPlayer.power += 5;
            bindViews();
        });

        //monster buttons
        findViewById(R.id.a_fight_monster_b_minus_1).setOnClickListener(v -> {
            mMonster.power--;
            bindViews();
        });
        findViewById(R.id.a_fight_monster_b_minus_2).setOnClickListener(v -> {
            mMonster.power -= 2;
            bindViews();
        });
        findViewById(R.id.a_fight_monster_b_minus_3).setOnClickListener(v -> {
            mMonster.power -= 3;
            bindViews();
        });
        findViewById(R.id.a_fight_monster_b_minus_4).setOnClickListener(v -> {
            mMonster.power -= 4;
            bindViews();
        });
        findViewById(R.id.a_fight_monster_b_minus_5).setOnClickListener(v -> {
            mMonster.power -= 5;
            bindViews();
        });
        findViewById(R.id.a_fight_monster_b_plus_1).setOnClickListener(v -> {
            mMonster.power++;
            bindViews();
        });
        findViewById(R.id.a_fight_monster_b_plus_2).setOnClickListener(v -> {
            mMonster.power += 2;
            bindViews();
        });
        findViewById(R.id.a_fight_monster_b_plus_3).setOnClickListener(v -> {
            mMonster.power += 3;
            bindViews();
        });
        findViewById(R.id.a_fight_monster_b_plus_4).setOnClickListener(v -> {
            mMonster.power += 4;
            bindViews();
        });
        findViewById(R.id.a_fight_monster_b_plus_5).setOnClickListener(v -> {
            mMonster.power += 5;
            bindViews();
        });


    }

    private void bindViews() {
        mTVPlayerName.setText(mPlayer.name);

        mTVPlayerLvl.setText(String.valueOf(mPlayer.lvl));
        mTVMonsterScore.setText(String.valueOf(mMonster.getScore()));

        int scoresDifference = mPlayer.getScore() - mMonster.getScore();

        if (mHelper != null) {
            mTVGetHelp.setText(R.string.change_helper);
            mTVHelperName.setVisibility(View.VISIBLE);
            mTVHelperName.setText("+" + mHelper.name + " (" + mHelper.getScore() + ")");
            scoresDifference = (mPlayer.getScore() + mHelper.getScore()) - mMonster.getScore();
            mTVPlayerScore.setText(String.valueOf(mPlayer.getScore() + mHelper.getScore()));
        } else {
            mTVGetHelp.setText(R.string.get_help);
            mTVHelperName.setVisibility(View.INVISIBLE);
            mTVPlayerScore.setText(String.valueOf(mPlayer.getScore()));
        }

        if (mHelper != null) {
            mTVPlayerScore.setTextColor(getColor(R.color.black));
        } else if (mPlayer.power > mInitPower) {
            mTVPlayerScore.setTextColor(getColor(R.color.green));
        } else if (mPlayer.power < mInitPower) {
            mTVPlayerScore.setTextColor(getColor(R.color.red));
        } else mTVPlayerScore.setTextColor(getColor(R.color.black));

        if (scoresDifference > 0) {
            mTVResultValue.setText("+" + scoresDifference);
            mTVResultValue.setTextColor(getColor(R.color.green));
            mButtonResult.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_treasure));
            mButtonResult.setColorFilter(getColor(R.color.green));
            mTVResult.setText(R.string.victory);
            mTVResult.setTextColor(getColor(R.color.green));
        } else {
            mTVResultValue.setText(String.valueOf(scoresDifference));
            mTVResultValue.setTextColor(getColor(R.color.red));
            mButtonResult.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_death));
            mButtonResult.setColorFilter(getColor(R.color.red));
            mTVResult.setText(R.string.try_to_run);
            mTVResult.setTextColor(getColor(R.color.red));
        }
        mButtonResult.setOnClickListener(new OnResultClickListener(scoresDifference));

    }

    @Override
    public void onAddHelper(int position) {
        getSupportFragmentManager().beginTransaction()
                .remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag(HelpersFrag.FRAGMENT_TAG)))
                .commit();
        mHelper = Repo.ins().getData().get(position);
        bindViews();
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDiceResult(boolean isResultEnough) {
        if (isResultEnough) {
            finish();
        } else {
            Toast.makeText(FightAct.this, R.string.toast_run_fail, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onKillMunchkin() {
        Repo.ins().changePower(mUnitId, -(Repo.ins().findUnitById(mUnitId).power));
        Toast.makeText(FightAct.this, R.string.toast_munchkin_died, Toast.LENGTH_SHORT).show();
        finish();
    }

    private class OnResultClickListener implements View.OnClickListener {

        private int dif;

        public OnResultClickListener(int dif) {
            this.dif = dif;
        }

        @Override
        public void onClick(View view) {
            if (dif > 0) {
                Toast.makeText(FightAct.this, getString(R.string.fight_result_win), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(DiceFrag.FRAGMENT_TAG)
                        .add(R.id.a_fight_container, new DiceFrag(DiceFrag.TYPE_RUN, FightAct.this))
                        .commit();
            }
        }
    }

}