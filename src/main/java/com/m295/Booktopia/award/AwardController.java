package com.m295.booktopia.award;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class AwardController {

    @Autowired
    private AwardService awardService;
   

    @GetMapping("/api/award")
    public List<Award> getAllAwards() {
        return awardService.getAllAwards();
    }

    @GetMapping("/api/award/{id}")
    public ResponseEntity<Award> getAwardById(@PathVariable Long id) {
        Optional<Award> award = awardService.getAwardById(id);
        return award.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

	
    @PostMapping("/api/award")
    public ResponseEntity<Award> createAward(@RequestBody Award award) {
        return new ResponseEntity<>(awardService.createAward(award), HttpStatus.CREATED);
    }

    @PutMapping("/api/award/{id}")
    public ResponseEntity<Award> updateAward(@PathVariable Long id, @RequestBody Award award) {
        award.setId(id);
        return new ResponseEntity<>(awardService.updateAward(award), HttpStatus.OK);
    }

    @DeleteMapping("/api/award/{id}")
    public ResponseEntity<Void> deleteAward(@PathVariable Long id) {
        awardService.deleteAward(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

