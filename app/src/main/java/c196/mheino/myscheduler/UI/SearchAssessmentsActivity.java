package c196.mheino.myscheduler.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import c196.mheino.myscheduler.Adapters.SearchAssessmentsAdapter;
import c196.mheino.myscheduler.Entity.AssessmentEntity;
import c196.mheino.myscheduler.Entity.CourseEntity;
import c196.mheino.myscheduler.MyUtils.DateUtil;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.AssessmentViewModel;
import c196.mheino.myscheduler.ViewModels.SearchAssessmentsViewModel;


public class SearchAssessmentsActivity extends AppCompatActivity {

    // View Components
    private Spinner courseListAllSpinner;
    private Button selectCourseButton;
    private Button selectMonthWeekButton;
    private Button clearSelectionButton;
    private RadioGroup weekMonthRadioGroup;
    private RecyclerView assessmentSearchRecyclerView;
    private SearchView searchViewItem;


    private ArrayList<AssessmentEntity> assessmentsArrayList = new ArrayList<>();
    private ArrayList<AssessmentEntity> assessmentsFilteredArrayList;
    private SearchAssessmentsAdapter searchAssessmentAdapter;

    private SearchAssessmentsViewModel searchAssessmentsViewModel;
    private AssessmentViewModel assessmentViewModel;

    private DateUtil dateChecker = new DateUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_assessments);

        //Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_assessments_tool_bar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        // Get View components
        courseListAllSpinner = findViewById(R.id.search_course_spinner);
        selectCourseButton = findViewById(R.id.search_select_course_assessment_button);
        selectMonthWeekButton = findViewById(R.id.search_monthly_weekly_assessment_list_button);
        clearSelectionButton = findViewById(R.id.search_reset_assessment_list_button);
        weekMonthRadioGroup = findViewById(R.id.search_assessments_group);
        assessmentSearchRecyclerView = findViewById(R.id.recycler_view_search_assessments);


        //Create the view model.
        searchAssessmentsViewModel =  ViewModelProviders.of(this).get(SearchAssessmentsViewModel.class);
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);

        // Get the list of courses.
        List<CourseEntity> listOfAllCourses = searchAssessmentsViewModel.getListOfAllCourses();

        // Get list of all the assessments.
        assessmentsArrayList.addAll(searchAssessmentsViewModel.getListOfAllAssessments());


        // Create the Spinner for the courses
        ArrayAdapter<CourseEntity> courseListAdapter
                = new ArrayAdapter<CourseEntity>(SearchAssessmentsActivity.this
                , android.R.layout.simple_spinner_item, listOfAllCourses);
        courseListAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        courseListAllSpinner.setAdapter(courseListAdapter);



        // Create the recycler.===============================================
        assessmentSearchRecyclerView.setLayoutManager(
                new LinearLayoutManager(SearchAssessmentsActivity.this));
        assessmentSearchRecyclerView.setHasFixedSize(true);

        searchAssessmentAdapter = new
                SearchAssessmentsAdapter(SearchAssessmentsActivity.this
                , assessmentsArrayList, assessmentViewModel);
        assessmentSearchRecyclerView.setAdapter(searchAssessmentAdapter);


        // Set the button Listeners============================================
        // Buttons==============================================================
        selectCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CourseEntity courseInfo = (CourseEntity) courseListAllSpinner
                        .getSelectedItem();
                getAssessmentsByCourse(courseInfo.getCourseId());

            }
        });
        selectMonthWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onRadioButtonClicked();

            }
        });
        clearSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearAssessmentsList();
            }
        });

    } // end onCreate.

    /** Method to retrieve assessments by the course ID.  THis method will
     *  receive a course Id and will assign the assessments associated with the
     *  course ID to the major search list.  A List will be returned from the
     *  database method.
     *
     * @param courseId
     *
     *
     */
    private void getAssessmentsByCourse(int courseId) {

        // Clear the main list and notify the adapter.
        assessmentsArrayList.clear();
        searchAssessmentAdapter.notifyDataSetChanged();

        if(assessmentsFilteredArrayList != null){
            assessmentsFilteredArrayList.clear();
            searchAssessmentAdapter.notifyDataSetChanged();
        }

        // Get the list of assessments associated with the course ID and add
        // them to the main list.
        assessmentsArrayList.addAll(searchAssessmentsViewModel
                .getListOfAllAssessmentsByCourseID(courseId));

        if(assessmentsFilteredArrayList != null) {
            assessmentsFilteredArrayList.addAll(searchAssessmentsViewModel
                    .getListOfAllAssessmentsByCourseID(courseId));
        }

        // Notify the adapter of the change.
        searchAssessmentAdapter.notifyDataSetChanged();

    } // end  getAssessmentsByCourse

    /** Method to clear the lists. This method will clear the lists.  This
     *  method will clear the lists to allow the user to have a fresh list of
     *  assessments.
     *
     */
    private void clearAssessmentsList() {

        // Clear the main assessment list.
        assessmentsArrayList.clear();
        searchAssessmentAdapter.notifyDataSetChanged();

        // Clear the filtered list if it is not null.
        if(assessmentsFilteredArrayList != null){

            assessmentsFilteredArrayList.clear();
            searchAssessmentAdapter.notifyDataSetChanged();
        }

        // Rebuild the list
        assessmentsArrayList.addAll(searchAssessmentsViewModel.getListOfAllAssessments());

        if(assessmentsFilteredArrayList != null){
            assessmentsFilteredArrayList.addAll(searchAssessmentsViewModel.getListOfAllAssessments());
        }

        searchAssessmentAdapter.notifyDataSetChanged();

    } // end clearAssessmentsList.


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_assessments, menu);
        MenuItem searchItem = menu.findItem(R.id.search_assessments);
        SearchView assessmentSearchView = (SearchView) searchItem.getActionView();


        // set the text listener
        assessmentSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // Filter the list of notes.
                filterAssessments(newText);
                return false;
            }
        });

        return true;

    } // end OnCreateOptionsMenu.

    /** Method to search assessments. This method will search assessments via text.
     *
     * @param newText
     */
    private void filterAssessments(String newText) {

        assessmentsFilteredArrayList = new ArrayList<>();

        // Iterate through main list.
        for(AssessmentEntity assessment : assessmentsArrayList){

            if(assessment.getAssessmentName().toLowerCase().contains(newText.toLowerCase()))
            {
                assessmentsFilteredArrayList.add(assessment);

            }
            else if(assessment.getAssessmentType().toLowerCase().contains(newText.toLowerCase()))
            {

                assessmentsFilteredArrayList.add(assessment);
            }

        } // end for

        if(assessmentsFilteredArrayList.isEmpty()){
            Toast.makeText(SearchAssessmentsActivity.this
                    , "No Assessments found", Toast.LENGTH_SHORT).show();
        }
        else{
            searchAssessmentAdapter.setAssessments(assessmentsFilteredArrayList);
        }

    } // end filterAssessments

    /** Method to handle the menu options selections. This method will display
     *  the help menu to the  user.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Process the selection of either add or help.
        switch(item.getItemId()){

            case R.id.help_search_assessments:

                AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.search_assessments_help_text);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SearchAssessmentsActivity.this, "Hope this helps."
                                , Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        } // end switch.

    } //end onOptionsItemSelected

    /** Method to handle the radio button selection.  This method will handle
     *  the selection of to view assessments either weekly or monthly.  This
     *  method will prepare the appropriate list to the recycler.
     *
     */
    public void onRadioButtonClicked(){

        int radioID =  weekMonthRadioGroup.getCheckedRadioButtonId();

        switch (radioID){

            case R.id.search_assessment_monthly_radio:
                searchByMonth();
                break;
            case R.id.search_assessment_weekly_radio:
                searchByWeek();
                break;
            default:
                Log.d("PHIL5", "INVALID CHOICE " + radioID);
                break;

        } // end switch

    } // end onRadioButtonClicked.

    /** Method to search for assessments that are in the upcoming week.  This
     *  method will return the assessments that are upcoming within the next
     *  7 days.  The list will be displayed to the user.
     *
     */
    private void searchByWeek() {

        assessmentsFilteredArrayList = new ArrayList<>();

        // Clear the list if applicable.
        // Clear the main list and notify the adapter.
        if(assessmentsFilteredArrayList != null){
            assessmentsFilteredArrayList.clear();
            searchAssessmentAdapter.notifyDataSetChanged();
        }

        // Search Assessment list for upcoming assessments.
        for(AssessmentEntity assessment : assessmentsArrayList)
        {
            // Check the dates of the assessments.
            if((dateChecker.isWithinOneWeek(assessment.getAssessmentStartDate()))
                    || (dateChecker.isWithinOneWeek(assessment.getAssessmentEndDate())))
            {
                assessmentsFilteredArrayList.add(assessment);
            }

        } // end for loop

        // Notify the adapter.
        searchAssessmentAdapter.setAssessments(assessmentsFilteredArrayList);
        searchAssessmentAdapter.notifyDataSetChanged();

    } // end searchByWeek

    /** Method to search for assessments that are in the upcoming month.  This
     *  method will return the assessments that are upcoming within the next
     *  30 days.  The list will be displayed to the user.
     *
     */
    private void searchByMonth() {

        assessmentsFilteredArrayList = new ArrayList<>();

        // Clear the list if applicable.
        // Clear the main list and notify the adapter.
        if(assessmentsFilteredArrayList != null){
            assessmentsFilteredArrayList.clear();
            searchAssessmentAdapter.notifyDataSetChanged();
        }

        // Search Assessment list for upcoming assessments.
        for(AssessmentEntity assessment : assessmentsArrayList)
        {

            // Check the dates of the assessments.
            if((dateChecker.isWithinOneMonth(assessment.getAssessmentStartDate()))
                    || (dateChecker.isWithinOneMonth(assessment.getAssessmentEndDate())))
            {
                //Log.d("PHIL5", "DATE IS WITHIN A MONTH! " + assessment.getAssessmentStartDate());
                assessmentsFilteredArrayList.add(assessment);

            }

        } // end for loop

        // Notify the adapter.
        searchAssessmentAdapter.setAssessments(assessmentsFilteredArrayList);
        searchAssessmentAdapter.notifyDataSetChanged();

    } // end searchByMonth

} // end class