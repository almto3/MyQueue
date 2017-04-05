package cs371m.myqueue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import android.widget.Toast;


public class MediaDetailsActivity extends AppCompatActivity {

    private final String TAG = "MediaDetailsActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_details_layout);
        Log.i("MediaDetailsActivity", "onCreate");

        String title = getIntent().getStringExtra("image");
        String path = Environment.getExternalStorageDirectory() + "/"+ title + ".png";
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        ImageView imageView = (ImageView) findViewById(R.id.movie_poster);
        imageView.setImageBitmap(bitmap);

        String movie_title = getIntent().getStringExtra("title");
        TextView titleTextView = (TextView) findViewById(R.id.item_details_title);
        titleTextView.setText(movie_title);

        String rotten = getIntent().getStringExtra("rotten");
        TextView rottenTextView = (TextView) findViewById(R.id.item_details_rotten_score);
        rottenTextView.setText(rotten);

        String movie_plot = getIntent().getStringExtra("movie_plot");
        TextView plotTextView = (TextView) findViewById(R.id.item_details_plot);
        plotTextView.setText(movie_plot);

        Toolbar itemDetailsToolbar = (Toolbar)findViewById(R.id.item_details_toolbar);
        setSupportActionBar(itemDetailsToolbar);

        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setListeners() {
        Log.d(TAG, "setListeners()");

        ImageView img0 = (ImageView) findViewById(R.id.item_details_bookmarkIcon);
        img0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_bookmarkIcon");
                Toast.makeText(getBaseContext(), "Bookmark feature isn't fully supported right now", Toast.LENGTH_LONG).show();

            }
        });

        ImageView img1 = (ImageView) findViewById(R.id.item_details_service0);
        img1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_service0");
                Toast.makeText(getBaseContext(), "Hulu isn't supported right now", Toast.LENGTH_LONG).show();
            }
        });

        ImageView img2 = (ImageView) findViewById(R.id.item_details_service1);
        img2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_service1");

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.netflix.com"));
                startActivity(browserIntent);

            }
        });

        ImageView img3 = (ImageView) findViewById(R.id.item_details_service2);
        img3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_service2");
                Toast.makeText(getBaseContext(), "HBO isn't supported right now", Toast.LENGTH_LONG).show();

            }
        });

        ImageView img4 = (ImageView) findViewById(R.id.item_details_service3);
        img4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_service3");
                Toast.makeText(getBaseContext(), "amazon prime isn't supported right now", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_browse:
                intent = new Intent(this, BrowseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            case R.id.menu_bookmarks:
                Toast.makeText(getBaseContext(), R.string.bookmarks_not_implemented, Toast.LENGTH_LONG).show();
                intent = new Intent(this, BrowseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            case R.id.menu_search:
                intent = new Intent(this, SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            case R.id.menu_settings:
                intent = new Intent(this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
