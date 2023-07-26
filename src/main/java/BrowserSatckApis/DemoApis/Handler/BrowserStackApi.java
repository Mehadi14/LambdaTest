package BrowserSatckApis.DemoApis.Handler;

import BrowserSatckApis.DemoApis.Model.ResponseDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;


@Component
public interface BrowserStackApi {


    public ResponseDto downloadVid(String username, String accesskey , String sessionId);

    public ResponseDto getSessionId(String username, String accesskey) throws Exception;

    public ResponseDto getSessionLogs(String username,String accessKey,  String sessionId) throws IOException;

    public  ResponseDto getSeleniumLog(String username,String accesskey,String sessionId) throws IOException;

    public  ResponseDto getNetworkLogs(String username,String  accesskey ,String sessionId) throws IOException;
}
