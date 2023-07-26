package BrowserSatckApis.DemoApis.Handler.imp;

import BrowserSatckApis.DemoApis.Handler.LamdbatestApi;
import BrowserSatckApis.DemoApis.Model.Datamodel;
import BrowserSatckApis.DemoApis.Model.ResponseDto;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class LambdsTestApiImpl implements LamdbatestApi {

    @Value("${apiselenium}")
    private String apiselenium;

    @Value("${apiCommand}")
    private  String apiCommand;

    @Value("${apinetwork}")
    private  String apinetwork;

    @Value("${apiConsole}")
    private  String apiConsole;
    @Override
    public ResponseDto getVideoLambda(String username, String accessKey) {
        ResponseDto responseDto = new ResponseDto();
//        String apiUrl="https://api.lambdatest.com/automation/api/v1/test/MNL2G-ZZZGC-SYKK4-BFQZ6/video.json";
        String apiUrl = "https://automation.lambdatest.com/public/video?testID=ZPQFL-LMZP6-JWTJN-2MOAC&auth=5778450c0eed8a09e4273a0720d20e97";

        String apiUrl1 = "https://api.lambdatest.com/automation/api/v1/sessions/ZPQFL-LMZP6-JWTJN-2MOAC";
        try {
            URL url = new URL(apiUrl1);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Authorization", getAuthHeader1(username, accessKey));

            int responsecode = httpURLConnection.getResponseCode();
            if (responsecode == httpURLConnection.HTTP_OK) {

                InputStream inputStream = httpURLConnection.getInputStream();
                String responsebody = new String(inputStream.readAllBytes());
                inputStream.close();

                String vidUrl = extractVidUrl(responsebody);
                InputStream in=new URL(vidUrl).openStream();
//                new File("D://video.mp4");
//                new File("D://Myviddd//video23.mp4");
//                new File("D://Myviddd//video4.mp4");
                Files.copy(in,Paths.get("D://Myviddd//video5.mp4"),StandardCopyOption.REPLACE_EXISTING);
                if (vidUrl != null) {
                    responseDto.setResponseObject(vidUrl);
                    responseDto.setResponseCode(HttpStatus.OK.value());
                    responseDto.setMessage("Vid fetched");
                    System.out.println("Video URL: " + vidUrl);
                } else {
                    System.out.println("Video URL not found in the session details.");
                }
            } else {
                System.out.println("Failed to retrieve session details. Response Code: " + responsecode);
            }

           httpURLConnection.disconnect();

        }catch (IOException e){
            e.printStackTrace();
        }
             return  responseDto;
            }

    @Override
    public ResponseDto getLogs(Datamodel datamodel) throws IOException {
        ResponseDto responseDto=new ResponseDto();
     String  sessionID=datamodel.getSessionID();
       String username="mehadiathani";
       String accesskey="8WuJRtvxqQMIPkkFrGz0Ig2yx4ZyBDrcr1VX33f12uzbOETSLk";
        String replace = apiselenium.replace("{sessionID}", datamodel.getSessionID());
        String apiurl="https://api.lambdatest.com/automation/api/v1/sessions/ZPQFL-LMZP6-JWTJN-2MOAC/log/selenium";

        URL url=new URL(replace);
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Authorization",getAuthHeader1(username,accesskey));
        int responsecode=httpURLConnection.getResponseCode();
        if(responsecode==HttpURLConnection.HTTP_OK){
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder=new StringBuilder();
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            responseDto.setResponseObject(stringBuilder);
            responseDto.setResponseCode(HttpStatus.OK.value());
             responseDto.setStatus("Fteched");
             responseDto.setMessage("Logggg fetcheddddd");
        }else {
            responseDto.setResponseObject(null);
            responseDto.setResponseCode(HttpStatus.NOT_FOUND.value());
            responseDto.setStatus(" not Fteched");
            responseDto.setMessage("No ....Logggg");
        }
        httpURLConnection.disconnect();

        return  responseDto;
    }

    @Override
    public ResponseDto getCommandlogs(Datamodel datamodel) throws IOException {

     ResponseDto responseDto=new ResponseDto();
     String username1=datamodel.getUsername().toString();
//     String accesskey=datamodel.getAccesskey();
        String username="mehadiathani";
        String accesskey="8WuJRtvxqQMIPkkFrGz0Ig2yx4ZyBDrcr1VX33f12uzbOETSLk";

     String urlCoomand=apiCommand.replace("{sessionID}",datamodel.getSessionID());

     URL url=new URL(urlCoomand);
     HttpURLConnection httpURLConnection1= (HttpURLConnection) url.openConnection();
      httpURLConnection1.setRequestMethod("GET");
      httpURLConnection1.setRequestProperty("Authorization",getAuthHeader1(username,accesskey));
      int responsecode=httpURLConnection1.getResponseCode();

      if(responsecode==HttpURLConnection.HTTP_OK){
          BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection1.getInputStream()));
          StringBuilder stringBuilder=new StringBuilder();
          String  line;
          while ((line=bufferedReader.readLine())!=null){
               stringBuilder.append(line);
          }
      bufferedReader.close();

          responseDto.setResponseObject(stringBuilder);
          responseDto.setResponseCode(HttpStatus.OK.value());
          responseDto.setMessage("Command logs fetched");
      }else {
          responseDto.setResponseObject(null);
          responseDto.setResponseCode(HttpStatus.NOT_FOUND.value());
          responseDto.setMessage("not foind command logs fetched");
      }

      return  responseDto;
    }

    @Override
    public ResponseDto getNetworklogs(Datamodel datamodel) throws IOException {
        ResponseDto responseDto=new ResponseDto();
        String username1=datamodel.getUsername().toString();
       String accesskey1=datamodel.getAccesskey().toString();
//        String username="mehadiathani";
//        String accesskey="8WuJRtvxqQMIPkkFrGz0Ig2yx4ZyBDrcr1VX33f12uzbOETSLk";

        String urlCoomand=apinetwork.replace("{sessionID}",datamodel.getSessionID());

        URL url=new URL(urlCoomand);
        HttpURLConnection httpURLConnection1= (HttpURLConnection) url.openConnection();
        httpURLConnection1.setRequestMethod("GET");
        httpURLConnection1.setRequestProperty("Authorization",getAuthHeader1(username1,accesskey1));
        int responsecode=httpURLConnection1.getResponseCode();

        if(responsecode==HttpURLConnection.HTTP_OK){
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection1.getInputStream()));
            StringBuilder stringBuilder=new StringBuilder();
            String  line;
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            bufferedReader.close();

            responseDto.setResponseObject(stringBuilder);
            responseDto.setResponseCode(HttpStatus.OK.value());
            responseDto.setMessage("Network logs fetched !!!! ");
            responseDto.setStatus("success");
        }else {
            responseDto.setResponseObject(null);
            responseDto.setResponseCode(HttpStatus.NOT_FOUND.value());
            responseDto.setMessage("not foind network logs fetched");
        }

        return  responseDto;

    }

    @Override
    public ResponseDto getConsoleLogs(Datamodel datamodel) throws IOException {

        ResponseDto responseDto=new ResponseDto();
//        https://api.lambdatest.com/automation/api/v1/sessions/ZPQFL-LMZP6-JWTJN-2MOAC/screenshots

//        String username1=datamodel.getUsername().toString();
//        String accesskey1=datamodel.getAccesskey().toString();
        String username="mehadiathani";
        String accesskey="8WuJRtvxqQMIPkkFrGz0Ig2yx4ZyBDrcr1VX33f12uzbOETSLk";

        String urlCoomand=apiConsole.replace("{sessionID}",datamodel.getSessionID());

        URL url=new URL(urlCoomand);
        HttpURLConnection httpURLConnection1= (HttpURLConnection) url.openConnection();
        httpURLConnection1.setRequestMethod("GET");
        httpURLConnection1.setRequestProperty("Authorization",getAuthHeader1(username,accesskey));
        int responsecode=httpURLConnection1.getResponseCode();

        if(responsecode==HttpURLConnection.HTTP_OK){
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection1.getInputStream()));
            StringBuilder stringBuilder=new StringBuilder();
            String  line;
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            bufferedReader.close();

            responseDto.setResponseObject(stringBuilder);
            responseDto.setResponseCode(HttpStatus.OK.value());
            responseDto.setMessage("Console logs fetched !!!! ");
            responseDto.setStatus("success consolee");
        }else {
            responseDto.setResponseObject(null);
            responseDto.setResponseCode(HttpStatus.NOT_FOUND.value());
            responseDto.setMessage("not foind console logs fetched");
        }

        return  responseDto;

    }


    private String extractVidUrl (String responsebody){
                   String video_url = null;
                    try {


                        JSONObject jsonObject = new JSONObject(responsebody);
                      JSONObject  data = jsonObject.getJSONObject("data");
                        video_url = data.getString("video_url");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return video_url;
                }

                private static String getAuthHeader1 (String username, String accessKey){
                    String credentials = username + ":" + accessKey;
                    return "Basic " + java.util.Base64.getEncoder().encodeToString(credentials.getBytes());//Basic
                }


        }