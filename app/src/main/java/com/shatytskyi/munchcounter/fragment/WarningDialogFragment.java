package com.shatytskyi.munchcounter.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.data.Repo;

public class WarningDialogFragment extends DialogFragment {

    public static final String TYPE_ALL_REMOVE = "all removed";
    public static final String TYPE_ALL_RESET = "all reset";
    public static final String TYPE_RESET = "reset";
    public static final String TYPE_REMOVE = "remove";

    private String mType;
    private long mUnitId = -1;

    public WarningDialogFragment() {

    }

    public WarningDialogFragment(String type) {
        mType = type;
    }

    public WarningDialogFragment(String type, long unitId) {
        mType = type;
        mUnitId = unitId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle(createTitle());
        alertDialogBuilder.setIcon(R.drawable.icon_warning);
        alertDialogBuilder.setMessage(createMessage());

        alertDialogBuilder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            switch (mType) {
                case TYPE_ALL_REMOVE:
                    Repo.ins().removeAll();
                    break;
                case TYPE_ALL_RESET:
                    Repo.ins().resetAll();
                    break;
                case TYPE_RESET:
                    Repo.ins().resetUnit(mUnitId);
                    break;
                case TYPE_REMOVE:
                    Repo.ins().removeUnit(mUnitId);
                    getParentFragmentManager().beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .remove(getParentFragmentManager().findFragmentByTag(UnitFragment.FRAGMENT_TAG))
                            .commit();
                    break;
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());

        return alertDialogBuilder.create();
    }

    private String createTitle() {
        switch (mType) {
            case TYPE_ALL_REMOVE:
                return "Remove all";
            case TYPE_ALL_RESET:
                return "Reset all";
            case TYPE_RESET:
                return "Reset " + Repo.ins().findUnitById(mUnitId).name;
            case TYPE_REMOVE:
                return "Remove " + Repo.ins().findUnitById(mUnitId).name;
        }
        return "No title";
    }

    private String createMessage() {
        switch (mType) {
            case TYPE_ALL_REMOVE:
                return "All players will be permanently removed";
            case TYPE_ALL_RESET:
                return "All player's stats will be reset";
            case TYPE_RESET:
                return "Player " + Repo.ins().findUnitById(mUnitId).name + " will lose all stats\n";
            case TYPE_REMOVE:
                return "Player " + Repo.ins().findUnitById(mUnitId).name + " will be permanently removed\n";

        }
        return "Are you sure?";
    }

}
