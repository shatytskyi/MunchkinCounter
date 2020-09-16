package com.shatytskyi.munchcounter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.shatytskyi.munchcounter.R;

import java.util.Random;

public class DiceFragment extends Fragment {

    private TextView mResultTV;
    private int mMaxResult = 6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_dice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mResultTV = view.findViewById(R.id.f_dice_result);
        Random r = new Random();
        int result = r.nextInt(mMaxResult) + 1;
        mResultTV.setText(String.valueOf(result));

        view.findViewById(R.id.f_dice_bg_white).setClickable(false);

        view.findViewById(R.id.f_dice_ok).setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .remove(this).commit();
        });

        view.findViewById(R.id.f_dice_bg).setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .remove(this).commit();
        });

    }
}