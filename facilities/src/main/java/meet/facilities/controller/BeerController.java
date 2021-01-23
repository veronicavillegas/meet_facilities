package meet.facilities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meetup")
public class BeerController {
    @Autowired
    public BeerController() {

    }
    
    @GetMapping
    public ResponseEntity<Beer> calculateBeer() {

    }
}
