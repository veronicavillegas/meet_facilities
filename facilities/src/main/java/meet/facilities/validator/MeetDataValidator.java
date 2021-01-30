package meet.facilities.validator;

import org.springframework.stereotype.Service;

import meet.facilities.exception.InvalidInputDataException;

@Service
public class MeetDataValidator extends Validator {

	public void validateBeersByBox(int beersByBox) throws InvalidInputDataException {
        if (beersByBox < 1) {
            throw new InvalidInputDataException("Given beers by box is not valid");
        }
	}

	public void validateAttendants(int attendants) throws InvalidInputDataException {
        if (attendants < 2) {
            throw new InvalidInputDataException("Given attendants  is not valid");
        }
	}
    
}
