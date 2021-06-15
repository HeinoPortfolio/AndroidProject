package c196.mheino.myscheduler.MyUtils;


import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;


/** This class will provide method for dealing with dates. This class will
 *  determine the validity of a date, as well as determine if a date falls
 *  within a date range.
 *
 * @author Matthew Heino
 *
 */
public class DateUtil {


    // Format of the accepted date
    private DateTimeFormatter dtf = DateTimeFormatter
            .ofPattern("MM/dd/yy", Locale.ENGLISH);

    /** Constructor of the class.  This constructor will take no parameters.
     *  Will be used to invoke the the method of the class
     *
     */
    public DateUtil() {
    }

    /** This method will check to see if a date falls within a given date of
     *  ranges. The start date will have one day subtracted from it and the
     *  end date will have one day added to it.
     *
     * @param checkDate
     * @param startDate
     * @param endDate
     * @return
     */
    public boolean isDateBetween(String checkDate, String startDate
            , String endDate)
    {
        boolean isValid = false;

        try{
            LocalDate startDateDTF = LocalDate.parse(startDate, dtf);
            LocalDate endDateDTF = LocalDate.parse(endDate, dtf);
            LocalDate checkDateDTF = LocalDate.parse(checkDate, dtf);

            if(checkDateDTF.isAfter(startDateDTF.minusDays(1))
                    && checkDateDTF.isBefore(endDateDTF.plusDays(1)))
            {

                isValid = true;
            }
        }
        catch(DateTimeParseException dpe){

            System.err.println("Invalid date!");
        }
        return isValid;
    }

    /** Method to determine if a given string is a valid date.  This method
     *  will determine if a date String matches a given date pattern. The
     *  pattern is MM/dd/yy.
     *
     * @param checkString
     * @return
     */
    public boolean isValidDateString(String checkString){

        try{

            LocalDate.parse(checkString, this.dtf);
        }
        catch(DateTimeParseException dtpe){

            return false;
        }

        return true;

    } // end isValidDateString

    /** Method to determine if a given date string falls within one week.  This
     *  method will determine if a given date is within one week of the current
     *  date.  Will return true if the date string is within seven days
     *  inclusive of the current date.
     *
     * @param date
     * @return
     */
    public boolean isWithinOneWeek(String date){

        boolean isWithinOneWeek = false;

        DateTimeFormatter dtf = DateTimeFormatter
                .ofPattern("MM/dd/yy", Locale.ENGLISH);

        LocalDate currentDate = LocalDate.now();

        try{

            LocalDate dateDTF = LocalDate.parse(date, dtf);

            if(dateDTF.isAfter(currentDate.minusDays(1))
                    && dateDTF.isBefore(currentDate.plusDays(7)))
            {

                isWithinOneWeek = true;
            }
        }
        catch(DateTimeParseException dpe){

            System.err.println("Invalid date!");
        }

        return isWithinOneWeek;

    } // end IsWithinOneWeek

    /** Method to determine if a given date string falls within one month
     *  (30 days). This method will determine if a given date is within one
     *  month of the current date.  Will return true if the data string is
     *  within 30 days inclusive of the current date.
     *
     * @param date
     * @return
     *
     */
    public boolean isWithinOneMonth(String date){

        boolean isWithinOneMonth = false;

        DateTimeFormatter dtf = DateTimeFormatter
                .ofPattern("MM/dd/yy", Locale.ENGLISH);

        LocalDate currentDate = LocalDate.now();

        try{

            LocalDate dateDTF = LocalDate.parse(date, dtf);

            if(dateDTF.isAfter(currentDate.minusDays(1))
                    && dateDTF.isBefore(currentDate.plusDays(31)))
            {

                isWithinOneMonth = true;
            }
        }
        catch(DateTimeParseException dpe){

            System.err.println("Invalid date!");
        }

        return isWithinOneMonth;

    } // end IsWithinOneMonth

} // end class.

