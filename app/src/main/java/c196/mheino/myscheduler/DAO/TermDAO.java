package c196.mheino.myscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import c196.mheino.myscheduler.Entity.TermEntity;

/** An interface for the Term.  This interface will be used to interact with
 *  the Room database.  It is the data access object that will handle all
 *  manipulation of the Term table in the ROOM database.  This DAO will allow
 *  the following actions: 1) insertions 2) updates 3) deletion of a single
 *  Term 4) deletion of all terms from the table 5) The retrieval of all the
 *  terms that are currently in the database.
 *
 * @author Matthew Heino
 *
 */
@Dao
public interface TermDAO {

    /** Method to insert a single term into the database. */
    @Insert
    void insert(TermEntity term);

    /** Method to update a single term in the database. */
    @Update
    void update(TermEntity term);

    /** Method to delete a single term from the database. */
    @Delete
    void delete(TermEntity term);

    /** Method to delete all the terms form the table. */
    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    /** Method to retrieve all the terms in the database. */
    @Query("SELECT * FROM term_table")
    LiveData<List<TermEntity>> getAllTerms();

    @Query("SELECT COUNT(*) FROM course_table WHERE termId = :termID")
    int numberOfCoursesInTerm(int termID);

    /** Method to retrieve a sinlge term's information. */
    @Query("SELECT * FROM term_table WHERE termID = :termID")
    TermEntity getTermInfo(int termID);

} // end TermDAO.
