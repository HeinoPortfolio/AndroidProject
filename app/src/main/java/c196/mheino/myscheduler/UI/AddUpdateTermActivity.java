package c196.mheino.myscheduler.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import c196.mheino.myscheduler.Entity.TermEntity;
import c196.mheino.myscheduler.MyUtils.DateUtil;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.TermViewModel;

public class AddUpdateTermActivity extends AppCompatActivity {

    // Keys for the class
    public static final String EXTRA_TERM_ID = "c196.mheino.myscheduler.EXTRA_ID_TERM";
    public static final String EXTRA_TITLE_TERM = "c196.mheino.myscheduler.EXTRA_TITLE_TERM";
    public static final String EXTRA_START_DATE_TERM = "c196.mheino.myscheduler.EXTRA_START_DATE_TERM";
    public static final String EXTRA_END_DATE_TERM = "c196.mheino.myscheduler.EXTRA_START_DATE+TERM";

    private DateUtil dateChecker = new DateUtil();

    TermViewModel termViewModel;

    Calendar startTermCalendar= Calendar.getInstance();
    Calendar endTermCalendar= Calendar.getInstance();

    // View components // added private
    private EditText addTermET;
    private EditText addStartDate;
    private EditText addEndDate;
    private TextInputLayout errorTermTitle;
    private TextInputLayout errorTermStartDate;
    private TextInputLayout errorTermEndDate;
    private FloatingActionButton addTermFAB;

    //Date listeners.
    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;

    /** Method to create the Add Term Activity.  This method will create the
     * Add Term Activity for the application. It will layout out the toolbar as
     * well.
     *
     *
     * @param savedInstanceState
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        //Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.terms_add_tool_bar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        // Get the handles to the view components.
        addTermET = findViewById(R.id.add_term_edit_text);
        addStartDate = findViewById(R.id.start_date_edit_text);
        addEndDate = findViewById(R.id.end_date_edit_text);
        errorTermTitle = findViewById(R.id.error_term_edit_title);
        errorTermStartDate = findViewById(R.id.error_term_start_date_error);
        errorTermEndDate = findViewById(R.id.error_term_end_date_error);

        // Get the intent and the available Extras.
        Intent updateIntent = getIntent();

        // Check to see if the intent has contents.
        if(updateIntent.hasExtra(EXTRA_TERM_ID)){

            //Change the title of the activity.
            setTitle(getString(R.string.edit_term));

            String title = updateIntent.getStringExtra(EXTRA_TITLE_TERM);
            String startDate = updateIntent.getStringExtra(EXTRA_START_DATE_TERM);
            String endDate = updateIntent.getStringExtra(EXTRA_END_DATE_TERM);

            // Set the view components
            addTermET.setText(updateIntent.getStringExtra(EXTRA_TITLE_TERM));
            addStartDate.setText(updateIntent.getStringExtra(EXTRA_START_DATE_TERM));
            addEndDate.setText(updateIntent.getStringExtra(EXTRA_END_DATE_TERM));

        }


        // Set the listeners for the edit text fields.***************************
        //***********************************************************************
        addStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create the DatePicker for the start date.
                new DatePickerDialog(AddUpdateTermActivity.this
                        , startDateListener,startTermCalendar.get(Calendar.YEAR)
                        , startTermCalendar.get(Calendar.MONTH)
                        , startTermCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(AddUpdateTermActivity.this
                        , endDateListener,endTermCalendar.get(Calendar.YEAR)
                        , endTermCalendar.get(Calendar.MONTH)
                        , endTermCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // term termCalendar

        //Create the DatePicker dialogs.***************************************
        //*********************************************************************
        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                startTermCalendar.set(Calendar.YEAR, year);
                startTermCalendar.set(Calendar.MONTH,month);
                startTermCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateStartDate();

            } // end onDateSet
        };

        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                endTermCalendar.set(Calendar.YEAR, year);
                endTermCalendar.set(Calendar.MONTH,month);
                endTermCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateEndDate();

            }
        };

        // Set up the FAB with listener and action.****************************
        addTermFAB = findViewById(R.id.add_term_fab);
        addTermFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String termTitle = addTermET.getText().toString().trim();
                String startDate  = addStartDate.getText().toString().trim();
                String endDate = addEndDate.getText().toString().trim();

                if(termTitleIsEmpty(termTitle) == false && termDatesIsValid(startDate, endDate))
                {
                    termViewModel = ViewModelProviders.of(AddUpdateTermActivity.this)
                            .get(TermViewModel.class);

                    Intent addUpdate = getIntent();

                    if (!addUpdate.hasExtra(EXTRA_TERM_ID))
                    {
                        Toast.makeText(AddUpdateTermActivity.this,
                                "Saved New \n Term Title:" + termTitle
                                        + "\n Start Date:" + startDate + "\n End Date: " + endDate
                                , Toast.LENGTH_SHORT).show();

                        // Insert into the database.
                        termViewModel.insert(new TermEntity(termTitle, startDate, endDate));

                        // GO back to the parent screen.
                        Intent termIntent = new Intent(AddUpdateTermActivity.this
                                , TermsActivity.class);
                    }
                    else {

                        int id = addUpdate.getIntExtra(EXTRA_TERM_ID, -1);

                        // Update the Term Information.
                        TermEntity updatedTerm =
                                new TermEntity(termTitle, startDate, endDate);

                        //Set the Term's Id.
                        updatedTerm.setTermID(id);
                        termViewModel.update(updatedTerm);

                        Toast.makeText(AddUpdateTermActivity.this,
                                "Term Updated: \n Term Title:" + termTitle
                                        + "\n Start Date:" + startDate
                                        + "\n End Date: " + endDate
                                , Toast.LENGTH_SHORT).show();

                    } // else

                    // GO back to the parent screen.
                    Intent termIntent = new Intent(AddUpdateTermActivity.this
                            , TermsActivity.class);

                    startActivity(termIntent);

                } // end validation check.

            } // onClick

        });

    }

    /** Method to check that valid dates has been entered.  This method
     *  will check to see if the user has entered  valid dates into the field.
     *  If the field is left blank an error will appear.
     *
     */
    private boolean termDatesIsValid(String startDate, String endDate) {

        boolean isValidDate = true;

        if(!dateChecker.isValidDateString(startDate)){

            errorTermStartDate.setError(getResources().getString(R.string.invalid_format_field));

            Toast.makeText(AddUpdateTermActivity.this
                    , getResources().getString(R.string.invalid_format_field)
                    , Toast.LENGTH_LONG).show();

            isValidDate =  false;

        }

        if(!dateChecker.isValidDateString(endDate))
        {

            errorTermEndDate.setError(getResources().getString(R.string.invalid_format_field));

            Toast.makeText(AddUpdateTermActivity.this
                    , getResources().getString(R.string.invalid_format_field)
                    , Toast.LENGTH_LONG).show();

            isValidDate =  false;

        }

        return  isValidDate;


    }

    /** Method to determine if the term title is empty.  Method that will
     *  determine if the term's title is empty.  Will display a message to the
     *  user as a Toast as well as a text prompt around the edit field.
     *
     * @param termTitle
     * @return
     */
    private boolean termTitleIsEmpty(String termTitle) {

        // boolean isTitleEmpty = false;

        if(TextUtils.isEmpty(termTitle)){

            errorTermTitle.setError(getResources()
                    .getString(R.string.term_title_error));

            Toast.makeText(AddUpdateTermActivity.this
                    , getResources().getString(R.string.term_error_empty_field)
                    , Toast.LENGTH_LONG).show();

            return true;

        }

        // return isTitleEmpty;
        return false;
    }

    /** Method to create the options menu.  This method will create the menu
     *  that will have one options 1)  Get help with the
     *  current screen.
     *
     * @param menu
     * @return
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        // Inflate the menu with help and add.
        getMenuInflater().inflate(R.menu.menu_add_term, menu);
        return super.onCreateOptionsMenu(menu);

    } // end onCreateOptionsMenu

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

            case R.id.help_add_term:
                Toast.makeText(AddUpdateTermActivity.this, "Help chosen"
                        , Toast.LENGTH_SHORT).show();
                AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.help_text_term_add);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddUpdateTermActivity.this, "Hope this helps."
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
        addStartDate.setText(sdf.format(startTermCalendar.getTime()));

    } // end updateStartDate.

    /** Method to update the stend date.  This method will update the end
     *
     *  date text field. It will format the date in US locale
     *
     */
    private void updateEndDate(){

        String startDateFormat ="MM/dd/yy";
        SimpleDateFormat sdf  = new SimpleDateFormat(startDateFormat, Locale.US);

        // Update the start date EditText.
        addEndDate.setText(sdf.format(endTermCalendar.getTime()));

    } // end updateStartDate.

}// end class.