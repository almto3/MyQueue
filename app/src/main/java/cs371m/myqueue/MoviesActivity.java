package cs371m.myqueue;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {



    private GridView gridView;

    private GridViewAdapter gridAdapter;
    private ArrayList<Result> results;
    private ArrayList<GridItem> mGridData;

    private String selected_source;
    private final String TAG = "MoviesActivity";
    final private String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private boolean project_permissions = false;
    private static final int MY_PERMISSIONS_REQUEST = 16969;

    private DatabaseReference mDatabase;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences
                (getBaseContext());
        /*
        if(!checkInternet()){
            getInternet();
        }
        */
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = user.getUid();
        if (user == null) {
            Log.d("MoviesActivity", "user null, this should never happen");
            Intent intent = new Intent(MoviesActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("keep", false);
            startActivity(intent);
//            startActivity(new Intent(MoviesActivity.this, LoginActivity.class));
            MoviesActivity.this.finish();
        }

        final List<String> source_list = new ArrayList<>();
        if (sharedPrefs.getBoolean(getString(R.string.netflix_source), false)) {
            Log.d("MoviesActivity", "netflix added to source_list");
            source_list.add("netflix");
        }
        if (sharedPrefs.getBoolean(getString(R.string.hulu_source), false)) {
            Log.d("MoviesActivity", "hulu added to source_list");
            source_list.add("hulu_free,hulu_plus");
        }
        if (sharedPrefs.getBoolean(getString(R.string.hbo_source), false)) {
            Log.d("MoviesActivity", "hbo added to source_list");
            source_list.add("hbo");
        }
        if (sharedPrefs.getBoolean(getString(R.string.amazon_source), false)) {
            Log.d("MoviesActivity", "amazon added to source_list");
            source_list.add("amazon_prime");
        }

        if (source_list.size() == 0) {
            Log.d("MoviesActivity", "check true");
            source_list.add("netflix");
            Intent intent = new Intent(MoviesActivity.this, SelectSourcesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            Toast.makeText(getBaseContext(),
                    R.string.force_select_service, Toast.LENGTH_LONG).show();
        }
        selected_source = source_list.get(0);

        setContentView(R.layout.browse_layout);

        new HttpRequestTask().execute();
        Toolbar moviesToolbar = (Toolbar)findViewById(R.id.browse_toolbar);
        moviesToolbar.setTitle(R.string.activity_movies);
        setSupportActionBar(moviesToolbar);

        gridView = (GridView) findViewById(R.id.gridView);

        mGridData = new ArrayList<>();
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, mGridData);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                GridItem item = (GridItem) parent.getItemAtPosition(position);
                Result result = results.get(position);

                //Create intent
                Intent intent = new Intent(MoviesActivity.this, MediaDetailsActivity.class);

                Log.i("image: ", result.getPoster120x171());
                //Pass the image title and url to MediaDetailsActivity
                intent.putExtra("title", result.getTitle()).
                        putExtra("image", result.getPoster120x171()).
                        putExtra("id", result.getId()).
                        putExtra("tMDBid", result.getThemoviedb()).
                        putExtra("selected_source", selected_source);

                 startActivity(intent);

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();


        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences
                (getBaseContext());

        final List<String> list = new ArrayList<>();
        final List<String> source_list = new ArrayList<>();
        if (sharedPrefs.getBoolean(getString(R.string.netflix_source), false)) {
            list.add("Netflix");
            source_list.add("netflix");
        }
        if (sharedPrefs.getBoolean(getString(R.string.hulu_source), false)) {
            list.add("Hulu");
            source_list.add("hulu_free,hulu_plus");
        }
        if (sharedPrefs.getBoolean(getString(R.string.hbo_source), false)) {
            list.add("HBO");
            source_list.add("hbo");
        }
        if (sharedPrefs.getBoolean(getString(R.string.amazon_source), false)) {
            list.add("Amazon");
            source_list.add("amazon_prime");
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
        if (source_list.size() == 0) {
            Log.d("MoviesActivity", "this should nvr happen");
            source_list.add("netflix");
        }
        selected_source = source_list.get(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {
                // your code here
                selected_source = source_list.get(position);
                Log.d("MoviesActivity", source_list.get(position));
                new HttpRequestTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

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
                intent = new Intent(this, QActivity.class);
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


    private class HttpRequestTask extends AsyncTask<Void, Void, Movies> {
        @Override
        protected Movies doInBackground(Void... params) {
            try {
                final String url = "http://api-public.guidebox.com/v2/movies?api_key=" +
                        "c302491413726d93c00a4b0192f8bc55fdc56da4&sources=" + selected_source +
                        "&limit=100";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Movies movies = restTemplate.getForObject(url, Movies.class);

                mGridData.clear();

                results = movies.getResults();
                GridItem item;

                for (Result result : results) {
                    Log.d("MoviesActivity", result.getTitle());
                    item = new GridItem();
                    item.setTitle(result.getTitle());
                    item.setImage(result.getPoster120x171());
                    item.settMDBid(result.getThemoviedb());
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

            Log.d("MoviesActivity", "onPostExecute");
            int a = mGridData.size();
            gridAdapter.setGridData(mGridData);

        }

    }
    //true if there's internet
    private boolean checkInternet(){
        Log.d(TAG, "checkInternet");
        if (InternetAccess.getInstance(this).isOnline()) {
            Toast.makeText(getBaseContext(), R.string.online, Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(getBaseContext(), R.string.offline, Toast.LENGTH_LONG).show();
            return false;
        }

    }
    //returns true if button is pressed
    private void getInternet() {

        Log.d(TAG, "getInternet");
        new AlertDialog.Builder(MoviesActivity.this)
                .setTitle(R.string.no_connection)
                .setMessage(R.string.press_to_refresh)
                .setNeutralButton(R.string.refresh, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(!checkInternet()){
                            getInternet();
                        }
                        else
                            restart();
                    }
                })
                .show();

    }
    private void restart() {
        Log.d(TAG, "restart");
        finish();
        startActivity(getIntent());
    }
}