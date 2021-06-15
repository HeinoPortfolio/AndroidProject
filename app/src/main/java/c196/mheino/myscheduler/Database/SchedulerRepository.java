package c196.mheino.myscheduler.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import c196.mheino.myscheduler.DAO.AssessmentDAO;
import c196.mheino.myscheduler.DAO.CourseDAO;
import c196.mheino.myscheduler.DAO.CourseInstructorDAO;
import c196.mheino.myscheduler.DAO.NoteEntityDAO;
import c196.mheino.myscheduler.DAO.TermDAO;
import c196.mheino.myscheduler.DAO.UserDAO;
import c196.mheino.myscheduler.Entity.AssessmentEntity;
import c196.mheino.myscheduler.Entity.CourseEntity;
import c196.mheino.myscheduler.Entity.CourseInstructorEntity;
import c196.mheino.myscheduler.Entity.NoteEntity;
import c196.mheino.myscheduler.Entity.TermEntity;
import c196.mheino.myscheduler.Entity.UserEntity;

/** Class that will provide a repository for the application.  This will
 *  provide the method that will be used to provide database operations.
 *  For example, it will allow the insertion, deletion, and updating of
 *  the tables that are in the database.
 *
 */
public class SchedulerRepository {

    // DAOs for the repository.================================================
    private UserDAO userDao;
    private TermDAO termDao;
    private CourseDAO courseDao;
    private AssessmentDAO assessmentDao;
    private NoteEntityDAO notesDao;
    private CourseInstructorDAO courseInstructorDAO;

    // Lists to hold the data. ================================================
    private LiveData<List<TermEntity>> allTerms;
    private LiveData<List<CourseEntity>> allCourses;
    private LiveData<List<AssessmentEntity>> allAssessments;
    private LiveData<List<NoteEntity>> allNotes;
    private LiveData<List<CourseInstructorEntity>> allCourseInstructors;

    /** Constructor for the repository.  This will get an instance of the
     *  database or it will have one created (by calling this method) if one is
     *  not already available.
     *
     * @param application
     */
    public SchedulerRepository(Application application){

        // Get an instance of the database.
        SchedulerDatabase schedulerDatabase
                = SchedulerDatabase.getInstance(application);

        userDao = schedulerDatabase.userDAO();

        // Get all the terms in the database.
        termDao = schedulerDatabase.termDAO();
        allTerms = termDao.getAllTerms();

        // Get all the courses in the database.
        courseDao = schedulerDatabase.courseDAO();
        allCourses = courseDao.getAllCourses();

        // Get all the Assessments in the database.
        assessmentDao = schedulerDatabase.assessmentDAO();
        allAssessments = assessmentDao.getAllAssessments();

        // Get all the Notes in the database.
        notesDao = schedulerDatabase.noteDAO();
        allNotes = notesDao.getAllNotes();

        // Get all the course Instructors.  // FINISH*****************************************
        courseInstructorDAO = schedulerDatabase.courseInstructorDAO();
        allCourseInstructors = courseInstructorDAO.getAllCourseInstructors();

    } // end constructor.


    // Methods that will called from outside the Repository.*******************
    // User Methods.
    public UserEntity getUserInfo(String username, String userpassword){

        return userDao.getUserInformation(username, userpassword);

    }



    // Term's methods.*********************************************************
    /** Method to insert into the Term table.  This method will insert a Term
     *  Object into the table.  This TermEntity object is passed a
     *  parameter to the method.
     *
     * @param term
     */
    public void insert(TermEntity term) { new InsertTermTask(termDao).execute(term); }

    /** Method to update  the Term in the table.  This method will update a Term
     *  Object into the table.  This TermEntity object is passed a
     *  parameter to the method.
     *
     * @param term
     */
    public void update(TermEntity term){ new UpdateTermTask(termDao).execute(term); }

    /** Method to delete a term from the Term table.  This method will insert
     *  a Term Object into the table.  This TermEntity object is passed a
     *  parameter to the method.
     *
     * @param term
     */
    public void delete(TermEntity term){ new DeleteTermTask(termDao).execute(term); }

    /** Method to delete all terms in the Term table.  This method will insert
     *  a Term Object into the table.  This TermEntity object is passed a
     *  parameter to the method.
     *
     */
    public void deleteAllTerms(){ new DeleteAllTermsTask(termDao).execute(); }

    /** Method to get all the Terms in the table.  This method will insert a
     *  Term Object into the table.  This is TermEntity object is passed a
     *  parameter to the method.
     *
     */
    public LiveData<List<TermEntity>> getAllTerms(){ return this.allTerms; }

    public int getNumberOfCoursesInTerm(int termID){

        return termDao.numberOfCoursesInTerm(termID);
    }

    /** Method to return the terms information
     *
     * @param termID
     * @return
     */
    public TermEntity getTermInfo(int termID){
        return termDao.getTermInfo(termID);

    }



    // Courses methods.================================================================
    //===================================================================================
    /** Method to insert a course in to the Course table */
    public void insertCourse(CourseEntity course){ new InsertCourseTask(courseDao).execute(course); }

    /** Method to update a course in the table. */
    public void updateCourse(CourseEntity course){ new UpdateCourseTask(courseDao).execute(course); }

    /** Method to delete a course from the database. */
    public void deleteCourse(CourseEntity course){ new DeleteCourseTask(courseDao).execute(course); }

    /** Method to delete all courses from the database */
    public void deleteAllCourses(){ new DeleteAllCoursesTask(courseDao).execute();}

    /** Method to get all the courses in the database */
    public LiveData<List<CourseEntity>> getAllCourses() { return this.allCourses; }

    /** Method to get all courses from a single term
     * @return*/
    public LiveData<List<CourseEntity>> getCoursesByTermID(int termId){

        return courseDao.getAllCoursesByTermID(termId);

    }

    /** Method to get a single course's information.  This method will return a
     *  single course's information based on its iD.
     *
     * @param courseId
     * @return
     *
     */
    public CourseEntity getCourseInfo(int courseId){

         return courseDao.getCourseInfo(courseId);
    }


    /** Method to get a list of all courses in the database.  This method will
     *  return all the courses that are available in the database.
     *
     * @return
     *
     */
    public List<CourseEntity> getAllcoursesList(){

        return courseDao.getAllCoursesList();
    }

    // Assessment Methods =====================================================
    // ========================================================================
    /** Method to insert into the Assessment table.  This method will insert an
     *  Assessment Object into the table.  This AssessmentEntity object is
     *  passed as a parameter to the method.
     *
     * @param assessment
     */
    public void insertAssessment(AssessmentEntity assessment) { new InsertAssessmentTask(assessmentDao).execute(assessment); }

    /** Method to update an assessment in the table. */
    public void updateAssessment(AssessmentEntity assessment){ new UpdateAssessmentTask(assessmentDao).execute(assessment); }

    /** Method to delete an assessment from the database. */
    public void deleteAssessment(AssessmentEntity assessment){ new DeleteAssessmentTask(assessmentDao).execute(assessment); }

    /** Method to delete all assessments from the database */
    public void deleteAllAssessments(){ new DeleteAllAssessmentsTask(assessmentDao).execute();}

    /** Method to get all the assessments in the database */
    public LiveData<List<AssessmentEntity>> getAllAssessments() { return this.allAssessments; }

    /** Method to get all assessments from a single course
     *
     *  @return*/
    public LiveData<List<AssessmentEntity>> getAssessmentsByCourseID(int courseId){

        return assessmentDao.getAllAssessmentsByCourseID(courseId);

    }

    /** Method to return a list of assessments. This method will return a list
     *  of all the assessments that are in the database. This method does not
     *  return Live Data.
     *
     * @return
     */
    public List<AssessmentEntity> getAllAssessmentsList(){

         return assessmentDao.getAllAssessmentsList();
    }

    /** Method to return a list of assessments based on course ID.  This method
     *  will return a list of assessments based on the courseID. This method
     *  does not use Live Data.
     *
     * @param courseID
     * @return
     *
     */
    public List<AssessmentEntity> getAllAssessmentsByCourseIDList(int courseID){
        return assessmentDao.getAllAssessmentsByCourseIDList(courseID);
    }


    // Notes Methods============================================================
    // ========================================================================
    /** Method to insert a Note in to the Course table */
    public void insertNote(NoteEntity note){ new InsertNoteTask(notesDao).execute(note); }

    /** Method to update a note in the table. */
    public void updateNote(NoteEntity note){ new UpdateNoteTask(notesDao).execute(note); }

    /** Method to delete a note from the database. */
    public void deleteNote(NoteEntity note){ new DeleteNoteTask(notesDao).execute(note); }

    /** Method to delete all notes from the database */
    public void deleteAllNotes(){ new DeleteAllNotesTask(notesDao).execute();}

    /** Method to get all the notes in the database */
    public LiveData<List<NoteEntity>> getAllNotes() { return this.allNotes; }

    /** Method to get all notes from a single course
     *
     *  @return*/
    public LiveData<List<NoteEntity>> getNotesByCourseID(int courseId){

        return notesDao.getAllNotesByCourseID(courseId);

    }

    /** Method to get all notes by courseID. This method will return all note
     *  that are for a given course. This method does not use Live Data.
     *
     * @param courseId
     * @return
     */
    public List<NoteEntity> getAllNotesByCourseIDList(int courseId){
        return notesDao.getAllNotesByCourseIDList(courseId);

    }


    /** Method to return all notes.  THis method will return all the notes in
     *  the database as a list.
     *
     * @return
     *
     */
    public List<NoteEntity> getAllNotesList(){
         return notesDao.getAllNotesList();
    }

    // Course Instructor Methods ==============================================
    //=========================================================================

    /** Get a course instructor's information.
     *
     * @param instructorID
     * @return
     */
    public CourseInstructorEntity getCourseInstructorInfo(int instructorID){

         return courseInstructorDAO.getCourseInstructorInfo(instructorID);

    } // end getCourseInstructorInfo

    public LiveData<List<CourseInstructorEntity>> getAllCourseInstructors(){

        return this.allCourseInstructors;
    }

    public List<CourseInstructorEntity> getAllCourseInstructorList(){

        return courseInstructorDAO.getListOfInstructors();

    }



    // Inner classes for Asynchronous tasks for database.**********************
    // Insertions, Deletions, etd. ********************************************

    //Term Aysnc Tasks ===========================================================
    //============================================================================
    /** Asycntask to insert a TermEntity into the database
     *
     */
    private static class InsertTermTask extends AsyncTask<TermEntity, Void, Void>{

        private TermDAO termDAO;

        // Constructor
        private InsertTermTask(TermDAO termDao){ this.termDAO = termDao; } // end constructor.

        @Override
        protected Void doInBackground(TermEntity... termEntities) {

            termDAO.insert(termEntities[0]);
            return null;

        } // end doInBackground.

    } // end InsertTermTask.

    /** Asycntask to update a TermEntity in the database
     *
     */
    private static class UpdateTermTask extends AsyncTask<TermEntity, Void, Void>{

        private TermDAO termDAO;

        // Constructor
        private UpdateTermTask(TermDAO termDao){ this.termDAO = termDao; } // end constructor.

        @Override
        protected Void doInBackground(TermEntity... termEntities) {

            termDAO.update(termEntities[0]);
            return null;

        } // end doInBackground.

    } // end UpdateTermTask.

    /** Asycntask to delete a TermEntity from the database
     *
     */
    private static class DeleteTermTask extends AsyncTask<TermEntity, Void, Void>{

        private TermDAO termDAO;

        // Constructor
        private DeleteTermTask(TermDAO termDao){ this.termDAO = termDao; } // end constructor.

        @Override
        protected Void doInBackground(TermEntity... termEntities) {

            termDAO.delete(termEntities[0]);
            return null;

        } // end doInBackground.

    } // end DeleteTermTask.

    /** Asycntask to delete all Terms in the database
     *
     * REMOVE TERM ENTITY IN AsynchTASK and the same for doInbackground
     *
     */
    private static class DeleteAllTermsTask extends AsyncTask<Void, Void, Void>{

        private TermDAO termDAO;

        // Constructor
        private DeleteAllTermsTask(TermDAO termDao){ this.termDAO = termDao; } // end constructor.

        @Override
        protected Void doInBackground(Void... voids) {

            termDAO.deleteAllTerms();
            return null;

        } // end doInBackground.

    } // end DeleteAllTermsTask.

    // Courses Async Tasks=====================================================
    //=========================================================================
    /** Asycntask to insert a CourseEntity into the database */
    private static class InsertCourseTask extends AsyncTask<CourseEntity, Void, Void>{

        private CourseDAO courseDAO;

        // Constructor
        private InsertCourseTask(CourseDAO courseDao){ this.courseDAO = courseDao; } // end constructor.

        @Override
        protected Void doInBackground(CourseEntity... courseEntities) {

            courseDAO.insert(courseEntities[0]);
            return null;

        } // end doInBackground.

    } // end InsertCourseTask.

    /** AsyncTask to update a course in the table. */
    private static class UpdateCourseTask extends AsyncTask<CourseEntity, Void, Void>{

        private CourseDAO courseDAO;

        // Constructor.------
        private  UpdateCourseTask(CourseDAO courseDao){this.courseDAO = courseDao; }

        @Override
        protected Void doInBackground(CourseEntity... courseEntities){

            courseDAO.update(courseEntities[0]);
            return null;

        } // end doInBackground.

    } // end UpdateCourseTask.

    /** AsyncTask for deleting a course in the table */
    private static class DeleteCourseTask extends AsyncTask<CourseEntity, Void, Void>{

        private CourseDAO courseDAO;

        //Constructor
        private DeleteCourseTask(CourseDAO courseDao)
        {
            this.courseDAO = courseDao;
        }

        @Override
        protected  Void doInBackground(CourseEntity... courseEntities){

            courseDAO.deleteCourse(courseEntities[0]);
            return null;

        } // end doInBackground

    } // end DeleteCourseTask


    /** AsyncTask to delete all the courses from the database. */
    private static class DeleteAllCoursesTask extends AsyncTask<Void, Void, Void>{

        private  CourseDAO courseDAO;

        // Constructor.
        private DeleteAllCoursesTask (CourseDAO courseDao){ this.courseDAO = courseDao; }

        @Override
        protected Void doInBackground(Void... voids) {

            courseDAO.deleteAllCourses();
            return null;
        }
    } // end DeleteAllCoursesTask.

    // Assessment Async Tasks==================================================
    //=========================================================================

    /** Asycntask to insert a AssessmentEntity into the database */
    private static class InsertAssessmentTask extends AsyncTask<AssessmentEntity, Void, Void>{

        private AssessmentDAO assessmentDAO;

        // Constructor
        private InsertAssessmentTask(AssessmentDAO assessmentDao){
            this.assessmentDAO = assessmentDao;
        } // end constructor.

        @Override
        protected Void doInBackground(AssessmentEntity... assessmentEntities) {

            assessmentDAO.insert(assessmentEntities[0]);
            return null;

        } // end doInBackground.

    } // end InsertAssessmentTask.


    /** AsyncTask to update an assessment in the table. */
    private static class UpdateAssessmentTask extends AsyncTask<AssessmentEntity, Void, Void>{

        private AssessmentDAO assessmentDAO;

        // Constructor.------
        private  UpdateAssessmentTask(AssessmentDAO assessmentDao){this.assessmentDAO = assessmentDao; }

        @Override
        protected Void doInBackground(AssessmentEntity... assessmentEntities){

            assessmentDAO.update(assessmentEntities[0]);
            return null;

        } // end doInBackground.

    } // end UpdateAssessmentTask.

    /** AsyncTask for deleting an assessment in the table */
    private static class DeleteAssessmentTask extends AsyncTask<AssessmentEntity, Void, Void>{

        private AssessmentDAO assessmentDAO;

        //Constructor
        private DeleteAssessmentTask(AssessmentDAO assessmentDao)
        {
            this.assessmentDAO = assessmentDao;
        }

        @Override
        protected Void doInBackground(AssessmentEntity... assessmentEntities){

            assessmentDAO.deleteAssessment(assessmentEntities[0]);
            return null;

        } // end doInBackground

    } // end DeleteAssessmentTask.


    /** AsyncTask to delete all the assessments from the database. */
    private static class DeleteAllAssessmentsTask extends AsyncTask<Void, Void, Void>{

        private  AssessmentDAO assessmentDAO;

        // Constructor.
        private DeleteAllAssessmentsTask (AssessmentDAO assessmentDao){ this.assessmentDAO = assessmentDao; }

        @Override
        protected Void doInBackground(Void... voids) {

            assessmentDAO.deleteAllAssessments();
            return null;
        }
    } // end DeleteAllAssessmentsTask.


    // Note Async Tasks========================================================
    //=========================================================================

    /** Asycntask to insert a NoteEntity into the database
     *
     */
    private static class InsertNoteTask extends AsyncTask<NoteEntity, Void, Void>{

        private NoteEntityDAO noteEntityDAO;

        // Constructor
        private InsertNoteTask(NoteEntityDAO noteDao){ this.noteEntityDAO = noteDao; } // end constructor.

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {

            noteEntityDAO.insert(noteEntities[0]);
            return null;

        } // end doInBackground.

    } // end InsertNoteTask.

    /** AsyncTask to update an note in the table. */
    private static class UpdateNoteTask extends AsyncTask<NoteEntity, Void, Void>{

        private NoteEntityDAO noteDAO;

        // Constructor.------
        private  UpdateNoteTask(NoteEntityDAO noteDao){this.noteDAO = noteDao; }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities){

            noteDAO.update(noteEntities[0]);
            return null;

        } // end doInBackground.

    } // end UpdateNoteTask.

    /** AsyncTask for deleting an note in the table */
    private static class DeleteNoteTask extends AsyncTask<NoteEntity, Void, Void>{

        private NoteEntityDAO noteDAO;

        //Constructor
        private DeleteNoteTask(NoteEntityDAO noteDao)
        {
            this.noteDAO = noteDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities){

            noteDAO.deleteNote(noteEntities[0]);
            return null;

        } // end doInBackground

    } // end DeleteNoteTask.

    /** AsyncTask to delete all the notes from the database. */
    private static class DeleteAllNotesTask extends AsyncTask<Void, Void, Void>{

        private  NoteEntityDAO noteDAO;

        // Constructor.
        private DeleteAllNotesTask (NoteEntityDAO noteDao){ this.noteDAO = noteDao; }

        @Override
        protected Void doInBackground(Void... voids) {

            noteDAO.deleteAllNotes();
            return null;
        }
    } // end DeleteAllNotesTask.

} // end class.
