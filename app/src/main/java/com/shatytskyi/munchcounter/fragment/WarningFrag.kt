package com.shatytskyi.munchcounter.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.data.Repo;

public class WarningFrag extends DialogFragment {

    public static final String TYPE_ALL_REMOVE = "all removed";
    public static final String TYPE_ALL_RESET = "all reset";
    public static final String TYPE_RESET = "reset";
    public static final String TYPE_REMOVE = "remove";
    public static final String TYPE_RESET_SOLO = "reset solo";

    private String mType;
    private long mUnitId = -1;
    private WarningDialogListener mListener;

    public WarningFrag(String type, WarningDialogListener listener) {
        mType = type;
        mListener = listener;
    }

    public WarningFrag(String type, long unitId, WarningDialogListener listener) {
        mType = type;
        mUnitId = unitId;
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder adb = new AlertDialog.Builder(requireContext());

        adb.setIcon(R.drawable.icon_warning);
        adb.setTitle(createTitle());
        adb.setMessage(createMessage());

        adb.setPositiveButton(getString(R.string.ok), (dialog, which) ->
                mListener.onDialogPositiveButtonClicked(mType, mUnitId));

        adb.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());

        return adb.create();
    }

    private String createTitle() {
        switch (mType) {
            case TYPE_ALL_REMOVE:
                return getString(R.string.clear);
            case TYPE_ALL_RESET:
                return getString(R.string.reset_all);
            case TYPE_RESET:
                return getString(R.string.reset) + " " + Repo.ins().findUnitById(mUnitId).name;
            case TYPE_REMOVE:
                return getString(R.string.remove) + " " + Repo.ins().findUnitById(mUnitId).name;
            case TYPE_RESET_SOLO:
                return getString(R.string.reset);
        }
        return "Warning!";
    }

    private String createMessage() {
        switch (mType) {
            case TYPE_ALL_REMOVE:
                return getString(R.string.warning_all_remove);
            case TYPE_ALL_RESET:
                return getString(R.string.warning_all_reset);
            case TYPE_RESET:
                return getString(R.string.player) + " " +
                        Repo.ins().findUnitById(mUnitId).name + " " + getString(R.string.warning_will_reset);
            case TYPE_REMOVE:
                return getString(R.string.player) + " " +
                        Repo.ins().findUnitById(mUnitId).name + " " + getString(R.string.warning_will_removed);
            case TYPE_RESET_SOLO:
                return getString(R.string.your_munchkin) + getString(R.string.warning_will_reset);
        }
        return "Are you sure?";
    }

    public interface WarningDialogListener {
        void onDialogPositiveButtonClicked(String dialogType, long unitId);
    }

}
