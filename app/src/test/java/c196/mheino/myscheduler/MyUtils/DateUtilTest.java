package c196.mheino.myscheduler.MyUtils;

import junit.framework.TestCase;

public class DateUtilTest extends TestCase {

    DateUtil myNewDateUtil = new DateUtil();

    String startDate = "05/05/21";
    String endDate = "08/25/21";
    String checkDate = "06/15/21";

    String inValidDateString = "005-23-21";

    public void testIsDateBetween() {
        assertTrue(myNewDateUtil.isDateBetween(checkDate, startDate, endDate));
    }

    public void testIsValidDateString() {
        assertFalse(myNewDateUtil.isValidDateString(inValidDateString));
    }

    public void testIsWithinOneWeek() {
        assertFalse(myNewDateUtil.isWithinOneWeek(checkDate));
    }

    public void testIsWithinOneMonth() {
        assertTrue(myNewDateUtil.isWithinOneMonth(checkDate));
    }
}