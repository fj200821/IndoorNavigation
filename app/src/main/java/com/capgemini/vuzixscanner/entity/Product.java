package com.capgemini.vuzixscanner.entity;

public class Product
{
    private String productDescription;

    private String[] gallery;

    private String rfid_Mum;

    private String barcode;

    private String ImageUrl;

    private String size;

    private String productId;

    private String discount;

    private String color;

    private String productAvailability;

    private Brand brand;

    private String rating;

    private String rfid_Ban;

    private String productName;

    private String productPrice;

    private String videoURL;

    private Review[] review;

    public String getProductDescription ()
    {
        return productDescription;
    }

    public void setProductDescription (String productDescription)
    {
        this.productDescription = productDescription;
    }

    public String[] getGallery ()
    {
        return gallery;
    }

    public void setGallery (String[] gallery)
    {
        this.gallery = gallery;
    }

    public String getRfid_Mum ()
    {
        return rfid_Mum;
    }

    public void setRfid_Mum (String rfid_Mum)
    {
        this.rfid_Mum = rfid_Mum;
    }

    public String getBarcode ()
    {
        return barcode;
    }

    public void setBarcode (String barcode)
    {
        this.barcode = barcode;
    }

    public String getImageUrl ()
    {
        return ImageUrl;
    }

    public void setImageUrl (String ImageUrl)
    {
        this.ImageUrl = ImageUrl;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }

    public String getProductId ()
    {
        return productId;
    }

    public void setProductId (String productId)
    {
        this.productId = productId;
    }

    public String getDiscount ()
    {
        return discount;
    }

    public void setDiscount (String discount)
    {
        this.discount = discount;
    }

    public String getColor ()
    {
        return color;
    }

    public void setColor (String color)
    {
        this.color = color;
    }

    public String getProductAvailability ()
    {
        return productAvailability;
    }

    public void setProductAvailability (String productAvailability)
    {
        this.productAvailability = productAvailability;
    }

    public Brand getBrand ()
    {
        return brand;
    }

    public void setBrand (Brand brand)
    {
        this.brand = brand;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    public String getRfid_Ban ()
    {
        return rfid_Ban;
    }

    public void setRfid_Ban (String rfid_Ban)
    {
        this.rfid_Ban = rfid_Ban;
    }

    public String getProductName ()
    {
        return productName;
    }

    public void setProductName (String productName)
    {
        this.productName = productName;
    }

    public String getProductPrice ()
    {
        return productPrice;
    }

    public void setProductPrice (String productPrice)
    {
        this.productPrice = productPrice;
    }

    public String getVideoURL ()
    {
        return videoURL;
    }

    public void setVideoURL (String videoURL)
    {
        this.videoURL = videoURL;
    }

    public Review[] getReview ()
    {
        return review;
    }

    public void setReview (Review[] review)
    {
        this.review = review;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [productDescription = "+productDescription+", gallery = "+gallery+", rfid_Mum = "+rfid_Mum+", barcode = "+barcode+", ImageUrl = "+ImageUrl+", size = "+size+", productId = "+productId+", discount = "+discount+", color = "+color+", productAvailability = "+productAvailability+", brand = "+brand+", rating = "+rating+", rfid_Ban = "+rfid_Ban+", productName = "+productName+", productPrice = "+productPrice+", videoURL = "+videoURL+", review = "+review+"]";
    }
}

