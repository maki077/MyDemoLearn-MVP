package com.mvp.demo.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mvp.demo.R;
import com.mvp.demo.base.activity.BaseActivity;
import com.mvp.demo.model.bean.Meizi;
import com.mvp.demo.presenter.MeiziPresenter;
import com.mvp.demo.ui.adapter.MeiziAdapter;
import com.mvp.demo.ui.iview.IMeiziView;
import com.mvp.demo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Administrator on 2016/9/21.
 */

public class MeiziActivity extends BaseActivity<MeiziPresenter> implements IMeiziView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    private MeiziAdapter adapter;
    private List<Meizi> meiziList;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean canLoading = true;
    private boolean isScrollingToBottom = true;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_meizi;
    }

    /**
     * BaseActivity
     * BasePresenter presenter.init() 执行iView.initView();
     * presenter 持有iview  并执行iveiew.func()
     */
    @Override
    protected void initPresenter() {
        presenter = new MeiziPresenter(this, this);
        presenter.init();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        presenter.fetchMeiziData(page);
    }

    /**
     * IBaseView方法
     */
    @Override
    public void initView() {
        meiziList = new ArrayList<>();
        adapter = new MeiziAdapter(this, meiziList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        onUpScroll();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                presenter.fetchMeiziData(page);
            }
        });


    }

    @Override
    public void showProgress() {
        if (!swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showNoMoreData() {
        canLoading = false;
        ToastUtil.showToast(this, "全部加载完毕");
    }

    @Override
    public void showMeiziList(List<Meizi> meiziList) {
        canLoading = true;
        page++;

        if (isRefresh) {
            this.meiziList.clear();
            this.meiziList.addAll(meiziList);
            isRefresh = false;
        } else {
            this.meiziList.addAll(meiziList);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 向上滑动
     */
    public void onUpScroll() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isScrollingToBottom = dy > 0;
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1) && isScrollingToBottom) {
                        if (canLoading) {
                            presenter.fetchMeiziData(page);
                            canLoading = false;
                        }
                    }
                }
            }
        });
    }

}