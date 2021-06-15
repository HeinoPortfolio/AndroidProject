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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import c196.mheino.myscheduler.Adapters.SearchNoteAdapter;
import c196.mheino.myscheduler.Entity.CourseEntity;
import c196.mheino.myscheduler.Entity.NoteEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.NotesViewModel;
import c196.mheino.myscheduler.ViewModels.SearchNotesViewModel;

public class SearchNotesActivity extends AppCompatActivity {


    private Button selectCourseButton;
    private Button clearCourseButton;
    private SearchNotesViewModel searchNotesViewModel;
    private Spinner courseListAll;
    private RecyclerView notesSearchRecyclerView;

    private ArrayList<NoteEntity> notesArrayList = new ArrayList<>();
    private ArrayList<NoteEntity> notesFilteredArrayList;

    /*View model for the Activity. */
    private NotesViewModel notesViewModel;

    private SearchNoteAdapter searchNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_notes);

        //Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_notes_tool_bar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        // Get View components
        selectCourseButton = findViewById(R.id.search_select_course_button);
        clearCourseButton = findViewById(R.id.search_reset_course_list_button);
        courseListAll = findViewById(R.id.search_course_spinner);
        notesSearchRecyclerView = findViewById(R.id.recycler_view_search_notes);

        //Create the view model.
        searchNotesViewModel =  ViewModelProviders.of(this).get(SearchNotesViewModel.class);
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        // Get the list of courses.
        List<CourseEntity> listOfAllCourses = searchNotesViewModel.getListOfAllCourses();

        // Get list of the notes.
        notesArrayList.addAll(searchNotesViewModel.getListOfAllNotes());

        Log.d("PHIL5", "Size of Notes ARRAY: " + notesArrayList.size()); // Remove******************************************************************

        // Create the Spinner for the courses
        ArrayAdapter<CourseEntity> courseListAdapter
                = new ArrayAdapter<CourseEntity>(SearchNotesActivity.this
                , android.R.layout.simple_spinner_item, listOfAllCourses);
        courseListAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        courseListAll.setAdapter(courseListAdapter);

        // Create the recycler.-------------------------------
        notesSearchRecyclerView.setLayoutManager(
                new LinearLayoutManager(SearchNotesActivity.this));
        notesSearchRecyclerView.setHasFixedSize(true);

        searchNoteAdapter = new SearchNoteAdapter(SearchNotesActivity.this, notesArrayList, notesViewModel);
        notesSearchRecyclerView.setAdapter(searchNoteAdapter);


        // Listeners. ===============================================================
        // Set the button listeners.
        selectCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Retrieve course information.
                CourseEntity courseInfo = (CourseEntity) courseListAll.getSelectedItem();

                // Set the list
                notesArrayList.clear();
                searchNoteAdapter.notifyDataSetChanged();
                notesArrayList.addAll( searchNotesViewModel
                        .getListOfNotesByCourseID(courseInfo.getCourseId()));

                if(notesFilteredArrayList != null){
                    notesFilteredArrayList.clear();
                    searchNoteAdapter.notifyDataSetChanged();
                }

                if(notesFilteredArrayList != null) {
                    notesFilteredArrayList.addAll(searchNotesViewModel
                            .getListOfNotesByCourseID(courseInfo.getCourseId()));
                }

                searchNoteAdapter.notifyDataSetChanged();

            }
        });

        clearCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearNotesList();
            }
        });

    } // end onCreate.

    /** Method to clear the lists.  This method will clear the list of all notes and it will clear
     *  the filtered list.
     *
     */
    private void clearNotesList() {

        notesArrayList.clear();
        searchNoteAdapter.notifyDataSetChanged();

        if(notesFilteredArrayList != null) {

            notesFilteredArrayList.clear();
            searchNoteAdapter.notifyDataSetChanged();

        }

        // Rebuild the list.
        notesArrayList.addAll(searchNotesViewModel.getListOfAllNotes());

        if(notesFilteredArrayList != null) {
            notesFilteredArrayList.addAll(searchNotesViewModel.getListOfAllNotes());
        }
        searchNoteAdapter.notifyDataSetChanged();

    } // end clearNotesList.

    /** Method to create the options menu.  This method will create the options
     *  menu as well as set the listeners for the SearchView.  This method will
     *  listen for any changes to the SearchView'text box.  Results will be
     *  displayed immediately to the user.
     *
     * @param menu
     * @return
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_note, menu);
        MenuItem searchItem = menu.findItem(R.id.search_notes);
        SearchView noteSearchView = (SearchView) searchItem.getActionView();

        // set the text listener
        noteSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // Filter the list of notes.
                filterNotes(newText);
                return false;
            }
        });

        return true;

    } // end OnCreateOptionsMenu

    /** Method to filter notes. This method will filter notes based on text
     *  input given by the user. If the text matches an item that is currently
     *  in the list the item will be added to the filtered list.
     *
     * @param newText
     *
     */
    private void filterNotes(String newText) {

        notesFilteredArrayList = new ArrayList<>();

        for(NoteEntity note : notesArrayList){

            if(note.getNoteTitle().toLowerCase().contains(newText.toLowerCase())) // Check the title
            {
                notesFilteredArrayList.add(note);
            } // end if
            else if(note.getNoteBodyText().toLowerCase().contains(newText.toLowerCase())) // Check note body
            {
                notesFilteredArrayList.add(note);
            }

        } // end for

        if(notesFilteredArrayList.isEmpty())
        {
            Toast.makeText(SearchNotesActivity.this, "No Notes Found"
                    , Toast.LENGTH_SHORT).show();
        }
        else{
            searchNoteAdapter.setNotes(notesFilteredArrayList);
        }

    } // end filteredNotes

    /** Method to process the option selected.  Method to process the selected
     *  option form the menu.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Process the selection of either add or help.
        switch(item.getItemId()){

            case R.id.help_search_notes:

                AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.search_notes_help_text);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SearchNotesActivity.this, "Hope this helps."
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

} // end class.