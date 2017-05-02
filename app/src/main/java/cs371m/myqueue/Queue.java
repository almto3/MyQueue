package cs371m.myqueue;


import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.Pair;

//Singleton class, should be called from MediaDetailsActivity
//should it be called form anywhere because of the toolbar?
public class Queue {

    private static Queue instance;
    private Map<Long, String> queue;       //Long=id, String=title. SharedPreferences, key = movie:::id. value = title



    private static final String TAG = "Queue";
    private BrowseActivity app;

    private Queue(){
        queue = new HashMap<Long, String>();

        app = BrowseActivity.get();
        //cleanMovies();
        parseMovies();
    }
    protected static Queue get() {
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

    protected boolean movieExists(Long movie_id){
        return queue.containsKey(movie_id);
    }
    protected boolean addMovie(Long movie_id, String movie_title){
        if(movieExists(movie_id))
            return false;
        queue.put(movie_id, movie_title);
        writeMovie(movie_id, movie_title);
        displayQueue();
        return true;
    }
    private void displayQueue(){
        for(Long x : queue.keySet()){
            Log.d(TAG, "displayQueue --> id = " + x + ", movie = " + queue.get(x));
        }
    }
    private void writeMovie(Long movie_id, String movie_title){
        HelperSharedPreferences.putSharedPreferencesString(app.getApplicationContext() , HelperSharedPreferences.key1_prefix + movie_id, movie_title);

    }
    //must run when queue is started, to load it up from the sharedprefs
    private void parseMovies(){
        Map<String, ?> allEntries = returnMap();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if(entry.getKey().substring(0,5).equals("movie")) {
                Log.d(TAG, "parseMovies --> id = " + entry.getKey().substring(entry.getKey().lastIndexOf(':') +1 ) + ", movie = " + entry.getValue());
                queue.put(new Long(entry.getKey().substring(entry.getKey().lastIndexOf(':') +1 )), entry.getValue().toString());
            }
        }
    }
    //returns a map of all what's in the sharedprefs
    private Map<String, ?> returnMap(){
        return HelperSharedPreferences.getAll(app.getApplicationContext());
    }
    protected Set<Long> returnKeys(){
        return queue.keySet();
    }
    protected String returnMovie(Long key){
        return queue.get(key).toString();
    }
    protected boolean deleteMovie(Long movie_id){
        Object exist = queue.remove(movie_id);
        if(exist != null)
            HelperSharedPreferences.deleteSharedPreferencesKey(app.getApplicationContext(), HelperSharedPreferences.key1_prefix + movie_id);
        return exist != null;
    }
    public void deleteAllMovies(){
        for (Long x : queue.keySet()){
            deleteMovie(x);
        }
    }

    //only for debugging, will clean the sharedprefs of incorrect inputs
    public void cleanMovies(){
        Map<String, ?> allEntries = returnMap();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if(entry.getKey().substring(0,5).equals("movie") && !entry.getKey().contains(":::")) {
                Log.d(TAG, "cleanMovies --> deleting key " + entry.getKey());
                HelperSharedPreferences.deleteSharedPreferencesKey(app.getApplicationContext(), entry.getKey());
            }
        }
    }
    //only for debugging
    private void displayPrefs(){
        Map<String, ?> allEntries = returnMap();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d(TAG, "displayPrefs --> " + entry.getKey() + ": " + entry.getValue().toString());
        }
    }
}


