package c196.mheino.myscheduler.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import c196.mheino.myscheduler.Database.SchedulerRepository;
import c196.mheino.myscheduler.Entity.CourseEntity;


public class TermDetailsViewModel  extends AndroidViewModel {

    // Repository for the TermDetailsViewModel.
    private SchedulerRepository termDetailsRepository;

    // List for all the terms
    private LiveData<List<CourseEntity>> allCourses;


    /** Constructor
     *
     */
    public TermDetailsViewModel(@NonNull Application application) {

        super(application);

        termDetailsRepository = new SchedulerRepository(application);
        allCourses = termDetailsRepository.getAllCourses();

    } // end constructor.

    //Wrapper Method to access the repository.=================================
    /** Method ot insert a course.
     *
     * @param course
     */
    public void insertCourse(CourseEntity course){ termDetailsRepository.insertCourse(course); }

    /** Method to update a course.
     *
     * @param course
     */
    public void updateCourse(CourseEntity course) {termDetailsRepository.updateCourse(course);}

    /** Method to delete a course
     *
     * @param course
     */
    public void deleteCourse(CourseEntity course){ termDetailsRepository.deleteCourse(course); }

    /** Method to delete all courses.
     *
     */
    public void deleteAllCourses() { termDetailsRepository.deleteAllCourses(); }

    /** Method to get all the courses in the database.*/
    public LiveData<List<CourseEntity>> getAllCourses(){
        return termDetailsRepository.getAllCourses();
    }

    /** Method to return the courses by term ID.
     *
     * @param termId
     * @return
     */
    public LiveData<List<CourseEntity>> getCoursesByTermID(int termId){

        return  termDetailsRepository.getCoursesByTermID(termId);

    }

} //end class.
