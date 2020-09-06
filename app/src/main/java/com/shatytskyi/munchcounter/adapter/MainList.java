package com.shatytskyi.munchcounter.adapter;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.activity.MainListActivity;
import com.shatytskyi.munchcounter.data.DataIO;
import com.shatytskyi.munchcounter.data.Repo;
import com.shatytskyi.munchcounter.data.Unit;
import com.shatytskyi.munchcounter.fragment.UnitFragment;

public class MainList {

    MainListActivity mActivity;
    MainListAdapter mAdapter;

    public MainListAdapter getAdapter() {
        return mAdapter;
    }

    public MainList(RecyclerView recyclerView, MainListActivity activity) {
        mActivity = activity;
        Repo.ins().setData(new DataIO(recyclerView.getContext()).read());
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        mAdapter = new MainListAdapter(recyclerView.getContext());
        recyclerView.setAdapter(mAdapter);
    }

    private class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListViewHolder>
            implements Repo.OnDataChangedListener {

        private DataIO mDataIO;

        public MainListAdapter(Context context) {
            setHasStableIds(true);
            mDataIO = new DataIO(context);
        }

        @NonNull
        @Override
        public MainListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MainListViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MainListViewHolder holder, int position) {
            holder.bind(Repo.ins().getData().get(position));
        }

        @Override
        public int getItemCount() {
            return Repo.ins().getData().size();
        }


        @Override
        public void onDataChanged() {
            notifyDataSetChanged();
        }

        @Override
        public long getItemId(int position) {
            return Repo.ins().getData().get(position).id;
        }

        class MainListViewHolder extends RecyclerView.ViewHolder {

            private TextView mTVName;
            private TextView mTVLvl;
            private TextView mTVScore;

            public MainListViewHolder(ViewGroup parent) {
                super(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_main_list, parent, false));
                mTVName = itemView.findViewById(R.id.i_main_list_tv_name);
                mTVLvl = itemView.findViewById(R.id.i_main_list_tv_lvl);
                mTVScore = itemView.findViewById(R.id.i_main_list_tv_score);

                itemView.findViewById(R.id.i_main_list_b_power_minus)
                        .setOnClickListener(v ->
                                Repo.ins().changePower(Repo.ins().getData().get(getAdapterPosition()).id,
                                        -1));
                itemView.findViewById(R.id.i_main_list_b_power_plus)
                        .setOnClickListener(v ->
                                Repo.ins().changePower(Repo.ins().getData().get(getAdapterPosition()).id,
                                        +1));
                itemView.findViewById(R.id.i_main_list_b_lvl_minus)
                        .setOnClickListener(v ->
                                Repo.ins().changeLvl(Repo.ins().getData().get(getAdapterPosition()).id,
                                        -1));
                itemView.findViewById(R.id.i_main_list_b_lvl_plus)
                        .setOnClickListener(v ->
                                Repo.ins().changeLvl(Repo.ins().getData().get(getAdapterPosition()).id,
                                        +1));

                final long[] mLastRemoveButtonClickTime = {0};
                itemView.setOnClickListener(v -> {
                    if (SystemClock.elapsedRealtime() - mLastRemoveButtonClickTime[0] < 1000) {
                        return;
                    }
                    mLastRemoveButtonClickTime[0] = SystemClock.elapsedRealtime();

                    mActivity.getSupportFragmentManager().beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .add(R.id.a_main_list_container,
                                    UnitFragment.newInstance(Repo.ins().getData().get(getAdapterPosition()).id))
                            .commit();
                });

            }

            public void bind (Unit unit) {
                mTVName.setText(unit.name);
                mTVLvl.setText(String.valueOf(unit.lvl));
                mTVScore.setText(String.valueOf(unit.getScore()));
            }
        }
    }

}
