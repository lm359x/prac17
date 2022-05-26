package org.example.departure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/departures_filter")
public class DepartureFilterController {
    @Autowired
    private DepartureService departureService;

    @GetMapping("{field}/{param}")
    public ResponseEntity<List<Departure>> filterDepartures(@PathVariable String field, @PathVariable String param){
        return ResponseEntity.ok(departureService.filterDepartures(field, param));
    }
}
