package c196.mheino.myscheduler.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import c196.mheino.myscheduler.MyUtils.DateUtil;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.Receiver.SchedulerReceiver;

public class AddNotificationActivity extends AppCompatActivity {

    //Keys
    public static final String EXTRA_NOTIFICATION_INFO_TEXT = "c196.mheino.myscheduler.EXTRA_NOTIFICATION_INFO_TEXT";
    public static final String EXTRA_NOTIFICATION_TYPE_TO_ADD = "c196.mheino.myscheduler.EXTRA_NOTIFICATION_TYPE_TO_ADD";

    DateUtil dateChecker = new DateUtil();


    // View Components
    private EditText notificationStartDateET;
    private EditText notificationEndDateET;
    private Button setStartDateButton;
    private Button setEndDateButton;
    private TextInputLayout errorSetStartDate;
    private TextInputLayout errorSetEndDate;


    // Calendars
    Calendar startNotificationCalendar= Calendar.getInstance();
    Calendar endNotificationCalendar = Calendar.getInstance();

    //Date listeners.
    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;

    // Notifications
    private String notificationInfo;
    private String notificationType;

    private AlarmManager notificationAlarmManager;
    private PendingIntent startNotificationPendingIntent;
    private PendingIntent endNotificationPendingIntent;
    public static int ALARM_NOTIFICATION_START = 20;
    public static int  ALARM_NOTIFICATION_END = 50;
    private long alarmStartDate;
    private long alarmEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_notification_tool_bar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        // Get the view components
        notificationStartDateET = findViewById(R.id.course_notification_start_date_edit_text);
        notificationEndDateET = findViewById(R.id.course_notification_end_date_edit_text);

        // NEW******************************************************************************
        setStartDateButton = findViewById(R.id.add_start_notification_button);
        errorSetStartDate = findViewById(R.id.error_start_date);

        setEndDateButton = findViewById(R.id.add_end_notification_button);
        errorSetEndDate = findViewById(R.id.error_end_date);

        // Create Alarm manager.
        notificationAlarmManager = (AlarmManager) AddNotificationActivity
                .this.getSystemService(ALARM_SERVICE);

        // Get the intent and the extra.*******
        Intent addNotification = getIntent();

        if(addNotification.hasExtra(EXTRA_NOTIFICATION_INFO_TEXT)){

            notificationInfo = addNotification.getStringExtra(EXTRA_NOTIFICATION_INFO_TEXT);
            notificationType = addNotification.getStringExtra(EXTRA_NOTIFICATION_TYPE_TO_ADD);

            this.setTitle(getResources().getString(R.string.add_notification_title)
                   + " for "+ notificationInfo);

        }

        setStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStartDateValidation();
            }
        });

        setEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndDateValidation();
            }
        });

        // Add listeners for the edit text fields for the dates.***************
        notificationStartDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create the DatePicker for the start date.
                new DatePickerDialog(AddNotificationActivity.this
                        , startDateListener
                        , startNotificationCalendar.get(Calendar.YEAR)
                        , startNotificationCalendar.get(Calendar.MONTH)
                        , startNotificationCalendar.get(Calendar.DAY_OF_MONTH)).show();

            } // end on click.
        });

        notificationEndDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(AddNotificationActivity.this
                        , endDateListener
                        , endNotificationCalendar.get(Calendar.YEAR)
                        , endNotificationCalendar.get(Calendar.MONTH)
                        , endNotificationCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        // Create the date pickers.
        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                startNotificationCalendar.set(Calendar.YEAR, year);
                startNotificationCalendar.set(Calendar.MONTH,month);
                startNotificationCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                alarmStartDate = startNotificationCalendar.getTimeInMillis();

                updateStartDate();

            } // end onDateSet
        };

        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                endNotificationCalendar.set(Calendar.YEAR, year);
                endNotificationCalendar.set(Calendar.MONTH, month);
                endNotificationCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                alarmEndDate = endNotificationCalendar.getTimeInMillis();

                updateEndDate();

            }
        };


    } // end onCreate


    private void setEndDateValidation() {

        String endDateString = notificationEndDateET.getText().toString().trim();

        if(TextUtils.isEmpty(endDateString))
        {
            errorSetEndDate.setError(getResources().getString(R.string.notification_end_date_error_empty_field));

            Toast.makeText(AddNotificationActivity.this
                    , getResources().getString(R.string.notification_end_date_error_empty_field)
                    , Toast.LENGTH_LONG).show();

        }
        else if(!dateChecker.isValidDateString(endDateString))
        {
            errorSetEndDate.setError(getResources().getString(R.string.invalid_format_field));
            Toast.makeText(AddNotificationActivity.this
                    ,getResources().getString(R.string.notification_start_date_error_empty_field)
                    , Toast.LENGTH_SHORT).show();

        }
        else
        {

            setEndNotification();

        }

    }

    /** Method to check that a valid start date has been entered.  This method
     *  will check to see if the user has entered a date into the field.  If
     *  the field is left blank an error will appear.
     *
     */
    private void setStartDateValidation() {

        String startDateString = notificationStartDateET.getText().toString().trim();

        if(TextUtils.isEmpty(startDateString))
        {

            errorSetStartDate.setError(getResources().getString(R.string.notification_start_date_error_empty_field));
            Toast.makeText(AddNotificationActivity.this
                    ,getResources().getString(R.string.notification_start_date_error_empty_field)
                    , Toast.LENGTH_SHORT).show();

        }
        else if(!dateChecker.isValidDateString(startDateString)){

            errorSetStartDate.setError(getResources().getString(R.string.invalid_format_field));
            Toast.makeText(AddNotificationActivity.this
                    ,getResources().getString(R.string.notification_start_date_error_empty_field)
                    , Toast.LENGTH_SHORT).show();

        }
        else
            {

            setStartNotification();

        }
    }

    /** Method to update the end date.  This method will update the start
     *  date text field. It will format the date in US locale
     *
     */
    private void updateEndDate() {

        String startDateFormat ="MM/dd/yy";
        SimpleDateFormat sdf  = new SimpleDateFormat(startDateFormat, Locale.US);

        // Update the start date EditText.
        notificationEndDateET.setText(sdf.format(endNotificationCalendar.getTime()));

    }

    /** Method to update the start date.  This method will update the end
     *  date text field. It will format the date in US locale
     *
     */
    private void updateStartDate() {

        String startDateFormat ="MM/dd/yy";
        SimpleDateFormat sdf  = new SimpleDateFormat(startDateFormat, Locale.US);

        // Update the start date EditText.
        notificationStartDateET.setText(sdf.format(startNotificationCalendar.getTime()));
    }

    /** Method to process to set the start of the notification
     *
     * @param
     */
    public void setStartNotification(){

        // Create the start notification intent.
        Intent startDateAlarm = new Intent(AddNotificationActivity.this
                , SchedulerReceiver.class);

        // Add the extras.
        startDateAlarm.putExtra(SchedulerReceiver.EXTRA_SCHEDULER_ALARM, "Your "
                + notificationInfo + " " + notificationType + " starts today! "
                + notificationStartDateET.getText().toString().trim());

        startDateAlarm.putExtra(SchedulerReceiver.EXTRA_SCHEDULER_ALARM_TITLE
                , notificationInfo + " " + notificationType);


        startNotificationPendingIntent = PendingIntent
                .getBroadcast(AddNotificationActivity.this
                        , ALARM_NOTIFICATION_START++, startDateAlarm, 0);


        notificationAlarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartDate
                , startNotificationPendingIntent);

        Toast.makeText(AddNotificationActivity.this
                , "Start date has been set!"
                , Toast.LENGTH_LONG).show();


    }

    /** Method to process to set the end of the notification.
     *
     * @param
     */
    public void setEndNotification(){

        Intent endDateAlarm = new Intent(AddNotificationActivity.this
                , SchedulerReceiver.class);

        endDateAlarm.putExtra(SchedulerReceiver.EXTRA_SCHEDULER_ALARM, "Your "
                + notificationInfo + " " + notificationType + " ends today! "
                + notificationEndDateET.getText().toString());

        endDateAlarm.putExtra(SchedulerReceiver.EXTRA_SCHEDULER_ALARM_TITLE
                , notificationInfo + " " + notificationType);

        endNotificationPendingIntent = PendingIntent.getBroadcast(AddNotificationActivity.this
                ,ALARM_NOTIFICATION_END++, endDateAlarm, 0 );

        notificationAlarmManager.set(AlarmManager.RTC_WAKEUP, alarmEndDate
                , endNotificationPendingIntent);

        Toast.makeText(AddNotificationActivity.this
                , "End date has been set!"
                , Toast.LENGTH_LONG).show();


    }

} // end class