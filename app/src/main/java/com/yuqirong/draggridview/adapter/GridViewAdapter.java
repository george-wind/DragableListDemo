package com.yuqirong.draggridview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuqirong.draggridview.R;

import java.util.List;

/**
 * Created by georgeni on 10/11/2017.
 */

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> strList;
    private int hidePosition = AdapterView.INVALID_POSITION;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context, List<String> strList) {
        this.context = context;
        this.strList = strList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return strList.size();
    }

    @Override
    public String getItem(int position) {
        return strList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //hide时隐藏Text
        if (position != hidePosition) {
            holder.textView.setText(strList.get(position));
            holder.textView.setBackgroundResource(R.drawable.item_selector);
        } else {
            holder.textView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            holder.textView.setText("");
        }
        convertView.setId(position);

        return convertView;
    }

    public void hideView(int pos) {
        hidePosition = pos;
        notifyDataSetChanged();
    }

    public void showHideView() {
        hidePosition = AdapterView.INVALID_POSITION;
        notifyDataSetChanged();
    }

    public void removeView(int pos) {
        strList.remove(pos);
        notifyDataSetChanged();
    }

    //更新拖动时的gridView
    public void swapView(int draggedPos, int destPos) {
        //从前向后拖动，其他item依次前移
        if (draggedPos < destPos) {
            strList.add(destPos + 1, getItem(draggedPos));
            strList.remove(draggedPos);
        }
        //从后向前拖动，其他item依次后移
        else if (draggedPos > destPos) {
            strList.add(destPos, getItem(draggedPos));
            strList.remove(draggedPos + 1);
        }
        hidePosition = destPos;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView textView;
    }
}
