package c196.mheino.myscheduler.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import c196.mheino.myscheduler.Entity.AssessmentEntity;

/** An interface for the Assessment.  This interface will be used to interact
 *  with the Room database.  It is the data access object that will handle all
 *  manipulation of the Assessment table in the ROOM database.  This DAO will
 *  allow the following actions: 1) insertions 2) updates 3) deletion of a
 *  single assessment 4) deletion of all assessments from the table 5) The
 *  retrieval of all the assessments that are currently in the database. 6) The
 *  retrieval of courses that correspond to a specific term.
 *
 * @author Matthew Heino
 *
 */

@Dao
public interface AssessmentDAO {

    /** Method to insert a single assessment into the database. */
    @Insert
    void insert(AssessmentEntity assessment);


    /** Method to update a single assessment in the database. */
    @Update
    void update(AssessmentEntity assessment);

    /** Method to delete an assessment from the database. */
    @Delete
    void deleteAssessment(AssessmentEntity course);

    /** Method to delete all the course in the database. */
    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    /** Method to retrieve all the assessments in the database. */
    @Query("SELECT * FROM assessment_table")
    LiveData<List<AssessmentEntity>> getAllAssessments();

    /** Method to retrieve all the assessments in the database that correspond
     *  to a given course ID.
     *
     */
    @Query("SELECT * FROM assessment_table WHERE assessmentCourseId = :courseId")
    LiveData<List<AssessmentEntity>> getAllAssessmentsByCourseID(int courseId);


    /** Method to retrieve a list of assessments by course ID. THis method will
     *  return a List of assessments that is based on ta given course ID. This
     *  method does not return Live Data.
     *
     * @param courseID
     * @return
     *
     */
    @Query("SELECT * FROM assessment_table WHERE assessmentCourseId = :courseID")
    List<AssessmentEntity> getAllAssessmentsByCourseIDList(int courseID);

    /** Method to retrieve a list of all assessments. This method will retrieve
     *  a List of all assessments that are in the database.  This method does
     *  not return Live Data.
     *
     * @return
     *
     */
    @Query("SELECT * FROM assessment_table")
    List<AssessmentEntity> getAllAssessmentsList();



} // end class.
