package com.interview.task.helpers;

import com.interview.task.SelectedCountriesReport;
import com.interview.task.Top15CountriesByLat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class XmlHelper {

	public static void readXmlFromCountryPojosArrayTop15(Top15CountriesByLat top15CountriesByLat, String fileName) {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(top15CountriesByLat.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //Marshal the countries list in console
//          jaxbMarshaller.marshal(top15CountriesByLat, System.out);

//			Marshal the countries list in file
            String pathNameToSaveTo = "results/" + fileName + ".xml";
            jaxbMarshaller.marshal(top15CountriesByLat, new File(pathNameToSaveTo));

        } catch (
                JAXBException e) {
            e.printStackTrace();
        }
    }



    public static void readXmlFromSelected(SelectedCountriesReport countries, String fileName) {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(countries.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //Marshal the countries list in console
//          jaxbMarshaller.marshal(top15CountriesByLat, System.out);

//			Marshal the countries list in file
            String pathNameToSaveTo = "results/" + fileName + ".xml";
            jaxbMarshaller.marshal(countries, new File(pathNameToSaveTo));

        } catch (
                JAXBException e) {
            e.printStackTrace();
        }
    }



}
