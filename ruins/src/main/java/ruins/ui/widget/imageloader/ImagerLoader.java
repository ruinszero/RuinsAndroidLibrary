package ruins.ui.widget.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * 图片加载类,封装图片加载功能,可以更换图片框架而不影响项目,解耦.
 */
public class ImagerLoader {

    public static void load(Context context, String imagerUrl, ImageView imageView){
        Picasso.with(context).load(imagerUrl).into(imageView);
    }

    public static void load(Context context,File file,ImageView imageView){
        Picasso.with(context).load(file).into(imageView);
    }

    public static void load(Context context,int resourceId,ImageView imageView){
        Picasso.with(context).load(resourceId).into(imageView);
    }
}
