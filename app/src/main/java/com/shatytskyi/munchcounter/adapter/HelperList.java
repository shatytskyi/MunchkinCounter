package com.shatytskyi.munchcounter.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shatytskyi.munchcounter.R;
import com.shatytskyi.munchcounter.data.Repo;
import com.shatytskyi.munchcounter.data.Unit;

import java.util.List;

public class HelperList {

    private HelperListener mHelperListener;
    private int dif;

    public HelperList(RecyclerView rv, HelperListener helperListener, int currentDifference) {
        mHelperListener = helperListener;
        dif = currentDifference;
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(new HelperListAdapter());
    }

    private class HelperListAdapter extends RecyclerView.Adapter<HelperListAdapter.HelperViewHolder> {

        private List<Unit> mData = Repo.ins().getData();

        @NonNull
        @Override
        public HelperListAdapter.HelperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HelperViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull HelperListAdapter.HelperViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }


        class HelperViewHolder extends RecyclerView.ViewHolder {

            TextView mTVName;
            TextView mTVScore;

            public HelperViewHolder(ViewGroup parent) {
                super(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_helper, parent, false));
            }

            public void bind(int position) {
                itemView.setOnClickListener(v -> mHelperListener.onAddHelper(position));
                mTVName = itemView.findViewById(R.id.i_helper_tv_name);
                mTVScore = itemView.findViewById(R.id.i_helper_tv_score);
                mTVName.setText(mData.get(position).name);


                if (dif + mData.get(position).getScore() > 0) {
                    mTVScore.setTextColor(itemView.getContext().getColor(R.color.green));
                    mTVScore.setText("+" + (dif + mData.get(position).getScore()));
                } else {
                    mTVScore.setTextColor(itemView.getContext().getColor(R.color.red));
                    mTVScore.setText(String.valueOf(dif + mData.get(position).getScore()));
                }

            }
        }

    }

    public interface HelperListener {
        void onAddHelper(int position);
    }


}
