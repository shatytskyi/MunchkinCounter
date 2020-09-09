package com.shatytskyi.munchcounter.fragment;

import android.content.Context;
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
import com.shatytskyi.munchcounter.data.Repo;

public class WarningFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    private static final String ARG_ID = "userID";
    public static final String TYPE_ALL_REMOVED = "all removed";
    public static final String TYPE_ALL_RESET = "all reset";
    public static final String TYPE_RESET = "reset";

    private String mType;
    private String mMessage;
    private long mUnitId = -1;

    public WarningFragment() {
    }

    public WarningFragment(long mUnitId) {
        this.mUnitId = mUnitId;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (mUnitId != -1)
        mMessage =
                getString(R.string.player) + " " +
                        Repo.ins().findUnitById(mUnitId).name + " " +
                        getString(R.string.warning_remove);
    }

    public static WarningFragment newInstance(String type) {
        WarningFragment fragment = new WarningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    public static WarningFragment newInstance(String type, long id) {
        WarningFragment fragment = new WarningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        args.putLong(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
            assert mType != null;
            switch (mType) {
                case TYPE_ALL_REMOVED:
                    mMessage = getString(R.string.warning_all_remove);
                    break;
                case TYPE_ALL_RESET:
                    mMessage = getString(R.string.warning_all_reset);
                    break;
                case TYPE_RESET:
                    mMessage =
                            getString(R.string.player) + " " +
                                    Repo.ins().findUnitById((getArguments().getLong(ARG_ID))).name + " " +
                                    getString(R.string.warning_reset);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_warning, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView mTVMessage = view.findViewById(R.id.f_warning_tv_warning);
        mTVMessage.setText(mMessage);

        view.findViewById(R.id.f_warning_bg).setOnClickListener(v -> closeFragment());

        view.findViewById(R.id.f_warning_b_cancel).setOnClickListener(v -> closeFragment());

        if (mType != null) {
            view.findViewById(R.id.f_warning_b_ok).setOnClickListener(v -> {
                switch (mType) {
                    case TYPE_ALL_REMOVED:
                        Repo.ins().removeAll();
                        closeFragment();
                        break;
                    case TYPE_ALL_RESET:
                        Repo.ins().resetAll();
                        closeFragment();
                        break;
                    case TYPE_RESET:
                        assert getArguments() != null;

                        closeFragment();
                        break;
                }
            });
        } else {
            view.findViewById(R.id.f_warning_b_ok).setOnClickListener(v -> {
                Repo.ins().removeUnit(mUnitId);
                closeFragment();
            });
        }
    }

    private void closeFragment() {
        getParentFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .remove(this).commit();
    }
}