package com.shaty.gamecounter.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.shaty.gamecounter.R;
import com.shaty.gamecounter.activity.MainListActivity;
import com.shaty.gamecounter.data.Repo;

public class AddUnitFragment extends Fragment {

    private MainListActivity mActivity;

    public AddUnitFragment(MainListActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_add_unit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText et = view.findViewById(R.id.f_add_et_name);
        et.requestFocus();
        showKeyboard(this.getContext(), et);
        if(et.requestFocus()) {
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        view.findViewById(R.id.f_add_b_cancel).setOnClickListener(v -> {
            closeFragment(this.getContext(), et);
        });
        view.findViewById(R.id.f_add_bg).setOnClickListener(v -> {
            closeFragment(this.getContext(), et);
        });
        view.findViewById(R.id.f_add_b_ok).setOnClickListener(v -> {
            try {
                Repo.instance().addUnit(et.getText().toString());
                closeFragment(this.getContext(), et);
            } catch (Repo.ZeroLengthException e) {
                TextView warning = view.findViewById(R.id.f_add_tv_warning);
                warning.setText("Unnamed players are not allowed");
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mActivity.setAddButtonVisible();
    }


    private void showKeyboard(Context context, View view) {
        try {
            InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.showSoftInput(view, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeFragment(Context context, View view) {
        try {
            InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getParentFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_from_down_right, R.animator.slide_out_to_down)
                .remove(this).commit();
    }

}