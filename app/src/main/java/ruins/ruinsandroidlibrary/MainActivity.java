package ruins.ruinsandroidlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import ruins.ui.base.AbstractBaseActivity;

public class MainActivity extends AbstractBaseActivity {
    @BindView(R.id.tv_navigationview)
    TextView tvNavigationView;

    @Override
    protected void initView(Bundle savedInstanceState) {
      tvNavigationView.setOnClickListener(v -> {
        startActivity(new Intent(this,NavigationViewActivity.class));
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
}
