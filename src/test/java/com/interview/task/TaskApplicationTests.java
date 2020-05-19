package com.interview.task;

import com.interview.task.helpers.FetchFromAPI;
import com.interview.task.helpers.JsonHelper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TaskApplicationTests {


	@Before
	public void before() throws IOException {

	}

    @Test
    public void canFetchAllCountriesAndReadToPojos() throws IOException {
		String url = "https://covid-19-data.p.rapidapi.com/help/countries?format=json";
		String key = "f78aa954c1msh0677c73556690d1p1bc211jsn25dca170af59";
		String allCountriesFetched = FetchFromAPI.getAllCountriesRequest(url, key);
		List<CountryPOJOBasic> countryPOJOS = JsonHelper.fromJsonArrayToPojosArrayCOUNTRY(allCountriesFetched);
	    assertEquals(253,countryPOJOS.size());
	    assertEquals(33.93911,countryPOJOS.get(0).getLatitude());
    }


    @Test
	public void canSortByLat() throws IOException {
		String url = "https://covid-19-data.p.rapidapi.com/help/countries?format=json";
		String key = "f78aa954c1msh0677c73556690d1p1bc211jsn25dca170af59";
		String allCountriesFetched = FetchFromAPI.getAllCountriesRequest(url, key);
		List<CountryPOJOBasic> countryPOJOS = JsonHelper.fromJsonArrayToPojosArrayCOUNTRY(allCountriesFetched);

		for (CountryPOJOBasic countrypojo: countryPOJOS) {
			System.out.print(countrypojo.getLatitude() + " ");
		}
		System.out.println("  ");
		Collections.sort(countryPOJOS, CountryPOJOBasic.latComparatorDesc);
		for (CountryPOJOBasic countrypojo: countryPOJOS) {
			System.out.print(countrypojo.getLatitude() + " ");
		}

	}

    @Test
    public void canGetDailyDataForSelectedCountry() throws IOException, InterruptedException {
	   String[] datesArray = new String[]{"2020-04-15","2020-04-14", "2020-04-13","2020-04-12","2020-04-11","2020-04-10","2020-04-09","2020-04-08","2020-04-07","2020-04-06"};
	   String belgiumCode = "be";
	   String[] dailyDataBelgium = new String[10];

	   for (int i = 0; i < datesArray.length; i++) {
	      String dayData = FetchFromAPI.getDayDataForSelectedCountryAndDate(datesArray[i], belgiumCode);
	      dailyDataBelgium[i] = dayData;
	      Thread.sleep(1500);
       }

        List<DailyDataPOJO> dailyData1 = JsonHelper.fromJsonArrayToPojosArrayDAILY(dailyDataBelgium[0]);
        List<DailyDataPOJO> dailyData2 = JsonHelper.fromJsonArrayToPojosArrayDAILY(dailyDataBelgium[1]);
        List<DailyDataPOJO> dailyData3 = JsonHelper.fromJsonArrayToPojosArrayDAILY(dailyDataBelgium[9]);
        System.out.println(dailyData1.get(0).getProvinces().get(0).getDeaths());
        System.out.println(dailyData2.get(0).getProvinces().get(0).getDeaths());
        System.out.println(dailyData3.get(0).getProvinces().get(0).getDeaths());
    }


}
