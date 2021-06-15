package c196.mheino.myscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import c196.mheino.myscheduler.Entity.NoteEntity;

/** An interface for the Note.  This interface will be used to interact
 *  with the Room database.  It is the data access object that will handle all
 *  manipulation of the Notes table in the ROOM database.  This DAO will
 *  allow the following actions: 1) insertions 2) updates 3) deletion of a
 *  single note 4) deletion of all notes from the table 5) The retrieval of all
 *  the notes that are currently in the database. 6) The retrieval of notes
 *  that correspond to a specific term.
 *
 * @author Matthew Heino
 *
 */
@Dao
public interface NoteEntityDAO {

    /** Method to insert a single note into the database. */
    @Insert
    void insert(NoteEntity note);

    /** Method to update a single note in the database. */
    @Update
    void update(NoteEntity note);

    /** Method to delete a note from the database. */
    @Delete
    void deleteNote(NoteEntity note);

    /** Method to delete all the notes form the table. */
    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    /** Method to retrieve all the notes in the database. */
    @Query("SELECT * FROM note_table")
    LiveData<List<NoteEntity>> getAllNotes();

    /** Method to retrieve all the notes in the database that
     correspond to a given term. */
    @Query("SELECT * FROM note_table WHERE courseID = :courseId")
    LiveData<List<NoteEntity>> getAllNotesByCourseID(int courseId);

    /** Method to retrieve a list of all the notes. This method will return a
     *  list of all the notes in the database.
     *
     * @return
     *
     */
    @Query("SELECT * FROM note_table")
    List<NoteEntity> getAllNotesList();

    /** Method to retrieve a list of notes by course ID. This method will
     *  retrieve a List of course notes by a given courseID. This method does
     *  not use Live Data.
     *
     * @param courseId
     * @return
     */
    @Query("SELECT * FROM note_table WHERE courseID = :courseId")
    List<NoteEntity> getAllNotesByCourseIDList(int courseId);

} // end class
