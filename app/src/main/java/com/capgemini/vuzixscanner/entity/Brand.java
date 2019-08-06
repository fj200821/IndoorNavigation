package com.capgemini.vuzixscanner.entity;

public class Brand
{
    private String companyName;

    private String logoImgURL;

    public String getCompanyName ()
    {
        return companyName;
    }

    public void setCompanyName (String companyName)
    {
        this.companyName = companyName;
    }

    public String getLogoImgURL ()
    {
        return logoImgURL;
    }

    public void setLogoImgURL (String logoImgURL)
    {
        this.logoImgURL = logoImgURL;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [companyName = "+companyName+", logoImgURL = "+logoImgURL+"]";
    }
}

