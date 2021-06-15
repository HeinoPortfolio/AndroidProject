package c196.mheino.myscheduler.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import c196.mheino.myscheduler.UI.AddUpdateTermActivity;
import c196.mheino.myscheduler.Entity.TermEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.UI.TermDetailsActivity;
import c196.mheino.myscheduler.ViewModels.TermViewModel;

/** Class for the Term Adapter.  This class will be used for the
 *  Recyclerview for the Term. It will aid in the displaying of data for
 *  Terms. It will define which components of the view wil be modified by
 *  the data that is contained in an TermEntity object.
 *
 * @author  Matthew Heino
 *
 */
public class TermAdapter extends RecyclerView.Adapter<TermHolder>{

    private List<TermEntity> termList = new ArrayList<>();

    Context context;

    TermViewModel termViewModel;

    //Constructor
    /*
    public TermAdapter(List<TermEntity> termList, Context context)
    {
            this.termList = termList;
            this.context = context;

    }
*/

    /** A Term adapter constructor.  This constructor will be used to create
     *  a TermAdapter object. It will receive a reference to a TermViewModel and the
     *  application Context.
     *
     * @param context
     * @param termViewModel
     */
    public TermAdapter(Context context, TermViewModel termViewModel)
    {
        this.context = context;
        this.termViewModel = termViewModel;

    }

    /** Method to create the view holder for the Term This method will
     *  use a XML layout to inflate the view for the Term.  The
     *  term holder will return a new TermHolder that has been
     *  inflated with the given layout.
     *
     * @param parent
     * @param viewType
     * @return
     *
     */
    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View termView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_list_item, parent, false);
        return new TermHolder(termView);
    }

    /** Method to bind and set the different view components to the view
     *  holder.  The method will also set the listener for card that is used in
     *  the term.
     *
     * @param holder
     * @param position
     *
     */
    @Override
    public void onBindViewHolder(@NonNull final TermHolder holder, final int position) {

        // Get the current term.
        TermEntity currentTerm  = termList.get(position);

        // Set the view components that are in the current term.
        holder.termTitleTextView.setText(currentTerm.getTermTitle());
        holder.termStartDateTextView.setText(currentTerm.getStartDate());
        holder.termEndDateTextView.setText(currentTerm.getEndDate());

        // Set the click listener for the image.
        holder.updateDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Will need to retrieve the id of the term***************************/
                TermEntity termSingle = termList.get(position);

                // Create the popup menu.
                PopupMenu termMenu = new PopupMenu(context, holder.updateDeleteImageView);
                termMenu.inflate(R.menu.popup_menu_terms);

                // Set the click listener.
                termMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId())
                        {

                            case R.id.delete_term:
                                // WIll need to check if the term has any courses.
                                if(termViewModel.getNumberOfCoursesInTerm(termSingle.getTermID()) == 0) {

                                  termViewModel.delete(termSingle);
                                    Toast.makeText(context, R.string.term_deleted
                                            , Toast.LENGTH_SHORT).show();

                                }
                                else{

                                    Toast.makeText(context, R.string.empty_term_notification
                                            , Toast.LENGTH_LONG).show();

                                }

                                break;

                            case R.id.update_term:

                                // Create an Intent for the update activity
                                Intent updateIntent = new Intent(context.getApplicationContext()
                                        , AddUpdateTermActivity.class);

                                // Add the Extras. ****************************
                                // Add the term ID.

                                int termID =  termSingle.getTermID();
                                updateIntent.putExtra(AddUpdateTermActivity.EXTRA_TERM_ID
                                        ,termID);

                                // Add the term title.
                                String termTitle = termSingle.getTermTitle();
                                updateIntent.putExtra(AddUpdateTermActivity.EXTRA_TITLE_TERM, termTitle);

                                // Add the start Date.
                                String termStartDate = termSingle.getStartDate();
                                updateIntent.putExtra(AddUpdateTermActivity.EXTRA_START_DATE_TERM
                                        , termStartDate);

                                // Add the end date.
                                String termEndDate = termSingle.getEndDate();
                                updateIntent.putExtra(AddUpdateTermActivity.EXTRA_END_DATE_TERM, termEndDate);

                                // Start the Update Activity.-----
                                context.startActivity(updateIntent);
                                break;

                            case R.id.term_details_option:

                                // Create the Term Details activity.
                                Intent termDetailsIntent = new Intent(context.getApplicationContext()
                                        , TermDetailsActivity.class);

                                int termID2 =  termSingle.getTermID();
                                termDetailsIntent.putExtra(TermDetailsActivity.EXTRA_DETAILS_TERM_ID
                                        ,termID2);

                                // Add the term title.
                                String termTitle2 = termSingle.getTermTitle();
                                termDetailsIntent.putExtra(TermDetailsActivity.EXTRA_DETAILS_TITLE_TERM, termTitle2);

                                // Add the start Date.
                                String termStartDate2 = termSingle.getStartDate();
                                termDetailsIntent.putExtra(TermDetailsActivity.EXTRA_DETAILS_START_DATE_TERM
                                        , termStartDate2);

                                // Add the end date.
                                String termEndDate2 = termSingle.getEndDate();
                                termDetailsIntent.putExtra(TermDetailsActivity.EXTRA_DETAILS_END_DATE_TERM, termEndDate2);

                                context.startActivity(termDetailsIntent);
                                break;

                        } // end switch

                        return false;
                    } // end OnMenuItemClick.
                });

                termMenu.show();

            }// end onClick
        });

    } // end onBindViewHOlder.

    /** Method to return the size of the term list.  This method will
     *  return an int, the size of the current term list.
     *
     * @return
     */
    @Override
    public int getItemCount() {  return termList.size(); }

    /** Method to set the List of terms. This method will set the terms that
     *  will be displayed in the RecyclerView.
     *
     * @param terms
     */
    public void setTerms(List<TermEntity> terms){

        this.termList = terms;
        notifyDataSetChanged();

    }

    /** Method to retrieve a term at a given position.  This method will return
     *  a TermEntity Object that is found at a given position within the list
     *  of terms.
     *
     * @param position
     * @return
     */
    public TermEntity getTermAt(int position){ return termList.get(position); }

} // end class.
