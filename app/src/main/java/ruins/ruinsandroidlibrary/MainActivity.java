package ruins.ruinsandroidlibrary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import ruins.ui.base.AbstractBaseActivity;

public class MainActivity extends AbstractBaseActivity {
    @BindView(R.id.tv_navigationview)
    TextView tvNavigationView;
    @BindView(R.id.tv_openGallery)
    TextView tvOpenGallery;

    @Override
    protected void initView(Bundle savedInstanceState) {
      tvNavigationView.setOnClickListener(v -> startActivity(new Intent(this,NavigationViewActivity.class)));
        tvOpenGallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                    "content://media/internal/images/media"));
            startActivity(intent);
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected ViewGroup getLoadingPartentView() {
        return null;
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }
}
