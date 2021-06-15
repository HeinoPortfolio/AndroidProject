package c196.mheino.myscheduler.Adapters;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import c196.mheino.myscheduler.R;


/** Class for the Course Holder.  This class will be used for the
 *  Recyclerview for the course. It will aid in the displaying of data for
 *  courses. It will define which components of the view wil be modified by
 *  the data that is contained in an CourseEntity object.
 *
 * @author  Matthew Heino
 *
 */
public class CourseHolder extends RecyclerView.ViewHolder{

    TextView  courseTitleTextView;          // Holds a reference to the Course Title TextView
    TextView  courseStartDateTextView;      // Holds a reference to the Start date TextVie
    TextView  courseEndDateTextView;        // Holds a reference to the End date TextViews
    TextView  courseStatusTextView;         // Holds a reference to the Course status TextView
    ImageView courseUpdateDelete;           // Holds a reference to the update and delete image

    /** Method that will access the different Views of the Course
     *  activity.  It will retrieve the references to the view components that
     *  will be used to hold the CourseEntity data.
     *
     * @param itemView
     */
    public CourseHolder(@NonNull View itemView) {

        super(itemView);

        // Get the view components.
        courseTitleTextView =  itemView.findViewById(R.id.course_title);
        courseStartDateTextView =  itemView.findViewById(R.id.courses_start_date_actual);
        courseEndDateTextView =  itemView.findViewById(R.id.courses_end_date_actual);
        courseStatusTextView = itemView.findViewById(R.id.course_status_tv);
        courseUpdateDelete = itemView.findViewById(R.id.update_delete_courses_menu);

    } // end CourseHolder.

} // end class



