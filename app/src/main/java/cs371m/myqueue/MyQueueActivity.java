package cs371m.myqueue;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.AlertDialog;
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
import java.util.ArrayList;
import java.util.List;


public class MyQueueActivity extends AppCompatActivity {

    private Queue q;

    private GridView gridView;

    private GridViewAdapter gridAdapter;
    private ArrayList<Result> results = new ArrayList<>(100);
    private ArrayList<GridItem> mGridData;

    private String selected_source;
    private final String TAG = "MyQueueActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        setContentView(R.layout.my_queue_layout);
        final List<String> source_list = new ArrayList<>();
        if (sharedPrefs.getBoolean(getString(R.string.netflix_selected), false)) {
            source_list.add("netflix");
        }
        if (sharedPrefs.getBoolean(getString(R.string.hulu_selected), false)) {
            source_list.add("hulu_free,hulu_plus");
        }
        if (sharedPrefs.getBoolean(getString(R.string.hbo_selected), false)) {
            source_list.add("hbo");
        }
        if (sharedPrefs.getBoolean(getString(R.string.amazon_selected), false)) {
            source_list.add("amazon");
        }
        selected_source = source_list.get(0);

        new MyQueueActivity.HttpRequestTask().execute();
        Toolbar myQueueToolbar = (Toolbar)findViewById(R.id.my_queue_toolbar);
        myQueueToolbar.setTitle("MyQueue!");
        myQueueToolbar.findViewById(R.id.my_queue_toolbar_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Clicked");

                new AlertDialog.Builder(MyQueueActivity.this)
                        .setTitle("Empty Queue?")
                        .setMessage("Are you sure you want to delete the whole Queue?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                q.deleteAllMovies();
                                emptyGrid();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        setSupportActionBar(myQueueToolbar);

        gridView = (GridView) findViewById(R.id.gridView);

        mGridData = new ArrayList<>();
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, mGridData);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                GridItem item = (GridItem) parent.getItemAtPosition(position);
                Result result = results.get(position);

                //Create intent
                Intent intent = new Intent(MyQueueActivity.this, MediaDetailsActivity.class);

                //Pass the image title and url to MediaDetailsActivity
                intent.putExtra("title", result.getTitle()).
                        putExtra("image", result.getPoster120x171()).
                        putExtra("id", result.getId()).
                        putExtra("tMDBid", result.getThemoviedb()).
                        putExtra("selected_source", selected_source);
                startActivity(intent);
            }
        });
        q = Queue.get();
    }

    private void restart() {
        finish();
        startActivity(getIntent());
    }

    private void emptyGrid(){
        Log.d(TAG, "1");
        mGridData.clear();
        //findViewById(R.id.RelativeLayout_myQueue).invalidate();
        findViewById(R.id.gridView).invalidate();
        Log.d(TAG, "2");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_browse:
                intent = new Intent(this, BrowseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent);
                return true;
            case R.id.menu_bookmarks:
                Toast.makeText(getBaseContext(), R.string.already_here, Toast.LENGTH_LONG).show();
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
            case R.id.menu_quit:
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Movies> {
        @Override
        protected Movies doInBackground(Void... params) {
            try {

                String url = "";
                Result result= null;
                for(Long key : Queue.get().returnKeys()){
                    Log.d(TAG, "HttpRequestTask --> key = " + key);
                    Log.d(TAG, "HttpRequestTask --> movie = " + Queue.get().returnMovie(key));
                    url = "http://api-public.guidebox.com/v2/movies/" + key +"?api_key=c302491413726d93c00a4b0192f8bc55fdc56da4&movie_id=143441";
                    Log.d(TAG, "HttpRequestTask --> url = " + url);
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    result = restTemplate.getForObject(url, Result.class);

                    if(result != null) {
                        Log.d(TAG, "ADDALL --> " + result.toString());
                        results.add(result);
                    }
                    else
                        Log.d(TAG, "ADDALL --> NULL!!!");
                }

                GridItem item;
                if(results != null)
                    for (Result r : results) {
                        item = new GridItem();
                        item.setTitle(r.getTitle());
                        item.setImage(r.getPoster120x171());
                        mGridData.add(item);
                    }

                Movies movie = null;
                return movie;
            } catch (Exception e) {
                Log.e(TAG, "EXCEPTION::: " + e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movies movies) {
            Log.d(TAG, "onPostExecute");
            int a = mGridData.size();
            Log.d(TAG, Integer.toString(a));
            gridAdapter.setGridData(mGridData);
        }
    }
}
