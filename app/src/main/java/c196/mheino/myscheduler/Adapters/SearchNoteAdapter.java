package c196.mheino.myscheduler.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import c196.mheino.myscheduler.Entity.NoteEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.UI.SearchNotesActivity;
import c196.mheino.myscheduler.UI.NoteActivity;
import c196.mheino.myscheduler.ViewModels.NotesViewModel;

/** Class for the Search Note Adapter.  This class will be used for the
 *  Recyclerview for the search Note. It will aid in the displaying of data for
 *  Notes. It will define which components of the view wil be modified by
 *  the data that is contained in an NoteEntity object.
 *
 * @author  Matthew Heino
 *
 */
public class SearchNoteAdapter extends RecyclerView.Adapter<SearchNoteHolder>
{

    /* List of all available notes */
    private ArrayList<NoteEntity> allNotesList = new ArrayList<>();

    /* An application context */
    private Context context;

    /*View model for the Activity. */
    private NotesViewModel notesViewModel;  // NEW******************

    // View Model
    // private SearchNotesViewModel searchNotesViewModel;

    /** A Search Note adapter constructor.  This constructor will be used to create
     *  a SearchNoteAdapter object. It will receive a List of notes and the
     *  application Context.
     *
     * @param
     * @param context
     */

    public SearchNoteAdapter(Context context, ArrayList<NoteEntity> notesArrayList, NotesViewModel noteViewModel) {

        // this.courseList = courseList;
        this.context = context;
        this.allNotesList = notesArrayList;
        this.notesViewModel = noteViewModel;

    } // end constructor.

    /** Method to create the view holder for the Note. This method will
     *  use a XML layout to inflate the view for the Note.  The
     *  note holder will return a new SearchNoteHolder that has been
     *  inflated with the given layout.
     *
     * @param parent
     * @param viewType
     * @return
     *
     */
    @NonNull
    @Override
    public SearchNoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View searchNoteView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_note_item, parent, false);
        return new SearchNoteHolder(searchNoteView);

    }

    /** Method to bind and set the different view components to the view
     *  holder.  The method will also set the listener for card that is used in
     *  the assessment.
     *
     * @param holder
     * @param position
     *
     */
    @Override
    public void onBindViewHolder(@NonNull SearchNoteHolder holder, int position) {

        NoteEntity currentNote = allNotesList.get(position);

        // Set the view components that are in the current note.
        holder.noteTitle.setText(currentNote.getNoteTitle());
        holder.noteBody.setText(currentNote.getNoteBodyText());

        //Set the listener for the menu.
        holder.noteOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NoteEntity myCurrentNote = allNotesList.get(position);

                PopupMenu noteOptionsMenu = new PopupMenu(context, holder.noteOptions);
                noteOptionsMenu.inflate(R.menu.popup_menu_note_details);

                // Set the click listener for the popup
                noteOptionsMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent noteIntent = new Intent(context, NoteActivity.class);

                        noteIntent.putExtra(NoteActivity.EXTRA_ADD_NOTE_COURSE_ID, myCurrentNote.getCourseID());
                        noteIntent.putExtra(NoteActivity.EXTRA_VIEW_NOTE_ID, myCurrentNote.getNoteId());
                        noteIntent.putExtra(NoteActivity.EXTRA_NOTE_TITLE, myCurrentNote.getNoteTitle());
                        noteIntent.putExtra(NoteActivity.EXTRA_NOTE_BODY, myCurrentNote.getNoteBodyText());

                        switch(item.getItemId()){

                            case R.id.delete_note:

                                notesViewModel.deleteNote(myCurrentNote);

                                Toast.makeText(context, "Note "
                                        + myCurrentNote.getNoteTitle() + " is deleted", Toast.LENGTH_LONG).show();

                                Intent updateNoteSearch = new  Intent( context.getApplicationContext()
                                        , SearchNotesActivity.class);
                                context.startActivity(updateNoteSearch);
                                break;
                            case R.id.update_note:

                                Log.d("PHIL5", "Update pressed"); // REMOVE***************************************

                                noteIntent.putExtra(NoteActivity.EXTRA_NOTE_ADD_UPDATE_MODE, 2);
                                context.startActivity(noteIntent);
                                break;
                            case R.id.note_details_option:
                                context.startActivity(noteIntent);
                                break;
                            case R.id.note_share_option:

                                // Share the note and the its information.
                                Intent sendNote = new Intent();
                                sendNote.setAction(Intent.ACTION_SEND);

                                String sendNoteBody = "Title: " + myCurrentNote.getNoteTitle()
                                        + "\n Note: \n" +myCurrentNote.getNoteBodyText();

                                sendNote.putExtra(Intent.EXTRA_TEXT, sendNoteBody);
                                sendNote.setType("text/plain");

                                Intent shareNoteIntent = Intent.createChooser(sendNote, "Note Share.");
                                context.startActivity(shareNoteIntent);

                                break;

                        } // end switch

                        return false;
                    }
                });

                noteOptionsMenu.show(); // To SHOW Menu !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            } // end onCLick.
        });

    } //onBindViewHolder

    /** Method to return the size of the notes list.  This method will
     *  return an int, the size of the current assessment list.
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return this.allNotesList.size();
    }

    /** Method to set the List of notes. This method will set the notes that
     *  will be displayed in the RecyclerView.
     *
     * @param notes
     */
    public void setNotes(ArrayList<NoteEntity> notes){

        this.allNotesList= notes;
        notifyDataSetChanged();

    }

}// end class.
