package c196.mheino.myscheduler.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/** This class will model data for the user. This class will be used to model
 *  the data that will compose the database table that will hold data about the
 *  user. The no primary key will be by the database.
 *
 * @author  Matthew Heino
 */
@Entity(tableName = "user_table")
public class UserEntity {

    @NonNull
    @PrimaryKey
    private String userName;
    private String userPassword;


    /** Constructor of the class. This constructor will take two parameters.
     *  The parameters are the String user name and the String user password.
     *
     * @param userName
     * @param userPassword
     *
     */
    public UserEntity(String userName, String userPassword) {

        this.userName = userName;
        this.userPassword = userPassword;

    } // end constructor.

    // Setters and Getters of the class.========================================
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
} // end class
