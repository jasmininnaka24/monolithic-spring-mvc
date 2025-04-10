package com.jas.jobapp.job.impl;

import com.jas.jobapp.company.Company;
import com.jas.jobapp.company.CompanyService;
import com.jas.jobapp.job.Job;
import com.jas.jobapp.job.JobRepository;
import com.jas.jobapp.job.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private JobRepository jobRepository;
    private CompanyService companyService;

    public JobServiceImpl(JobRepository jobRepository, CompanyService companyService) {
        this.jobRepository = jobRepository;
        this.companyService = companyService;
    }

    public JobRepository getJobRepository() {
        return jobRepository;
    }

    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }


    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }



    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
        if (!jobRepository.existsById(id)) {
            return false;
        }
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }

    @Override
    public boolean addJob(Long companyId, Job job) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            job.setCompany(company);
            jobRepository.save(job);
            return true;
        }
        return false;
    }

}
