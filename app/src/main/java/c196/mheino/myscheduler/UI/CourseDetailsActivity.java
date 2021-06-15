package c196.mheino.myscheduler.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import c196.mheino.myscheduler.Adapters.AssessmentAdapter;
import c196.mheino.myscheduler.Adapters.NoteAdapter;
import c196.mheino.myscheduler.Entity.AssessmentEntity;
import c196.mheino.myscheduler.Entity.CourseInstructorEntity;
import c196.mheino.myscheduler.Entity.NoteEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.AssessmentViewModel;
import c196.mheino.myscheduler.ViewModels.NotesViewModel;
/** This is the Courses  Details activity class.  This class will be the main entry point for
 *  all course details activities in the application.  You will be navigate to all other
 *  course activities from this screen.
 *
 * @author Matthew Heino
 *
 */
public class CourseDetailsActivity extends AppCompatActivity {


    public static final String EXTRA_DETAILS_COURSE_ID = "c196.mheino.myscheduler.EXTRA_DETAILS_ID_COURSE";
    public static final String EXTRA_DETAILS_COURSE_TITLE = "c196.mheino.myscheduler.EXTRA_DETAILS_COURSE_TITLE";
    public static final String EXTRA_DETAILS_START_DATE_TERM = "c196.mheino.myscheduler.EXTRA_DETAILS_COURSE_START_DATE";
    public static final String EXTRA_DETAILS_END_DATE_TERM = "c196.mheino.myscheduler.EXTRA_COURSE_END_DATE_";
    public static final String EXTRA_DETAILS_INSTRUCTOR_ID_TERM = "c196.mheino.myscheduler.EXTRA_COURSE_INSTRUCTOR_ID";

    private int courseID;
    private  int instructorID;
    private AssessmentAdapter assessmentAdapter;
    private AssessmentViewModel assessmentDetailsViewModel;
    private NoteAdapter notesAdapter;
    private NotesViewModel notesViewModel;

    // View Components
    private TextView courseTitleTV;
    private TextView courseStartDateTV;
    private TextView courseEndDateTV;
    private TextView courseInstructorTV;
    private TextView courseInstructorPhoneTV;
    private TextView courseInstructorEmailTV;
    private RecyclerView assessmentRecyclerView;
    private RecyclerView notesRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        //Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.course_details_tool_bar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        // Get the view components.
        courseTitleTV = findViewById(R.id.course_title_tv);
        courseStartDateTV = findViewById(R.id.course_start_date_tv);
        courseEndDateTV= findViewById(R.id.course_end_date_tv);
        courseInstructorTV= findViewById(R.id.course_instructor_tv);
        courseInstructorPhoneTV= findViewById(R.id.course_instructor_phone_tv);
        courseInstructorEmailTV= findViewById(R.id.course_instructor_email_tv);


        // Set up the recycler.
        assessmentRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_assessments_details);
        assessmentRecyclerView.setLayoutManager(new GridLayoutManager(CourseDetailsActivity.this, 2));
        assessmentRecyclerView.setHasFixedSize(true);

        // Create the view model
        assessmentDetailsViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentAdapter = new AssessmentAdapter(getApplication(), assessmentDetailsViewModel);
        assessmentRecyclerView.setAdapter(assessmentAdapter);

        notesRecyclerView = findViewById(R.id.recycler_view_notes_details);
        notesRecyclerView.setLayoutManager(new GridLayoutManager(CourseDetailsActivity.this, 2));
        notesRecyclerView.setHasFixedSize(true);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesAdapter = new NoteAdapter(getApplication(), notesViewModel);
        notesRecyclerView.setAdapter(notesAdapter);

        // Get the intent and the available Extras.
        Intent courseDetailsIntent = getIntent();

        if(courseDetailsIntent.hasExtra(EXTRA_DETAILS_COURSE_ID)){

            this.setTitle(R.string.course_details_title);

            courseID = courseDetailsIntent.getIntExtra(EXTRA_DETAILS_COURSE_ID, -1);
            String courseTitle = courseDetailsIntent.getStringExtra(EXTRA_DETAILS_COURSE_TITLE);
            String courseStartDate = courseDetailsIntent.getStringExtra(EXTRA_DETAILS_START_DATE_TERM);
            String courseEndDate = courseDetailsIntent.getStringExtra(EXTRA_DETAILS_END_DATE_TERM);
            instructorID = courseDetailsIntent.getIntExtra(EXTRA_DETAILS_INSTRUCTOR_ID_TERM, -1);

            CourseInstructorEntity courseInstructorEntity;

            courseInstructorEntity = notesViewModel.getCourseInstructorInfo(instructorID);

            // Set the text views.
            courseTitleTV.setText(courseTitle);
            courseStartDateTV.setText(courseStartDate);
            courseEndDateTV.setText(courseEndDate);
            courseInstructorTV.setText(courseInstructorEntity.toString());
            courseInstructorPhoneTV.setText(courseInstructorEntity.getPhoneNumber());
            courseInstructorEmailTV.setText(courseInstructorEntity.getEmail());

            assessmentDetailsViewModel.getAllAssessmentsByCourseID(courseID)
                    .observe(this, new Observer<List<AssessmentEntity>>() {
                @Override
                public void onChanged(List<AssessmentEntity> assessmentEntities) {

                    assessmentAdapter.setAssessments(assessmentEntities);

                }
            });

            // set the notes view model
            notesViewModel.getAllNotesByCourseID(courseID).observe(this, new Observer<List<NoteEntity>>() {
                @Override
                public void onChanged(List<NoteEntity> noteEntities) {
                    notesAdapter.setNotes(noteEntities);
                }
            });

        }

    } // end on Create

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
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return super.onCreateOptionsMenu(menu);

    } // end onCreateOptionsMenu.

    /** Method to process the toolbar selections. This method will handle the
     *  user selecting options from the toolbar menu. Options it will handle
     *  are: 1) add a term 2) display a help menu.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Process the selection of either add or help.
        switch(item.getItemId())
        {

            case R.id.help_course_details:

                AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.course_details_help);

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CourseDetailsActivity.this, "Hope this helps."
                                , Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;

            case  R.id.course_details_add_assessment:

                // Create the Add AssessmentIntent
                Intent addAssessmentIntent = new Intent(CourseDetailsActivity.this, AddAssessmentActivity.class);

                addAssessmentIntent.putExtra(AddAssessmentActivity.EXTRA_ASSESSMENT_COURSE_ID, this.courseID);
                addAssessmentIntent.putExtra(AddAssessmentActivity.EXTRA_ASSESSMENT_MODE, 1);

                startActivity(addAssessmentIntent);

                return true;

            case R.id.course_details_add_notes:

                Intent addNoteIntent = new Intent(CourseDetailsActivity.this, NoteActivity.class);
                addNoteIntent.putExtra(NoteActivity.EXTRA_ADD_NOTE_COURSE_ID, this.courseID);
                addNoteIntent.putExtra(NoteActivity.EXTRA_NOTE_ADD_UPDATE_MODE, 1);
                startActivity(addNoteIntent);

            default:
                return super.onOptionsItemSelected(item);

        } // end switch.

    }// endOptionsItemSelected.

} // end class.