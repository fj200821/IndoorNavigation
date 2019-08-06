package com.capgemini.vuzixscanner.entity;

public class Review
{
    private String reviewerImage;

    private String reviewerEmail;

    private String reviewerName;

    private String review;

    public String getReviewerImage ()
    {
        return reviewerImage;
    }

    public void setReviewerImage (String reviewerImage)
    {
        this.reviewerImage = reviewerImage;
    }

    public String getReviewerEmail ()
    {
        return reviewerEmail;
    }

    public void setReviewerEmail (String reviewerEmail)
    {
        this.reviewerEmail = reviewerEmail;
    }

    public String getReviewerName ()
    {
        return reviewerName;
    }

    public void setReviewerName (String reviewerName)
    {
        this.reviewerName = reviewerName;
    }

    public String getReview ()
    {
        return review;
    }

    public void setReview (String review)
    {
        this.review = review;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [reviewerImage = "+reviewerImage+", reviewerEmail = "+reviewerEmail+", reviewerName = "+reviewerName+", review = "+review+"]";
    }
}