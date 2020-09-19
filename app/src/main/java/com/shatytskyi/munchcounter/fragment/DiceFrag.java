package com.shatytskyi.munchcounter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.shatytskyi.munchcounter.R;

import java.util.Random;

public class DiceFrag extends Fragment {

    public static final String FRAGMENT_TAG = "dice fragment";
    public static final int TYPE_SIMPLE = 0;
    public static final int TYPE_RUN = 1;
    private int mType;
    private DiceListener mListener;
    private int mBonusValue = 0;
    private boolean mIsResultEnough;
    private int mThrownResult;

    private View mBonusBlock;
    private ImageView mButtonBonusPlus;
    private ImageView mButtonBonusMinus;
    private TextView mTVBonusValue;
    private View mButtonThrow;
    private TextView mTVDes;
    private TextView mButtonKill;
    private TextView mTVRun;

    private TextView mTVResult;
    private TextView mTVResultValue;
    private TextView mButtonOk;

    public DiceFrag(int type, @Nullable DiceListener listener) {
        mType = type;
        mListener = listener;
    }

    public interface DiceListener {
        void onDiceResult(boolean isResultEnough);

        void onKillMunchkin();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_dice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews();
        setInitVisibility();

        switch (mType) {
            case DiceFrag.TYPE_RUN:
                mTVBonusValue.setText(String.valueOf(mBonusValue));
                mButtonBonusMinus.setOnClickListener(v -> {
                    if (mBonusValue > -2)
                        mBonusValue--;
                    mTVBonusValue.setText(String.valueOf(mBonusValue));
                });
                mButtonBonusPlus.setOnClickListener(v -> {
                    if (mBonusValue < 4)
                        mBonusValue++;
                    mTVBonusValue.setText(String.valueOf(mBonusValue));
                });

                mButtonThrow.setOnClickListener(v -> {
                    mButtonOk.setVisibility(View.VISIBLE);
                    mTVResult.setVisibility(View.VISIBLE);
                    mTVResultValue.setVisibility(View.VISIBLE);
                    mTVDes.setVisibility(View.VISIBLE);
                    mButtonThrow.setVisibility(View.GONE);
                    mBonusBlock.setVisibility(View.GONE);
                    Random random = new Random();
                    mThrownResult = random.nextInt(6) + 1;
                    mTVResultValue.setText(String.valueOf(mThrownResult));
                    mIsResultEnough = (mThrownResult - 4 + mBonusValue) > 0;

                    if (mIsResultEnough) {
                        mTVDes.setText(R.string.dice_success);
                        mTVDes.setTextColor(getResources().getColor(R.color.green, getActivity().getTheme()));
                    } else {
                        mButtonKill.setVisibility(View.VISIBLE);
                        mButtonKill.setOnClickListener(v1 -> {
                            mListener.onKillMunchkin();
                            closeFragment();
                        });
                        mTVDes.setText(R.string.dice_fail);
                        mTVDes.setTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
                        mButtonOk.setText(R.string.leave);
                    }

                });
                break;
            case DiceFrag.TYPE_SIMPLE:
                Random random = new Random();
                mTVResultValue.setText(String.valueOf(random.nextInt(6) + 1));
        }

        mButtonOk.setOnClickListener(v -> {
            if (mListener != null)
                mListener.onDiceResult(mIsResultEnough);
            closeFragment();
        });
        view.findViewById(R.id.f_dice_bg_white).setOnClickListener(v -> {
        });
        view.findViewById(R.id.f_dice_bg).setOnClickListener(v -> {
            if (mType == DiceFrag.TYPE_SIMPLE)
                closeFragment();
        });

    }

    private void closeFragment() {
        getParentFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .remove(this).commit();
        getParentFragmentManager().popBackStack();
    }

    private void findViews() {
        mBonusBlock = requireView().findViewById(R.id.f_dice_bonus_block);
        mButtonKill = requireView().findViewById(R.id.f_dice_b_kill);
        mTVRun = requireView().findViewById(R.id.f_dice_tv_run);

        mButtonBonusPlus = requireView().findViewById(R.id.f_dice_b_bonus_plus);
        mButtonBonusMinus = requireView().findViewById(R.id.f_dice_b_bonus_minus);
        mTVBonusValue = requireView().findViewById(R.id.f_dice_tv_bonus_value);

        mTVResult = requireView().findViewById(R.id.f_dice_tv_result);
        mTVResultValue = requireView().findViewById(R.id.f_dice_tv_result_value);

        mTVDes = requireView().findViewById(R.id.f_dice_tv_des);

        mButtonThrow = requireView().findViewById(R.id.f_dice_b_throw);
        mButtonOk = requireView().findViewById(R.id.f_dice_b_ok);
    }

    private void setInitVisibility() {
        switch (mType) {
            case TYPE_SIMPLE:
                mBonusBlock.setVisibility(View.GONE);
                mButtonThrow.setVisibility(View.GONE);
                mButtonKill.setVisibility(View.GONE);
                mTVRun.setVisibility(View.GONE);
                break;
            case TYPE_RUN:
                mTVResult.setVisibility(View.GONE);
                mTVResultValue.setVisibility(View.GONE);
                mButtonOk.setVisibility(View.GONE);
                mButtonKill.setVisibility(View.GONE);
        }
    }
}