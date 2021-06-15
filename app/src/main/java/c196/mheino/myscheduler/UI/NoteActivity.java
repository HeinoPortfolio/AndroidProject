package c196.mheino.myscheduler.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import c196.mheino.myscheduler.Entity.NoteEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.NotesViewModel;

/** This is the Note activity class.  This class will be the main entry point for
 *  all note activities in the application.  You will be navigate to all other
 *  note activities from this screen.
 *
 * @author Matthew Heino
 *
 */
public class NoteActivity extends AppCompatActivity {

    public static final String EXTRA_ADD_NOTE_COURSE_ID = "c196.mheino.myscheduler.EXTRA_ADD_NOTE_ID_COURSE";
    public static final String EXTRA_VIEW_NOTE_ID = "c196.mheino.myscheduler.EXTRA_VIEW_NOTE";
    public static final String EXTRA_NOTE_TITLE = "c196.mheino.myscheduler.EXTRA_NOTE_TITLE";
    public static final String EXTRA_NOTE_BODY = "c196.mheino.myscheduler.EXTRA_NOTE_BODY";
    public static final String EXTRA_NOTE_ADD_UPDATE_MODE = "c196.mheino.myscheduler.NOTE_MODE";

    private int courseId;
    private int noteId;
    private String noteTitle;
    private String noteBody;

    /* Note mode will be 1 for add and 2 for update. */
    private  int noteMode;

    /*View model for the Activity. */
    private NotesViewModel notesViewModel;

    // View Components
    private EditText noteTitleET;
    private EditText noteBodyET;
    private TextInputLayout errorNoteTitle;
    private FloatingActionButton addNoteFAB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.course_add_note_bar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        // Get View Components
        noteTitleET = findViewById(R.id.add_note_title_edit_text);
        errorNoteTitle = findViewById(R.id.error_note_edit_title);
        noteBodyET =findViewById(R.id.add_note_body_text);
        addNoteFAB = findViewById(R.id.add_note_fab);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        Intent noteIntent = getIntent();

        if(noteIntent.hasExtra(EXTRA_ADD_NOTE_COURSE_ID)){

            this.courseId = noteIntent.getIntExtra(EXTRA_ADD_NOTE_COURSE_ID, -1);

        }
        if(noteIntent.hasExtra(EXTRA_VIEW_NOTE_ID)){

            this.noteId = noteIntent.getIntExtra(this.EXTRA_VIEW_NOTE_ID,-1);
            this.noteTitle = noteIntent.getStringExtra(this.EXTRA_NOTE_TITLE);
            this.noteBody = noteIntent.getStringExtra(this.EXTRA_NOTE_BODY);

            // Set the fields to their values--------------
            noteTitleET.setText(noteTitle);
            noteBodyET.setText(noteBody);

        } // end else if.

        if(noteIntent.hasExtra(EXTRA_NOTE_ADD_UPDATE_MODE)){

            this.noteMode = noteIntent.getIntExtra(this.EXTRA_NOTE_ADD_UPDATE_MODE,-1);

            if(noteMode == 1){this.setTitle(R.string.add_note);}
            else if(noteMode == 2){this.setTitle(R.string.update_note);}

        }
        else{

            this.setTitle(R.string.view_note);
        }

        // Set the FAB with the listener and action.
        addNoteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Extract the information from the fields
                String noteTitle = noteTitleET.getText().toString().trim();
                String noteBody = noteBodyET.getText().toString().trim();

                NoteEntity newNote = new NoteEntity(noteTitle, noteBody, courseId);

                if(isNoteTitleEmpty(noteTitle) == false) {

                    if (noteMode == 1) // Add note to the database.
                    {

                        // Insert new Note into the database.
                        notesViewModel.insertNote(newNote);

                        // Display a message.
                        Toast.makeText(NoteActivity.this, "Note Saved: \n NoteTitle: "
                                        + noteTitle + "\n Note Body: " + noteBody
                                , Toast.LENGTH_SHORT).show();

                        // Reset the fields
                        noteTitleET.setText("");
                        noteBodyET.setText("");

                    }
                    else if (noteMode == 2)  // Update date the note in the database.
                    {

                        newNote.setNoteId(noteId);

                        notesViewModel.updateNote(newNote);

                        // Display a message.
                        Toast.makeText(NoteActivity.this, "Note Updated: \n NoteTitle: "
                                + noteTitle + "\n Note Body: " + noteBody
                                + "\n End Date: ", Toast.LENGTH_SHORT).show();

                    }
                    else
                        {

                        // Display a message.
                        Toast.makeText(NoteActivity.this
                                , "Note is currently read only!   "
                                        + "\nPlease go back to previous screen "
                                        + "and select another option."
                                , Toast.LENGTH_SHORT).show();

                    }

                } // end validate

            } // end OnCLick.

        });

    } // end onCreate

    /** Method to determine if the note title is empty.  Method that will
     *  determine if the note's title is empty.  Will display a message
     *  to the user as a Toast as well as a text prompt around the edit field.
     *
     * @param noteTitle
     * @return boolean
     *
     */
    private boolean isNoteTitleEmpty(String noteTitle) {

        if(TextUtils.isEmpty(noteTitle)){

            errorNoteTitle.setError(getResources()
                    .getString(R.string.note_title_error));

            Toast.makeText(NoteActivity.this
                    , getResources().getString(R.string.note_error_empty_field)
                    , Toast.LENGTH_LONG).show();

            return true;

        }
        return false;
    }

    /** Method to create the options menu.  This method will create the menu
     *  that will have one options 1) Get help with the
     *  current screen.
     *
     * @param menu
     * @return
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        // Inflate the menu with help and add.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return super.onCreateOptionsMenu(menu);

    } // end onCreateOptionsMenu.

    /** Method to process the toolbar selections. This method will handle the
     *  user selecting options from the toolbar menu. Options it will handle
     *  are: 1) display a help menu.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Process the selection of either add or help.
        switch(item.getItemId())
        {

            case R.id.add_note_help:

                AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.help_text_add_notes);

                alertDialogBuilder.setPositiveButton("OK"
                        , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NoteActivity.this
                                , "Hope this helps."
                                , Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        } // end switch.

    }// endOptionsItemSelected.

} // end class