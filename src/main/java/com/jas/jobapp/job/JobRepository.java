package com.jas.jobapp.job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    Job getCompanyById(Long companyId);
}
