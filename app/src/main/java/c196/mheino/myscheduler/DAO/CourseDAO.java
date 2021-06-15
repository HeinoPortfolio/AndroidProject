package c196.mheino.myscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import c196.mheino.myscheduler.Entity.CourseEntity;
import c196.mheino.myscheduler.Entity.TermEntity;

/** An interface for the Course.  This interface will be used to interact with
 *  the Room database.  It is the data access object that will handle all
 *  manipulation of the Course table in the ROOM database.  This DAO will allow
 *  the following actions: 1) insertions 2) updates 3) deletion of a single
 *  course 4) deletion of all courses from the table 5) The retrieval of all
 *  the courses that are currently in the database. 6) The retrieval of courses
 *  that correspond to a specific term.
 *
 * @author Matthew Heino
 *
 */
@Dao
public interface CourseDAO {

    /** Method to insert a single course into the database. */
    @Insert
    void insert(CourseEntity course);

    /** Method to update a single course in the database. */
    @Update
    void update(CourseEntity course);

    /** Method to delete a course from the database. */
    @Delete
    void deleteCourse(CourseEntity course);

    /** Method to delete all the terms form the table. */
    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    /** Method to retrieve all the courses in the database. */
    @Query("SELECT * FROM course_table")
    LiveData<List<CourseEntity>> getAllCourses();

    /** Method to retrieve all the courses in the database that
    correspond to a given term. */
    @Query("SELECT * FROM course_table WHERE termId = :termId")
    LiveData<List<CourseEntity>> getAllCoursesByTermID(int termId);

    /** Method to retrieve a single term's information. */
    @Query("SELECT * FROM course_table WHERE courseId = :courseID")
    CourseEntity getCourseInfo(int courseID);

    /** Method to get a list of the courses in the data base.
     *
      * @return
     */
    @Query("SELECT * FROM course_table")
    List<CourseEntity> getAllCoursesList();

} // end CourseDAO.
