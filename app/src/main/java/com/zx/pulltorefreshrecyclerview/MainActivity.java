package com.zx.pulltorefreshrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 1、PullToRefreshRecyclerView实现上拉加载和下拉刷新，样式可自定义
 * 2、可以添加EmptyView
 */
public class MainActivity extends AppCompatActivity implements PullToRefreshListener {

    private PullToRefreshRecyclerView recyclerView;
    private List<ItemData> data;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        initRecyclerView();
    }


    private void initView() {
        findViewById(R.id.clearAll_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //模拟没有数据的情况
                data.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    //初始化RecyclerView的配置及上拉加载，下拉刷新
    private void initRecyclerView() {
        recyclerView = (PullToRefreshRecyclerView) findViewById(R.id.recyclerView);
        //添加HeaderView
        View headView = View.inflate(this, R.layout.layout_head_view, null);
        recyclerView.addHeaderView(headView);
        //添加HeaderView
        View headView2 = View.inflate(this, R.layout.layout_head2_view, null);
        recyclerView.addHeaderView(headView2);
        //添加FooterView
        View footerView = View.inflate(this, R.layout.layout_foot_view, null);
        recyclerView.addFooterView(footerView);
        //设置EmptyView
        View emptyView = View.inflate(this, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setEmptyView(emptyView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(this, data);
        recyclerView.setAdapter(adapter);

        //设置是否开启上拉加载
        recyclerView.setLoadingMoreEnabled(true);
        //设置是否开启下拉刷新
        recyclerView.setPullRefreshEnabled(true);
        //设置是否显示上次刷新的时间
        recyclerView.displayLastRefreshTime(true);
        //设置刷新回调
        recyclerView.setPullToRefreshListener(this);
        //主动触发下拉刷新操作
        //recyclerView.onRefresh();
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i=0;i<3;i++){
            data.add(new ItemData(R.mipmap.app_refresh_people_0));
        }
    }

    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshComplete();
                //模拟加载数据的情况
                for (int i = 0; i < 4; i++) {
                    data.add(0,new ItemData(R.mipmap.app_refresh_people_0));
                }
                adapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLoadMoreComplete(); //加载数据完成
                //模拟加载数据的情况
                for (int i = 0; i < 4; i++) {
                    data.add(new ItemData(R.mipmap.app_refresh_people_0));
                }
                adapter.notifyDataSetChanged();
            }
        }, 2000);
    }
}
