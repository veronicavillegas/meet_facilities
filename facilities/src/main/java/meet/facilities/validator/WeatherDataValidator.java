package meet.facilities.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meet.facilities.dto.Location;
import meet.facilities.exception.InvalidInputDataException;

@Service
public class WeatherDataValidator extends Validator {
    @Autowired
    public WeatherDataValidator() {

    } 
}

	
