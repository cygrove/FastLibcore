package com.cygrove.libcore.main.contract;

import com.cygrove.libcore.main.entity.HousingEstate;
import com.xiongms.libcore.bean.BaseBean;
import com.xiongms.libcore.mvp.IPresenter;
import com.xiongms.libcore.mvp.IView;

import java.util.List;

/**
 *
 */
public interface Contract {

    interface View extends IView {
        void setLoaddingDialogText(String text);


        String getPhone();

        String getCode();

        void showList(BaseBean<List<HousingEstate>> t);

        void finish();
    }

    interface Presenter extends IPresenter<View> {
        void getList();
    }
}