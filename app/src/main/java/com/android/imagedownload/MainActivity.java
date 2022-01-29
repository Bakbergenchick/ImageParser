package com.android.imagedownload;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private String url =
            "https://img2.akspic.ru/previews/5/7/1/6/6/166175/166175-gubka_bob-multfilm-multik-bikini_bottom-nikelodeon-500x.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

    }

    public void imgDowload(View view) {

        Download download = new Download();
        Bitmap bitmap = null;
        try {
            bitmap = download.execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        imageView.setImageBitmap(bitmap);

    }

    private static class Download extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null) urlConnection.disconnect();
            }

            return null;
        }
    }
}