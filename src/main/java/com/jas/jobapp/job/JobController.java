package com.jas.jobapp.job;

import com.jas.jobapp.reviews.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping("/{companyId}")
    public ResponseEntity<String> createJob(@PathVariable Long companyId, @RequestBody Job job) {
        boolean isJobSaved = jobService.addJob(companyId, job);
        if (isJobSaved) {
            return new ResponseEntity<>("Job added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findJob(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if(job != null) {
            return new ResponseEntity<>(job, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        boolean deleted = jobService.deleteJobById(id);
        if (deleted)
            return new ResponseEntity<>("Job successfully deleted", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {
        boolean updated = jobService.updateJob(id, updatedJob);
        if (updated) {
            return new ResponseEntity<>("Job successfully updated", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
