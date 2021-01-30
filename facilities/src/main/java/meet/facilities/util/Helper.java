package meet.facilities.util;

;
ParseException;

import java.util.Date;

import meet.facilities.exception.InvalidInputDataException;

public class Helper {

    public static Date parseToDate(String value) throws InvalidInputDataException {
        try {
            return new SimpleDateFormat(Constant.DATE_FORMAT).parse(value);
        } catch (ParseException ex) {
            throw new InvalidInputDataException("Date is not valid value");
        } 
    }
}
