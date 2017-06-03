package com.example.ash.landonus.Loopers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ash on 5/21/2017.
 */

public class PhotoSizeReduceTask extends AsyncTask<Void ,Void, Bitmap> {

    private ImageView imageView;
    private Context context;
    private Bitmap bitmap;
    private ByteArrayOutputStream bytearrayoutputstream;
    private byte[] BYTE;
    private ProgressDialog progressDialog;
    private String file_name;

    public PhotoSizeReduceTask(Context context, ImageView imageView, Bitmap bitmap, String file_name){
        this.context = context;
        this.imageView = imageView;
        this.bitmap = bitmap;
        this.file_name = file_name;

    }
    @Override
    protected Bitmap doInBackground(Void... params) {

        bytearrayoutputstream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,bytearrayoutputstream);
        BYTE = bytearrayoutputstream.toByteArray();
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);



        Log.d("path", saveToInternalStorage(bitmap2));

        return bitmap2;
    }

    @Override
    protected void onPreExecute() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Reducing file size");
        progressDialog.show();

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        progressDialog.hide();

        imageView.setImageBitmap(bitmap);

        //Log.d("ashraf",saveToInternalStorage(bitmap));

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("Landonus", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,file_name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
