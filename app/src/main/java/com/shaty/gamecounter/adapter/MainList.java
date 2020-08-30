package com.shaty.gamecounter.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaty.gamecounter.R;
import com.shaty.gamecounter.activity.MainListActivity;
import com.shaty.gamecounter.data.DataIO;
import com.shaty.gamecounter.data.Repo;
import com.shaty.gamecounter.data.Unit;
import com.shaty.gamecounter.fragment.AddUnitFragment;
import com.shaty.gamecounter.fragment.UnitFragment;

public class MainList {

    MainListActivity mActivity;

    public MainList(RecyclerView recyclerView, MainListActivity activity) {
        mActivity = activity;
        Repo.instance().setData(new DataIO(recyclerView.getContext()).read());
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new MainListAdapter(recyclerView.getContext()));
    }

    private class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListViewHolder> implements Repo.OnDataChangedListener {

        private DataIO mDataIO;

        public MainListAdapter(Context context) {
            Repo.instance().subscribe(this);
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
            holder.bind(Repo.instance().getData().get(position));
        }

        @Override
        public int getItemCount() {
            return Repo.instance().getData().size();
        }


        @Override
        public void onDataChanged() {
            notifyDataSetChanged();
            mDataIO.write();
        }

        @Override
        public long getItemId(int position) {
            return Repo.instance().getData().get(position).id;
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
                                Repo.instance().changePower(Repo.instance().getData().get(getAdapterPosition()).id,
                                        -1));
                itemView.findViewById(R.id.i_main_list_b_power_plus)
                        .setOnClickListener(v ->
                                Repo.instance().changePower(Repo.instance().getData().get(getAdapterPosition()).id,
                                        +1));
                itemView.findViewById(R.id.i_main_list_b_lvl_minus)
                        .setOnClickListener(v ->
                                Repo.instance().changeLvl(Repo.instance().getData().get(getAdapterPosition()).id,
                                        -1));
                itemView.findViewById(R.id.i_main_list_b_lvl_plus)
                        .setOnClickListener(v ->
                                Repo.instance().changeLvl(Repo.instance().getData().get(getAdapterPosition()).id,
                                        +1));

                final long[] mLastRemoveButtonClickTime = {0};
                itemView.findViewById(R.id.i_main_list_b_more).setOnClickListener(v -> {
                    if (SystemClock.elapsedRealtime() - mLastRemoveButtonClickTime[0] < 1000) {
                        return;
                    }
                    mLastRemoveButtonClickTime[0] = SystemClock.elapsedRealtime();

                    mActivity.getSupportFragmentManager().beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .add(R.id.a_main_list_container, UnitFragment.newInstance(Repo.instance().getData().get(getAdapterPosition()).id))
                            .commit();
                });

                // TODO: 28.08.2020 unsubscribe
            }

            public void bind (Unit unit) {
                mTVName.setText(unit.name);
                mTVLvl.setText(String.valueOf(unit.lvl));
                mTVScore.setText(String.valueOf(unit.getScore()));
            }
        }
    }

}
