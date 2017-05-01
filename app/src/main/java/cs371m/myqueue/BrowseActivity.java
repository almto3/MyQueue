package cs371m.myqueue;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class BrowseActivity extends AppCompatActivity {

    private GridView gridView;
    private ProgressBar mProgressBar;

    private GridViewAdapter gridAdapter;
    private ArrayList<Result> results;
    private ArrayList<GridItem> mGridData;

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
        new HttpRequestTask().execute();
        gridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mGridData = new ArrayList<>();
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, mGridData);
        gridView.setAdapter(gridAdapter);

          gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                GridItem item = (GridItem) parent.getItemAtPosition(position);
                Result result = results.get(position);

             //   TypedArray rotten = getResources().obtainTypedArray(R.array.rotten_tomatoes_score);
             //   TypedArray movie_plot = getResources().obtainTypedArray(R.array.movie_plots);

/*
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

*/
                //Create intent
                Intent intent = new Intent(BrowseActivity.this, MediaDetailsActivity.class);
                //List<String> hello = result.getAlternateTitles();
                //Pass the image title and url to DetailsActivity

                intent.putExtra("title", result.getTitle()).
                        putExtra("image", result.getPoster120x171()).
                        putExtra("rotten_tomatoes",result.getRottentomatoes()).
                        putExtra("movie_plot",result.getThemoviedb());

                startActivity(intent);
/*
              intent.putExtra("title", item.getTitle());
                intent.putExtra("image", "imageBitmap");
                intent.putExtra("rotten",rotten.getString(position));
                intent.putExtra("movie_plot",movie_plot.getString(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent); */
            }
        });

        Toolbar browseToolbar = (Toolbar)findViewById(R.id.browse_toolbar);
        setSupportActionBar(browseToolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

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


    private class HttpRequestTask extends AsyncTask<Void, Void, Movies> {
        @Override
        protected Movies doInBackground(Void... params) {
            try {
                final String url = "http://api-public.guidebox.com/v2/movies?api_key=c302491413726d93c00a4b0192f8bc55fdc56da4&sources=amazon_prime&limit=10";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Movies movies = restTemplate.getForObject(url, Movies.class);
                results = movies.getResults();
                GridItem item;
                for (Result result : results) {
                    Log.d("BrowseActivity", result.getTitle());
                    item = new GridItem();
                    item.setTitle(result.getTitle());
                    Log.d("BrowseActivity", item.getTitle());
                    item.setImage(result.getPoster120x171());
                    mGridData.add(item);
                }
                return movies;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Movies movies) {
            Log.d("BrowseActivity", "onPostExecute");
            int a = mGridData.size();
            Log.d("BrowseActivity", Integer.toString(a));
            gridAdapter.setGridData(mGridData);
            //TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            //TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            //greetingIdText.setText(greeting.getId());
            //greetingContentText.setText(greeting.getContent());
        }

    }
}
