package c196.mheino.myscheduler.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

/** This is the database class for the Scheduler/ Course tracking application.
 *  This class will be the hold all the data for the application.  It will hold
 *  data for the Term, Courses, Assessments, and the instructors. No other
 *  databases will be used in this application.
 *
 * @author  Matthew Heino
 *
 */
@Database(entities = {UserEntity.class, TermEntity.class, CourseEntity.class, AssessmentEntity.class,
        NoteEntity.class, CourseInstructorEntity.class}, version = 1
        , exportSchema = false)
public abstract class SchedulerDatabase extends RoomDatabase {

    /* Holds instance of the application database. */
    private static SchedulerDatabase INSTANCE;

    // DAOs for the database. All DAOs that are used in this database will be
    // found here.============================================================

    public abstract UserDAO userDAO();                          // User DAO
    public abstract TermDAO termDAO();                          // Term DAO
    public abstract CourseDAO courseDAO();                      // Course DAO
    public abstract AssessmentDAO assessmentDAO();              // Assessment DAO
    public abstract NoteEntityDAO noteDAO();                    // Note DAO

    public abstract CourseInstructorDAO courseInstructorDAO();  // Course instructor DAO

    /** Get or create an instance of the database.  This method will get a
     *  single instance of the database or it will create one if one does not
     *  exist. Note: allowMainThreadQueries() is needed to allow direct queries
     *   using the UI thread.
     *
     * **/
    public static synchronized SchedulerDatabase getInstance(Context context){

        // Check to see a current instance exists. If not create one.
        if(INSTANCE == null){

            INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                    ,SchedulerDatabase.class, "scheduler_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(schedulerCallback).build();

        } // end if.

        return INSTANCE;            // Instance of the database is returned.

    } // end getInstance.

    /** Method callback that will be used create and populate the database.
     *  This callback will be used to create the database.  It will use a
     *  AsyncTask to populate the database database with sample data.
     *
     */
    private static RoomDatabase.Callback schedulerCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {

            super.onCreate(db);

            // Method to populate the database with some data.
            new BuildDBAsyncTask(INSTANCE).execute();

        } // end onCreate
    };

    /** Class to load the database with some sample data at start.  The class
     *  will load data into each table that correspond to each of the
     *  application's entities
     *
     */
    private static class BuildDBAsyncTask extends AsyncTask<Void, Void, Void>{

        /* ************ DAOs used to create the database **********************/
        private UserDAO userDAO;
        private TermDAO termDao;
        private CourseDAO courseDAO;
        private AssessmentDAO assessmentDAO;
        private CourseInstructorDAO courseInstructorDAO;
        private NoteEntityDAO noteEntityDAO;

        /** Constructor for the Scheduler Database.
         *
         * @param sdb
         */
        private BuildDBAsyncTask (SchedulerDatabase sdb){

            userDAO = sdb.userDAO();
            termDao = sdb.termDAO();
            courseDAO = sdb.courseDAO();
            assessmentDAO = sdb.assessmentDAO();
            courseInstructorDAO = sdb.courseInstructorDAO();
            noteEntityDAO = sdb.noteDAO();

        } // end constructor.

        /** A donInBackground task that will populate the database. This task
         *  will populate the database with sample data into each of the
         *  entity/tables of the database.
         *
         * @param voids
         * @return
         */
        @Override
        protected Void doInBackground(Void... voids) {

            // Add a user to the database
            userDAO.insert(new UserEntity("test", "test"));

            /**

            //Adding terms to the database.====================================
            termDao.insert(new TermEntity("Fall 2021", "02/02/21"
                    , "05/03/22"));
            termDao.insert(new TermEntity("Spring 2022", "08/03/21"
                    , "02/03/22"));
            termDao.insert(new TermEntity("Summer 1 2022", "09/03/21"
                    , "03/03/22"));

            termDao.insert(new TermEntity("Summer 2 2022", "12/03/21"
                    , "07/23/22"));


            // Adding courses to the Database.=================================
            courseDAO.insert(new CourseEntity("Software I"
                    , "12/25/21", "01/02/22"
                    , "Completed", 1, 1));

            courseDAO.insert(new CourseEntity("Logic Design I"
                    , "01/25/21", "02/02/22"
                    , "Dropped", 2, 2));

            courseDAO.insert(new CourseEntity("Software II"
                    , "01/25/21", "02/02/22"
                    , "In Progress", 3, 2));

            courseDAO.insert(new CourseEntity("Computer Architecture"
                    , "12/25/22", "01/02/23"
                    , "Plan to Take", 2, 1));

            // Adding Assessments to the database.==================================
            assessmentDAO.insert(new AssessmentEntity("Assessment 1", "04/03/21", "06/05/21"
                    , "Objective", 1 ));

            assessmentDAO.insert(new AssessmentEntity("Assessment 2", "06/07/21","06/14/21"
                    , "Performance", 2 ));

            assessmentDAO.insert(new AssessmentEntity("Assessment 3","08/01/21", "09/01/21"
                    , "Objective", 1 ));

            // Adding Notes to the database====================================
            //NoteEntity(String noteTitle, String noteBodyText, int courseID)
            noteEntityDAO.insert(new NoteEntity("Software I note", "This is a test of the note for a Software I note "
                    , 1));

            noteEntityDAO.insert(new NoteEntity("Logic Design #1", "This is a Logic Design test of the note"
                    , 2));

            noteEntityDAO.insert(new NoteEntity("Software II #1", "This is a Software II test of the note"
                    , 3));

            noteEntityDAO.insert(new NoteEntity("Software I note #2", "This is a test of software  1 note the forth note note"
                    , 1));
             **/
            // Adding Course Instructors to the database.===========================
            courseInstructorDAO.insert(new CourseInstructorEntity("Matthew", "Heino"
                    ,"123-456-7890", "someemail@gmail.com"));

            courseInstructorDAO.insert(new CourseInstructorEntity("Bill", "Gates"
                    ,"222-444-7888", "someemail@outlook.com"));

            courseInstructorDAO.insert(new CourseInstructorEntity("Linus", "Torvalds"
                    ,"333-444-5555", "meemail@hotmail.com"));



            return null;

        } // end doInBackGround

    } // end BuildDBAsyncTask

} // end class.
