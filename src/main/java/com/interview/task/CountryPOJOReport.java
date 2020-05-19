package com.interview.task;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "country")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryPOJOReport {

    private String name;
    @XmlElement(name = "daily-data")
    private List<DailyDataPOJO> dailyReportsLast10Days;

    private int totalDeaths;

//    on creation of this object - we will loop through

    public CountryPOJOReport() {
    }


    public CountryPOJOReport(String name, List<DailyDataPOJO> dailyReportsLast10Days, int totalDeaths) {
        this.name = name;
        this.dailyReportsLast10Days = dailyReportsLast10Days;
        this.totalDeaths = totalDeaths;
    }

    public void calculateTotalDeaths() {
        int total = 0;
        List<DailyDataPOJO> reports = this.getDailyReportsLast10Days();

        for (int i = 0; i<reports.size(); i++) {
//            provinces of a given daily report
            List<ProvincePOJO> provinces = reports.get(i).getProvinces();

            for(int p = 0; p < provinces.size(); p++) {
                total += provinces.get(p).getDeaths();
            }
        }
        this.setTotalDeaths(total);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<DailyDataPOJO> getDailyReportsLast10Days() {
        return dailyReportsLast10Days;
    }

    public void setDailyReportsLast10Days(List<DailyDataPOJO> dailyReportsLast10Days) {
        this.dailyReportsLast10Days = dailyReportsLast10Days;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }
}
