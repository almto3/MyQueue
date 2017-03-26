package cs371m.myqueue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImage extends AppCompatActivity {

    private TextView mDownloadingMessage;
    private TextView mWinnerMessage;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);

        mDownloadingMessage = (TextView) findViewById(R.id.message_downloading);
        mWinnerMessage = (TextView) findViewById(R.id.message_winner);
        mImageView = (ImageView) findViewById(R.id.image);
        mDownloadingMessage.setText(R.string.downloading_image);

        int winner = getIntent().getIntExtra("winner", 0);
        String message = getIntent().getStringExtra("message");

        String urlString = displayWinnerInfo(winner, message);
        downloadImage(urlString);
    }

    private String displayWinnerInfo(int winner, String message) { String urlString = "";
        if(winner == 1)
            urlString = getString(R.string.url_tie);
        else if(winner == 2)
            urlString = getString(R.string.url_winner);
        else if (winner == 3)
            urlString = getString(R.string.url_loser);
        else
            message = "Error!!!!";

        mWinnerMessage.setText(message);
        return urlString;
    }

    private void downloadImage(String urlString) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
            new DownloadImageTask().execute(urlString);
        else
            mDownloadingMessage.setText(R.string.no_network_connection);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
    {
        protected Bitmap doInBackground(String... params) {
            Bitmap returnImage = null;
            URL url = null;
            try {
                url = new URL(params[0]);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection urlConnection = null;
            if(url != null){
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(urlConnection != null){
                try {
                    InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
                    returnImage = BitmapFactory.decodeStream(in);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    urlConnection.disconnect();
                }
            }
            return returnImage;
        }
        protected void onPostExecute(Bitmap result) {
            if(result != null){
                mImageView.setImageBitmap(result);
                mDownloadingMessage.setText(R.string.download_complete);
            }
        }
    }
}
