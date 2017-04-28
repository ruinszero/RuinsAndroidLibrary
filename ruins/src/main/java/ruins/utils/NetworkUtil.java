package ruins.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * 判断网络状态工具类
 * 不完善,网络制式可能不全
 * 用了过时的api
 * Created by ruinszero on 16/6/28.
 */
@SuppressWarnings("unused")
public class NetworkUtil {
    //没有网络连接
    public static final int NETWORN_NONE = 0;
    //wifi连接
    public static final int NETWORN_WIFI = 1;
    //手机网络数据连接类型
    public static final int NETWORN_2G = 2;
    public static final int NETWORN_3G = 3;
    public static final int NETWORN_4G = 4;
    public static final int NETWORN_MOBILE = 5;

    private NetworkUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取当前网络连接类型
     * @param context
     * @return
     */
    public static int getNetworkState(Context context) {
        //获取系统的网络服务
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //如果当前没有网络
        if (null == connManager) return NETWORN_NONE;
        //获取当前网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NETWORN_NONE;
        }

        //判断编译版本，如果小于21那么用getNetworkInfo()方法

            // 判断是不是连接的是不是wifi
            NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (null != wifiInfo) {
                NetworkInfo.State state = wifiInfo.getState();
                if (null != state)
                    if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                        return NETWORN_WIFI;
                    }
            }

            // 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
            NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (null != networkInfo) {
                NetworkInfo.State state = networkInfo.getState();
                String strSubTypeName = networkInfo.getSubtypeName();
                if (null != state)
                    if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                        switch (activeNetInfo.getSubtype()) {
                            //如果是2g类型
                            case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                            case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                            case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                            case TelephonyManager.NETWORK_TYPE_1xRTT:
                            case TelephonyManager.NETWORK_TYPE_IDEN:
                                return NETWORN_2G;
                            //如果是3g类型
                            case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                            case TelephonyManager.NETWORK_TYPE_HSUPA:
                            case TelephonyManager.NETWORK_TYPE_HSPA:
                            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                            case TelephonyManager.NETWORK_TYPE_EHRPD:
                            case TelephonyManager.NETWORK_TYPE_HSPAP:
                                return NETWORN_3G;
                            //如果是4g类型
                            case TelephonyManager.NETWORK_TYPE_LTE:
                                return NETWORN_4G;
                            default:
                                //中国移动 联通 电信 三种3G制式
                                if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                    return NETWORN_3G;
                                } else {
                                    return NETWORN_MOBILE;
                                }
                        }
                    }
            }
        return NETWORN_NONE;
    }

    /**
     * 获取当前网络状态2G网也算网络状态不好
     * @param context
     * @return
     */
    public static boolean isNetworkStatus(Context context){
        boolean result = false;
        switch (getNetworkState(context)) {
            case 0:
            case 2:
                result = false;
                break;
            case 1:
            case 3:
            case 4:
            case 5:
                result = true;
                break;
        }
        return result;
    }
}
