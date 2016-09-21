package com.mvp.demo.ui.iview;

import com.mvp.demo.base.iview.IBaseView;
import com.mvp.demo.model.bean.Meizi;

import java.util.List;

/**
 * Administrator on 2016/9/21.
 */

public interface IMeiziView extends IBaseView{
    void showProgress();
    void hideProgress();
    void showErrorView();
    void showNoMoreData();
    void showMeiziList(List<Meizi> meiziList);
}
