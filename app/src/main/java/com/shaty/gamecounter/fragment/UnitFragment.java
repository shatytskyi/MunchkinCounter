package com.shaty.gamecounter.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shaty.gamecounter.R;
import com.shaty.gamecounter.data.Repo;
import com.shaty.gamecounter.data.Unit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UnitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnitFragment extends Fragment implements Repo.OnDataChangedListener {

    private static final String ARG_ID = "param1";

    private long mUnitID;
    private Unit mUnit;
    private TextView mTVLvl;
    private TextView mTVName;
    private TextView mTVScore;

    public static UnitFragment newInstance(long userID) {
        UnitFragment fragment = new UnitFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUnitID = getArguments().getLong(ARG_ID);
        }
        Repo.instance().subscribe(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_unit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUnit = Repo.instance().findUnitById(mUnitID);
        mTVName = view.findViewById(R.id.f_unit_tv_name);
        mTVScore = view.findViewById(R.id.f_unit_tv_score);
        mTVLvl = view.findViewById(R.id.f_unit_tv_lvl);
        onDataChanged();
        findButtons(view);
    }


    private void findButtons (View view) {

        view.findViewById(R.id.f_unit_bg).setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .remove(this).commit();
        });

        // Power change buttons
        view.findViewById(R.id.f_unit_b_minus_1).setOnClickListener(v -> {
            Repo.instance().changePower(mUnitID, -1);
        });
        view.findViewById(R.id.f_unit_b_minus_2).setOnClickListener(v -> {
            Repo.instance().changePower(mUnitID, -2);
        });
        view.findViewById(R.id.f_unit_b_minus_3).setOnClickListener(v -> {
            Repo.instance().changePower(mUnitID, -3);
        });
        view.findViewById(R.id.f_unit_b_minus_4).setOnClickListener(v -> {
            Repo.instance().changePower(mUnitID, -4);
        });
        view.findViewById(R.id.f_unit_b_minus_5).setOnClickListener(v -> {
            Repo.instance().changePower(mUnitID, -5);
        });
        view.findViewById(R.id.f_unit_b_plus_1).setOnClickListener(v -> {
            Repo.instance().changePower(mUnitID, 1);
        });
        view.findViewById(R.id.f_unit_b_plus_2).setOnClickListener(v -> {
            Repo.instance().changePower(mUnitID, 2);
        });
        view.findViewById(R.id.f_unit_b_plus_3).setOnClickListener(v -> {
            Repo.instance().changePower(mUnitID, 3);
        });
        view.findViewById(R.id.f_unit_b_plus_4).setOnClickListener(v -> {
            Repo.instance().changePower(mUnitID, 4);
        });
        view.findViewById(R.id.f_unit_b_plus_5).setOnClickListener(v -> {
            Repo.instance().changePower(mUnitID, 5);
        });

        // Lvl change buttons
        view.findViewById(R.id.f_unit_b_lvl_minus).setOnClickListener(v -> {
            Repo.instance().changeLvl(mUnitID, -1);
        });
        view.findViewById(R.id.f_unit_b_lvl_plus).setOnClickListener(v -> {
            Repo.instance().changeLvl(mUnitID, 1);
        });

    }


    @Override
    public void onDataChanged() {
        mTVName.setText(mUnit.name);
        mTVScore.setText(String.valueOf(mUnit.getScore()));
        mTVLvl.setText(String.valueOf(mUnit.lvl));
    }
}