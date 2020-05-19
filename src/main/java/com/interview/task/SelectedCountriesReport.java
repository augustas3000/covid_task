package com.interview.task;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "selected-5-countries-10-days-report")
@XmlAccessorType(XmlAccessType.FIELD)
public class SelectedCountriesReport {

    @XmlElement(name = "country")
    private List<CountryPOJOReport> countries;

    public List<CountryPOJOReport> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryPOJOReport> countries) {
        this.countries = countries;
    }
}
