package com.shatytskyi.munchcounter.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.data.Repo;

public class WarningDialogFragment extends DialogFragment {

    public static final String TYPE_ALL_REMOVED = "all removed";
    public static final String TYPE_ALL_RESET = "all reset";
    public static final String TYPE_RESET = "reset";
    public static final String TYPE_REMOVE = "remove";

    private String mType;
    private long mUnitId = -1;

    public WarningDialogFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        alertDialogBuilder.setMessage(createMessage());
        alertDialogBuilder.setIcon(R.drawable.icon_warning);

        alertDialogBuilder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            switch (mType) {
                case TYPE_ALL_REMOVED:
                    Repo.ins().removeAll();
                    break;
                case TYPE_ALL_RESET:
                    Repo.ins().resetAll();
                    break;
                case TYPE_RESET:
                    if (mUnitId != -1) {
                        Repo.ins().resetUnit(mUnitId);
                    }
                    break;
                case TYPE_REMOVE:
                    if (mUnitId != -1) {
                        Repo.ins().removeUnit(mUnitId);
                    }
                    break;
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());

        return alertDialogBuilder.create();
    }

    private String createTitle() {
        switch (mType) {
            case TYPE_ALL_REMOVED:
                return "Remove all players";
            case TYPE_ALL_RESET:
                return "Reset all players";
            case TYPE_RESET:
                return "Reset " + Repo.ins().findUnitById(mUnitId).name;
            case TYPE_REMOVE:
                return "Remove " + Repo.ins().findUnitById(mUnitId).name;
        }
        return "No title";
    }

    private String createMessage() {
        switch (mType) {
            case TYPE_RESET:
                return "Player " + Repo.ins().findUnitById(mUnitId).name + " will lose all stats \n Are you sure?";
            case TYPE_REMOVE:
                return "Player " + Repo.ins().findUnitById(mUnitId).name + " will be removed from list \n Are you sure?";
        }
        return "Are you sure?";
    }

}
