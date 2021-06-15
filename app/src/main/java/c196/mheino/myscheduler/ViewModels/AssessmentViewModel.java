package c196.mheino.myscheduler.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import c196.mheino.myscheduler.Database.SchedulerRepository;
import c196.mheino.myscheduler.Entity.AssessmentEntity;
import c196.mheino.myscheduler.Entity.CourseEntity;


/** This is the Assessment View Model class.  This class will be the main point
 *  of interaction for the assessment and the database.  It will provide access
 *  to the database method that are available in the repository.
 *
 * @author Matthew Heino
 *
 */
public class AssessmentViewModel extends AndroidViewModel {

    //Repository for the view model
    private SchedulerRepository assessmentRepository;

    // List for all the terms
    private LiveData<List<AssessmentEntity>> allAssessments;


    /** Constructor for the Assessment View Model.
     *
     * @param application
     */
    public AssessmentViewModel(@NonNull Application application) {

        super(application);

        assessmentRepository = new SchedulerRepository(application);
        allAssessments = assessmentRepository.getAllAssessments();

    } // end constructor.


    //Wrapper Methods to access the repository.********************************
    // Assessments ************************************************************

    /** Method to insert an Assessment.
     *
     * @param assessment
     */
    public void insertAssessment(AssessmentEntity assessment){
        assessmentRepository.insertAssessment(assessment);
    }

    /** Method to update the assessment.
     *
     * @param assessment
     */
    public void updateAssessment(AssessmentEntity assessment){

        assessmentRepository.updateAssessment(assessment);
    }

    /** Method to delete an Assessment from the database.
     *
     * @param assessment
     */
    public void deleteAssessment(AssessmentEntity assessment){

        assessmentRepository.deleteAssessment(assessment);

    }

    /** Method to delete all the assessments that are in the database.
     *
     */
    public void deleteAllAssessments(){

        assessmentRepository.deleteAllAssessments();
    }

    /** Method to retrieve all the terms that are in the database.
     *
     * @return
     */
    public LiveData<List<AssessmentEntity>> getAllAssessments(){ return allAssessments; }

    public LiveData<List<AssessmentEntity>> getAllAssessmentsByCourseID(int courseId){

        return assessmentRepository.getAssessmentsByCourseID(courseId);

    }

    /** Method to retrieve the the course's information.  This method will
     *  retrieve all of the information that is associated with a
     *  CourseEntity.
     *
     * @param courseID
     * @return
     *
     */
    public CourseEntity getCourseInfo(int courseID){

        return assessmentRepository.getCourseInfo(courseID);

    }

} // end class.
