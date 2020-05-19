package com.interview.task;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "top-15-countries-by-lat")
@XmlAccessorType(XmlAccessType.FIELD)
public class Top15CountriesByLat {

    @XmlElement(name = "country")
    private List<CountryPOJOBasic> countries;

    public List<CountryPOJOBasic> getCountries() {
        return countries;
    }
    public void setCountries(List<CountryPOJOBasic> countries) {
        this.countries = countries;
    }
}
