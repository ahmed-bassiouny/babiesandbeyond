package tech.ntam.mylibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Developer on 10/01/18.
 */

public class ImageFactor {

    public static Bitmap getBitmapImageFromFilePathAfterResize(File file) throws FileNotFoundException {
        return getSampleBitmapFromFile(file.getAbsolutePath(), 150, 150);
    }

    private static Bitmap getSampleBitmapFromFile(String bitmapFilePath, int reqWidth, int reqHeight) throws FileNotFoundException {
        // calculating image size
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(new FileInputStream(new File(bitmapFilePath)), null, options);

        int scale = calculateInSampleSize(options, reqWidth, reqHeight);

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;

        return BitmapFactory.decodeStream(new FileInputStream(new File(bitmapFilePath)), null, o2);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}
