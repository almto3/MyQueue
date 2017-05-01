package cs371m.myqueue;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class MediaDetailsActivity extends AppCompatActivity {

    private final String TAG = "MediaDetailsActivity";

    private static final int REQUEST_WRITE = 0;
    private static final int REQUEST_READ = 1;


    // added by saleh to Keep track of context
    // http://stackoverflow.com/questions/14057273/android-singleton-with-global-context
    private static MediaDetailsActivity instance;
    public static MediaDetailsActivity get() { return instance; }
    Queue q;
    String title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.media_details_layout);
        Log.d(TAG, "onCreate");
        instance = this;

        ImageView imageView = (ImageView) findViewById(R.id.movie_poster);
        TextView titleTextView = (TextView) findViewById(R.id.item_details_title);

        title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        titleTextView.setText(Html.fromHtml(title));

        Picasso.with(this).load(image).into(imageView);
        q = Queue.get();

        setListeners();
        checkPermissions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setListeners() {
        Log.d(TAG, "setListeners()");

        TableRow row0 = (TableRow) findViewById(R.id.tableRow4);
        row0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_bookmarkIcon");
                boolean x = q.addMovie(title);
                if(x)
                    Toast.makeText(getBaseContext(), title + " added to Queue", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getBaseContext(), title + " is already in Queue", Toast.LENGTH_LONG).show();

            }
        });

        ImageView img1 = (ImageView) findViewById(R.id.item_details_service0);
        img1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_service0");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hulu.com"));
                startActivity(browserIntent);
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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hbo.com"));
                startActivity(browserIntent);

            }
        });

        ImageView img4 = (ImageView) findViewById(R.id.item_details_service3);
        img4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_service3");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.smile.amazon..com"));
                startActivity(browserIntent);

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


    protected boolean checkPermissions() {
        final String TAG = "checkPermissions";
        int permissionChecka = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheckb = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheckc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
        int permissionCheckd = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        boolean a = permissionChecka == PackageManager.PERMISSION_GRANTED;
        boolean b = permissionCheckb == PackageManager.PERMISSION_GRANTED;
        boolean c = permissionCheckc == PackageManager.PERMISSION_GRANTED;
        boolean d = permissionCheckd == PackageManager.PERMISSION_GRANTED;

        Log.d(TAG, " - a = " + a);
        Log.d(TAG, " - b = " + b);
        Log.d(TAG, " - c = " + c);
        Log.d(TAG, " - d = " + d);

        if(a)
            requestWritePermission();
        if(b)
            requestReadPermission();

        //couldn't figure out how to programatically retrieve the complied sdk version, but i know it's 23, so we should ask for runtime permissions
        return !(a && b && c && d);
    }

    private void requestWritePermission(){
        final String TAG = "requestWritePermission";
        Log.d(TAG, "");

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
    }
    private void requestReadPermission(){
        final String TAG = "requestReadPermission";

        Log.d(TAG, "");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ);
    }

}
