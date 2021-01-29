package meet.facilities.validator;

import org.springframework.stereotype.Service;

import meet.facilities.dto.Meet;
import meet.facilities.exception.InvalidInputDataException;

@Service
public class MeetDataValidator extends Validator {

    public void validateMeet(Meet meet) throws InvalidInputDataException {
        if (meet == null) {
            throw new InvalidInputDataException("Given meet data is not valid");
        } else {
            super.validateDate(meet.getDate());
            super.validateLocation(meet.getLocation());
        }
	}

	public void validateBeersByBox(int beersByBox) throws InvalidInputDataException {
        if (beersByBox < 1) {
            throw new InvalidInputDataException("Given beers by box is not valid");
        }
	}

	public void validateAttendants(int attendants) throws InvalidInputDataException {
        if (attendants < 1) {
            throw new InvalidInputDataException("Given attendants  is not valid");
        }
	}
    
}
