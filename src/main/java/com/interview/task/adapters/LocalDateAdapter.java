package com.interview.task.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

// JAXB (Java Architecture for XML Binding), by default,  doesn't make good friends with LocalDate type, so
// I had to write an adapter:

public class LocalDateAdapter
        extends XmlAdapter<String, LocalDate> {

    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }

}