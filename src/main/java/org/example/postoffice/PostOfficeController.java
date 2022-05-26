package org.example.postoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/office")
public class PostOfficeController {
    @Autowired
    PostOfficeService postOfficeService;

    @GetMapping
    public ResponseEntity<List<PostOffice>> getDeparturesList() {
        return ResponseEntity.ok(postOfficeService.getOffices());
    }

    @GetMapping("{officeId}")
    public ResponseEntity<PostOffice> getDeparture(@PathVariable Long officeId) {
        PostOffice postOffice = postOfficeService.getOffice(officeId);
        if(postOffice==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(postOffice);
    }

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            "application/vnd.example.todo_payload+json"})
    public ResponseEntity<PostOffice> createDeparture(@RequestBody PostOfficeOnload payload) {
        return ResponseEntity.ok(postOfficeService.addOffice(payload));
    }

    @GetMapping(path = "{officeId}/edit", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            "application/vnd.example.todo_payload+json"
    })
    public ResponseEntity<PostOffice> modifyDeparture(@PathVariable Long officeId, @RequestBody PostOfficeOnload onload){
        PostOffice postOffice = postOfficeService.modifyOffice(onload, officeId);
        if (postOffice==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(postOffice);
    }
    @GetMapping("{officeId}/delete")
    public ResponseEntity<Void> deleteDeparture(@PathVariable Long officeId){
        if (postOfficeService.deleteOffice(officeId)==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
