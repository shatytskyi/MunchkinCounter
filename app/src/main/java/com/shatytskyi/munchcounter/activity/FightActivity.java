package com.shatytskyi.munchcounter.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.adapter.HelperList;
import com.shatytskyi.munchcounter.data.Repo;
import com.shatytskyi.munchcounter.data.Unit;
import com.shatytskyi.munchcounter.fragment.HelperListFragment;

import java.util.Objects;

public class FightActivity extends AppCompatActivity implements HelperList.HelperListener {

    public static final String EXTRA_USER_ID = "extra_user_id";

    private long mUserId;
    private Unit mPlayer;
    private Unit mHelper;
    private Unit mMonster;

    private TextView mTVPlayerName;
    private TextView mTVPlayerScore;
    private TextView mTVPlayerLvl;
    private TextView mTVMonsterScore;
    private TextView mTVHelperName;
    private TextView mTVResult;
    private TextView mTVGetHelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_fight);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupViews();
        mUserId = getIntent().getLongExtra(EXTRA_USER_ID, -1);
        mPlayer = Repo.ins().findUnitById(mUserId).copy();
        mMonster = new Unit("Monster", 0, 0);
        bindViews();

    }

    private void setupViews() {

        //changeable text views
        mTVPlayerName = findViewById(R.id.a_fight_tv_table_player);
        mTVHelperName = findViewById(R.id.a_fight_tv_table_helper);
        mTVResult = findViewById(R.id.a_fight_tv_table_result);
        mTVPlayerLvl = findViewById(R.id.a_fight_tv_lvl);
        mTVPlayerScore = findViewById(R.id.a_fight_tv_score);
        mTVMonsterScore = findViewById(R.id.a_fight_tv_monster_score);
        mTVGetHelp = findViewById(R.id.a_fight_tv_get_help);

        //functional buttons
        findViewById(R.id.a_fight_b_finish).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.a_fight_b_reset_fight).setOnClickListener(v -> {
            mMonster = new Unit("Monster", 0, 0);
            mPlayer = Repo.ins().findUnitById(mUserId).copy();
            mHelper = null;
            bindViews();
            Toast.makeText(this, R.string.fight_was_reset, Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.a_fight_b_get_help).setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.a_fight_container,
                            new HelperListFragment((mPlayer.getScore() - mMonster.getScore()), this),
                            "helper_list_fragment")
                    .addToBackStack("helper_list_fragment")
                    .commit();
        });

        findViewById(R.id.a_fight_tv_table_helper).setOnClickListener(v -> {
            mHelper = null;
            Toast.makeText(this, "Partner removed", Toast.LENGTH_SHORT).show();
            bindViews();
        });


        //player buttons
        findViewById(R.id.a_fight_b_lvl_minus).setOnClickListener(v -> {
            mPlayer.lvl--;
            bindViews();
        });
        findViewById(R.id.a_fight_b_lvl_plus).setOnClickListener(v -> {
            mPlayer.lvl++;
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
            mTVHelperName.setText("+ " + mHelper.name);
            scoresDifference = (mPlayer.getScore() + mHelper.getScore()) - mMonster.getScore();
            mTVPlayerScore.setText(mPlayer.getScore() + "\n + \n" + mHelper.getScore());
        } else {
            mTVGetHelp.setText(R.string.get_help);
            mTVHelperName.setVisibility(View.INVISIBLE);
            mTVPlayerScore.setText(String.valueOf(mPlayer.getScore()));
        }

        if (scoresDifference > 0) {
            mTVResult.setText("+" + scoresDifference);
            mTVResult.setTextColor(getColor(R.color.green));
        } else {
            mTVResult.setText(String.valueOf(scoresDifference));
            mTVResult.setTextColor(getColor(R.color.warning));
        }

    }

    @Override
    public void onAddHelper(int position) {
        getSupportFragmentManager().beginTransaction()
                .remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("helper_list_fragment")))
                .commit();
        mHelper = Repo.ins().getData().get(position);
        bindViews();
    }
}