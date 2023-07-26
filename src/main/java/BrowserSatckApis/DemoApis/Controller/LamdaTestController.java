package BrowserSatckApis.DemoApis.Controller;

import BrowserSatckApis.DemoApis.Handler.imp.LambdsTestApiImpl;
import BrowserSatckApis.DemoApis.Model.Datamodel;
import BrowserSatckApis.DemoApis.Model.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/my")
public class LamdaTestController {

    @Autowired
    LambdsTestApiImpl lambdsTestApi;

    @GetMapping("/lambdavid/{username}/{accessKey}")
    public ResponseDto getVid(@PathVariable String username ,@PathVariable String accessKey){

        return lambdsTestApi.getVideoLambda(username,accessKey);
    }

    @GetMapping("/seleliumlogs")
    public ResponseDto getLogs(@RequestBody Datamodel datamodel) throws IOException {
        return lambdsTestApi.getLogs(datamodel);
    }

    @GetMapping("/commandLogs")
    public  ResponseDto getCommandLogs(@RequestBody Datamodel datamodel) throws IOException {

        return lambdsTestApi.getCommandlogs(datamodel);
    }

    @GetMapping("/netwrokLogs")
    public  ResponseDto getNetworkLogs(@RequestBody Datamodel datamodel) throws IOException {

        return lambdsTestApi.getNetworklogs(datamodel);
    }

    @GetMapping("/consoleLogs")
    public  ResponseDto getConsoleLogs(@RequestBody Datamodel datamodel) throws IOException {

        return  lambdsTestApi.getConsoleLogs(datamodel);
    }
}
