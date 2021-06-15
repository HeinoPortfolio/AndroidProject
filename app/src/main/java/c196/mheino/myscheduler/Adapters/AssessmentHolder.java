package c196.mheino.myscheduler.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import c196.mheino.myscheduler.R;


/** Class for the Assessment Holder.  This class will be used for the
 *  Recyclerview for the assessment.. It will aid in the displaying of data for
 *  assessments. It will define which components of the view wil be modified by
 *  the data that is contained in an AssessmentEntity object.
 *
 * @author  Matthew Heino
 *
 */
public class AssessmentHolder extends RecyclerView.ViewHolder{

    TextView assessmentTitle;               // The assessment title
    TextView assessmentStartDate;           // The start date of the assessment
    TextView assessmentEndDate;             // The end date of the assessment
    TextView assessment_type;               // Type of the Assessment
    ImageView assessmentUpdateDelete;       // Image for the action

    /** Method that will access the different Views of the Assessment
     *  activity.  It will retrieve the references to the view components that
     *  will be used to hold the AssessmentEntity data.
     *
     * @param itemView
     */
    public AssessmentHolder(@NonNull View itemView) {

        super(itemView);

        assessmentTitle = itemView.findViewById(R.id.assessment_title);
        assessmentStartDate = itemView.findViewById(R.id.assessment_start_date_actual);
        assessmentEndDate = itemView.findViewById(R.id.assessment_end_date_actual);
        assessment_type = itemView.findViewById(R.id.assessment_type_tv);
        assessmentUpdateDelete = itemView.findViewById(R.id.update_delete_assessment_menu);

    } // end AssessmentHolder

} // end AssessmentHolder.
