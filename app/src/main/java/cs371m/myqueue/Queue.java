package cs371m.myqueue;


import android.util.Log;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Singleton class, should be called from MediaDetailsActivity
//should it be called form anywhere because of the toolbar?
public class Queue {

    private static Queue instance;
    private List<Long> queue;       //List equals the ids. SharedPreferences, key = movie:::id.
    // value = title
    private static final String TAG = "Queue";

    private MediaDetailsActivity app;

    private Queue(){
        queue = new ArrayList<Long>();
        app = MediaDetailsActivity.get();
        loadMovies();
    }

    public static Queue get() {
        Log.d(TAG, "Queue get");
        if(instance == null)
            instance = getSync();
        return instance;
    }

    private static synchronized Queue getSync() {
        if(instance == null)
            instance = new Queue();
        return instance;
    }

    public boolean movieExists(Long movie_id){
        return queue.contains(movie_id);
    }

    public boolean addMovie(String movie_title, Long movie_id){
        if(movieExists(movie_id))
            return false;
        queue.add(movie_id);
        writeMovie(movie_title, movie_id);
        displayQueue();
        displayPrefs();
        return true;
    }

    private void displayQueue(){
        for(Long x : queue)
            Log.d(TAG, "displayQueue --> " + x);
    }
    private void displayPrefs(){
        Map<String, ?> allEntries = readAllMovies();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d(TAG, "displayPrefs --> " + entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    private void writeMovie(String movie_title, Long movie_id){
        HelperSharedPreferences.putSharedPreferencesString(app.getApplicationContext() ,
                HelperSharedPreferences.key1_prefix + movie_id, movie_title);
    }

    private void loadMovies(){
        Map<String, ?> allEntries = readAllMovies();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d(TAG, "loadMovies --> " + entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    //need to do something with the map
    private Map<String, ?> readAllMovies(){
        return HelperSharedPreferences.getAll(app.getApplicationContext());
    }

    public boolean deleteMovie(Long movie_id){
        boolean exist = queue.remove(movie_id);

        if(exist)
            HelperSharedPreferences.deleteSharedPreferencesKey(app.getApplicationContext(),
                    HelperSharedPreferences.key1_prefix + movie_id);
        return exist;
    }
    public void deleteMovies(){
        for (Long x : queue){
            deleteMovie(x);
        }
    }
}


