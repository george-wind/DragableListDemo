package com.yuqirong.draggridview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.yuqirong.draggridview.R;
import com.yuqirong.draggridview.adapter.RecycleAdapter;
import com.yuqirong.draggridview.bean.DataBean;
import com.yuqirong.draggridview.view.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by georgeni on 10/11/2017.
 */

public class SecondActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecycleAdapter adapter;
    private List<DataBean> data = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initData();
        initRecycleView();
    }

    private void initData() {
        data.add(new DataBean("常用应用", 0));
        data.add(new DataBean("考勤打卡", 1));
        data.add(new DataBean("工资查询", 1));
        data.add(new DataBean("电话会议", 1));
        data.add(new DataBean("视频会议", 1));
        data.add(new DataBean("其他应用", 0));
        data.add(new DataBean("请假", 1));
        data.add(new DataBean("投票活动", 1));
        data.add(new DataBean("日程", 1));
        data.add(new DataBean("工作汇报", 1));
        data.add(new DataBean("网盘", 1));
        data.add(new DataBean("报销", 1));
        data.add(new DataBean("客户管理", 1));
        data.add(new DataBean("预定会议室", 1));
        data.add(new DataBean("预订车辆", 1));
        data.add(new DataBean("物品领用", 1));
        data.add(new DataBean("报修", 1));
        data.add(new DataBean("邮箱", 1));
    }

    private void initRecycleView() {
        adapter = new RecycleAdapter(data, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        GridLayoutManager layout = new GridLayoutManager(this, 4);
        layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spanSize = 4;
                switch (adapter.getItemViewType(position)) {
                    case 0:
                        spanSize = 4;
                        break;
                    case 1:
                        spanSize = 1;
                        break;
                }
                return spanSize;
            }
        });
//设置布局管理器
        mRecyclerView.setLayoutManager(layout);
//设置adapter
        mRecyclerView.setAdapter(adapter);
//设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
////添加分割线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(
//                getActivity(), DividerItemDecoration.HORIZONTAL_LIST));

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                int type = adapter.getItemViewType(toPosition);
                if (type == 1) {
                    if (fromPosition < toPosition) {
                        for (int i = fromPosition; i < toPosition; i++) {
                            Collections.swap(data, i, i + 1);
                        }
                    } else {
                        for (int i = fromPosition; i > toPosition; i--) {
                            Collections.swap(data, i, i - 1);
                        }
                    }
                    adapter.notifyItemMoved(fromPosition, toPosition);
                }
                return true;

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }
        });
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {

            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                DataBean bean = data.get(position);
                if (bean.type != 0) {
                    mItemTouchHelper.startDrag(vh);
                }
            }

        });
    }
}
