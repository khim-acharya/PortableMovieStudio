package com.example.samplepaint.functions;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.samplepaint.ui.custome_ui.DrawView;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveFile {
    DrawView drawingView;
    Context context;
    public SaveFile(DrawView drawingView, Context context) {
        this.drawingView = drawingView;
        this.context = context;
    }
    private Bitmap getBitmapFromView() {
        Bitmap returnedBitmap = Bitmap.createBitmap(drawingView.getWidth(), drawingView.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = drawingView.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        drawingView.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
    public void saveBitmapImage() {
        long timestamp = System.currentTimeMillis();
        LocalDateTime localTimeInstance = LocalDateTime.now();
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");


        //Tell the media scanner about the new file so that it is immediately available to the user.
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, timestamp);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, formatter.format(localTimeInstance));

        values.put(MediaStore.Images.Media.DATE_TAKEN, timestamp);
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + formatter.format(localTimeInstance));
        values.put(MediaStore.Images.Media.IS_PENDING, true);
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri != null) {
            try {
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                if (outputStream != null) {
                    try {
                        Bitmap bitmap = getBitmapFromView();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        Log.e(TAG, "saveToGallery: ", e);
                    }
                }
                values.put(MediaStore.Images.Media.IS_PENDING, false);
                context.getContentResolver().update(uri, values, null, null);

                Toast.makeText(context, "Saved...", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, "saveToGallery: ", e);
            }
        }
    }

    public void setDefaultColor() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getPackageName(),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("MyColorPickerDialog_COLOR", Color.BLACK);
        editor.putInt("MyColorPickerDialog_SLIDER_BRIGHTNESS", 0);
        editor.apply();
    }
//    If Build.VERSION minSdk < 29 Then use This Function
//    File imageFileFolder = new File(Environment.getExternalStorageDirectory().toString() + '/' + getString(R.string.app_name));
//            if (!imageFileFolder.exists()) {
//                imageFileFolder.mkdirs();
//            }
//            String mImageName = "" + formatter.format(instance) + ".png";
//
//            File imageFile = new File(imageFileFolder, mImageName);
//            try {
//                OutputStream outputStream = new FileOutputStream(imageFile);
//                try {
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    outputStream.close();
//                } catch (Exception e) {
//                    Log.e(TAG, "saveToGallery: ", e);
//                }
//                values.put(MediaStore.Images.Media.DATA, imageFile.getAbsolutePath());
//                getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//                Toast.makeText(this, "Saved...", Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                Log.e(TAG, "saveToGallery: ", e);
//            }
}

