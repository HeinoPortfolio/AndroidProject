package c196.mheino.myscheduler.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import c196.mheino.myscheduler.Entity.TermEntity;
import c196.mheino.myscheduler.Entity.UserEntity;

/** An interface for the User Entity.  This interface will be used to interact
 *  with the Room database.  It is the data access object that will handle all
 *  manipulation of the User table in the ROOM database.  This DAO will allow
 *  the following actions: 1) insertions 2) updates 3) deletion of a single
 *  Term 4) deletion of all terms from the table 5) The retrieval of all the
 *  terms that are currently in the database.
 *
 * @author Matthew Heino
 *
 */
@Dao
public interface UserDAO {

    /** Method to insert a single user into the database. */
    @Insert
    void insert(UserEntity term);

    @Query("SELECT * FROM user_table WHERE userName = :username AND userPassword = :userpassword")
    UserEntity getUserInformation(String username, String userpassword);

} // end User DAO
