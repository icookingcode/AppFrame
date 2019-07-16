package com.hnhy.epapp.mvp.contract;


import com.hnhy.epapp.mvp.ui.BasePresenter;
import com.hnhy.epapp.mvp.ui.BaseView;
import com.hnhy.epapp.mvp.ui.model.bean.User;

/**
 * Created by guc on 2019/7/15.
 * 描述：
 */
public interface ContractLogin {
    interface Presenter extends BasePresenter {
        void login(Object tag,String username, String password);
    }

    interface View extends BaseView<Presenter> {
        void onShowLoading(boolean show);

        void onInputIsInvalid(String msg);

        void onLoginCallback(boolean bool, User user, String msg);
    }
}
