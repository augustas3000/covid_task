package com.interview.task;

import com.interview.task.adapters.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@XmlRootElement(name = "daily-data")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType()
public class DailyDataPOJO {

    private String country;
    @XmlElement(name = "province")
    private List<ProvincePOJO> provinces;
    private double latitude; //try avoid duplicating these
    private double longitude; //try avoid duplicating these
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;




    public DailyDataPOJO() {
    }

    public DailyDataPOJO(String country, List<ProvincePOJO> provinces, double latitude, double longitude, LocalDate date) {
        this.country = country;
        this.provinces = provinces;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
    }


    //    implementing Comparator as anonymous inner class, for latitude:
    public static final Comparator<DailyDataPOJO> deathTollComparatorDesc = new Comparator<DailyDataPOJO>() {

        public int compare(DailyDataPOJO day1, DailyDataPOJO day2) {
            return (Integer.compare(sumUpProvincesDeaths(day2.getProvinces()), sumUpProvincesDeaths(day1.getProvinces())));
        }

        private int sumUpProvincesDeaths(List<ProvincePOJO> provinces) {
            int totDeaths = 0;
            if (provinces.size() > 1) {
              for (ProvincePOJO prov : provinces) {
                  totDeaths += prov.getDeaths();
              }

            } else {
              totDeaths += provinces.get(0).getDeaths();
            }
            return totDeaths;
        };

    };



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<ProvincePOJO> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<ProvincePOJO> provinces) {
        this.provinces = provinces;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
