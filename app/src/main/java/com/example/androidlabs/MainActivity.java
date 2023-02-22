package com.example.androidlabs;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

//---------------------On create-----------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DuckImages duckImages = new DuckImages();
        duckImages.execute("https://random-d.uk/api/v2/random?type=jpg");
    }

    private class DuckImages extends AsyncTask<String, Integer, Void> {


        public Void doInBackground(String ... args) {
            while (true) {
                Bitmap photo;
                ImageView duckImageView = findViewById(R.id.imageView);
                try {
                    URL url = new URL(args[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //wait for data
                    InputStream response = conn.getInputStream();
                    //load JSON
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    String result = sb.toString();

                    //create JSON object
                    JSONObject object = new JSONObject(result);

                    //get photo URL
                    URL photoUrl = new URL(object.getString("url"));
                    String filename = object.getString("url");
                    System.out.println(filename);

                    //check if file exists
                    File file = new File(getFilesDir(), filename);
                    if (file.exists()) {
                        photo = BitmapFactory.decodeFile(getFilesDir() + filename);
                        duckImageView.setImageBitmap(photo);

                    } else {

                        //download photo
                        HttpURLConnection connection = (HttpURLConnection) photoUrl.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();

                        // Decode the input stream into a Bitmap object
                        photo = BitmapFactory.decodeStream(input);
                        duckImageView.setImageBitmap(photo);


                        System.out.println("input: " + input.toString());
                        FileOutputStream outputStream = openFileOutput(getFilesDir() + filename, Context.MODE_PRIVATE);
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    }

                } catch (Exception e) {
                    System.out.println("------------------" + e);
                }

                for (int j = 0; j < 100; j++) {
                    try {
                        publishProgress(j);
                        Thread.sleep(30);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void onProgressUpdate (Integer ... values) {
            ProgressBar progressBar = findViewById(R.id.progressBar);
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        public void onPostExecute (Void done) {
        }
    }
}