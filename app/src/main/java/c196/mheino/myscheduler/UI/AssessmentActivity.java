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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import c196.mheino.myscheduler.Adapters.AssessmentAdapter;
import c196.mheino.myscheduler.Entity.AssessmentEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.AssessmentViewModel;

/** This is the Assessment activity class.  This class will be the main entry point for
 *  all assessment activities in the application.  You will be navigate to all other
 *  activities activities from this screen.
 *
 * @author Matthew Heino
 *
 */
public class AssessmentActivity extends AppCompatActivity {

    private AssessmentAdapter assessmentAdapter;
    private RecyclerView assessmentRecyclerView;

    // View Model
    private AssessmentViewModel assessmentViewModel;

    /** Method to create the Assessment Activity.  This method will create the Assessment
     *  Activity for the application. It will layout out the toolbar as well.
     *
     *
     * @param savedInstanceState
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        //Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.assessment_tool_bar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        // Create the recycler.
        assessmentRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_assessments);
        assessmentRecyclerView.setLayoutManager(new GridLayoutManager(AssessmentActivity.this, 2));
        assessmentRecyclerView.setHasFixedSize(true);

        // Create the view model and set the adapter.
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
       // assessmentAdapter = new AssessmentAdapter(getApplication(), assessmentViewModel);
        assessmentAdapter = new AssessmentAdapter(AssessmentActivity.this, assessmentViewModel); // REMOVE IF IT DOES NOT WORK
        assessmentRecyclerView.setAdapter(assessmentAdapter);

        // Setup the list of assessments.
        assessmentViewModel.getAllAssessments().observe(this,
                new Observer<List<AssessmentEntity>>() {
                    @Override
                    public void onChanged(List<AssessmentEntity> assessmentEntities) {
                        assessmentAdapter.setAssessments(assessmentEntities);
                    }
                });

    } // end onCreate

    /** Method to create the options menu.  This method will create the menu
     *  that will have two options 1) Add a new assessment 2) Get help with the
     *  current screen.
     *
     * @param menu
     * @return
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        // Inflate the menu with help and add.
        getMenuInflater().inflate(R.menu.menu_assessments, menu);
        return super.onCreateOptionsMenu(menu);

    } // end onCreateOptionsMenu.

    /** Method to process the toolbar selections. This method will handle the
     *  user selecting options from the toolbar menu. Options it will handle
     *  are: 1) add an assessment 2) display a help menu.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Process the selection of either add or help.
        switch(item.getItemId()){
            case R.id.add_assessment:
                Toast.makeText(AssessmentActivity.this, "Add Assessment"
                        , Toast.LENGTH_SHORT).show();
                return true;
            case R.id.help_assessment:

                AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.help_text_assessments);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AssessmentActivity.this, "Hope this helps."
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

}// end class.