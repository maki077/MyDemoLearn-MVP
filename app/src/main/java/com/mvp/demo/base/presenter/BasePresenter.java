package com.mvp.demo.base.presenter;

import android.content.Context;

import com.mvp.demo.base.iview.IBaseView;

import rx.Subscription;

/**
 * Administrator on 2016/9/21.
 * 基础presenter  P持有view对象 构造方法内传值IView iv = View
 */

public abstract class BasePresenter <V extends IBaseView>{
    protected Subscription subscription;
    protected Context context;
    protected V iView;

    public BasePresenter(Context context, V iView) {
        this.context = context;
        this.iView = iView;
    }

    public void init(){
        iView.initView();
    }

    public abstract void release();


}
