package ruins.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import zero.ruins.R;

public abstract class AbstractBaseFragment extends Fragment {
    protected ViewGroup mViewGroup;
    protected View rootView;
    protected View mLoadingView;
    protected LayoutInflater mInflater;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView==null) rootView = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, rootView);
        mInflater=inflater;
        initView();
        return rootView;

    }

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 获取布局文件
     * @return 布局文件id
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
            mLoadingView = mInflater.inflate(R.layout.loadingview, mViewGroup, false);
            mViewGroup.addView(mLoadingView,0);
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
