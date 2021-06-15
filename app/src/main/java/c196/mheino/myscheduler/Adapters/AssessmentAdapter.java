package c196.mheino.myscheduler.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;;
import java.util.List;

import c196.mheino.myscheduler.UI.AddNotificationActivity;
import c196.mheino.myscheduler.UI.AddAssessmentActivity;
import c196.mheino.myscheduler.Entity.AssessmentEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.AssessmentViewModel;

/** Class for the Assessment Adapter.  This class will be used for the Recyclerview for the
 *  assessment.. It will aid in the displaying of data for assessments.
 *
 * @author  Matthew Heino
 *
 */
public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentHolder>{

    /* A list that will hold the list of assessments in the database. */
    List<AssessmentEntity> assessmentList =new ArrayList<>();

    /* Context to the invoking application. */
    Context context;

    AssessmentViewModel assessmentViewModel;


    /** Constructor for the adapter.  This is a  class constructor for the
     *  adapter.  It receives two arguments the a List that is the list of the
     *  assessments that are currently available and a Context that is to the
     *  invoking applications
     *
     * @param assessmentList
     * @param context
     *
     */
    /*
    public AssessmentAdapter(List<AssessmentEntity> assessmentList, Context context) {

        this.assessmentList = assessmentList;
        this.context = context;

    } // end constructor.

     */

    /** A Term adapter constructor.  This constructor will be used to create
     *  a TermAdapter object. It will receive a reference to a TermViewModel and the
     *  application Context.
     *
     * @param context
     * @param termViewModel
     */
    public AssessmentAdapter(Context context, AssessmentViewModel termViewModel)
    {
        this.context = context;
        this.assessmentViewModel = termViewModel;

    }


    /** Method to create the view holder for the Assessment. This method will
     *  use a XML layout to inflate the view for the assessment.  The
     *  assessment holder will return a new AssessmentHolder that has been
     *  inflated with the given layout.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View assessmentView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentHolder(assessmentView);

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
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {

        // Get the current assessment.
        AssessmentEntity currentAssessment = assessmentList.get(position);

        // Set the view components that are in the current assessment.
        holder.assessmentTitle.setText(currentAssessment.getAssessmentName());
        holder.assessmentStartDate.setText(currentAssessment.getAssessmentStartDate());
        holder.assessmentEndDate.setText(currentAssessment.getAssessmentEndDate());
        holder.assessment_type.setText(currentAssessment.getAssessmentType());

        // Set the listener for the menu
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

            } // end onClick
        });

    } // end onBindViewHolder.

    /** Method to return the size of the assessment list.  This method will
     *  return an int, the size of the current assessment list.
     *
     * @return
     */
    @Override
    public int getItemCount() {  return assessmentList.size();  }

    /** Method to set the List of terms. This method will set the terms that
     *  will be displayed in the RecyclerView.
     *
     * @param assessments
     */
    public void setAssessments(List<AssessmentEntity> assessments){

        this.assessmentList= assessments;
        notifyDataSetChanged();

    }

} // end Adapter
