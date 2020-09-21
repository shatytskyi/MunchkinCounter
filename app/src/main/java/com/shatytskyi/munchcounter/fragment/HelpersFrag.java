package com.shatytskyi.munchcounter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.adapter.HelpersAdapt;
import com.shatytskyi.munchcounter.data.Repo;
import com.shatytskyi.munchcounter.data.Unit;

public class HelpersFrag extends Fragment {

    public static final String FRAGMENT_TAG = "helper_list_fragment_tag";
    private HelpersAdapt.HelperListener mHelperListener;
    private int dif;

    public HelpersFrag(int dif, HelpersAdapt.HelperListener helperListener) {
        this.dif = dif;
        mHelperListener = helperListener;
    }

    public HelpersFrag() {
        // TODO: 17.09.2020 solo help
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_helper_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new HelpersAdapt(view.findViewById(R.id.f_helper_rv), mHelperListener, dif);
        view.findViewById(R.id.f_helper_bg).setOnClickListener(v -> {
            closeFragment();
        });

        EditText et = view.findViewById(R.id.f_helper_et_custom);

        view.findViewById(R.id.f_helper_b_ok).setOnClickListener(v -> {
            Unit customPartner = new Unit(getString(R.string.partner), 1,
                    Integer.parseInt(et.getText().toString())-1);
            Repo.ins().addUnit(customPartner);
            mHelperListener.onAddHelper(Repo.ins().getData().indexOf(customPartner));
            closeFragment();
        });
    }

    private void closeFragment() {
        getParentFragmentManager()
                .beginTransaction().remove(this)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        getParentFragmentManager().popBackStack();
    }
}