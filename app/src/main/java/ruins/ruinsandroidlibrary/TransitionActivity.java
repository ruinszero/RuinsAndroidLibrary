package ruins.ruinsandroidlibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;

public class TransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_transition);
        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
        getWindow().setExitTransition(slide);
        getWindow().setEnterTransition(slide);

    }
}
