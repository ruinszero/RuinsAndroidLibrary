package ruins.ui.widget.loading;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by haowei on 2017/6/6.
 * 加载动画布局
 */

public class LoadingStatusView extends FrameLayout {

	private View contentView;
	private View emptyView;
	private View emptyContentView;
	private View errorView;
	private View errorContentView;
	private View progressView;
	private View progressContentView;
	private View currentShowingView;

	private TextView emptyTextView;
	private TextView errorTextView;
	private TextView progressTextView;
	private ImageView errorImageView;
	private ImageView emptyImageView;
	private ProgressBar progressBar;

	public LoadingStatusView(@NonNull Context context) {
		super(context);
	}

	public LoadingStatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public LoadingStatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
}
