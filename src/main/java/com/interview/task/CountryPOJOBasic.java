package com.interview.task;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Comparator;

@XmlRootElement(name = "country")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryPOJOBasic {
    private String name;
    private String alpha2code;
    private String alpha3code;
    private double latitude;
    private double longitude;

    public CountryPOJOBasic() {
    }

    public CountryPOJOBasic(String name, String alpha2code, String alpha3code, double latitude, double longitude) {
        this.name = name;
        this.alpha2code = alpha2code;
        this.alpha3code = alpha3code;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //    implementing Comparator as anonymous inner class, for latitude:
    public static Comparator<CountryPOJOBasic> latComparatorDesc = new Comparator<CountryPOJOBasic>() {
        public int compare(CountryPOJOBasic country1, CountryPOJOBasic country2) {
            return (Double.compare(country2.getLatitude(), country1.getLatitude()));
        }
    };

    public static Comparator<CountryPOJOBasic> getLatComparatorDesc() {
        return latComparatorDesc;
    }

    public static void setLatComparatorDesc(Comparator<CountryPOJOBasic> latComparatorDesc) {
        CountryPOJOBasic.latComparatorDesc = latComparatorDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha2code() {
        return alpha2code;
    }

    public void setAlpha2code(String alpha2code) {
        this.alpha2code = alpha2code;
    }

    public String getAlpha3code() {
        return alpha3code;
    }

    public void setAlpha3code(String alpha3code) {
        this.alpha3code = alpha3code;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
