package cs371m.myqueue;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.permission;


public class BrowseActivity extends AppCompatActivity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;


    final private String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private boolean project_permissions = false;
    private static final int MY_PERMISSIONS_REQUEST = 16969;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = sharedPrefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = sharedPrefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), true);
            edit.commit();
            startActivity(new Intent(BrowseActivity.this, WelcomeActivity.class));
        }

        setContentView(R.layout.browse_layout);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                TypedArray rotten = getResources().obtainTypedArray(R.array.rotten_tomatoes_score);
                TypedArray movie_plot = getResources().obtainTypedArray(R.array.movie_plots);

                try {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    Log.d("path: ", path );
                    Log.d("item: ", item.getTitle() );
                    File file = new File(path + File.separator +"imageBitmap" + ".jpg");
                    Log.d("file: ", file.toString());


                    checkPermissions(PERMISSIONS);

                    if (!project_permissions) {
                        requestPermissions(PERMISSIONS);
                    }

                    if(file.exists())
                        Log.d("EXISTS", "TRUE");
                    else
                        Log.d("DOES NOT EXISTS", "TRUE");

                    FileOutputStream stream = new FileOutputStream(file);
                    item.getImage().compress(Bitmap.CompressFormat.JPEG, 50, stream);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                //Create intent
                Intent intent = new Intent(BrowseActivity.this, MediaDetailsActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("image", "imageBitmap");
                intent.putExtra("rotten",rotten.getString(position));
                intent.putExtra("movie_plot",movie_plot.getString(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        Toolbar browseToolbar = (Toolbar)findViewById(R.id.browse_toolbar);
        setSupportActionBar(browseToolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void checkPermissions(String[] permissions){

        ArrayList<Boolean> permissions_bools = new ArrayList<Boolean>();

        for (String permission : permissions){
            int permission_result = ContextCompat.checkSelfPermission(this, permission);

            if (permission_result == PackageManager.PERMISSION_GRANTED) {
                Log.d("checkPermissions()", permission + ": true");
                permissions_bools.add(new Boolean(true));
            }
            else {
                Log.d("checkPermissions()", permission + ": false");
                permissions_bools.add(new Boolean(false));
            }
        }
        project_permissions = true;

        for (Boolean b : permissions_bools)
            if(b.booleanValue() == false)
                project_permissions = false;
        Log.d("checkPermissions()", "project_permissions = " + project_permissions);
    }

    private void requestPermissions(String[] permission){
        ActivityCompat.requestPermissions(this, permission, MY_PERMISSIONS_REQUEST);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_browse:
                return true;
            case R.id.menu_bookmarks:
                Toast.makeText(getBaseContext(), R.string.bookmarks_not_implemented, Toast.LENGTH_LONG).show();
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

    /**
     * Prepare some dummy data for gridview
     */
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));

            String movie_name = imgs.getString(i).replace("res/drawable/", "");
            movie_name = movie_name.replace(".jpg", "");
            movie_name = movie_name.replace("_", " ");
            movie_name = movie_name.toUpperCase();
            imageItems.add(new ImageItem(bitmap, movie_name));
        }
        return imageItems;
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Movies> {
        @Override
        protected Movies doInBackground(Void... params) {
            try {
                final String url = "http://api-public.guidebox.com/v2/movies?api_key=c302491413726d93c00a4b0192f8bc55fdc56da4&sources=amazon_prime&limit=10";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Movies movies = restTemplate.getForObject(url, Movies.class);
                return movies;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Movies movies) {
            List<Result> results = movies.getResults();
            for (Result result : results) {
                Log.d("BrowseActivity", result.getTitle());
            }
            //TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            //TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            //greetingIdText.setText(greeting.getId());
            //greetingContentText.setText(greeting.getContent());
        }

    }
}
