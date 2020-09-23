package com.shatytskyi.munchcounter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.data.Repo;

public class EditFrag extends Fragment {

    public static final String FRAGMENT_TAG = "edit fragment tag";
    private long mUnitId;

    public EditFrag(long unitId) {
        mUnitId = unitId;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.f_edit_bg).setOnClickListener(v -> {
            closeFragment();
        });
        view.findViewById(R.id.f_edit_b_cancel).setOnClickListener(v -> {
            closeFragment();
        });
        view.findViewById(R.id.f_edit_bg_white).setOnClickListener(v -> {
        });

        EditText mETName = view.findViewById(R.id.f_edit_et_name);
        mETName.requestFocus();
        mETName.setText(Repo.ins().findUnitById(mUnitId).name);
        EditText mETLvl = view.findViewById(R.id.f_edit_et_lvl);
        EditText mETGear = view.findViewById(R.id.f_edit_et_gear);

        view.findViewById(R.id.f_edit_b_ok).setOnClickListener(v -> {
            if (!mETName.getText().toString().isEmpty()) {
                int newLvl = Repo.ins().findUnitById(mUnitId).lvl;
                int newGear = Repo.ins().findUnitById(mUnitId).power;
                if (!mETLvl.getText().toString().isEmpty()) {
                    newLvl = Integer.parseInt(mETLvl.getText().toString());
                }
                if (!mETGear.getText().toString().isEmpty()) {
                    newGear = Integer.parseInt(mETGear.getText().toString());
                }
                Repo.ins().editUnit(mUnitId,
                        mETName.getText().toString().trim(), newLvl, newGear);
                closeFragment();
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_name_warning), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void closeFragment() {
        try {
            InputMethodManager keyboard = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getParentFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .remove(this).commit();
        getParentFragmentManager().popBackStack();
    }
}