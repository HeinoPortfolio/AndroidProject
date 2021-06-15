package c196.mheino.myscheduler.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import c196.mheino.myscheduler.Database.SchedulerRepository;
import c196.mheino.myscheduler.Entity.TermEntity;

/** This is the Term View Model class.  This class will be the main point
 *  of interaction for the terms and the database.  It will provide access
 *  to the database method that are available in the repository.
 *
 * @author Matthew Heino
 *
 */
public class TermViewModel extends AndroidViewModel {

    //Repository for the view model
    private SchedulerRepository termRepository;

    // List for all the terms
    private LiveData<List<TermEntity>> allTerms;

    /**
     * @param application
     */
    // Constructor
    public TermViewModel(@NonNull Application application) {

        super(application);

        termRepository = new SchedulerRepository(application);
        allTerms = termRepository.getAllTerms();


    } // end constructor.

    /** Method to insert a Term entity
     *
     * @param term
     *
     * @return
     */
    //Wrapper Method to access the repository.********************************
    public void insert(TermEntity term){ termRepository.insert(term); }

    /** Method to update a Term entity.
     *
     * @param term
     *
     */
    public void update(TermEntity term){ termRepository.update(term); }

    /** Method to delete a TermEntity
     *
     * @param term
     *
     */
    public void delete(TermEntity term){ termRepository.delete(term); }

    /** Method to delete all the terms from the database.
     *
     */
    public void deleteAllTerms(){ termRepository.deleteAllTerms(); }

    /** Method to retrieve all the terms that are in the database.
     *
     * @return
     */
    public LiveData<List<TermEntity>> getAllTerms(){ return allTerms; }

    /** Method to get the number of courses in a given term.  This method will
     *  return the number of courses that are in a given term.  It will use the
     *  Course table and look up the information by the term's ID
     *
     * @param termID
     * @return
     */
    public int getNumberOfCoursesInTerm(int termID){

        return termRepository.getNumberOfCoursesInTerm(termID);

    }

} // end class.
