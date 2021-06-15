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
import java.util.List;

import c196.mheino.myscheduler.Entity.NoteEntity;
import c196.mheino.myscheduler.UI.NoteActivity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.NotesViewModel;

public class NoteAdapter extends RecyclerView.Adapter<NoteHolder>{


    /* A list that will hold the list of assessments in the database. */
    List<NoteEntity> notesList =new ArrayList<>();

    /* Context to the invoking application. */
    Context context;
    NotesViewModel notesViewModel;


    /** A Note adapter constructor.  This constructor will be used to create
     *  a NoteAdapter object. It will receive a reference to a NotesViewModel and the
     *  application Context.
     *
     * @param context
     * @param noteViewModel
     */
    public NoteAdapter(Context context, NotesViewModel noteViewModel)
    {
        this.context = context;
        this.notesViewModel = noteViewModel;

    }

    /** Method to create the view holder for the Note. This method will
     *  use a XML layout to inflate the view for the Note.  The
     *  note holder will return a new NoteHolder that has been
     *  inflated with the given layout.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View noteView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item, parent, false);

        return new NoteHolder(noteView);

    } // end onCreateViewHolder

    /** Method to bind and set the different view components to the view
     *  holder.  The method will also set the listener for card that is used in
     *  the assessment.
     *
     * @param holder
     * @param position
     *
     */
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        // Get the current Note.
        NoteEntity currentNote = notesList.get(position);

        // Set the view components that are in the current Note.
        holder.noteTitle.setText(currentNote.getNoteTitle());

        // Set the listener for the menu
        holder.noteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Will need to retrieve the ID*****************************************************************

                NoteEntity myCurrentNote = notesList.get(position);

                // Create the popup menu.
                PopupMenu noteMenu = new PopupMenu(context, holder.noteDetails);
                noteMenu.inflate(R.menu.popup_menu_note_details);

                // Set a listener for the popup menu- used for updates and deletes.
                noteMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent noteIntent = new Intent(context, NoteActivity.class);

                        noteIntent.putExtra(NoteActivity.EXTRA_ADD_NOTE_COURSE_ID, myCurrentNote.getCourseID());
                        noteIntent.putExtra(NoteActivity.EXTRA_VIEW_NOTE_ID, myCurrentNote.getNoteId());
                        noteIntent.putExtra(NoteActivity.EXTRA_NOTE_TITLE, myCurrentNote.getNoteTitle());
                        noteIntent.putExtra(NoteActivity.EXTRA_NOTE_BODY, myCurrentNote.getNoteBodyText());

                        switch (item.getItemId()) {

                            case R.id.delete_note:

                                notesViewModel.deleteNote(myCurrentNote);
                                break;

                            case R.id.update_note:
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

                noteMenu.show();

            } // end onClick
        });

    } // end OnBindViewHolder

    /** Method to return the size of the notes list.  This method will
     *  return an int, the size of the current assessment list.
     *
     * @return
     */
    @Override
    public int getItemCount() {  return notesList.size();  }

    /** Method to set the List of notes. This method will set the notes that
     *  will be displayed in the RecyclerView.
     *
     * @param notes
     */
    public void setNotes(List<NoteEntity> notes){

        this.notesList= notes;
        notifyDataSetChanged();

    }

} // end class
