package ruins.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * HttpClient
 * @author Ruins
 */
@SuppressWarnings("unused")
public class HttpClient {
    private static HttpClient httpClient;
    private Retrofit retrofit;

    /**
     * 获取HttpClient
     * @param baseUrl  URL
     * @param log      是否打印日志
     */
    public static HttpClient getInstance(String baseUrl,boolean log,int connectTimeout){
        if (httpClient==null){
            synchronized (HttpClient.class){
                if (httpClient==null){
                    httpClient=new HttpClient(baseUrl,log,connectTimeout);
                }
            }
        }
        return httpClient;
    }

    private HttpClient(String baseUrl,boolean log,int connectTimeout){
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        if (log){
            //Log信息拦截器
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置Log
            builder.addInterceptor(interceptor);
        }
        builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);   //设置超时时间
        OkHttpClient client = builder.build();

        retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> T createService(Class<T> tClass){
        return retrofit.create(tClass);
    }
}
