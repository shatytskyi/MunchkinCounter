package com.shatytskyi.munchcounter.fragment;

import android.content.Intent;
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
import com.shatytskyi.munchcounter.activity.FightAct;
import com.shatytskyi.munchcounter.activity.ListAct;
import com.shatytskyi.munchcounter.activity.SoloAct;
import com.shatytskyi.munchcounter.data.Repo;
import com.shatytskyi.munchcounter.data.Unit;


public class UnitFrag extends Fragment implements Repo.OnDataChangedListener, WarningFrag.WarningDialogListener {

    public static final String FRAGMENT_TAG = "unit_fragment_tag";
    private static final String ARG_ID = "param1";

    private long mUnitID;
    private Unit mUnit;
    private TextView mTVLvl;
    private TextView mTVName;
    private TextView mTVScore;

    public static UnitFrag newInstance(long userID) {
        UnitFrag fragment = new UnitFrag();
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
        Repo.ins().subscribe(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_unit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUnit = Repo.ins().findUnitById(mUnitID);
        mTVName = view.findViewById(R.id.f_unit_tv_name);
        mTVScore = view.findViewById(R.id.f_unit_tv_score);
        mTVLvl = view.findViewById(R.id.f_unit_tv_lvl);
        onDataChanged();
        findButtons(view);
    }


    private void findButtons (View view) {

        view.findViewById(R.id.f_unit_b_back).setOnClickListener(v -> {
            closeFragment();
        });
        view.findViewById(R.id.f_unit_bg).setOnClickListener(v -> {
            closeFragment();
        });
        view.findViewById(R.id.f_unit_bg_white).setOnClickListener(v -> {
        });

        view.findViewById(R.id.f_unit_b_edit).setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.a_main_list_container, new EditFrag(mUnitID), EditFrag.FRAGMENT_TAG)
                    .addToBackStack(EditFrag.FRAGMENT_TAG)
                    .commit();
        });

        view.findViewById(R.id.f_unit_b_go_fullscreen).setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SoloAct.class);
            intent.putExtra(SoloAct.EXTRA_UNIT_ID, mUnitID);
            startActivity(intent);
        });

        view.findViewById(R.id.f_unit_b_reset).setOnClickListener(v -> {
            WarningFrag f = new WarningFrag(WarningFrag.TYPE_RESET, mUnitID, this);
            f.show(getParentFragmentManager(), null);
        });

        view.findViewById(R.id.f_unit_b_remove).setOnClickListener(v -> {
            WarningFrag f = new WarningFrag(WarningFrag.TYPE_REMOVE, mUnitID, this);
            f.show(getParentFragmentManager(), null);
        });

        view.findViewById(R.id.f_unit_b_fight).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FightAct.class);
            intent.putExtra(FightAct.EXTRA_USER_ID, mUnitID);
            startActivity(intent);
        });

        // Power change buttons
        view.findViewById(R.id.f_unit_b_minus_1).setOnClickListener(v -> Repo.ins().changePower(mUnitID, -1));
        view.findViewById(R.id.f_unit_b_minus_2).setOnClickListener(v -> Repo.ins().changePower(mUnitID, -2));
        view.findViewById(R.id.f_unit_b_minus_3).setOnClickListener(v -> Repo.ins().changePower(mUnitID, -3));
        view.findViewById(R.id.f_unit_b_minus_4).setOnClickListener(v -> Repo.ins().changePower(mUnitID, -4));
        view.findViewById(R.id.f_unit_b_minus_5).setOnClickListener(v -> Repo.ins().changePower(mUnitID, -5));
        view.findViewById(R.id.f_unit_b_plus_1).setOnClickListener(v -> Repo.ins().changePower(mUnitID, 1));
        view.findViewById(R.id.f_unit_b_plus_2).setOnClickListener(v -> Repo.ins().changePower(mUnitID, 2));
        view.findViewById(R.id.f_unit_b_plus_3).setOnClickListener(v -> Repo.ins().changePower(mUnitID, 3));
        view.findViewById(R.id.f_unit_b_plus_4).setOnClickListener(v -> Repo.ins().changePower(mUnitID, 4));
        view.findViewById(R.id.f_unit_b_plus_5).setOnClickListener(v -> Repo.ins().changePower(mUnitID, 5));

        // Lvl change buttons
        view.findViewById(R.id.f_unit_b_lvl_minus).setOnClickListener(v -> Repo.ins().changeLvl(mUnitID, -1));
        view.findViewById(R.id.f_unit_b_lvl_plus).setOnClickListener(v -> Repo.ins().changeLvl(mUnitID, 1));

    }


    @Override
    public void onDataChanged() {
        mTVName.setText(mUnit.name);
        mTVScore.setText(String.valueOf(mUnit.getScore()));
        mTVLvl.setText(String.valueOf(mUnit.lvl));
    }

    @Override
    public void onDialogPositiveButtonClicked(String dialogType, long unitId) {
        switch (dialogType) {
            case WarningFrag.TYPE_RESET:
                Repo.ins().resetUnit(unitId);
                break;
            case WarningFrag.TYPE_REMOVE:
                Repo.ins().removeUnit(unitId);
                closeFragment();
                break;
        }
    }

    private void closeFragment() {
        getParentFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .remove(this).commit();
        getParentFragmentManager().popBackStack();
        ListAct a = (ListAct) getActivity();
        a.setButtonAddVisible();
    }
}