package com.shaty.gamecounter.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shaty.gamecounter.R;
import com.shaty.gamecounter.data.Repo;

public class WarningFragment extends Fragment {

    private static final String ARG_TYPE = "param1";
    public static final String TYPE_ALL_REMOVED = "all removed";
    public static final String TYPE_ALL_RESET = "all_reset";

    private String mType;
    private String mMessage;


    public static WarningFragment newInstance(String param1) {
        WarningFragment fragment = new WarningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
            assert mType != null;
            if (mType.equals(TYPE_ALL_REMOVED)) {
                mMessage = getString(R.string.warning_all_remove);
            } else if (mType.equals(TYPE_ALL_RESET)) {
                mMessage = getString(R.string.warning_all_reset);
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

        view.findViewById(R.id.f_warning_bg).setOnClickListener(v -> {
            closeFragment();
        });
        view.findViewById(R.id.f_warning_b_cancel).setOnClickListener(v -> {
            closeFragment();
        });
        view.findViewById(R.id.f_warning_b_ok).setOnClickListener(v -> {
            if (mType.equals(TYPE_ALL_REMOVED)) {
                Repo.instance().removeAll();
                closeFragment();
            }
            else if (mType.equals(TYPE_ALL_RESET)) {
                Repo.instance().resetAll();
                closeFragment();
            }
        });
    }

    private void closeFragment() {
        getParentFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_from_down_right, R.animator.slide_out_to_up_right)
                .remove(this).commit();
    }
}