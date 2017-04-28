package ruins.utils;

import android.app.Activity;
import android.os.Build;

/**
 * @author Ruins
 */
public class UIUtils{

    /**
     * 设置虚拟按键颜色
     * @param activity
     * @param color
     */
    public static void setNavigationBarColor(Activity activity, int color){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            activity.getWindow().setNavigationBarColor(color);
        }
    }
}
