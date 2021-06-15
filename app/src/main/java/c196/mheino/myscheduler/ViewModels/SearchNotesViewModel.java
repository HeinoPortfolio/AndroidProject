package c196.mheino.myscheduler.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import c196.mheino.myscheduler.Database.SchedulerRepository;
import c196.mheino.myscheduler.Entity.CourseEntity;
import c196.mheino.myscheduler.Entity.NoteEntity;

/** Class that will be the View model for searching for notes. This
 *  class will provide method that will allow the searching of notes.
 *
 * @author  Matthew Heino
 *
 */
public class SearchNotesViewModel extends AndroidViewModel {

    // Repository for the SearchNotesViewModel.
    private SchedulerRepository searchNotesRepository;


    /** Constuctor of the class
     *
     * @param application
     */
    public SearchNotesViewModel(@NonNull Application application) {

        super(application);
        searchNotesRepository = new SchedulerRepository(application);


    } // end constructor

    /** Method that will return all the courses that are in the database.
     *
     * @return
     */
    public List<CourseEntity> getListOfAllCourses(){

        return searchNotesRepository.getAllcoursesList();

    } // end  getListOfAllCourses.

    /** Method to get all the notes.  This method will return a List of all the
     *  notes that are in the database.
     *
     * @return
     *
     */
    public List<NoteEntity> getListOfAllNotes(){
        return searchNotesRepository.getAllNotesList();
    }

    /** Method to return list of notes by course ID.  This method will return
     *  all the notes by given course ID.  Live Data was not used in this method.
     *
     * @param courseID
     * @return
     *
     */
    public List<NoteEntity> getListOfNotesByCourseID(int courseID){

        return searchNotesRepository.getAllNotesByCourseIDList(courseID);
    }


} // end class.
