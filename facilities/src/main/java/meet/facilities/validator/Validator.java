package meet.facilities.validator;

import java.util.Date;

import meet.facilities.dto.Location;
import meet.facilities.dto.User;
import meet.facilities.exception.InvalidInputDataException;

public abstract class Validator {
    public void validateDate(Date date) throws InvalidInputDataException {
        if (date == null || date.before(new Date())) {
            throw new InvalidInputDataException("Given date is not valid");
        }
    }

    public void validateUser(String emailUser) throws InvalidInputDataException {
        //TODO: Mockear api rest para validar user
        if (emailUser == null || emailUser.isEmpty()) {
            throw new InvalidInputDataException("Given email user is not valid");
        }
    }


    public void validateLocation(Location location) throws InvalidInputDataException {
        if (!isValidCity(location.getCity())) {
            throw new InvalidInputDataException("Given city is not valid");
        }
        
        if (!isValidCountry(location.getCountry())){
            throw new InvalidInputDataException("Given country is not valid");
        }
	}

    private boolean isValidCountry(String country) {
        return country != null && !country.equals("");
    }

    private boolean isValidCity(String city) {
        return city != null && !city.equals("");
    }
}
