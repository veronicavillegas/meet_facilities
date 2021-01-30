package meet.facilities.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public static Date parseToDate(String value) throws ParseException {
        return new SimpleDateFormat(Constant.DATE_FORMAT).parse(value);
    }
}
