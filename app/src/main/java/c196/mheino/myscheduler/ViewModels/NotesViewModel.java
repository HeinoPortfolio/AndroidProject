package c196.mheino.myscheduler.ViewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import c196.mheino.myscheduler.Database.SchedulerRepository;
import c196.mheino.myscheduler.Entity.CourseInstructorEntity;
import c196.mheino.myscheduler.Entity.NoteEntity;

/** This is the Assessment View Model class.  This class will be the main point
 *  of interaction for the assessment and the database.  It will provide access
 *  to the database method that are available in the repository.
 *
 * @author Matthew Heino
 *
 */
public class NotesViewModel extends AndroidViewModel{


    //Repository for the view model
    private SchedulerRepository noteRepository;

    // List for all the notes
    private LiveData<List<NoteEntity>> allNotes;


    /** Constructor for the Assessment View Model.
     *
     * @param application
     */
    public NotesViewModel(@NonNull Application application) {

        super(application);

        noteRepository = new SchedulerRepository(application);
        allNotes = noteRepository.getAllNotes();

    } // end constructor.


    //Wrapper Methods to access the repository.********************************
    // Assessments ************************************************************

    /** Method to insert a Note.
     *
     * @param note
     */
    public void insertNote(NoteEntity note){  noteRepository.insertNote(note); }

    /** Method to update the note.
     *
     * @param note
     */
    public void updateNote(NoteEntity note){  noteRepository.updateNote(note); }

    /** Method to delete a note from the database.
     *
     * @param note
     */
    public void deleteNote(NoteEntity note){ noteRepository.deleteNote(note); }

    /** Method to delete all the notes that are in the database.
     *
     */
    public void deleteAllNotes(){ noteRepository.deleteAllNotes(); }

    /** Method to retrieve all the notes that are in the database.
     *
     * @return
     */
    public LiveData<List<NoteEntity>> getAllNotes(){ return allNotes; }

    /** Method to retrieve all the notes that are in the database based on a
     *  course ID.
     *
     * @return
     */
    public LiveData<List<NoteEntity>> getAllNotesByCourseID(int courseId){

        return noteRepository.getNotesByCourseID(courseId);

    }

    public CourseInstructorEntity getCourseInstructorInfo(int courseInstructorId){

        return noteRepository.getCourseInstructorInfo(courseInstructorId);

    }


} // end class.
