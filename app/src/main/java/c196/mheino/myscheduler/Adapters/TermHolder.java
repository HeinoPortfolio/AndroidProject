package c196.mheino.myscheduler.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import c196.mheino.myscheduler.R;

/** Class for the Term Holder.  This class will be used for the
 *  Recyclerview for the term. It will aid in the displaying of data for
 *  terms. It will define which components of the view wil be modified by
 *  the data that is contained in an TermEntity object.
 *
 * @author  Matthew Heino
 *
 */
public class TermHolder extends RecyclerView.ViewHolder {

    TextView termTitleTextView;         // Holds a reference to the Term Title TextView
    TextView termStartDateTextView;     // Holds a reference to the Start date TextView
    TextView termEndDateTextView;       // Holds a reference to the End date TextViews
    ImageView updateDeleteImageView;    // Holds a reference to the update and delete image


    /** Method that will access the different Views of the Term
     *  activity.  It will retrieve the references to the view components that
     *  will be used to hold the TermEntity data.
     *
     * @param itemView
     */
    public TermHolder(@NonNull View itemView) {

        super(itemView);

        // Get the view components.
        termTitleTextView = itemView.findViewById(R.id.term_title);
        termStartDateTextView = itemView.findViewById(R.id.term_start_date_actual);
        termEndDateTextView = itemView.findViewById(R.id.term_end_date_actual);
        updateDeleteImageView = itemView.findViewById(R.id.update_delete_menu);

    } // end constructor.

} // end class.
