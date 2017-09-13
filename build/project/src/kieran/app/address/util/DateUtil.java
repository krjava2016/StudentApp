package kieran.app.address.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/*
  Helper functions for handling dates.
 */
public class DateUtil {

    // The date pattern that is used for conversion.
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    // The date formatter. 
    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    // Returns the given date as String.
     
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

   
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /*
      Checks the String whether it is a valid date.
      true if the String is a valid date
     */
    public static boolean validDate(String dateString) {
      
        return DateUtil.parse(dateString) != null;
    }
}
