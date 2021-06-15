package c196.mheino.myscheduler.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import c196.mheino.myscheduler.UI.AddNotificationActivity;
import c196.mheino.myscheduler.UI.AddCourseActivity;
import c196.mheino.myscheduler.UI.CourseDetailsActivity;
import c196.mheino.myscheduler.Entity.CourseEntity;
import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.ViewModels.TermDetailsViewModel;

/** Class for the Course Adapter.  This class will be used for the
 *  Recyclerview for the Course. It will aid in the displaying of data for
 *  Courses. It will define which components of the view wil be modified by
 *  the data that is contained in an CourseEntity object.
 *
 * @author  Matthew Heino
 *
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseHolder>{

    /* List of all available courses */
    List<CourseEntity> courseList = new ArrayList<>();

    /* An application context */
    Context context;

    // View Model
    TermDetailsViewModel termDetailsViewModel;

    /** A Course adapter constructor.  This constructor will be used to create
     *  a CourseAdapter object. It will receive a List of courses and the
     *  application Context.
     *
     * @param
     * @param context
     */
    public CourseAdapter(Context context, TermDetailsViewModel termDetailsViewModel) {

        // this.courseList = courseList;
        this.context = context;
        this.termDetailsViewModel = termDetailsViewModel;

    }

    /** Method to create the view holder for the Course. This method will
     *  use a XML layout to inflate the view for the Course.  The
     *  course holder will return a new CourseHolder that has been
     *  inflated with the given layout.
     *
     * @param parent
     * @param viewType
     * @return
     *
     */
    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View termView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.courses_list_item, parent, false);
        return new CourseHolder(termView);
    }


    /** Method to bind and set the different view components to the view
     *  holder.  The method will also set the listener for card that is used in
     *  the course.
     *
     * @param holder
     * @param position
     *
     */
    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {

        // Get the current course.
        CourseEntity currentCourse = courseList.get(position);

        // Set the view components that are in the current course
        holder.courseTitleTextView.setText(currentCourse.getCourseTitle());
        holder.courseStartDateTextView.setText(currentCourse.getStartDate());
        holder.courseEndDateTextView.setText(currentCourse.getEndDate());
        holder.courseStatusTextView.setText(currentCourse.getStatus());

        // Set the click listener for the image.
        holder.courseUpdateDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Will need to retrieve the ID*****************************************************************

                // Create the popup menu.
                PopupMenu courseMenu = new PopupMenu(context, holder.courseUpdateDelete);
                courseMenu.inflate(R.menu.popup_menu_courses);

                // Set a listener for the popup menu- used for updates and deletes.
                courseMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId())
                        {

                            case R.id.delete_course:

                                termDetailsViewModel.deleteCourse(currentCourse);
                                Toast.makeText(context, "Course "
                                        + currentCourse.getCourseTitle() + " is deleted", Toast.LENGTH_LONG).show();

                                break;
                            case R.id.update_course:

                                Intent termsIntent = new Intent(context.getApplicationContext(), AddCourseActivity.class);

                                termsIntent.putExtra(AddCourseActivity.EXTRA_MODE_ADD_COURSE, 2);
                                termsIntent.putExtra(AddCourseActivity.EXTRA_ADD_COURSE_ID, currentCourse.getCourseId());
                                termsIntent.putExtra(AddCourseActivity.EXTRA_COURSE_TITLE_ADD_COURSE, currentCourse.getCourseTitle());
                                termsIntent.putExtra(AddCourseActivity.EXTRA_START_DATE_ADD_COURSE, currentCourse.getStartDate());
                                termsIntent.putExtra(AddCourseActivity.EXTRA_END_DATE_ADD_COURSE, currentCourse.getEndDate());
                                termsIntent.putExtra(AddCourseActivity.EXTRA_STATUS_ADD_COURSE, currentCourse.getStatus());
                                termsIntent.putExtra(AddCourseActivity.EXTRA_INSTRUCTOR_ID_ADD_COURSE, currentCourse.getCourseInstructorID());
                                termsIntent.putExtra(AddCourseActivity.EXTRA_TERM_ID_ADD_COURSE, currentCourse.getTermId());

                                context.startActivity(termsIntent);
                                break;

                            case R.id.course_details_option:

                                //Launch course details activity
                                Intent courseDetailsIntent = new Intent(context.getApplicationContext(), CourseDetailsActivity.class);

                                // Add the extras to the Intent
                                int courseId = currentCourse.getCourseId();
                                String courseTitle = currentCourse.getCourseTitle();
                                String courseStartDate = currentCourse.getStartDate();
                                String endDate = currentCourse.getEndDate();
                                int instructorID = currentCourse.getCourseInstructorID();

                                courseDetailsIntent.putExtra
                                        (CourseDetailsActivity.EXTRA_DETAILS_COURSE_ID, courseId);
                                courseDetailsIntent.putExtra
                                        (CourseDetailsActivity.EXTRA_DETAILS_COURSE_TITLE, courseTitle);
                                courseDetailsIntent.putExtra
                                        (CourseDetailsActivity.EXTRA_DETAILS_START_DATE_TERM, courseStartDate);
                                courseDetailsIntent.putExtra
                                        (CourseDetailsActivity.EXTRA_DETAILS_END_DATE_TERM, endDate);
                                courseDetailsIntent.putExtra
                                        (CourseDetailsActivity.EXTRA_DETAILS_INSTRUCTOR_ID_TERM, instructorID);

                                context.startActivity(courseDetailsIntent);

                                break;


                            case R.id.course_notification_option:

                                Intent courseNotificationIntent = new  Intent( context.getApplicationContext()
                                        , AddNotificationActivity.class);

                                courseNotificationIntent.putExtra(AddNotificationActivity.EXTRA_NOTIFICATION_INFO_TEXT
                                        , currentCourse.getCourseTitle());

                                courseNotificationIntent.putExtra(AddNotificationActivity.EXTRA_NOTIFICATION_TYPE_TO_ADD
                                        , "course");

                                context.startActivity(courseNotificationIntent);

                        } // end switch

                        return false;

                    }
                });

                courseMenu.show();

            } // end onClick.

        });

    } // end onBindViewHolder.

    /** Method to return the size of the course list.  This method will
     *  return an int, the size of the current course list.
     *
     * @return
     */
    @Override
    public int getItemCount() {  return courseList.size(); }


    /** Method to set the List of courses. This method will set the courses that
     *  will be displayed in the RecyclerView.
     *
     * @param courses
     */
    public void setCourses(List<CourseEntity> courses){

        this.courseList = courses;
        notifyDataSetChanged();

    }

    /** Method to retrieve a course at a given position.  This method will return
     *  a CourseEntity Object that is found at a given position within the list
     *  of courses.
     *
     * @param position
     * @return
     */
    public CourseEntity getTermAt(int position){ return courseList.get(position); }

} // end CourseAdapter
