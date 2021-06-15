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
import java.util.Locale;

import c196.mheino.myscheduler.Entity.AssessmentEntity;
import c196.mheino.myscheduler.Entity.CourseEntity;
import c196.mheino.myscheduler.Entity.TermEntity;
import c196.mheino.myscheduler.MyUtils.DateUtil;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.AssessmentViewModel;


/** This is the  Add Assessment activity class.  This class will be the main entry point for
 *  adding assessment activities in the application.  You will be to add an assessment
 *  from this screen.
 *
 * @author Matthew Heino
 *
 */
public class AddAssessmentActivity extends AppCompatActivity {

    // Keys
    public static final String EXTRA_ASSESSMENT_COURSE_ID = "c196.mheino.myscheduler.EXTRA_ASSESSMENT_COURSE_ID";
    public static final String EXTRA_ASSESSMENT_ID = "c196.mheino.myscheduler.EXTRA_ASSESSMENT_ID";
    public static final String EXTRA_ASSESSMENT_NAME = "c196.mheino.myscheduler.EXTRA_ASSESSMENT_NAME";
    public static final String EXTRA_ASSESSMENT_START_DATE = "c196.mheino.myscheduler.EXTRA_ASSESSMENT_START_DATE";
    public static final String EXTRA_ASSESSMENT_END_DATE = "c196.mheino.myscheduler.EXTRA_ASSESSMENT_END_DATE";
    public static final String EXTRA_ASSESSMENT_TYPE = "c196.mheino.myscheduler.EXTRA_ASSESSMENT_TYPE";
    public static final String EXTRA_ASSESSMENT_MODE = "c196.mheino.myscheduler.EXTRA_ASSESSMENT_MODE";

    // Date picker for the end date of the Assessment.
    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;
    Calendar startAssessmentCalendar = Calendar.getInstance();
    Calendar endAssessmentCalendar= Calendar.getInstance();

    // Assessment Variables
    private int courseID;                               // Course ID for insertion into the database.
    private int assessmentMode;
    private int assessmentID;
    private String assessmentName;
    private String assessmentStartDate;
    private String assessmentEndDate;
    private String assessmentType;

    // View Model
    private AssessmentViewModel assessmentViewModel;

    private DateUtil dateChecker = new DateUtil();

    // View Components
    private EditText assessmentTitleET;
    private EditText addStartDateET;
    private EditText addEndDateET;
    private Spinner typeSpinner;
    private TextInputLayout errorAssessmentTitle;
    private TextInputLayout errorAssessmentStartDate;
    private TextInputLayout errorAssessmentEndDate;
    private FloatingActionButton addAssessmentFAB;
    private FloatingActionButton addAssessmentNotificationFAB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        //Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_assessment_tool_bar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        //Get handles to the view components.
        assessmentTitleET = findViewById(R.id.add_assessment_title_details_tv);
        addStartDateET = findViewById(R.id.add_assessment_start_date_actual_tv);
        addEndDateET = findViewById(R.id.add_assessment_end_date_actual_tv);
        errorAssessmentTitle = findViewById(R.id.error_assessment_edit_title);
        errorAssessmentStartDate = findViewById(R.id.error_assessment_start_date);
        errorAssessmentEndDate = findViewById(R.id.error_assessment_end_date);
        typeSpinner = findViewById(R.id.type_spinner);

        // Create the Spinner for type of assessment.
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(AddAssessmentActivity.this
                , R.array.type_array, android.R.layout.simple_list_item_1);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        typeSpinner.setAdapter(typeAdapter);

        // Create the View model
        assessmentViewModel= ViewModelProviders.of(this).get(AssessmentViewModel.class);

        // Get the intent and the available Extras.
        Intent assessmentIntent = getIntent();

        if(assessmentIntent.hasExtra(EXTRA_ASSESSMENT_COURSE_ID)){

            this.courseID = assessmentIntent.getIntExtra(EXTRA_ASSESSMENT_COURSE_ID, -1);
            this.assessmentMode = assessmentIntent.getIntExtra(EXTRA_ASSESSMENT_MODE, -1);

            if(assessmentMode == 1){this.setTitle(R.string.add_an_assessment);}
            else if(assessmentMode == 2){this.setTitle(R.string.update_an_assessment);}
        }

        if(assessmentIntent.hasExtra(EXTRA_ASSESSMENT_ID)){

            // Get the assessment's information.
            assessmentID = assessmentIntent.getIntExtra(EXTRA_ASSESSMENT_ID, -1);
            assessmentName = assessmentIntent.getStringExtra(EXTRA_ASSESSMENT_NAME);
            assessmentStartDate = assessmentIntent.getStringExtra(EXTRA_ASSESSMENT_START_DATE);
            assessmentEndDate = assessmentIntent.getStringExtra(EXTRA_ASSESSMENT_END_DATE);
            assessmentType = assessmentIntent.getStringExtra(EXTRA_ASSESSMENT_TYPE);

            // Set the values for the Edit fields.
            assessmentTitleET.setText(assessmentName);
            addStartDateET.setText(assessmentStartDate);
            addEndDateET.setText(assessmentEndDate);

            // Set the default Type Spinner value.
            int typePosition = typeAdapter.getPosition(assessmentType);

            // Get and set the default assessment type for the spinner.
            typeSpinner.setSelection(typePosition);

        }

        // Add listeners for the edit text fields for the dates.***************
        addStartDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the DatePicker for the start date.
                new DatePickerDialog(AddAssessmentActivity.this
                        , startDateListener
                        , startAssessmentCalendar.get(Calendar.YEAR)
                        , startAssessmentCalendar.get(Calendar.MONTH)
                        , startAssessmentCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startAssessmentCalendar.set(Calendar.YEAR, year);
                startAssessmentCalendar.set(Calendar.MONTH, month);
                startAssessmentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateStartDate();
            }
        };


        addEndDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create the DatePicker for the start date.
                new DatePickerDialog(AddAssessmentActivity.this
                        , endDateListener
                        , endAssessmentCalendar.get(Calendar.YEAR)
                        , endAssessmentCalendar.get(Calendar.MONTH)
                        , endAssessmentCalendar.get(Calendar.DAY_OF_MONTH)).show();

            } // end on click.
        });

        // Create the date pickers.
        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                endAssessmentCalendar.set(Calendar.YEAR, year);
                endAssessmentCalendar.set(Calendar.MONTH, month);
                endAssessmentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateEndDate();

            } // end onDateSet
        };

        // Set up the FAB with the listener and action
        addAssessmentFAB = findViewById(R.id.add_assessment_fab);
        addAssessmentFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Extract the information.
                String assessmentTitle = assessmentTitleET.getText().toString().trim();
                String startDate = addStartDateET.getText().toString().trim();
                String endDate = addEndDateET.getText().toString().trim();
                String assessmentType = typeSpinner.getSelectedItem().toString().trim();

                AssessmentEntity assessmentEntity = new AssessmentEntity(assessmentTitle
                        , startDate, endDate, assessmentType, courseID);


                if(isAssessmentTitleEmpty(assessmentTitle) == false && assessmentDatesIsValid(startDate, endDate)) {

                    if (assessmentStartAndEndDatesValid(startDate, endDate, courseID)) {
                        if (assessmentMode == 1) {
                            Toast.makeText(AddAssessmentActivity.this
                                    , "Saved Assessment \n" + "Assessment Title: "
                                            + assessmentTitle + "\n Assessment Start Date: "
                                            + startDate + "\nAssessment End Date: "
                                            + endDate + "Assessment Type:"
                                            + assessmentType, Toast.LENGTH_LONG).show();

                            // Insert the new assessment into the database.
                            assessmentViewModel.insertAssessment(assessmentEntity);

                            // Set the values for the Edit fields.
                            //assessmentTitleET.setText("");
                            //addStartDateET.setText("");
                            //addEndDateET.setText("");

                        } else if (assessmentMode == 2) {

                            assessmentEntity.setAssessmentId(assessmentID);
                            assessmentViewModel.updateAssessment(assessmentEntity);

                            Toast.makeText(AddAssessmentActivity.this
                                    , "Assessment Updated\n" + "Assessment Title: "
                                            + assessmentTitle + "\nAssessment End Date: "
                                            + endDate + "Assessment Type:"
                                            + assessmentType, Toast.LENGTH_LONG).show();

                        }
                    }
                }
            } // end onClick.
        });

        addAssessmentNotificationFAB = findViewById(R.id.add_assessment_notification_fab);
        addAssessmentNotificationFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addNotificationIntent = new Intent(AddAssessmentActivity.this
                        , AddNotificationActivity.class);

                addNotificationIntent
                        .putExtra(AddNotificationActivity.EXTRA_NOTIFICATION_INFO_TEXT
                                , assessmentTitleET.getText().toString().trim());
                addNotificationIntent
                        .putExtra(AddNotificationActivity.EXTRA_NOTIFICATION_TYPE_TO_ADD
                                , "assessment");

                startActivity(addNotificationIntent);

            }
        });

    } // end onCreate

    private boolean assessmentStartAndEndDatesValid(String startDate, String endDate, int courseID) {


        boolean isValidDate = true;

        Log.d("PHIL5", "courseStartAndEndDatesValid: (Assessment DATES) "+ startDate + " END DATE: " + endDate);  // REMOVE **********************************

        CourseEntity courseDates = assessmentViewModel.getCourseInfo(courseID);

        Log.d("PHIL5", "Contents of TERM DATES: " + courseDates.toString()); // REMOVE ***********************************************************

        String courseStartDate = courseDates.getStartDate();
        String courseEndDate = courseDates.getEndDate();

        if(!dateChecker.isDateBetween(startDate, courseStartDate, courseEndDate)){

            Log.d("PHIL5",  "Start Date is NOT between!" + courseStartDate  + " " + courseEndDate); // REMOVE****************************************

            isValidDate = false;

        }

        if(!dateChecker.isDateBetween(endDate, courseStartDate,courseEndDate)){

            Log.d("PHIL5",  "End Date is NOT between!" + courseStartDate  + " " + courseEndDate); // REMOVE ************************************************

            isValidDate = false;

        }

        if(isValidDate == false){

            AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
            String errorString = getString(R.string.invalid_date_range, courseStartDate, courseEndDate);
            alertDialogBuilder.setMessage(errorString);

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(AddAssessmentActivity.this, "Hope this helps."
                            , Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        return isValidDate;


    }

    /** Method to check that valid dates has been entered.  This method
     *  will check to see if the user has entered valid dates into the field.
     *  If the field is left blank an error will appear.
     *
     */
    private boolean assessmentDatesIsValid(String startDate, String endDate) {

        boolean isValidDate = true;

        Log.d("PHIL5", "IN TERM DATE IS VALID with Start date: "
                + startDate +" and ENd Date: " + endDate ); // REMOVE***************************************************************************************

        if(!dateChecker.isValidDateString(startDate)){

            errorAssessmentStartDate.setError(getResources().getString(R.string.invalid_format_field));

            Toast.makeText(AddAssessmentActivity.this
                    , getResources().getString(R.string.invalid_format_field)
                    , Toast.LENGTH_LONG).show();

            isValidDate =  false;

        }

        if(!dateChecker.isValidDateString(endDate))
        {

            errorAssessmentEndDate.setError(getResources().getString(R.string.invalid_format_field));

            Toast.makeText(AddAssessmentActivity.this
                    , getResources().getString(R.string.invalid_format_field)
                    , Toast.LENGTH_LONG).show();

            isValidDate =  false;

        }

        return isValidDate;

    }

    /** Method to determine if the assessment title is empty.  Method that will
     *  determine if the assessment's title is empty.  Will display a message
     *  to the user as a Toast as well as a text prompt around the edit field.
     *
     * @param assessmentTitle
     * @return
     *
     */
    private boolean isAssessmentTitleEmpty(String assessmentTitle) {

        if(TextUtils.isEmpty(assessmentTitle)){

            errorAssessmentTitle.setError(getResources()
                    .getString(R.string.assessment_title_error));

            Toast.makeText(AddAssessmentActivity.this
                    , getResources().getString(R.string.assessment_error_empty_field)
                    , Toast.LENGTH_LONG).show();

            return true;

        }
        return false;

    }

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
        getMenuInflater().inflate(R.menu.menu_add_assessment, menu);
        return super.onCreateOptionsMenu(menu);

    } // end onCreateOptionsMenu.

    /** Method to process the toolbar selections. This method will handle the
     *  user selecting options from the toolbar menu. Options it will handle
     *  are: 1)  display a help menu.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Process the selection of either add or help.
        switch(item.getItemId()){

            case R.id.add_assessment_help:

                Toast.makeText(AddAssessmentActivity.this, "Help chosen from add assessment"
                        , Toast.LENGTH_SHORT).show();

                AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.help_text_add_assessments);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddAssessmentActivity.this, "Hope this helps."
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

    /** Method to update the end date.  This method will update the end
     *  date text field. It will format the date in US locale
     *
     */
    private void updateEndDate(){

        String startDateFormat ="MM/dd/yy";
        SimpleDateFormat sdf  = new SimpleDateFormat(startDateFormat, Locale.US);

        // Update the start date EditText.
        addEndDateET.setText(sdf.format(endAssessmentCalendar.getTime()));

    } // end updateStartDate.

    /** Method to update the start date text field.
     *
     */
    private void updateStartDate(){

        String startDateFormat ="MM/dd/yy";
        SimpleDateFormat sdf  = new SimpleDateFormat(startDateFormat, Locale.US);

        // Update the start date EditText.
        addStartDateET.setText(sdf.format(startAssessmentCalendar.getTime()));

    }


} // end class