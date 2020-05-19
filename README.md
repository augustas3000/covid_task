# covid_task
Covid19 data task for Cognizant  qa engineer/junior developer position

A simple ephemeral(runt-output-terminate) program to fetch and analyse covid-19 data as per instructions below:

Use https://rapidapi.com/, "COVID-19 data" resource. Register to get API key, don't forget subscribe for the resource (free plan).

1.       Fetch Covid-19 data, 
https://covid-19-data.p.rapidapi.com/help/countries
From <https://rapidapi.com/Gramzivi/api/covid-19-data?endpoint=apiendpoint_80bfb292-72a4-485b-8e76-2900bd8246bd> 
 (Country code, Contry name, Longitute, Latitude)
 
2. Sort countries by Latitude Descending order. Select top 15 Countries;¬

3. Get last 10 days data from selected 15 countries, using endpoint.
https://covid-19-data.p.rapidapi.com/report/country/code
From <https://rapidapi.com/Gramzivi/api/covid-19-data?endpoint=apiendpoint_a0a3b806-6a57-4726-8fe7-6883b6dbaa9e> 

4. Store daily data (Country code, date, confirmed, critical, deaths, recovered)

5. Select stored entries for every country, one day data, that has worst (biggest) death toll;

6.  Convert it to XML. Email in html email body and xml-generated file attached.


Tech used:
API: rapidapi.com covid-19-data 
okhttp - for fetching the data
Jackson Object Mapper - for deserialization of json into pojos
JAXB - for writting pojos to xml
Java mail - for automated emails and attachments
Xsl/Xslt - wrote a stylesheet and attempted transformation of xml to html.


Reflection:
API constraints:
rapidapi.com - at basic subscription, the COVID-19-data api, allows 1 request per second. This was not a problem for
the fetch required in task 1, but it had serious implications for task 3. Since the endpoint giving daily report for
all countries was locked for basic subscription, the only way was to perform multiple fetches (per each country, 10
fetches to get last 10 days data) as per method: getDailyDataDataForSelectedCountries(). This was not ideal too, as the
api limits a basic user to 1 request per second. 

Since the task was written as an ephemeral - run, output, terminate application, the simple workaround was to make the
program sleep for 1.5s after each request Thread.sleep(1500) - see method getDailyDataDataForSelectedCountries().
This approach also required to fetch synchronously, because asynch fetches would make the program break. So to get 10 days
data, for 15 countries results in 150 fetches - 1.5s each. Hence the app works, but is clunky - taking more than a minute to
output results.

If the app was written to run on web-server, the fetching would have to be asynchronous, but given the 1request/second
limitation of the basic subscription, data loading would probably take too long. So if this was a project in a commercial
setting, most likely one would either have to upgrade to better subscription with rapidapi.com, or just find another api.

Pojos to XML to HTML:
Managed to write xmls based on pojos, and the structure made sence. However, I am quite new to the format, and only used it
for configuration purposes in the past, and so the transformation of it was challenging - I managed to write an xsl stylesheet
to achieve the desired table output in html, this works in a browser, but I struggled to achieve the actual transformation.
(commented code 5.3 in TaskApplication)

Auto-emails: 
The commented code (5.4) in Task application also shows a workflow to compose an email and attach three generated xmls from 
/results directory. Feel free to comment out and try it with your details. Gmail likely to require a setting to allow less secure
apps to connect.


For simplicity, I will provide a link to this repo in email, as well as three xml files that were generated using this app,
as well as an xsl stylesheet. But feel free to run it, baring in mind it takes about 2-3 min to fetch daily data due to api
limitations.




















