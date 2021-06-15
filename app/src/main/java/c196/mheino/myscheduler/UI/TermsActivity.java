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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import c196.mheino.myscheduler.Adapters.TermAdapter;
import c196.mheino.myscheduler.Entity.TermEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.TermViewModel;


/** This is the Terms activity class.  This class will be the main entry point for
 *  all term activities in the application.  You will be navigate to all other
 *  term activities from this screen.
 *
 * @author Matthew Heino
 *
 */
public class TermsActivity extends AppCompatActivity {

    private TermAdapter termAdapter;
    private RecyclerView termRecyclerView;

    // View Model.
    private TermViewModel termViewModel;


    /** Method to create the Terms Activity.  This method will create the Terms
     *  Activity for the application. It will layout out the toolbar as well.
     *
     *
     * @param savedInstanceState
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        //Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.terms_tool_bar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        termRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_terms);
        termRecyclerView.setLayoutManager(new LinearLayoutManager(TermsActivity.this));
        termRecyclerView.setHasFixedSize(true);


        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termAdapter = new TermAdapter(getApplication(), termViewModel);
        termRecyclerView.setAdapter(termAdapter);

        termViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>()
        {
            @Override
            public void onChanged(List<TermEntity> terms) {

                termAdapter.setTerms(terms);

            }
        });

    } // end onCreate

    /** Method to create the options menu.  This method will create the menu
     *  that will have two options 1) Add a new term 2) Get help with the
     *  current screen.
     *
     * @param menu
     * @return
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        // Inflate the menu with help and add.
        getMenuInflater().inflate(R.menu.menu_terms_avail, menu);
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

            case R.id.add_term:

                Intent termsIntent = new Intent(TermsActivity.this, AddUpdateTermActivity.class);
                startActivity(termsIntent);

                return true;
            case R.id.help_term:

                AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.help_text_terms);

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TermsActivity.this, "Hope this helps."
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

} // end class

