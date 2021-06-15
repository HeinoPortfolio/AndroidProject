package c196.mheino.myscheduler.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import c196.mheino.myscheduler.Database.SchedulerRepository;
import c196.mheino.myscheduler.Entity.CourseEntity;
import c196.mheino.myscheduler.Entity.CourseInstructorEntity;
import c196.mheino.myscheduler.Entity.TermEntity;

//May need to remove*********************************************
public class CourseViewModel extends AndroidViewModel {

    // Repository for the TermDetailsViewModel.
    private SchedulerRepository courseRepository;


    /** Constructor for the Course View Model.
     *
     * @param application
     */
    public CourseViewModel(@NonNull Application application) {

        super(application);
        courseRepository = new SchedulerRepository(application);

    } // end constructor.

    /** Method to insert a Course.
     *
     * @param course
     */
    public void insertCourse(CourseEntity course){
        courseRepository.insertCourse(course);
    }

    /** Method to get all the Instructors in the database.
     *
     * @return
     */
    public LiveData<List<CourseInstructorEntity>> getCourseInstructors(){

       return courseRepository.getAllCourseInstructors();

    }

    /** Method to update the course in the database.
     *
     * @param course
     */
    public void updateCourse(CourseEntity course){courseRepository.updateCourse(course);}

    /** Method to get the course instructor information
     *
     * @param instructorID
     * @return
     */
    public CourseInstructorEntity getCourseInstructorInformation(int instructorID){
       return courseRepository.getCourseInstructorInfo(instructorID);
    }

    public List<CourseInstructorEntity> getListOfCourseInstructors(){
        return courseRepository.getAllCourseInstructorList();
    }


    /** Method to get the term information.  Will be used to validate the dates
     *  for a given course fall between  the dates of the term.
     *
     * @param termID
     * @return
     */
    public TermEntity getTermInfo(int termID){

        Log.d("PHIL5", "getTermInfo: " + termID); // REMOVE ******************************************************

         return courseRepository.getTermInfo(termID);

    }

} // end class.
