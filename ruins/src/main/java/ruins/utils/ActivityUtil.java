package ruins.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

/**
 * <pre>
 *  Activity 相关工具类
 *  Created by ruinszero on 2016/12/9.
 * </pre>
 */
@SuppressLint("unused")
public class ActivityUtil {
    //工具类不允许实例化
    private ActivityUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断是否存在Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(Context context, String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(context.getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(context.getPackageManager()) == null ||
                context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

}
