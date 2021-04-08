// Name: Subhaan Saleem     Matriculation Number: S1708061

package org.me.gcu.myapplication;

import java.io.Serializable;

public class PullParser implements Serializable
{ //Variables
    private String title;
    private String description;
    private String link;
    private String pubDate;
    private String category;
    private String geolat;
    private String geolong;
    private String depth;
    private String location;
    private String magnitude;

    public PullParser()
    {
    title = "";
    description = "";
    link = "";
    pubDate = "";
    category = "";
    geolat = "";
    geolong = "";

    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String atitle)
    {
        title = atitle;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String adescription) {
        description = adescription;
        String[] split = adescription.split(";|: ");
        for(int i = 0 ; i < split.length ; i ++)
        {
            if(i == 3)
            {
                this.setLocation(split[i].trim());
            }
            else if (i == 7)
            {
                this.setDepth(split[i].trim());
            } else if( i == 9)
            {
                this.setMagnitude(split[i].trim());
            }
        }
    } //Getters and Setters
    public String getLatitude()
    {
        return geolat;
    }

    public void setLatitude(String ageolat)
    {
        geolat = ageolat;
    }

    public String getLongitude()
    {
        return geolong;
    }

    public void setLongitude(String ageolong) { geolong = ageolong; }

    public String getDepth() { return depth; }

    public void setDepth(String aDepth) { depth = aDepth.replace("km", ""); }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String acategory)
    {
        category = acategory;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String alink)
    {
        link = alink;
    }

    public String getPubDate()
    {
        return pubDate;
    }

    public void setPubDate(String apubDate)
    {
        pubDate = apubDate;
    }


    public String toString() {
        String temp;

        temp = title + " " + description + " " + link + " " + pubDate + " " + category + " " + geolat + " " + geolong;

        return temp;
    }
    //Getters and Setters
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }
}
