package c196.mheino.myscheduler.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import c196.mheino.myscheduler.Database.SchedulerRepository;
import c196.mheino.myscheduler.Entity.AssessmentEntity;
import c196.mheino.myscheduler.Entity.CourseEntity;

/** Class that will be the View model for searching for assessments. This
 *  class will provide method that will allow the searching of assessments.
 *
 * @author  Matthew Heino
 *
 */
public class SearchAssessmentsViewModel  extends AndroidViewModel {


    // Repository for the SearchAssessmentsViewModel.
    private SchedulerRepository searchAssessmentsRepository;

    /** Constuctor of the class
     *
     * @param application
     */
    public SearchAssessmentsViewModel(@NonNull Application application) {

        super(application);
        searchAssessmentsRepository = new SchedulerRepository(application);

    } // end constructor


    /** Method that will return all the courses that are in the database.
     *
     * @return
     */
    public List<CourseEntity> getListOfAllCourses(){

        return searchAssessmentsRepository.getAllcoursesList();

    } // end  getListOfAllCourses.

    /** Method to get a list of all assessments.  This method will return a
     *  List of all the assessments that are in the database. This method does
     *  not return Live Data.
     *
     * @return
     *
     */
    public List<AssessmentEntity> getListOfAllAssessments(){

        return searchAssessmentsRepository.getAllAssessmentsList();
    }

    /** Method to get a list of assessments based on course ID.  This method
     *  will return a List of assessments based on a course ID.  This method
     *  does not return Live Data.
     *
     * @param courseID
     * @return
     *
     */
    public List<AssessmentEntity> getListOfAllAssessmentsByCourseID(int courseID){

        return searchAssessmentsRepository.getAllAssessmentsByCourseIDList(courseID);
    }






} // end class.
