package BrowserSatckApis.DemoApis.Handler.imp;

import BrowserSatckApis.DemoApis.Handler.BrowserStackApi;
import BrowserSatckApis.DemoApis.Model.ResponseDto;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.lang.StringBuilder;
import java.util.stream.IntStream;

@Component
public class BrowserStackAmpiImpl  implements BrowserStackApi {


    @Value("${apiEndpointforvid}")
    private String apiEndpointforvid1;

    @Value("${apifetchSessionLogs}")
    private String apifetchSessionLogs1;

    @Value("${apifetchSeleniumLog}")
    private String apifetchSeleniumLog;

    @Value("${apifetchNetworkLogs}")
    private  String apifetchNetworkLogs;
    @Override
    public ResponseDto downloadVid(String username, String accesskey, String sessionId) {
           ResponseDto responseDto=new ResponseDto();
//        String apiEndpointforvid = "https://api.browserstack.com/automate/sessions/"+sessionId+".json";

         String apiEndpoint=apiEndpointforvid1.replace("{sessionId}",sessionId);
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", getAuthHeader(username, accesskey));

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                String responseBody = new String(inputStream.readAllBytes());
                inputStream.close();

                String videoUrl = extractVideoUrl(responseBody);

                InputStream in = new URL(videoUrl).openStream();
//                new File("D://video.mp4");
                new File("D://Myviddd//video5.mp4");
                Files.copy(in, Paths.get("D://Myviddd//video5.mp4"), StandardCopyOption.REPLACE_EXISTING);


                if (videoUrl != null) {

                    responseDto.setResponseObject(videoUrl);
                    responseDto.setResponseCode(HttpStatus.OK.value());
                    responseDto.setMessage("Vid fetched");
                    System.out.println("Video URL: " + videoUrl);
                } else {
                    System.out.println("Video URL not found in the session details.");
                }
            } else {
                System.out.println("Failed to retrieve session details. Response Code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseDto;

    }

    private static String getAuthHeader(String username, String accessKey) {
        String credentials = username + ":" + accessKey;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    private static String extractVideoUrl(String responseBody) {
        String videoUrl = null;
        try {
            JSONObject jsonObject=new JSONObject(responseBody);
            JSONObject automationSession = jsonObject.getJSONObject("automation_session");
            videoUrl = automationSession.getString("video_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return videoUrl;
    }

    @Override
    public ResponseDto getSessionId(String username, String accesskey) throws MalformedURLException {
        ResponseDto responseDto=new ResponseDto();
        String sessionId = execute(username, accesskey);
        responseDto.setResponseObject(sessionId);
        responseDto.setResponseCode(HttpStatus.OK.value());
        responseDto.setMessage("Succes fetched iD");
        return responseDto;
    }

    @Override
    public ResponseDto getSessionLogs(String username, String accessKey, String sessionId) throws IOException {
        ResponseDto responseDto=new ResponseDto();
//  String apiUrl= "https://api.browserstack.com/automate/sessions/168a10732f7bcb197728ccda0459a238bc54d215/logs";

//      String apiUrl=apifetchSessionLogs1.replace("{sessionId}",sessionId);
       String apiUrl=apifetchSessionLogs1;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        String auth = username + ":" + accessKey;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        connection.setRequestProperty("Authorization", getAuthHeader(username,accessKey));
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

           String res= response.toString();
           responseDto.setResponseObject(res);
           responseDto.setResponseCode(HttpStatus.OK.value());
           responseDto.setMessage("Logs fetched");
        } else {

            System.out.println("Failed to fetch session logs. Response Code: " + responseCode);
        }

        connection.disconnect();

        return responseDto;


    }

    @Override
    public ResponseDto getSeleniumLog(String username, String accesskey, String sessionId) throws IOException {
         ResponseDto responseDto=new ResponseDto();
        String apiUrl=apifetchSeleniumLog.replace("{sessionId}",sessionId);
        URL url=new URL(apiUrl);
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
       httpURLConnection.setRequestMethod("GET");
      httpURLConnection.setRequestProperty("Authorization",getAuthHeader(username,accesskey));

      int responseCode=httpURLConnection.getResponseCode();
      if(responseCode==HttpURLConnection.HTTP_OK){
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
          StringBuilder response=new StringBuilder();
          String line;
          while (( line=bufferedReader.readLine())!=null){
                response.append(line);
          }
          bufferedReader.close();

          String res=response.toString();
          responseDto.setResponseObject(res);
          responseDto.setResponseCode(HttpStatus.OK.value());
           responseDto.setMessage("selenium logs fetched");


      }else {

          responseDto.setResponseObject(null);
          responseDto.setResponseCode(HttpStatus.NOT_FOUND.value());
          responseDto.setMessage("not foind selenium logs fetched");
      }

        return responseDto;


    }

    @Override
    public ResponseDto getNetworkLogs(String username, String accesskey, String sessionId) throws IOException {
        ResponseDto responseDto=new ResponseDto();
         String apiUrl="https://api.browserstack.com/automate/sessions/168a10732f7bcb197728ccda0459a238bc54d215/networklogs.json";
//        String apiUrl=apifetchNetworkLogs.replace("{sessionId}",sessionId);
        URL url=new URL(apiUrl);
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
     httpURLConnection.setRequestMethod("GET");
     httpURLConnection.setRequestProperty("Authorization" ,getAuthHeader(username,accesskey));

     int responseCode=httpURLConnection.getResponseCode();
     if(responseCode==HttpURLConnection.HTTP_OK){
       BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
       StringBuilder stringBuilder=new StringBuilder();
       String line;

       while((line=bufferedReader.readLine())!=null){
            stringBuilder.append(line);
       }
       bufferedReader.close();



//         Gson gson = new Gson();
//
//// Parse the JSON string to a JsonElement
//         JsonElement jsonElement = gson.fromJson(response.toString(), JsonElement.class);
//         JsonObject jsonObject= (JsonObject) jsonElement.getAsJsonObject();
//         JsonArray entries1 = jsonObject.getAsJsonObject("log").getAsJsonArray("entries");
//
//         List<Map<String,Double>> timingList =new ArrayList<>();
//         if (entries1!=null){
//
//
//             IntStream.range(0,entries1.size()).mapToObj(entries1::get).forEach(entryElement->
//             {
//                 JsonObject asJsonObject = entryElement.getAsJsonObject();
//                 JsonObject timings = asJsonObject.getAsJsonObject("timings");
//
//                 double send = timings.get("send").getAsDouble();
//                 double wait = timings.get("wait").getAsDouble();
//                 double receive = timings.get("receive").getAsDouble();
//
//                 // Create a map to store the send, wait, and receive values for this entry
//                 Map<String, Double> timingsMap = new HashMap<>();
//                 timingsMap.put("send", send);
//                 timingsMap.put("wait", wait);
//                 timingsMap.put("receive", receive);
//
//                 // Add the timings map to the list
//                 timingList.add(timingsMap);
//             });
//
//             for (int i = 0; i < timingList.size(); i++) {
//                 Map<String, Double> timingsMap = timingList.get(i);
//
//                 System.out.println("Step " + (i + 1));
//                 System.out.println("Send: " + timingsMap.get("send"));
//                 System.out.println("Wait: " + timingsMap.get("wait"));
//                 System.out.println("Receive: " + timingsMap.get("receive"));
//                 System.out.println();
         String res = stringBuilder.toString();
         responseDto.setResponseObject(res);
         responseDto.setResponseCode(HttpStatus.OK.value());
         responseDto.setMessage("networkLogs fteched...");
     }else {
         responseDto.setResponseObject("not fetched");
         responseDto.setResponseCode(HttpStatus.NOT_FOUND.value());
         responseDto.setMessage("networkLogs fteched...");

     }

        return responseDto;
    }









    public String execute(String username, String accesskey) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "10");
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "latest");
        caps.setCapability("browserstack.networkLogs", "true");

        String BROWSERSTACK_URL= "https://" + username + ":" + accesskey+ "@hub-cloud.browserstack.com/wd/hub";
        WebDriver driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), caps);

        String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
        System.out.println("Session ID: " + sessionId);

        driver.get("https://www.google.com/");

        WebElement searchInput = driver.findElement(By.name("q"));
        searchInput.sendKeys("Fireflink");
        searchInput.submit();

        driver.quit();

        return sessionId;
    }
}
