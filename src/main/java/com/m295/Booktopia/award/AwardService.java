package com.m295.booktopia.award;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m295.booktopia.book.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AwardService {

    @Autowired
    private AwardRepository awardRepository;

    public AwardService(AwardRepository awardRepository) {
    	this.awardRepository = awardRepository;
    }
    
    public List<Award> getAllAwards() {
        return awardRepository.findAll();
    }

    public Optional<Award> getAwardById(Long id) {
        return awardRepository.findById(id);
    }

    public Award createAward(Award award) {
        return awardRepository.save(award);
    }

    
    public Award updateAward(Award award) {
        return awardRepository.save(award);
    }

    public void deleteAward(Long id) {
        awardRepository.deleteById(id);
        
    }
    

}
