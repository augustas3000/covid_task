package com.interview.task;

import com.interview.task.helpers.FetchFromAPI;
import com.interview.task.helpers.JsonHelper;
import com.interview.task.helpers.XmlHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Factory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import sun.rmi.transport.Transport;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class TaskApplication {

	public static void main(String[] args)  {
		SpringApplication.run(TaskApplication.class, args);

//		1.1 fetch countries
		String url = "https://covid-19-data.p.rapidapi.com/help/countries?format=json";
		String key = "f78aa954c1msh0677c73556690d1p1bc211jsn25dca170af59";
		String allCountries = "";
		try {
			allCountries = FetchFromAPI.getAllCountriesRequest(url, key);
			Thread.sleep(1000); //wait for 1.5 sec - rapidapi limitation (1 request per second)
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

//		1.2 Read json array to POJO(Plain Old Java Object)s list.
		List<CountryPOJOBasic> allCountriesPojos = null;
		try {
			allCountriesPojos = JsonHelper.fromJsonArrayToPojosArrayCOUNTRY(allCountries);
			System.out.println(allCountriesPojos);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		2. Sort by lat, select top 15, using a custom-defined comparator
		Collections.sort(allCountriesPojos, CountryPOJOBasic.latComparatorDesc);
		System.out.println(allCountriesPojos);

		int numCountries = 15;

		ArrayList<CountryPOJOBasic> top15ByLat = new ArrayList<CountryPOJOBasic>();
		for (int i = 0; i < numCountries; i++) {
			top15ByLat.add(allCountriesPojos.get(i));
		}

//		2.1 pojo to xml conversion:
		Top15CountriesByLat top15CountriesByLat = new Top15CountriesByLat();
		top15CountriesByLat.setCountries(top15ByLat);

		XmlHelper.readXmlFromCountryPojosArrayTop15(top15CountriesByLat, "top_15_countries_by_lat");

//		3. get last 10 days data for selected 15 countries:
		HashMap<String, List<DailyDataPOJO>> dailyDataCountries = null;
		try {
			dailyDataCountries = FetchFromAPI.getDailyDataDataForSelectedCountries(15, allCountriesPojos, 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

//		4. Convert to xml and store:
//		4.1 Create pojos to reflect intended xml structure:
		SelectedCountriesReport selectedCountriesReport = new SelectedCountriesReport();

		for (Map.Entry mapElement : dailyDataCountries.entrySet()) {
			key = (String) mapElement.getKey();
			CountryPOJOReport country = new CountryPOJOReport();
			List<DailyDataPOJO> listPojos = (List<DailyDataPOJO>) mapElement.getValue();
			country.setDailyReportsLast10Days(listPojos);
			country.setName(listPojos.get(0).getCountry());
			country.calculateTotalDeaths();

			if (selectedCountriesReport.getCountries() == null) {
				List<CountryPOJOReport> empty = new ArrayList<>();
				selectedCountriesReport.setCountries(empty);
				selectedCountriesReport.getCountries().add(country);
			} else {
				selectedCountriesReport.getCountries().add(country);
			}
		}

//		4.2 Write to xml.
		XmlHelper.readXmlFromSelected(selectedCountriesReport, "selected-15-countries-10-days-daily-reports");

//		5. For stored entries, select one day that has the biggest death toll
//	    5.1 Similar loop to be used, with additional step to descend sort the daily data by death toll, and then select
//	    the first day report item.
		SelectedCountriesReport selectedCountriesReportHighestDeathToll = new SelectedCountriesReport();

		for (Map.Entry mapElement : dailyDataCountries.entrySet()) {
			key = (String) mapElement.getKey();
			CountryPOJOReport country = new CountryPOJOReport();

			List<DailyDataPOJO> listPojos = (List<DailyDataPOJO>) mapElement.getValue();

//			sort by death toll using a comparator that processes the death toll data from each province of given country:
			Collections.sort(listPojos, DailyDataPOJO.deathTollComparatorDesc);

			List<DailyDataPOJO> refinedList = new ArrayList<>();
			refinedList.add(listPojos.get(0));
			country.setDailyReportsLast10Days(refinedList);

			country.setName(listPojos.get(0).getCountry());
			country.calculateTotalDeaths();

			if (selectedCountriesReportHighestDeathToll.getCountries() == null) {
				List<CountryPOJOReport> empty = new ArrayList<>();
				selectedCountriesReportHighestDeathToll.setCountries(empty);
				selectedCountriesReportHighestDeathToll.getCountries().add(country);
			} else {
				selectedCountriesReportHighestDeathToll.getCountries().add(country);
			}
		}

//		5.2 - write to XML
		XmlHelper.readXmlFromSelectedWithXsl(selectedCountriesReportHighestDeathToll, "selected-15-countries-highest-death-toll");


//		5.3 - attempt to transform an xml to html, based on results/report.xsl stylesheet which shoudl tidy up [rovinces data as well...
//		probably close but more research is required. This was my first experience working with xmls other than config files.


//		Document resultDoc = null;
//
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		dbf.setNamespaceAware(true);
//		DocumentBuilder db = null;
//		try {
//			db = dbf.newDocumentBuilder();
//			Document xslt = db.parse("results/report.xsl");
//			Document xml = db.newDocument();
//			xml.appendChild(xml.createElementNS(null, "root"));
//
//			Source xmlSource = new DOMSource(xml);
//			Source xsltSource = new DOMSource(xslt);
//			DOMResult result = new DOMResult();
//
//			// the factory pattern supports different XSLT processors
//			TransformerFactory transFact
//					= TransformerFactory.newInstance();
//			Transformer trans = transFact.newTransformer(xsltSource);
//
//			trans.transform(xmlSource, result);
//
//			resultDoc = (Document) result.getNode();
//			System.out.println(resultDoc);
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (TransformerConfigurationException e) {
//			e.printStackTrace();
//		} catch (TransformerException e) {
//			e.printStackTrace();
//		}




//		5.3 - email everything: The code sends an email with the three xml files(as attachments) generated in this task.
//		remove commenting and provide details to see this in action. Gmail likely to require a setting to allow less secure
//		apps to connect.


////    final String username = "-----";
//		String password = "-----";
////		senders email:
//		String fromEmail = "augusto.pasaulis@gmail.com";
////		recipients email:
//		String toEmail = "asarine@gmail.com";
////		host:
//		String host = "smtp.gmail.com";
//
////		get system properties and setup mail server
//		Properties properties = System.getProperties();
//		properties.put("mail.smtp.host", host);
//		properties.put("mail.smtp.port", "465");
//		properties.put("mail.smtp.ssl.enable", "true");
//		properties.put("mail.smtp.auth", "true");
//
//		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(fromEmail, password);
//			}
//		});
//
////		start out mail message:
//		session.setDebug(true);
//		try {
//			// Create a default MimeMessage object.
//			MimeMessage message = new MimeMessage(session);
//
//			// Set From: header field of the header.
//			message.setFrom(new InternetAddress(fromEmail));
//
//			// Set To: header field of the header.
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//
//			// Set Subject: header field
//			message.setSubject("This is the Subject Line!");
//
//
//			Multipart multipart = new MimeMultipart();
//
//			MimeBodyPart attachmentPart1 = new MimeBodyPart();
//			MimeBodyPart attachmentPart2 = new MimeBodyPart();
//			MimeBodyPart attachmentPart3 = new MimeBodyPart();
//
//			MimeBodyPart textPart = new MimeBodyPart();
//
//			try {
//				File selected15countries10daysDaily = new File("results/selected-15-countries-10-days-daily-reports.xml");
//				File selected15countriesHighestDeathToll = new File("results/selected-15-countries-highest-death-toll.xml");
//				File top15byLat = new File("results/top_15_countries_by_lat.xml");
//
//
//				attachmentPart1.attachFile(selected15countries10daysDaily);
//				attachmentPart2.attachFile(selected15countriesHighestDeathToll);
//				attachmentPart3.attachFile(top15byLat);
//
//				textPart.setText("This is text");
//
//				multipart.addBodyPart(textPart);
//				multipart.addBodyPart(attachmentPart1);
//				multipart.addBodyPart(attachmentPart2);
//				multipart.addBodyPart(attachmentPart3);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			// Now set the actual message
//			message.setContent(multipart);
//
//			System.out.println("sending...");
//			// Send message
//			Transport.send(message);
//			System.out.println("Sent message successfully....");
//
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
	}
}