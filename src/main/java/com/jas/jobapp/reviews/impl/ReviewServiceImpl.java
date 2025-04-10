package com.jas.jobapp.reviews.impl;

import com.jas.jobapp.company.Company;
import com.jas.jobapp.company.CompanyService;
import com.jas.jobapp.reviews.Review;
import com.jas.jobapp.reviews.ReviewRepository;
import com.jas.jobapp.reviews.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    public ReviewRepository getReviewRepository() {
        return reviewRepository;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);

        return reviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            updatedReview.setCompany(company);
            updatedReview.setId(reviewId);
            reviewRepository.save(updatedReview);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null && reviewRepository.existsById(reviewId)) {
            Review review = reviewRepository.findById(reviewId).orElse(null);
            // removing from the list in company entity list
            company.getReviews().remove(review);
            // updating company's review list
            companyService.updateCompany(company, companyId);
            // deleting from the database
            reviewRepository.deleteById(reviewId);
            return true;
        } else {
            return false;
        }
    }
}
