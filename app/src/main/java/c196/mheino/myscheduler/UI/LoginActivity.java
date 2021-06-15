package c196.mheino.myscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import c196.mheino.myscheduler.Entity.UserEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.UserViewModel;

/** The login class.  This will be the main entry point for the application.
 *  This class will provide the login functionality for the application.
 *
 * @author Matthew Heino
 *
 */
public class LoginActivity extends AppCompatActivity {

    //  View Components
    private EditText userName;
    private EditText userPasswword;
    private Button clearButton;
    private Button loginButton;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get View components.
        userName = findViewById(R.id.username_et);
        userPasswword = findViewById(R.id.password_et);
        clearButton = findViewById(R.id.clear_button);
        loginButton = findViewById(R.id.loginin_button);

        // Create the View Model
        userViewModel =  ViewModelProviders.of(this).get(UserViewModel.class);

        // Set the listeners
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Clear the EditText fields
                userName.setText("");
                userPasswword.setText("");

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = userName.getText().toString().trim();
                String userPassword = userPasswword.getText().toString().trim();

                if(!username.isEmpty() && !userPassword.isEmpty()) {

                    UserEntity loginUser = userViewModel.getUserInfo(username, userPassword);

                    //Launch main screen
                    if(loginUser != null) {
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                    }
                    else{
                        // Show an error message
                        Toast.makeText(LoginActivity.this
                                ,getResources().getString(R.string.error_user_info),
                                Toast.LENGTH_SHORT).show();
                    }

                }
                else
                    Toast.makeText(LoginActivity.this
                            ,getResources().getString(R.string.empty_user_pass),
                            Toast.LENGTH_SHORT).show();

            } // end on Click
        });

    } // end onCreate.

} // end class