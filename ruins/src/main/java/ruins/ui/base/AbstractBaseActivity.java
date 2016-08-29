package ruins.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.ButterKnife;
import ruins.ui.widget.loading.LoadingView;
import zero.ruins.R;

public abstract class AbstractBaseActivity extends AppCompatActivity {
    LoadingView mLoadingView;
    ViewGroup mViewGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        initView(savedInstanceState);
        showLoadingView();
    }

    /**
     * 初始化View
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 获取布局文件
     * @return 布局文件
     */
    protected abstract int getLayoutResource();

    /**
     * 获取要展示加载动画的父布局
     * @return 加载动画所在的父布局
     */
    protected abstract ViewGroup getLoadingPartentView();

    /**
     * 显示加载动画
     */
    protected void showLoadingView(){
        mViewGroup=getLoadingPartentView();
        if (mViewGroup!=null){
            View loadView = LayoutInflater.from(this).inflate(R.layout.loadingview,mViewGroup,false);
            mViewGroup.addView(loadView,0);
        }
    }

    /**
     * 关闭加载动画
     */
    protected void closeLoadingView(){
        if (mViewGroup!=null){
           mViewGroup.removeView(mLoadingView);
        }
    }
}
