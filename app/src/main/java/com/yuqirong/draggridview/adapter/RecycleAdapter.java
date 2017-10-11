package com.yuqirong.draggridview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuqirong.draggridview.R;
import com.yuqirong.draggridview.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by georgeni on 10/11/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter {
    private List<DataBean> dataList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public RecycleAdapter(List<DataBean> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 0:
                itemView = inflater.inflate(R.layout.item, null);
                TitleViewHolder titleViewHolder = new TitleViewHolder(itemView);
                return titleViewHolder;
            case 1:
                itemView = inflater.inflate(R.layout.oa_adapter_item, null);
                TabViewHolder tabViewHolder = new TabViewHolder(itemView);
                return tabViewHolder;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        DataBean bean = dataList.get(position);
        return bean.type;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataBean bean = dataList.get(position);
        switch (getItemViewType(position)) {
            case 0:
                ((TitleViewHolder) holder).textView.setText(bean.content);
                break;
            case 1:
                ((TabViewHolder) holder).textView.setText(bean.content);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public TitleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }

    class TabViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public TabViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.module_name);
        }
    }
}
