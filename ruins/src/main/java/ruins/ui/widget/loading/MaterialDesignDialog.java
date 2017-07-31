package ruins.ui.widget.loading;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
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

public class MaterialDesignDialog extends AlertDialog implements View.OnClickListener {

	public static final int NORMAL_TYPE = 0;
	public static final int ERROR_TYPE = 1;
	public static final int SUCCESS_TYPE = 2;
	public static final int WARNING_TYPE = 3;
	public static final int CUSTOM_IMAGE_TYPE = 4;
	public static final int PROGRESS_TYPE = 5;
	public static final int EMPTY_TYPE = 6;

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

	private Drawable mCustomImgDrawable;
	private Drawable mProgressDrawable;

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

	private ProgressBar mProgress;

	private TextView mTitleTextView;
	private TextView mContentTextView;

	private Button mBtnConfirm;
	private Button mBtnCancel;


	public static interface OnMaterialClickListener {
		public void onClick(MaterialDesignDialog materialDesignDialog);
	}

	public MaterialDesignDialog(@NonNull Context context) {
		super(context);
	}

	public MaterialDesignDialog(@NonNull Context context, int alertType) {
		super(context, R.style.alert_dialog);
		setCancelable(true);
		setCanceledOnTouchOutside(false);
		mProgress = new ProgressBar(context);

		mAlertType = alertType;
	}

	public MaterialDesignDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_material_design_dialog);

		mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);

		mErrorFrame = (FrameLayout) findViewById(R.id.frame_error);
		mEmptyFrame = (FrameLayout) findViewById(R.id.frame_empty);
		mSuccessFrame = (FrameLayout) findViewById(R.id.frame_success);
		mWarningFrame = (FrameLayout) findViewById(R.id.frame_warning);
		mProgressFrame = (FrameLayout) findViewById(R.id.frame_progress);

		mCustomImage = (ImageView) findViewById(R.id.img_custom);
		mError = (ImageView) mErrorFrame.findViewById(R.id.img_error);
		mEmpty = (ImageView) mEmptyFrame.findViewById(R.id.img_empty);
		mWarning = (ImageView) mWarningFrame.findViewById(R.id.img_warning);
		mSuccess = (ImageView) mSuccessFrame.findViewById(R.id.img_success);

		mProgress = (ProgressBar) mProgressFrame.findViewById(R.id.progressBar);

		mTitleTextView = (TextView) findViewById(R.id.tv_normalTitle);
		mContentTextView = (TextView) findViewById(R.id.tv_content);

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

	public MaterialDesignDialog setTitleText (String text) {
		mTitleText = text;
		if (mTitleTextView != null && mTitleText != null) {
			mTitleTextView.setText(mTitleText);
		}
		return this;
	}

	public MaterialDesignDialog setContentText (String text) {
		mContentText = text;
		if (mContentTextView != null && mContentText != null) {
			showContentText(true);
			mContentTextView.setText(mContentText);
		}
		return this;
	}

	public MaterialDesignDialog showContentText(boolean isShow) {
		mShowContent = isShow;
		if (mContentTextView != null) {
			mContentTextView.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
		}
		return this;
	}

	public MaterialDesignDialog setProgressDrawable(Drawable drawable) {
		mProgressDrawable = drawable;
		if (mProgress != null && mProgressDrawable != null) {
			mProgress.setIndeterminateDrawable(mProgressDrawable);
		}
		return this;
	}

	public MaterialDesignDialog setCancelText (String text) {
		mCancelText = text;
		if (mBtnCancel != null && mCancelText != null) {
			showCancelButton(true);
			mBtnCancel.setText(mCancelText);
		}
		return this;
	}

	public MaterialDesignDialog showCancelButton(boolean isShow) {
		mShowCancel = isShow;
		if (mBtnCancel != null) {
			mBtnCancel.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
		}
		return this;
	}

	public MaterialDesignDialog setConfirmText (String text) {
		mConfirmText = text;
		if (mBtnConfirm != null && mConfirmText != null) {
			mBtnConfirm.setText(mConfirmText);
		}
		return this;
	}

	public MaterialDesignDialog setCancelClickListener (OnMaterialClickListener listener) {
		mCancelClickListener = listener;
		return this;
	}

	public MaterialDesignDialog setConfirmClickListener (OnMaterialClickListener listener) {
		mConfirmClickListener = listener;
		return this;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_cancel) {
			if (mCancelClickListener != null) {
				mCancelClickListener.onClick(MaterialDesignDialog.this);
			} else {
				dismissWithAnimation();
			}
		} else if (v.getId() == R.id.btn_confirm) {
			if (mConfirmClickListener != null) {
				mConfirmClickListener.onClick(MaterialDesignDialog.this);
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
				case EMPTY_TYPE:
					mEmptyFrame.setVisibility(View.VISIBLE);
					break;
				default:
					break;
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
	}

	public MaterialDesignDialog setCustomImage(Drawable drawable) {
		mCustomImgDrawable = drawable;
		if (mCustomImage != null && mCustomImgDrawable != null) {
			mCustomImage.setVisibility(View.VISIBLE);
			mCustomImage.setImageDrawable(mCustomImgDrawable);
		}
		return this;
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
	public ProgressBar getProgress () {
		return mProgress;
	}
	public Drawable getProgressDrawable(){
		return mProgressDrawable;
	}
	public void dismissWithAnimation() {
		dismissWithAnimation(false);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void cancel() {
		dismissWithAnimation(true);
	}

	public void dismissWithAnimation(boolean fromCancel) {
		mCloseFromCancel = fromCancel;
		if (mCloseFromCancel) {
			MaterialDesignDialog.super.cancel();
		} else {
			MaterialDesignDialog.super.dismiss();
		}
	}
}
