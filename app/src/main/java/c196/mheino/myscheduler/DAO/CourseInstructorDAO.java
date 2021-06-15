package c196.mheino.myscheduler.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import c196.mheino.myscheduler.Entity.CourseInstructorEntity;


/** An interface for the Course Instructor.  This interface will be used to
 *  interact with the Room database.  It is the data access object that will
 *  handle all manipulation of the Course Instructor table in the ROOM
 *  database.  This DAO will allow the following actions: 1) insertions
 *  2) updates 3) deletion of a single instructor 4) deletion of all
 *  instructors from the table 5) The retrieval of all the instructors that are
 *  currently in the database.
 *
 * @author Matthew Heino
 *
 */
@Dao
public interface CourseInstructorDAO {

    /** Method to insert a single instructor into the database. */
    @Insert
    void insert(CourseInstructorEntity course);

    /** Method to update a single instructor in the database. */
    @Update
    void update(CourseInstructorEntity course);

    /** Method to delete a single instructor from the database. */
    @Delete
    void delete(CourseInstructorEntity course);

    /** Method to delete all the instructors form the table. */
    @Query("DELETE FROM course_instructor_table")
    void deleteAllCourseInstructors();

    /** Method to retrieve all the instructors in the database. */
    @Query("SELECT * FROM course_instructor_table")
    LiveData<List<CourseInstructorEntity>> getAllCourseInstructors();

    /** Method to retrieve a course instructors information
     *
     * @param instructorId
     * @return
     */
    @Query("SELECT * FROM course_instructor_table WHERE id = :instructorId")
    CourseInstructorEntity getCourseInstructorInfo(int instructorId);

    @Query ("SELECT * FROM course_instructor_table")
    List<CourseInstructorEntity> getListOfInstructors( );

} // end class
