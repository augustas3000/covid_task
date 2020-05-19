package com.interview.task.helpers;

import com.interview.task.CountryPOJOBasic;
import com.interview.task.DailyDataPOJO;
import com.squareup.okhttp.*;
import sun.reflect.Reflection;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FetchFromAPI {

//  OkHttp client was used for fetching data from rapidapi.com

//  Reflection:
//  rapidapi.com - at basic subscription, the COVID-19-data api, allows 1 request per second. This was not a problem for
//  the fetch required in task 1, but it had serious implications for task 3. Since the endpoint giving daily report for
//  all countries was locked for basic subscription, the only way was to perform multiple fetches (per each country, 10
//  fetches to get last 10 days data) as per method: getDailyDataDataForSelectedCountries(). This was not ideal too, as the
//  api limits a basic user to 1 request per second.
//
//  Since the task was written as an ephemeral - run, output, terminate application, the simple workaround was to make the
//  program sleep for 1.5s after each request Thread.sleep(1500) - see method getDailyDataDataForSelectedCountries().
//  This approach also required to fetch synchronously, because asynch fetches would make the program break.
//
//  If the app was written to run on web-server, the fetching would have to be asynchronous, but given the 1request/second
//  limitation of the basic subscription, data loading would probably take too long. So if this was a project in a commercial
//  setting, most likely one would either have to upgrade to better subscription with rapidapi.com, or just find another api.


    private static OkHttpClient client = new OkHttpClient();

//  get all countries - part of Task 1.
    public static String getAllCountriesRequest(String url, String key) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                .addHeader("x-rapidapi-key", key)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }

//   get day data for selected country - part of Task 3

    public static String getDayDataForSelectedCountryAndDate(String dateYMD, String countryCode) throws IOException {
        String currentUrl =  "https://covid-19-data.p.rapidapi.com/report/country/code?format=json&date-format=YYYY-MM-DD&date=" + dateYMD + "&code=" + countryCode.toLowerCase();

        Request request = new Request.Builder()
                .url(currentUrl)
                .get()
                .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "f78aa954c1msh0677c73556690d1p1bc211jsn25dca170af59")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

//    get num days data for selected num countries, based on input list of countries from earlier steps - part of Task 3.

    public static HashMap<String, List<DailyDataPOJO>> getDailyDataDataForSelectedCountries(Integer numberOfCountries, List<CountryPOJOBasic> allCountries, Integer numberOfDays) throws InterruptedException {

        ArrayList<CountryPOJOBasic> selectedCountries = new ArrayList<CountryPOJOBasic>();
        for (int i = 0; i < numberOfCountries; i++) {
            selectedCountries.add(allCountries.get(i));
        }

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        String[] dateStringsForUrlFormation = new String[numberOfDays];

        LocalDate currentDate;
        for(int day = 0; day < numberOfDays; day++) {
            currentDate = yesterday.minusDays(day);
            dateStringsForUrlFormation[day] = currentDate.toString();
        }

        HashMap<String, List<DailyDataPOJO>> dailyDataCountries = new HashMap<>();

        for(int countryItem = 0; countryItem<selectedCountries.size(); countryItem++) {
            String countryCodeForUrlFormation = selectedCountries.get(countryItem).getAlpha2code().toLowerCase();
            for(int dateItem = 0; dateItem<dateStringsForUrlFormation.length; dateItem++) {

                try {
                    String dayData = FetchFromAPI.getDayDataForSelectedCountryAndDate(dateStringsForUrlFormation[dateItem],countryCodeForUrlFormation);
                    List<DailyDataPOJO> dailyData = JsonHelper.fromJsonArrayToPojosArrayDAILY(dayData);
                    DailyDataPOJO flatPojo = dailyData.get(0);

                    if (dateItem == 0) {
                        dailyDataCountries.put(countryCodeForUrlFormation, new ArrayList<DailyDataPOJO>());
                    }

                    dailyDataCountries.get(countryCodeForUrlFormation).add(flatPojo);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Thread.sleep(1500);
            }
        }

        return dailyDataCountries;
    };

}
