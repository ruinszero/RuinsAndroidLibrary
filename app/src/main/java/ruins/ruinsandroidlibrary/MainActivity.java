package ruins.ruinsandroidlibrary;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_navigationview)
    TextView tvNavigationView;
    @BindView(R.id.tv_openGallery)
    TextView tvOpenGallery;
    @BindView(R.id.tv_transition)
    TextView tvTransition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setEnterTransition(new Explode().setDuration(2000));
        tvNavigationView.setOnClickListener(v -> startActivity(new Intent(this, NavigationViewActivity.class)));
        tvOpenGallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                    "content://media/internal/images/media"));
            startActivity(intent);
        });
        tvTransition.setOnClickListener(v -> startActivity(new Intent(this,TransitionActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()));


        test()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integers1 -> {
                    Log.e("Thread", Thread.currentThread().getName()+integers1);
                });
//        Observable.from(testThread()).subscribeOn(Schedulers.io()).flatMap(new Func1<Integer, Observable<String>>() {
//            @Override
//            public Observable<String> call(final Integer integer) {
//                return Observable.create(new Observable.OnSubscribe<String>() {
//                    @Override
//                    public void call(Subscriber<? super String> subscriber) {
//                        Log.e("my", "call: FlatMap " + Thread.currentThread().getName());
//                        try {
//                            Thread.sleep(200);
//                            subscriber.onNext(integer + 100 + " FlatMap");
//                            subscriber.onCompleted();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            subscriber.onError(e);
//                        }
//                    }
//                }).subscribeOn(Schedulers.newThread());
//            }
//        }).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.e("my", "onCompleted: FlatMap");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("my", "onError: FlatMap");
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.e("my", "onNext: FlatMap " + s);
//                    }
//                });
    }

    public Integer[] testThread() {
        Log.e("my++", Thread.currentThread().getName());
        return new Integer[]{1,2,3};
    }

    public Observable<Integer> test() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.e("my", Thread.currentThread().getName());
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

}
