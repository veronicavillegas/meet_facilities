package meet.facilities.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.tomcat.util.bcel.Const;

import meet.facilities.dto.Location;
import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.util.Constant;

public abstract class Validator {
    public void validateDate(Date date) throws InvalidInputDataException {
        if (date == null || date.before(new Date())) {
            throw new InvalidInputDataException(Constant.INVALID_DATE_MESSAGE);
        }
    }

    public void validateUser(String emailUser) throws InvalidInputDataException {
        if (emailUser == null || emailUser.isEmpty()) {
            throw new InvalidInputDataException(Constant.INVALID_USER_EMAIL_MESSAGE);
        }
    }


    public void validateLocation(Location location) throws InvalidInputDataException {
        if (!isValidCity(location.getCity())) {
            throw new InvalidInputDataException(Constant.INVALID_CITY_MESSAGE);
        }
        
        if (!isValidCountry(location.getCountry())){
            throw new InvalidInputDataException(Constant.INVALID_COUNTRY_MESSAGE);
        }
	}

    private boolean isValidCountry(String country) {
        return country != null && !country.equals("");
    }

    private boolean isValidCity(String city) {
        return city != null && !city.equals("");
    }
}
