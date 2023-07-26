package BrowserSatckApis.DemoApis.Controller;


import BrowserSatckApis.DemoApis.Handler.BrowserStackApi;
import BrowserSatckApis.DemoApis.Model.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/browser")
public class BrowserController {

    @Autowired
    BrowserStackApi browserStackApi;

    @GetMapping("/sessionId")
    public ResponseDto getID(@RequestParam String username , @RequestParam String accesskey) throws Exception {

        return browserStackApi.getSessionId(username,accesskey);
    }

    @GetMapping("/vid/{sessionId}")
    public ResponseDto getVid(@RequestParam String username , @RequestParam String accesskey ,@PathVariable String sessionId) throws MalformedURLException {

        return browserStackApi.downloadVid(username,accesskey,sessionId);
    }

    @GetMapping("/{sessionId}")
    public  ResponseDto getSessionLogs(@RequestParam String username, @RequestParam String accesskey,@PathVariable String sessionId) throws IOException {
        return browserStackApi.getSessionLogs(username,accesskey,sessionId);
    }

     @GetMapping("/sel/{sessionId}")
    public ResponseDto getSeleniumLogs(@RequestParam String username, @RequestParam String accesskey,@PathVariable String sessionId) throws IOException {
        return browserStackApi.getSeleniumLog(username,accesskey,sessionId);
    }


    @GetMapping("/net/{sessionId}")
    public  ResponseDto getNetworkLogs(@RequestParam String username, @RequestParam String accesskey,@PathVariable String sessionId) throws IOException {

        return browserStackApi.getNetworkLogs(username,accesskey,sessionId);
    }

}
