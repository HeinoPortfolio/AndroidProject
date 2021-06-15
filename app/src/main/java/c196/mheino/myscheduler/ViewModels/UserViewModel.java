package c196.mheino.myscheduler.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import c196.mheino.myscheduler.Database.SchedulerRepository;
import c196.mheino.myscheduler.Entity.UserEntity;

public class UserViewModel extends AndroidViewModel {

    // Repository for the TermDetailsViewModel.
    private SchedulerRepository userRepository;


    /** Constucot for the the UserViewModel
     *
     * @param application
     */
    public UserViewModel(@NonNull Application application) {

        super(application);
        userRepository = new SchedulerRepository(application);

    } // end constructor


    /** Method to retrieve the user's information.  This method will return the users'
     *
     * @param username
     * @param userpassword
     * @return
     */
    public UserEntity getUserInfo(String username, String userpassword){

        return userRepository.getUserInfo(username, userpassword);

    } // end getUserInfo

} // end class
