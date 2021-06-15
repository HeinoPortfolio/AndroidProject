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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import c196.mheino.myscheduler.Adapters.CourseAdapter;
import c196.mheino.myscheduler.Entity.CourseEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.TermDetailsViewModel;


/** This is the Courses activity class.  This class will be the main entry point for
 *  all course activities in the application.  You will be navigate to all other
 *  course activities from this screen.
 *
 * @author Matthew Heino
 *
 */
public class CoursesActivity extends AppCompatActivity {


    //private CourseAdapter courseAdapter;
    private CourseAdapter coursesAdapter;
    private RecyclerView courseRecyclerView;


    private TermDetailsViewModel courseViewModel;

    /** Method to create the Courses Activity.  This method will create the Courses
     *  Activity for the application. It will layout out the toolbar as well.
     *
     *
     * @param savedInstanceState
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        //Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.courses_tool_bar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        // Create the recycler.
        courseRecyclerView = findViewById(R.id.recycler_view_courses);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(CoursesActivity.this));
        courseRecyclerView.setHasFixedSize(true);

        courseViewModel =  ViewModelProviders.of(this).get(TermDetailsViewModel.class);
        coursesAdapter = new CourseAdapter(getApplication(), courseViewModel);
        courseRecyclerView.setAdapter(coursesAdapter);

        courseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courseEntities) {
                coursesAdapter.setCourses(courseEntities);
            }
        });

    } // end onCreate.


    /** Method to create the options menu.  This method will create the menu
     *  that will have two options 1) Add a new course 2) Get help with the
     *  current screen.
     *
     * @param menu
     * @return
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        // Inflate the menu with help and add.
        getMenuInflater().inflate(R.menu.menu_courses_avail, menu);
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

            case R.id.help_course:

                AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.help_text_courses);

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CoursesActivity.this, "Hope this helps."
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

} // end CoursesActivity