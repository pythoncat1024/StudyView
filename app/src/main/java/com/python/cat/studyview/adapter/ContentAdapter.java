package com.python.cat.studyview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.R;
import com.python.cat.studyview.utils.ToastUtils;

import java.util.List;

/**
 * Created by cat on 2018/6/20.
 */

public class ContentAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> dataList;


    private ViewHolder holder;

    public ContentAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public String getItem(int position) {
        int size = dataList.size();
        return dataList.get(position % size);
    }

    @Override
    public long getItemId(int position) {
        int size = dataList.size();
        return position % size;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
//        LogUtils.d("getView called, position: " + position + ", convertView: " + convertView);
        final View view;
        final ViewHolder viewHolder;
        String item = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.content_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.btn = view.findViewById(R.id.btn_item_click);
            viewHolder.textView = view.findViewById(R.id.tv_item);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        holder = viewHolder;

        viewHolder.textView.setText(item);
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(context, "item button click....");
                LogUtils.i("item button click... ###");
            }
        });

//        viewHolder.btn.setClickable(false);

        return view;
    }

    static class ViewHolder {
        Button btn;
        TextView textView;
    }


    public void release() {
        if (holder != null) {
            holder.textView = null;
            holder.btn = null;
        }
    }

}
