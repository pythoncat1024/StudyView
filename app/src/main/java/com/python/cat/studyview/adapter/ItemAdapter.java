package com.python.cat.studyview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder> {
    private final List<String> mDataList;

    public ItemAdapter(List<String> list) {
        mDataList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootV = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mini_layout, parent, false);
        return new Holder(rootV);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        int index = position % mDataList.size();
        String text = mDataList.get(index);
        holder.tv.setText(text);
        holder.tv.setTextSize(16);
        holder.itemView.setAlpha(0.2f);
        LogUtils.d("onBindViewHolder: %s, %s", index, text);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    protected static class Holder extends RecyclerView.ViewHolder {
        TextView tv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.mini_tv);
        }
    }
}
