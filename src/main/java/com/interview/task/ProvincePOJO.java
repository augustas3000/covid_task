package com.interview.task;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "province")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProvincePOJO {
    private String province;
    private int confirmed;
    private int recovered;
    private int deaths;
    private int active;


    public ProvincePOJO() {

    }

    public ProvincePOJO(String province, int confirmed, int recovered, int deaths, int active) {
        this.province = province;
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.deaths = deaths;
        this.active = active;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
