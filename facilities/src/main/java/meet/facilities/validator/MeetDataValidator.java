package meet.facilities.validator;

import org.springframework.stereotype.Service;

import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.util.Constant;

@Service
public class MeetDataValidator extends Validator {

	public void validateBeersByBox(int beersByBox) throws InvalidInputDataException {
        if (beersByBox < 1) {
            throw new InvalidInputDataException(Constant.INVALID_BEERS_BY_BOX_MESSAGE);
        }
	}

	public void validateAttendants(int attendants) throws InvalidInputDataException {
        if (attendants < 2) {
            throw new InvalidInputDataException(Constant.INVALID_ATTENDANTS_MESSAGE);
        }
	}
    
}
