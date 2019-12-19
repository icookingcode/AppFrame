package com.hnhy.epapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hnhy.epapp.R;
import com.hnhy.framework.frame.BaseActivity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by guc on 2019/12/19.
 * 描述：RxJava测试
 */
public class ActivityRxJavaDemo extends BaseActivity {
    @BindView(R.id.tv_counter)
    TextView mTvCounter;
    @BindView(R.id.tv_reply)
    TextView mTvReply;

    private Disposable mDisposable;
    private Random random = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_start, R.id.btn_hello, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startCounter();
                break;
            case R.id.btn_stop:
                if (mDisposable != null) mDisposable.dispose();
                break;
            case R.id.btn_hello:
                replayDelay();
                break;
        }
    }

    private void replayDelay() {
        Observable.create((ObservableEmitter<String> emitter) -> {
            //耗时操作
            Thread.sleep(1000);
            emitter.onNext("你好啊帅哥" + random.nextInt(100));
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mTvReply.setText(s);
                    }
                });
    }

    private void startCounter() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        mDisposable = Observable.interval(1000, 1000, TimeUnit.MILLISECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return aLong + 1;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mTvCounter.setText(String.format("%d", aLong));
                    }
                });
    }
}
