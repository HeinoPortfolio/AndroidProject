package c196.mheino.myscheduler.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import c196.mheino.myscheduler.Adapters.TermDetailsAdapter;
import c196.mheino.myscheduler.Entity.CourseEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.TermDetailsViewModel;

/** This is the Term Details activity class.  This class will be used to show
 *  details about the terms all note activities in the application.  You will
 *  be navigate to all othernote activities from this screen.
 *
 * @author Matthew Heino
 *
 */

public class TermDetailsActivity extends AppCompatActivity {


    //Keys.
    public static final String EXTRA_DETAILS_TERM_ID = "c196.mheino.myscheduler.EXTRA_DETAILS_ID_TERM";
    public static final String EXTRA_DETAILS_TITLE_TERM = "c196.mheino.myscheduler.EXTRA_DETAILS_TITLE_TERM";
    public static final String EXTRA_DETAILS_START_DATE_TERM = "c196.mheino.myscheduler.EXTRA_DETAILS_START_DATE_TERM";
    public static final String EXTRA_DETAILS_END_DATE_TERM = "c196.mheino.myscheduler.EXTRA_START_DATE+TERM";

    private int id;


    // Components of the View.
    TextView titleTV;
    TextView startDateTV;
    TextView endDateTV;

    private TermDetailsAdapter termsDetailAdapter;
    private RecyclerView termsDetailRecyclerView;

    // View Model
    private TermDetailsViewModel termDetailsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);


        //Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.terms_details_tool_bar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        termsDetailRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_terms_details);
        termsDetailRecyclerView.setLayoutManager(new LinearLayoutManager(TermDetailsActivity.this));
        termsDetailRecyclerView.setHasFixedSize(true);

        // Create the View Model
        termDetailsViewModel =  ViewModelProviders.of(this).get(TermDetailsViewModel.class);
        termsDetailAdapter = new TermDetailsAdapter(getApplication(), termDetailsViewModel);
        termsDetailRecyclerView.setAdapter(termsDetailAdapter); // set the adapter

        // Get the view components.
        titleTV = findViewById(R.id.term_title_details_tv);
        startDateTV =  findViewById(R.id.term_details_start_date_actual_tv);
        endDateTV =  findViewById(R.id.term_details_end_date_actual_tv);

        // Get the intent and the available Extras.
        Intent detailsIntent = getIntent();

        if (detailsIntent.hasExtra(EXTRA_DETAILS_TERM_ID)) {

            id = detailsIntent.getIntExtra(EXTRA_DETAILS_TERM_ID, -1);

            // Extract the information.
            String title = detailsIntent.getStringExtra(EXTRA_DETAILS_TITLE_TERM);
            String startDate = detailsIntent.getStringExtra(EXTRA_DETAILS_START_DATE_TERM);
            String endDate = detailsIntent.getStringExtra(EXTRA_DETAILS_END_DATE_TERM);

            // Set the TextView's text.
            titleTV.setText(title);
            startDateTV.setText(startDate);
            endDateTV.setText(endDate);

            termDetailsViewModel.getCoursesByTermID(id).observe(this, new Observer<List<CourseEntity>>() {
                @Override
                public void onChanged(List<CourseEntity> courseEntities) {
                    termsDetailAdapter.setCourses(courseEntities);
                }
            });

        } // end if
        else if(savedInstanceState == null)
        {
            Log.d("PHIL4", "onCreate: Terms Details Activity");
        }

    } // end onCreate



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

        // Save the state.
        Intent detailsIntent = getIntent();
        savedInstanceState.putInt( "term_ID", 1);

        super.onSaveInstanceState(savedInstanceState);
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
        getMenuInflater().inflate(R.menu.menu_help, menu);
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

            case R.id.help_term_details:

                AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.help_text_term_details);

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TermDetailsActivity.this, "Hope this helps."
                                , Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;

            case R.id.add_course_term_details:

                // Launch course add activity
                Intent courseAddIntent = new Intent(TermDetailsActivity.this, AddCourseActivity.class);

                courseAddIntent.putExtra(AddCourseActivity.EXTRA_MODE_ADD_COURSE, 1);
                courseAddIntent.putExtra(AddCourseActivity.EXTRA_TERM_ID_ADD_COURSE, id);

                startActivity(courseAddIntent);

                return  true;

            default:
                return super.onOptionsItemSelected(item);

        } // end switch.

    }// endOptionsItemSelected.

} // end class