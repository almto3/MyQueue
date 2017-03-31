package cs371m.myqueue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by scottm on 6/7/2016.
 */
public class SortDialogFragment extends DialogFragment {

    private static final String TAG = "Sort By Dialog";

    private static final String SORT_KEY = "difficulty";

    /**
     * Create a new instance of Difficulty, initialized to
     * show the current difficulty
     */
    public static SortDialogFragment newInstance(int sort) {
        SortDialogFragment result = new SortDialogFragment();

        // Supply difficulty input as an argument.
        Bundle args = new Bundle();
        args.putInt(SORT_KEY , sort);
        result.setArguments(args);

        return result;
    }

    public int getSelectedSort() {

        return getArguments().getInt(SORT_KEY, 0);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int currentDifficulty = getSelectedSort();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.sort_choose)
                .setSingleChoiceItems(R.array.difficulty_levels, currentDifficulty,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // save the difficulty
                                getArguments().putInt(SORT_KEY, which);
                            }
                        })
                // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // user pressed okay, so we are going to change the difficulty
                        int newDifficulty = getArguments().getInt(SORT_KEY, 0);
                        Log.d(TAG, "User clicked okay. Changing difficulty to: " + newDifficulty);
                        //((MainActivity)getActivity()).setDifficulty(newDifficulty);
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // don't change difficulty
                        dismiss();
                    }
                });

        return builder.create();
    }
}
