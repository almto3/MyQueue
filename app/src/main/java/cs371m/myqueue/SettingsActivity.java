package cs371m.myqueue;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        private SharedPreferences sharedPrefs;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            getPreferenceManager().setSharedPreferencesName("AndroidTicTacToe");
            sharedPrefs = getActivity().getSharedPreferences("AndroidTicTacToe", MODE_PRIVATE);

            addPreferencesFromResource(R.xml.preferences);

            updateDifficultyLevelSummary();
            setDifficultyListener();

            updateVictoryMessageSummary();
            setVictoryListener();
        }

        private void setDifficultyListener() {
            final Preference difficultyLevelPref = findPreference("difficulty_level");
            difficultyLevelPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    String difficultySummary = "Current difficulty: " + newValue;
                    difficultyLevelPref.setSummary(difficultySummary);

                    SharedPreferences.Editor ed = sharedPrefs.edit();
                    ed.putString("difficulty_level", newValue.toString());
                    ed.apply();
                    return true;
                }
            });
        }

        private void updateDifficultyLevelSummary() {
            String difficultySummary = "Current difficulty: " + sharedPrefs.getString("difficulty_level", getString(R.string.difficulty_expert));
            Preference difficultyLevelPref = findPreference("difficulty_level");
            difficultyLevelPref.setSummary(difficultySummary);
        }

        private void updateVictoryMessageSummary() {
            String victorySummary = "Current message: " + sharedPrefs.getString("victory_message", getString(R.string.result_human_wins));
            Log.d("updateVictoryMessageSum", "" + victorySummary);
            Log.d("updateVictoryMessageSum", "" + victorySummary);
            Preference victoryMessagePref = findPreference("victory_message");
            victoryMessagePref.setSummary(victorySummary);
        }

        private void setVictoryListener() {
            final Preference victoryLevelPref = findPreference("victory_message");
            victoryLevelPref.setOnPreferenceChangeListener( new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    String victorySummary = "Current message: " + newValue; victoryLevelPref.setSummary(victorySummary);

                    SharedPreferences.Editor ed = sharedPrefs.edit();
                    ed.putString("victory_message", newValue.toString());
                    ed.apply(); return true;
                }
            });
        }
    }
}
