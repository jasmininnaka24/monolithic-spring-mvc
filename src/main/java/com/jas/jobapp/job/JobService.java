package com.jas.jobapp.job;

import java.util.List;

public interface JobService {
    List<Job> findAll();
    Job getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean updateJob(Long id, Job updatedJob);

    boolean addJob(Long companyId, Job job);
}
