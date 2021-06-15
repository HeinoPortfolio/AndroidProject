package c196.mheino.myscheduler.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import c196.mheino.myscheduler.R;

/** Class for the Note Holder.  This class will be used for the
 *  Recyclerview for the Note. It will aid in the displaying of data for
 *  notes. It will define which components of the view wil be modified by
 *  the data that is contained in an NoteEntity object.
 *
 * @author  Matthew Heino
 *
 */
public class SearchNoteHolder extends RecyclerView.ViewHolder {

    TextView noteTitle;                 // The note title
    TextView noteBody;                  // The body of the note.
    ImageView noteOptions;              // Image for the action

    /** Method that will access the different Views of the Note
     *  activity.  It will retrieve the references to the view components that
     *  will be used to hold the NoteEntity data.
     *
     * @param itemView
     */
    public SearchNoteHolder(@NonNull View itemView) {

        super(itemView);

        noteTitle = itemView.findViewById(R.id.search_note_title);
        noteBody = itemView.findViewById(R.id.search_note_body);
        noteOptions = itemView.findViewById(R.id.search_note_menu);

    } // end AssessmentHolder

} // end class.
