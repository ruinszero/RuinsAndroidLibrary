package ruins.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import ruins.ui.widget.loading.LoadingView;
import ruins.utils.NetworkUtil;
import zero.ruins.R;

@SuppressWarnings("unused")
public abstract class AbstractBaseActivity extends AppCompatActivity {
    LoadingView mLoadingView;
    ViewGroup mViewGroup;
    NetworkReceiver networkReceiver;
    protected boolean isConnection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        initView(savedInstanceState);
        showLoadingView();
    }

    /**
     * <pre>
     * 初始化View
     * @param savedInstanceState
     * </pre>
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 获取布局文件
     *
     * @return 布局文件
     */
     protected abstract int getLayoutResource();

    /**
     * 获取要展示加载动画的父布局
     *
     * @return 加载动画所在的父布局
     */
    protected abstract ViewGroup getLoadingPartentView();

    /**
     * 显示加载动画
     */
    protected void showLoadingView() {
        mViewGroup = getLoadingPartentView();
        if (mViewGroup != null) {
            View loadView = LayoutInflater.from(this).inflate(R.layout.loadingview, mViewGroup, false);
            mViewGroup.addView(loadView, 0);
        }
    }

    /**
     * <pre>是否开启Eventbus</pre>
     */
    protected abstract boolean useEventBus();

    /**
     * 关闭加载动画
     */
    protected void closeLoadingView() {
        if (mViewGroup != null) {
            mViewGroup.removeView(mLoadingView);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onResume() {
        if (networkReceiver == null) {
            networkReceiver = new NetworkReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(networkReceiver);
        super.onPause();
    }


    class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            isConnection = NetworkUtil.isNetworkStatus(context);
        }
    }

}
