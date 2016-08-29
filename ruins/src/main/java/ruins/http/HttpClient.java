package ruins.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * HttpClient
 */
public class HttpClient {
    private static HttpClient httpClient;
    private Retrofit retrofit;
    public static HttpClient getInstance(String baseUrl){
        if (httpClient==null){
            synchronized (HttpClient.class){
                if (httpClient==null)
                    httpClient=new HttpClient(baseUrl);
            }
        }
        return httpClient;
    }

    public HttpClient(String baseUrl){
        retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public <T> T createService(Class<T> tClass){
        return retrofit.create(tClass);
    }
}
