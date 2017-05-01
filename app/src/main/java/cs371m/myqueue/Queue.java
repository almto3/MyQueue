package cs371m.myqueue;


import android.util.Log;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Singleton class, should be called from MediaDetailsActivity
//should it be called form anywhere because of the toolbar?
public class Queue {

    private static Queue instance;
    private List<String> queue;       //has movie ids
    private static final String TAG = "Queue";

    private MediaDetailsActivity app;

    private Queue(){
        queue = new ArrayList<String>();
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
    public static Queue getInstance(){
        return instance;
    }

    public boolean movieExists(String movie){
        return queue.contains(movie);
    }

    public boolean addMovie(String movie){
        if(movieExists(movie))
            return false;
        queue.add(movie);
        writeMovie(movie);
        displayQueue();
        displayPrefs();
        return true;
    }

    private void displayQueue(){
        for(String x : queue)
            Log.d(TAG, "displayQueue --> " + x);
    }
    private void displayPrefs(){
        Map<String, ?> allEntries = readAllMovies();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d(TAG, "displayPrefs --> " + entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    private void writeMovie(String movie){
        HelperSharedPreferences.putSharedPreferencesString(app.getApplicationContext() , HelperSharedPreferences.key1_prefix + movie, movie);
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

    public boolean deleteMovie(String movie){
        boolean exist = queue.remove(movie);

        if(exist)
            HelperSharedPreferences.deleteSharedPreferencesKey(app.getApplicationContext(), HelperSharedPreferences.key1_prefix + movie);
        return exist;
    }
    public void deleteMovies(){
        for (String x : queue){
            deleteMovie(x);
        }
    }
}


