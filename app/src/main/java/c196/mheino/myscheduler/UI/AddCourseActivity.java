package c196.mheino.myscheduler.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import c196.mheino.myscheduler.Entity.CourseEntity;
import c196.mheino.myscheduler.Entity.CourseInstructorEntity;
import c196.mheino.myscheduler.Entity.TermEntity;
import c196.mheino.myscheduler.MyUtils.DateUtil;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.CourseViewModel;

public class AddCourseActivity extends AppCompatActivity {

    // Keys
    public static final String EXTRA_ADD_COURSE_ID = "c196.mheino.myscheduler.EXTRA_ADD_COURSE";
    public static final String EXTRA_COURSE_TITLE_ADD_COURSE = "c196.mheino.myscheduler.EXTRA_COURSE_TITLE_ADD_COURSE";
    public static final String EXTRA_START_DATE_ADD_COURSE = "c196.mheino.myscheduler.EXTRA_START_DATE_ADD_COURSE";
    public static final String EXTRA_END_DATE_ADD_COURSE = "c196.mheino.myscheduler.EXTRA_END_DATE_ADD_COURSE";
    public static final String EXTRA_STATUS_ADD_COURSE = "c196.mheino.myscheduler.EXTRA_STATUS_ADD_COURSE";
    public static final String EXTRA_INSTRUCTOR_ID_ADD_COURSE = "c196.mheino.myscheduler.EXTRA_INSTRUCTOR_ID_ADD_COURSE";
    public static final String EXTRA_TERM_ID_ADD_COURSE = "c196.mheino.myschduler.EXTRA_TERM_ID_ADD_COURSE";

    // Mode for the screen.
    public static final String EXTRA_MODE_ADD_COURSE ="c196.mheino.mysceduler.EXTRA_MODE_ADD_COURSE";

    private CourseViewModel courseViewModel;
    private DateUtil dateChecker = new DateUtil();

    private int termId;
    private int courseID;
    private int courseInstructorID;
    private int modeID;
    private String courseTitle;
    private String courseStartDate;
    private String courseEndDate;
    private String courseStatus;

    private CourseInstructorEntity courseInstructorEntity;

    Calendar startCourseCalendar= Calendar.getInstance();
    Calendar endCourseCalendar= Calendar.getInstance();

    //View Components.*******************************************************
    private EditText addCourseTitleET;
    private EditText addCourseStartDateET;
    private EditText addCourseEndDateET;
    private Spinner statusSpinner;
    private Spinner instructorSpinner;
    private TextInputLayout errorCourseTitle;
    private TextInputLayout errorCourseStartDate;
    private TextInputLayout errorCourseEndDate;
    private FloatingActionButton addCourseFAB;
    private FloatingActionButton addCourseNotificationFAB;

    //Date listeners.
    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        //Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.course_add_tool_bar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        // Get handle to view components
        addCourseTitleET = findViewById(R.id.add_course_edit_text);
        errorCourseTitle = findViewById(R.id.error_course_edit_title);
        addCourseStartDateET = findViewById(R.id.add_course_start_date_edit_text);
        errorCourseStartDate = findViewById(R.id.error_course_start_date);
        addCourseEndDateET = findViewById(R.id.add_course_end_date_edit_text);
        errorCourseEndDate = findViewById(R.id.error_course_end_date);
        statusSpinner = findViewById(R.id.status_spinner);
        instructorSpinner = findViewById(R.id.instructor_spinner);

        courseViewModel =  ViewModelProviders.of(this).get(CourseViewModel.class);

        List<CourseInstructorEntity> instructorList = courseViewModel.getListOfCourseInstructors();

        //Create the Spinner for instructor.
        ArrayAdapter<CourseInstructorEntity> instructorAdapter
                = new ArrayAdapter<CourseInstructorEntity>(AddCourseActivity.this
                , android.R.layout.simple_spinner_item,instructorList);
        instructorAdapter.setDropDownViewResource
                (android.R.layout.simple_dropdown_item_1line);
        instructorSpinner.setAdapter(instructorAdapter);

        //Create the Spinner for status.
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(AddCourseActivity.this
                , R.array.status_array, android.R.layout.simple_list_item_1);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        statusSpinner.setAdapter(statusAdapter);


        // Get the intent and the extra.*******
        Intent addCourse = getIntent();

        if (addCourse.hasExtra(EXTRA_MODE_ADD_COURSE)){

            this.termId = addCourse.getIntExtra(EXTRA_TERM_ID_ADD_COURSE, -1);
            this.modeID = addCourse.getIntExtra(EXTRA_MODE_ADD_COURSE, -1);

            if(modeID == 1){ this.setTitle(R.string.add_course_title); }
            else if(modeID == 2){ this.setTitle(R.string.update_course_title); }

        }

        if(addCourse.hasExtra(EXTRA_COURSE_TITLE_ADD_COURSE)){

            // Extract the rest of the information.
            courseID = addCourse.getIntExtra(EXTRA_ADD_COURSE_ID, -1);
            courseTitle = addCourse.getStringExtra(EXTRA_COURSE_TITLE_ADD_COURSE);
            courseStartDate = addCourse.getStringExtra(EXTRA_START_DATE_ADD_COURSE);
            courseEndDate = addCourse.getStringExtra(EXTRA_END_DATE_ADD_COURSE);
            courseStatus = addCourse.getStringExtra(EXTRA_STATUS_ADD_COURSE);
            courseInstructorID = addCourse.getIntExtra(EXTRA_INSTRUCTOR_ID_ADD_COURSE, -1);


            // Set the edit text with the data.
            addCourseTitleET.setText(courseTitle);
            addCourseStartDateET.setText(courseStartDate);
            addCourseEndDateET.setText(courseEndDate);

            // Set the status spinner to the default value
            int statusPosition  = statusAdapter.getPosition(courseStatus);

            // Get and set the default assessment type for the spinner.
            statusSpinner.setSelection(statusPosition);

            // Get and set the default value for the course instructor.

            courseInstructorEntity = courseViewModel.getCourseInstructorInformation(courseInstructorID);

            int instructorPosition = getPositionOfInstructor(instructorList, courseInstructorID);

            instructorSpinner.setSelection(instructorPosition);

        }

        // Add listeners for the edit text fields for the dates.***************
        addCourseStartDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create the DatePicker for the start date.
                new DatePickerDialog(AddCourseActivity.this
                        , startDateListener,startCourseCalendar.get(Calendar.YEAR)
                        , startCourseCalendar.get(Calendar.MONTH)
                        , startCourseCalendar.get(Calendar.DAY_OF_MONTH)).show();

            } // end on click.
        });

        addCourseEndDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create the DatePicker for the start date.
                new DatePickerDialog(AddCourseActivity.this
                        , endDateListener,endCourseCalendar.get(Calendar.YEAR)
                        , endCourseCalendar.get(Calendar.MONTH)
                        , endCourseCalendar.get(Calendar.DAY_OF_MONTH)).show();

            } // end on click
        });

        // Create the date pickers.
        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                startCourseCalendar.set(Calendar.YEAR, year);
                startCourseCalendar.set(Calendar.MONTH,month);
                startCourseCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateStartDate();

            } // end onDateSet
        };

        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                endCourseCalendar.set(Calendar.YEAR, year);
                endCourseCalendar.set(Calendar.MONTH,month);
                endCourseCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateEndDate();

            }
        };

        // Set up the FAB with listener and action
        addCourseFAB = findViewById(R.id.add_course_fab);

        addCourseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                // Extract the information.
                String courseTitle = addCourseTitleET.getText().toString().trim();
                String startDate = addCourseStartDateET.getText().toString().trim();
                String endDate =   addCourseEndDateET.getText().toString().trim();
                String status = statusSpinner.getSelectedItem().toString();
                
                CourseInstructorEntity courseInstructorEntity
                        = (CourseInstructorEntity) instructorSpinner.getSelectedItem();

                int instructorID = courseInstructorEntity.getId();

                if(isCourseTitleEmpty(courseTitle) == false && courseDatesIsValid(startDate, endDate)) {

                    if(courseStartAndEndDatesValid(startDate, endDate, termId)) {

                        if (modeID == 1) {

                            Toast.makeText(AddCourseActivity.this,
                                    "Saved Course \n" + "Course Title: "
                                            + courseTitle + "\nStart Date:"
                                            + startDate + "\nCourse End Date: "
                                            + endDate + "\nStatus"
                                            + status, Toast.LENGTH_SHORT).show();

                            courseViewModel.insertCourse(new CourseEntity(courseTitle
                                    , startDate, endDate, status, instructorID, termId));

                            // Reset the fields
                            //addCourseTitleET.setText("");
                           // addCourseStartDateET.setText("");
                            //addCourseEndDateET.setText("");

                        } // end add course to the database
                        else if (modeID == 2) // Update a course in the database.
                        {

                            CourseEntity updatedCourse = new CourseEntity(courseTitle
                                    , startDate, endDate, status, instructorID, termId);

                            // Set the course's ID.
                            updatedCourse.setCourseId(courseID);

                            // Update the course in the database.
                            courseViewModel.updateCourse(updatedCourse);

                            Toast.makeText(AddCourseActivity.this,
                                    "Updated Course \n" + "Course Title: "
                                            + courseTitle + "\nStart Date:"
                                            + startDate + "\nCourse End Date: "
                                            + endDate + "\nStatus"
                                            + "Click the back arrow to take you back"
                                            + " to the previous screen.  Or to add a "
                                            + "notification click the calendar icon at"
                                            + " the bottom of the screen.  "
                                            + status, Toast.LENGTH_LONG).show();

                        }
                    }
                } // end is valid.

            } // end on Click.

        });

        addCourseNotificationFAB = findViewById(R.id.add_course_notification_fab);
        addCourseNotificationFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String courseTitle =  addCourseTitleET.getText().toString();

                Intent notificationIntent = new Intent(AddCourseActivity.this, AddNotificationActivity.class);
                notificationIntent.putExtra(AddNotificationActivity.EXTRA_NOTIFICATION_INFO_TEXT, courseTitle);
                notificationIntent.putExtra(AddNotificationActivity.EXTRA_NOTIFICATION_TYPE_TO_ADD, "course");
                startActivity(notificationIntent);
            }
        });

    }// end onCreate

    /** Method to check that valid dates has been entered.  This method
     *  will check to see if the user has entered valid dates into the field.
     *  If the field is left blank an error will appear.
     *
     */
    private boolean courseStartAndEndDatesValid(String startDate, String endDate, int termId) {

        boolean isValidDate = true;

        //Log.d("PHIL5", "courseStartAndEndDatesValid: (COURSE DATES) "+ startDate + " END DATE: " + endDate);  // REMOVE **********************************

        TermEntity termDates = courseViewModel.getTermInfo(termId);

        //Log.d("PHIL5", "Contents of TERM DATES: " + termDates.toString()); // REMOVE ***********************************************************

        String termStartDate = termDates.getStartDate();
        String termEndDate = termDates.getEndDate();

        if(!dateChecker.isDateBetween(startDate, termStartDate, termEndDate)){

           // Log.d("PHIL5",  "Start Date is NOT between!" + termStartDate  + " " + termEndDate); // REMOVE****************************************

            isValidDate = false;

        }

        if(!dateChecker.isDateBetween(endDate, termStartDate,termEndDate)){

            //Log.d("PHIL5",  "End Date is NOT between!" + termStartDate  + " " + termEndDate); // REMOVE ************************************************

            isValidDate = false;

        }

        if(isValidDate == false){

            AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
            String errorString = getString(R.string.invalid_date_range, termStartDate, termEndDate);
            alertDialogBuilder.setMessage(errorString);

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(AddCourseActivity.this, "Hope this helps."
                            , Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        return isValidDate;

    } // end courseStartAndEndDatesValid.

    /** Method to check that valid dates has been entered.  This method
     *  will check to see if the user has entered valid dates into the field.
     *  If the field is left blank an error will appear.
     *
     */
    private boolean courseDatesIsValid(String startDate, String endDate) {

        boolean isValidDate = true;

        if(!dateChecker.isValidDateString(startDate)){

            errorCourseStartDate.setError(getResources().getString(R.string.invalid_format_field));

            Toast.makeText(AddCourseActivity.this
                    , getResources().getString(R.string.invalid_format_field)
                    , Toast.LENGTH_LONG).show();

            isValidDate =  false;

        }

        if(!dateChecker.isValidDateString(endDate))
        {

            errorCourseEndDate.setError(getResources().getString(R.string.invalid_format_field));

            Toast.makeText(AddCourseActivity.this
                    , getResources().getString(R.string.invalid_format_field)
                    , Toast.LENGTH_LONG).show();

            isValidDate =  false;

        }

        return isValidDate;


    } // end courseDatesIsValid

    /** Method to determine if the course title is empty.  Method that will
     *  determine if the courses's title is empty.  Will display a message
     *  to the user as a Toast as well as a text prompt around the edit field.
     *
     * @param courseTitle
     * @return
     *
     */
    private boolean isCourseTitleEmpty(String courseTitle) {

        if(TextUtils.isEmpty(courseTitle)){

            errorCourseTitle.setError(getResources()
                    .getString(R.string.course_title_error));

            Toast.makeText(AddCourseActivity.this
                    , getResources().getString(R.string.course_error_empty_field)
                    , Toast.LENGTH_LONG).show();

            return true;

        }
        return false;

    }

    /** Method to create the options menu.  This method will create the menu
     *  that will have two options 1) Add a new Course 2) Get help with the
     *  current screen.
     *
     * @param menu
     * @return
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        // Inflate the menu with help and add.
        getMenuInflater().inflate(R.menu.menu_add_course, menu);
        return super.onCreateOptionsMenu(menu);

    } // end onCreateOptionsMenu.

    /** Method to process the toolbar selections. This method will handle the
     *  user selecting options from the toolbar menu. Options it will handle
     *  are: 1) add a course 2) display a help menu.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Process the selection of either add or help.
        switch(item.getItemId()){
            /*
            case R.id.add_term_from_course:

                Intent termsIntent = new Intent(AddCourseActivity.this, AddUpdateTermActivity.class);
                startActivity(termsIntent);

                return true;

             */
            case R.id.help_course_from_add:

                AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.help_text_course_add);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddCourseActivity.this, "Hope this helps."
                                , Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        } // end switch.

    }// endOptionsItemSelected.

    /** Method to update the start date.  This method will update the start
     *  date text field. It will format the date in US locale
     *
     */
    private void updateStartDate(){

        String startDateFormat ="MM/dd/yy";
        SimpleDateFormat sdf  = new SimpleDateFormat(startDateFormat, Locale.US);

        // Update the start date EditText.
        addCourseStartDateET.setText(sdf.format(startCourseCalendar.getTime()));

    } // end updateStartDate.

    /** Method to update the end date.  This method will update the end
     *  date text field. It will format the date in US locale
     *
     */
    private void updateEndDate(){

        String startDateFormat ="MM/dd/yy";
        SimpleDateFormat sdf  = new SimpleDateFormat(startDateFormat, Locale.US);

        // Update the start date EditText.
        addCourseEndDateET.setText(sdf.format(endCourseCalendar.getTime()));

    } // end updateStartDate.

    /** Method to find the position of the instructor in the list.  This
     *  method will find the position of the instructor in the list of
     *  instructors.  Will be used to set the default value of the spinner for
     *  use in the update mode of the screen.
     *
     * @param listOfInstructors
     * @param instructorID
     * @return
     *
     */
    public int getPositionOfInstructor(List<CourseInstructorEntity> listOfInstructors, int instructorID){

        int position = 0;
        boolean foundID = true;

        for( CourseInstructorEntity ci : listOfInstructors){

            if(ci.getId() == instructorID) {
                return position;
            }
            position++;
        }

        return position;
    }

} // end class