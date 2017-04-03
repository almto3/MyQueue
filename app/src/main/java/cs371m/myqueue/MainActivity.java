package cs371m.myqueue;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private boolean mHumanGoesFirst;

    private boolean mHumansTurnToMove;

    // is the game over or not?
    private boolean mGameOver;

    // Buttons making up the board
    private BoardView mBoardView;

    // Various text display
    private TextView mInfoTextView;

    // tracks how many time each outcome occurs (human wins,
    // tie, android wins

    // displays for the number of each outcome
    private TextView[] mOutcomeCounterTextViews;

    private final int SETTINGS_REQUEST = 1;

    //NEEDS TO BE ADDED MORE IN CODE
    private boolean mSoundOn = true;
    private boolean mShowResultImage = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen1);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mBoardView = (BoardView) findViewById(R.id.boardView);
        setInstanceVarsFromSharedPrefs();
        mBoardView.setOnTouchListener(mTouchListener);
        initOutcomeTextViews();
        restoreScores();

        // on resume will start computer turn if necessary
    }

    private void initOutcomeTextViews() {
        mOutcomeCounterTextViews = new TextView[3];
        mOutcomeCounterTextViews[0] = (TextView) findViewById(R.id.human_wins_tv);
        mOutcomeCounterTextViews[1] = (TextView) findViewById(R.id.ties_tv);
        mOutcomeCounterTextViews[2] = (TextView) findViewById(R.id.android_wins_tv);
    }

    // Set up the game board.
    private void startNewGame() {
    }
    private void handleEndGame(int winner) {


    }
    private void endGameActions(String messageId, int soundId) {
        mInfoTextView.setText(messageId);
        if(mSoundOn)
            System.out.println("tmp");
           // mSounds.play(soundId, 1, 1, 1, 0, 1);
    }

    // Code below this point was added in tutorial 3.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refreshScreen();
                return true;

            case R.id.menu_genre:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, SETTINGS_REQUEST);
                return true;

            case R.id.menu_sortBy:
                ResetScoresDialogFragment resetScoresDialogFragment = new ResetScoresDialogFragment();
                resetScoresDialogFragment.show(fm, "reset");
                return true;

            case R.id.menu_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refreshScreen() {
    }

    // Listen for touches on the board. Only apply move if game not over.
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (!mGameOver && mHumansTurnToMove) {
                // Determine which cell was touched
                int col = (int) event.getX() / mBoardView.getBoardCellWidth();
                int row = (int) event.getY() / mBoardView.getBoardCellHeight();
                int pos = row * 3 + col;
                // is that an open spot?
                /*
                if (mGame.getBoardOccupant(pos) == TicTacToeGame.OPEN_SPOT) {
                    mHumansTurnToMove = false;
                    // make the human move
                    setMove(TicTacToeGame.HUMAN_PLAYER, pos, mHumanMoveSoundID);
                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        startComputerDelay();
                    } else {
                        handleEndGame(winner);
                    }
                }
                */
            }

            // So we aren't notified of continued events when finger is moved
            return false;
        }
    };

    // Code below added /updated in tutorial 5

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //outState.putCharArray("board", mGame.getBoardState());
        outState.putCharSequence("info", mInfoTextView.getText());
        outState.putBoolean("mGameOver", mGameOver);
        outState.putBoolean("mHumansTurnToMove", mHumansTurnToMove);
        outState.putBoolean("mHumanGoesFirst", mHumanGoesFirst);
        //outState.putInt("difficulty", mGame.getDifficultyLevel().ordinal());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        //mGame.setBoardState(savedInstanceState.getCharArray("board"));
        int difficultyInt = savedInstanceState.getInt("difficulty");
        //TicTacToeGame.DifficultyLevel difficulty;
        //difficulty = TicTacToeGame.DifficultyLevel.values()[difficultyInt];
        //mGame.setDifficultyLevel(difficulty);
        mGameOver = savedInstanceState.getBoolean("mGameOver");
        mHumansTurnToMove = savedInstanceState.getBoolean("mHumansTurnToMove");
        mHumanGoesFirst = savedInstanceState.getBoolean("mHumanGoesFirst");
        mInfoTextView.setText(savedInstanceState.getCharSequence("info"));

        restoreScores();
    }

    private void saveGameState(SharedPreferences.Editor editor) {
        // save all relevant information about current game
        // so we can reconstruct in onCreate
        //editor.putInt("difficulty", mGame.getDifficultyLevel().ordinal());
        //editor.putString("board_state", mGame.toStringSimple());
        editor.putBoolean("mGameOver", mGameOver);
        editor.putBoolean("mHumansTurnToMove", mHumansTurnToMove);
        editor.putBoolean("mHumanGoesFirst", mHumanGoesFirst);
        editor.putString("info", mInfoTextView.getText().toString());
    }


    private void restoreScores() {
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
    }

    private void setInstanceVarsFromSharedPrefs() {
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        int difficulty = sharedPref.getInt("difficulty", 0);
        //TicTacToeGame.DifficultyLevel diff = TicTacToeGame.DifficultyLevel.values()[difficulty];
        String boardState = sharedPref.getString("board_state", "*");

        mGameOver = sharedPref.getBoolean("mGameOver", false);
        mHumanGoesFirst = sharedPref.getBoolean("mHumanGoesFirst", true);
        mHumansTurnToMove = sharedPref.getBoolean("mHumansTurnToMove", true);
        mSoundOn = sharedPref.getBoolean("sound", true);
        mShowResultImage = sharedPref.getBoolean("result_image", true);
        mInfoTextView.setText(sharedPref.getString("info", getString(R.string.human_first)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.d("BLABLA", "mShowResultImage = " + mShowResultImage);
        if (requestCode == SETTINGS_REQUEST) {
            SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);

            mSoundOn = sharedPref.getBoolean("sound", true);
            String[] levels = getResources().getStringArray(R.array.difficulty_levels);

            String difficultyLevel = sharedPref.getString("difficulty_level", levels[levels.length - 1]);
            int i = 0;
            while(i < levels.length) {
                if(difficultyLevel.equals(levels[i])) {
                    //mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.values()[i]);
                    i = levels.length; // to stop loop
                }
                i++;
            }
            mShowResultImage = sharedPref.getBoolean("result_image", true);
            //Log.d("BLABLA2", "mShowResultImage = " + mShowResultImage);
        }
    }

    private void prepDownloadImageActivity(int winner, String message) {
        Intent intent = new Intent(this, DownloadImage.class);
        intent.putExtra("winner", winner);
        intent.putExtra("message", message);
        startActivity(intent);
    }
}
