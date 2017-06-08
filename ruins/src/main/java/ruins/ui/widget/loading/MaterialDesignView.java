package ruins.ui.widget.loading;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import zero.ruins.R;

/**
 * Created by haowei on 2017/6/7.
 * MaterialDesign风格的布局
 */

public class MaterialDesignView extends AlertDialog implements View.OnClickListener {

	public static final int NORMAL_TYPE = 0;
	public static final int ERROR_TYPE = 1;
	public static final int SUCCESS_TYPE = 2;
	public static final int WARNING_TYPE = 3;
	public static final int CUSTOM_IMAGE_TYPE = 4;
	public static final int PROGRESS_TYPE = 5;

	private String mTitleText;
	private String mContentText;
	private String mCancelText;
	private String mConfirmText;

	private boolean mShowCancel;
	private boolean mShowContent;
	private boolean mCloseFromCancel;

	private int mAlertType;

	private OnMaterialClickListener mCancelClickListener;
	private OnMaterialClickListener mConfirmClickListener;

	private View mDialogView;
	private View mSuccessLeftMask;
	private View mSuccessRightMask;

	private Drawable mCustomImgDrawable;

	private AnimationSet mErrorXInAnim;
	private AnimationSet mSuccessLayoutAnimSet;
	private AnimationSet mModalInAnim;
	private AnimationSet mModalOutAnim;

	private Animation mOverlayOutAnim;
	private Animation mErrorInAnim;
	private Animation mSuccessBowAnim;

	private FrameLayout mErrorFrame;
	private FrameLayout mSuccessFrame;
	private FrameLayout mProgressFrame;
	private FrameLayout mEmptyFrame;
	private FrameLayout mWarningFrame;

	private ImageView mCustomImage;
	private ImageView mError;
	private ImageView mEmpty;
	private ImageView mWarning;
	private ImageView mSuccess;

	private ProgressBar mProgressWheel;

	private TextView mTitleTextView;
	private TextView mContentTextView;

	private Button mBtnConfirm;
	private Button mBtnCancel;


	public static interface OnMaterialClickListener {
		public void onClick(MaterialDesignView materialDesignView);
	}

	public MaterialDesignView(@NonNull Context context) {
		super(context);
	}

	public MaterialDesignView(@NonNull Context context, int alertType) {
		super(context, R.style.alert_dialog);
		setCancelable(true);
		setCanceledOnTouchOutside(false);
		mProgressWheel = new ProgressBar(context);

		mAlertType = alertType;
		mErrorInAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.error_frame_in);
		mErrorXInAnim = (AnimationSet)OptAnimationLoader.loadAnimation(getContext(), R.anim.error_x_in);
		mSuccessBowAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.success_bow_roate);
		mSuccessLayoutAnimSet = (AnimationSet)OptAnimationLoader.loadAnimation(getContext(), R.anim.success_mask_layout);
		mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
		mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
		mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mDialogView.setVisibility(View.GONE);
				mDialogView.post(() -> {
					if (mCloseFromCancel) {
						MaterialDesignView.super.cancel();
					} else {
						MaterialDesignView.super.dismiss();
					}
				});
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		// dialog overlay fade out
		mOverlayOutAnim = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				WindowManager.LayoutParams wlp = getWindow().getAttributes();
				wlp.alpha = 1 - interpolatedTime;
				getWindow().setAttributes(wlp);
			}
		};
		mOverlayOutAnim.setDuration(120);
	}

	public MaterialDesignView(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_normalview);

		mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);

		mErrorFrame = (FrameLayout) findViewById(R.id.error_frame);
		mEmptyFrame = (FrameLayout) findViewById(R.id.empty_frame);
		mSuccessFrame = (FrameLayout) findViewById(R.id.success_frame);
		mWarningFrame = (FrameLayout) findViewById(R.id.warning_frame);
		mProgressFrame = (FrameLayout) findViewById(R.id.progress_frame);

		mCustomImage = (ImageView) findViewById(R.id.custom_image);
		mError = (ImageView) mErrorFrame.findViewById(R.id.error);
		mEmpty = (ImageView) mEmptyFrame.findViewById(R.id.empty);
		mWarning = (ImageView) mWarningFrame.findViewById(R.id.warning);
		mSuccess = (ImageView) mSuccessFrame.findViewById(R.id.success);

		mProgressWheel = (ProgressBar) mProgressFrame.findViewById(R.id.progressWheel);

		mTitleTextView = (TextView) findViewById(R.id.tv_normalTitle);
		mContentTextView = (TextView) findViewById(R.id.content_text);

		mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
		mBtnCancel = (Button) findViewById(R.id.btn_cancel);
		mBtnConfirm.setOnClickListener(this);
		mBtnCancel.setOnClickListener(this);

		setTitleText(mTitleText);
		setContentText(mContentText);
		setCancelText(mCancelText);
		setConfirmText(mConfirmText);
		changeAlertType(mAlertType, true);
	}

	public MaterialDesignView setTitleText (String text) {
		mTitleText = text;
		if (mTitleTextView != null && mTitleText != null) {
			mTitleTextView.setText(mTitleText);
		}
		return this;
	}

	public MaterialDesignView setContentText (String text) {
		mContentText = text;
		if (mContentTextView != null && mContentText != null) {
			showContentText(true);
			mContentTextView.setText(mContentText);
		}
		return this;
	}

	public MaterialDesignView showContentText(boolean isShow) {
		mShowContent = isShow;
		if (mContentTextView != null) {
			mContentTextView.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
		}
		return this;
	}

	public MaterialDesignView setCancelText (String text) {
		mCancelText = text;
		if (mBtnCancel != null && mCancelText != null) {
			showCancelButton(true);
			mBtnCancel.setText(mCancelText);
		}
		return this;
	}

	public MaterialDesignView showCancelButton(boolean isShow) {
		mShowCancel = isShow;
		if (mBtnCancel != null) {
			mBtnCancel.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
		}
		return this;
	}

	public MaterialDesignView setConfirmText (String text) {
		mConfirmText = text;
		if (mBtnConfirm != null && mConfirmText != null) {
			mBtnConfirm.setText(mConfirmText);
		}
		return this;
	}

	public MaterialDesignView setCancelClickListener (OnMaterialClickListener listener) {
		mCancelClickListener = listener;
		return this;
	}

	public MaterialDesignView setConfirmClickListener (OnMaterialClickListener listener) {
		mConfirmClickListener = listener;
		return this;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_cancel) {
			if (mCancelClickListener != null) {
				mCancelClickListener.onClick(MaterialDesignView.this);
			} else {
				dismissWithAnimation();
			}
		} else if (v.getId() == R.id.btn_confirm) {
			if (mConfirmClickListener != null) {
				mConfirmClickListener.onClick(MaterialDesignView.this);
			} else {
				dismissWithAnimation();
			}
		}
	}

	public void changeAlertType(int alertType, boolean fromCreate) {
		mAlertType = alertType;
		// call after created views
		if (mDialogView != null) {
			if (!fromCreate) {
				// restore all of views state before switching alert type
				restore();
			}
			switch (mAlertType) {
				case ERROR_TYPE:
					mErrorFrame.setVisibility(View.VISIBLE);
					break;
				case SUCCESS_TYPE:
					mSuccessFrame.setVisibility(View.VISIBLE);
					// initial rotate layout of success mask
					//mSuccessLeftMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(0));
					//mSuccessRightMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(1));
					break;
				case WARNING_TYPE:
					mBtnConfirm.setBackgroundResource(R.drawable.red_button_background);
					mWarningFrame.setVisibility(View.VISIBLE);
					break;
				case CUSTOM_IMAGE_TYPE:
					setCustomImage(mCustomImgDrawable);
					break;
				case PROGRESS_TYPE:
					mProgressFrame.setVisibility(View.VISIBLE);
					mBtnConfirm.setVisibility(View.GONE);
					break;
			}
			if (!fromCreate) {
				//playAnimation();
			}
		}
	}

	public void restore () {
		mCustomImage.setVisibility(View.GONE);
		mErrorFrame.setVisibility(View.GONE);
		mSuccessFrame.setVisibility(View.GONE);
		mWarningFrame.setVisibility(View.GONE);
		mProgressFrame.setVisibility(View.GONE);
		mBtnConfirm.setVisibility(View.VISIBLE);

		mBtnConfirm.setBackgroundResource(R.drawable.blue_button_background);
		mErrorFrame.clearAnimation();
		mError.clearAnimation();
		mSuccess.clearAnimation();
		mSuccessLeftMask.clearAnimation();
		mSuccessRightMask.clearAnimation();
	}

	public MaterialDesignView setCustomImage(Drawable drawable) {
		mCustomImgDrawable = drawable;
		if (mCustomImage != null && mCustomImgDrawable != null) {
			mCustomImage.setVisibility(View.VISIBLE);
			mCustomImage.setImageDrawable(mCustomImgDrawable);
		}
		return this;
	}

	public void playAnimation () {
		if (mAlertType == ERROR_TYPE) {
			mErrorFrame.startAnimation(mErrorInAnim);
			mError.startAnimation(mErrorXInAnim);
		} else if (mAlertType == SUCCESS_TYPE) {
			//mSuccess.startTickAnim();
			mSuccessRightMask.startAnimation(mSuccessBowAnim);
		}
	}

	public int getAlerType () {
		return mAlertType;
	}

	public void changeAlertType(int alertType) {
		changeAlertType(alertType, false);
	}
	public String getTitleText () {
		return mTitleText;
	}
	public boolean isShowCancelButton () {
		return mShowCancel;
	}
	public boolean isShowContentText () {
		return mShowContent;
	}
	public String getCancelText () {
		return mCancelText;
	}
	public String getConfirmText () {
		return mConfirmText;
	}
	public ProgressBar getProgressHelper () {
		return mProgressWheel;
	}
	public void dismissWithAnimation() {
		dismissWithAnimation(false);
	}
	protected void onStart() {
		mDialogView.startAnimation(mModalInAnim);
		//playAnimation();
	}
	@Override
	public void cancel() {
		dismissWithAnimation(true);
	}

	public void dismissWithAnimation(boolean fromCancel) {
		mCloseFromCancel = fromCancel;
		mBtnConfirm.startAnimation(mOverlayOutAnim);
		mDialogView.startAnimation(mModalOutAnim);
	}
}
