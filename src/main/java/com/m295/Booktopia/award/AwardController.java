package com.m295.booktopia.award;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.m295.booktopia.security.Roles;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class AwardController {

    @Autowired
    private AwardService awardService;
   

    @GetMapping("/api/award")
    @RolesAllowed({Roles.Admin, Roles.Read, Roles.Update})
    public List<Award> getAllAwards() {
        return awardService.getAllAwards();
    }

    @GetMapping("/api/award/{id}")
    @RolesAllowed({Roles.Admin, Roles.Read, Roles.Update})
    public ResponseEntity<Award> getAwardById(@PathVariable Long id) {
        Optional<Award> award = awardService.getAwardById(id);
        return award.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

	
    @PostMapping("/api/award")
    @RolesAllowed({Roles.Admin})
    public ResponseEntity<Award> createAward(@RequestBody Award award) {
        return new ResponseEntity<>(awardService.createAward(award), HttpStatus.CREATED);
    }

    @PutMapping("/api/award/{id}")
    @RolesAllowed({Roles.Admin, Roles.Update})
    public ResponseEntity<Award> updateAward(@PathVariable Long id, @RequestBody Award award) {
        award.setId(id);
        return new ResponseEntity<>(awardService.updateAward(award), HttpStatus.OK);
    }

    @DeleteMapping("/api/award/{id}")
    @RolesAllowed({Roles.Admin})
    public ResponseEntity<Void> deleteAward(@PathVariable Long id) {
        awardService.deleteAward(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

