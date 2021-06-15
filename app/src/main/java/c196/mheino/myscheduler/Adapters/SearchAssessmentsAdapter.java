package c196.mheino.myscheduler.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import c196.mheino.myscheduler.Entity.AssessmentEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.UI.SearchAssessmentsActivity;
import c196.mheino.myscheduler.UI.AddAssessmentActivity;
import c196.mheino.myscheduler.UI.AddNotificationActivity;
import c196.mheino.myscheduler.ViewModels.AssessmentViewModel;

/** Class for the Search Assessment Adapter.  This class will be used for the
 *  Recyclerview for the search Assessment. It will aid in the displaying of data for
 *  Assessments. It will define which components of the view wil be modified by
 *  the data that is contained in an AssessmentEntity object.
 *
 * @author  Matthew Heino
 *
 */
public class SearchAssessmentsAdapter extends RecyclerView.Adapter<AssessmentHolder>{

    /* List of all the Assessments in the database. */
    private ArrayList<AssessmentEntity> allAssessmentsList = new ArrayList<>();

    /* Application context */
    private Context context;

    private AssessmentViewModel assessmentViewModel;

    /** Constructor for the SearchNoteAdapter.  Constructor for the adapter
     *  that will receive an ArrayList of assessments for use in the adapter.
     *
     * @param context
     * @param assessmentArrayList
     *
     */
    public SearchAssessmentsAdapter (Context context
            , ArrayList<AssessmentEntity> assessmentArrayList, AssessmentViewModel assessmentViewModel)
    {

        this.context = context;
        this.allAssessmentsList = assessmentArrayList;
        this.assessmentViewModel = assessmentViewModel;

    } // end constructor


    /** Method to create the view holder for the assessment. This method will
     *  use an XML layout to inflate the view ofr the assessment. The
     *  Assessment holder will return a new
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View searchAssessmentView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentHolder(searchAssessmentView);
    }

    /** Method to bind and set the different view components to the view
     *  holder. This method will also set the listener for the card is used in
     *  the assessment.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {

        AssessmentEntity currentAssessment = allAssessmentsList.get(position);

        // Set the view components that are in the current assessment.
        holder.assessmentTitle.setText(currentAssessment.getAssessmentName());
        holder.assessmentStartDate.setText(currentAssessment.getAssessmentStartDate());
        holder.assessmentEndDate.setText(currentAssessment.getAssessmentEndDate());
        holder.assessment_type.setText(currentAssessment.getAssessmentType());

        // Set the listener for the card's menu.
        holder.assessmentUpdateDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create the popup menu.
                PopupMenu assessmentMenu = new PopupMenu(context, holder.assessmentUpdateDelete);
                assessmentMenu.inflate(R.menu.popup_menu_assessments);

                // Set a listener for the popup menu- used for updates and deletes.
                assessmentMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent assessmentIntent = new Intent(context, AddAssessmentActivity.class);

                        switch(item.getItemId())
                        {

                            case R.id.delete_assessment:

                                assessmentViewModel.deleteAssessment(currentAssessment);
                                Toast.makeText(context, "Assessment "
                                        + currentAssessment.getAssessmentName() + " is deleted", Toast.LENGTH_LONG).show();
                                Intent updateAssessmentSearch = new  Intent( context.getApplicationContext()
                                        , SearchAssessmentsActivity.class);
                                context.startActivity(updateAssessmentSearch);
                                break;

                            case R.id.update_assessment:

                                assessmentIntent.putExtra
                                        (AddAssessmentActivity.EXTRA_ASSESSMENT_MODE, 2);
                                assessmentIntent
                                        .putExtra(AddAssessmentActivity.EXTRA_ASSESSMENT_COURSE_ID
                                                , currentAssessment.getAssessmentCourseId());

                                assessmentIntent.putExtra(AddAssessmentActivity.EXTRA_ASSESSMENT_ID
                                        , currentAssessment.getAssessmentId());

                                assessmentIntent.putExtra
                                        (AddAssessmentActivity.EXTRA_ASSESSMENT_NAME
                                                , currentAssessment.getAssessmentName());

                                assessmentIntent.putExtra
                                        (AddAssessmentActivity.EXTRA_ASSESSMENT_START_DATE
                                                , currentAssessment.getAssessmentStartDate());

                                assessmentIntent.putExtra
                                        (AddAssessmentActivity.EXTRA_ASSESSMENT_END_DATE
                                                , currentAssessment.getAssessmentEndDate());

                                assessmentIntent.putExtra
                                        (AddAssessmentActivity.EXTRA_ASSESSMENT_TYPE
                                                , currentAssessment.getAssessmentType());

                                context.startActivity(assessmentIntent);

                                break;

                            case R.id.assessment_notification_option:

                                Intent assessmentNotificationIntent
                                        = new Intent(context.getApplicationContext()
                                        , AddNotificationActivity.class);

                                assessmentNotificationIntent
                                        .putExtra(AddNotificationActivity.EXTRA_NOTIFICATION_INFO_TEXT
                                                , currentAssessment.getAssessmentName());

                                assessmentNotificationIntent.putExtra(AddNotificationActivity.EXTRA_NOTIFICATION_TYPE_TO_ADD
                                        , "assessment");

                                context.startActivity(assessmentNotificationIntent);

                        } // end switch

                        return false;
                    }
                });

                assessmentMenu.show();

            } // end on Click
        });

    } // end onBindViewHolder


    /** Method to return the size of the assessment list.  This method will
     *  return an int, the size of the current assessment list.
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return this.allAssessmentsList.size();
    }

    /** Method to set the List of notes. This method will set the notes that
     *  will be displayed in the RecyclerView.
     *
     * @param assessments
     */
    public void setAssessments(ArrayList<AssessmentEntity> assessments){

        this.allAssessmentsList= assessments;
        notifyDataSetChanged();

    }
} // end class
