package c196.mheino.myscheduler.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import c196.mheino.myscheduler.R;

/** This is main activity class.  This class will be the main entry point for
 *  the application.  You will be navigate to all other main activities from
 *  this screen. THe class will use a navigation drawer to make it easier to
 *  navigate
 *
 * @author Matthew Heino
 *
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener

{

    /* Instructions used on the main screen to aid the user. */
    String htmlInstructions ="<h2>How to use this Application?</h2><br>"  +
            "<p><b>Navigation Drawer</b><br> <br>To the top left is of the screen is an " +
            "icon that will open the navigation drawer. In this drawer you will find menu " +
            "choices for TERMS, COURSES and ASSESSMENTS. Click on these to bring up " +
            "the desired activity.</p> <br>  <p><b>Note:</b><br> I will try to provide a " +
            "HELP(?) option in the toolbar to aid with apps use.</p>";


    /** Method to create the Main Activity.  This method will create the Main
     *  Activity for the application. It will layout out the toolbar as well
     *  as set the action of the navigation drawer. It will add a listener for
     *  the drawer and the item selected.
     *
     * @param savedInstanceState
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
       setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer
                , toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);

        // Add drawer listener
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set the TextView
        TextView mainTextView = findViewById(R.id.main_text_view);
        mainTextView.setText(HtmlCompat.fromHtml(htmlInstructions, 0));

    } // end onCreate.

    /** Method to handle the choices from the navigation drawer.  This method
     *  will handle the choices made in the selection of the terms, courses,
     *  and assessments. This method will invoke the appropriate activity.
     *  Method will also close the drawer.
     *
     * @param item
     * @return
     *
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        /*Get the id of the selected item */
        int id = item.getItemId();

        // Process the selected item.
        switch(id)
        {
            case R.id.nav_terms:
                Intent termsIntent = new Intent(MainActivity.this, TermsActivity.class);
                startActivity(termsIntent);
                break;
            case R.id.nav_courses:
                Intent coursesIntent = new Intent(MainActivity.this, CoursesActivity.class);
                startActivity(coursesIntent);
                break;
            case R.id.nav_assessments:
                Intent assessmentIntent = new Intent(MainActivity.this, AssessmentActivity.class);
                startActivity(assessmentIntent);
                break;
            case R.id.close_app:
                Toast.makeText(MainActivity.this, "Good Bye!", Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
                break;
            case  R.id.nav_search_notes:
                Toast.makeText(MainActivity.this, "Search Notes", Toast.LENGTH_SHORT).show();
                Intent searchNotesIntent = new Intent(MainActivity.this, SearchNotesActivity.class);
                startActivity(searchNotesIntent);
                break;
            case R.id.nav_search_assessments:
                Toast.makeText(MainActivity.this, "Search Assessments", Toast.LENGTH_SHORT).show();
                Intent searchAssessmentsIntent = new Intent(MainActivity.this, SearchAssessmentsActivity.class);
                startActivity(searchAssessmentsIntent);
                break;
            default:
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                break;
        }// end switch.

        // Close the drawer
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;

    } // end onNavigationItemSelected

    /** Method to reset the drawer.  This method will reset the drawer when
     * the BACK button is pressed.
     *
     */
    @Override
    public void onBackPressed() {

        // Find the drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    } // end onBackPressed.

} // end MainActivity