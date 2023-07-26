package BrowserSatckApis.DemoApis.Handler;

import BrowserSatckApis.DemoApis.Model.Datamodel;
import BrowserSatckApis.DemoApis.Model.ResponseDto;

import java.io.IOException;
import java.security.PublicKey;

public interface LamdbatestApi {

    public ResponseDto getVideoLambda(String username,String accessKey);


    public ResponseDto getLogs(Datamodel datamodel) throws IOException;

    public ResponseDto getCommandlogs(Datamodel datamodel) throws IOException;

    public ResponseDto getNetworklogs(Datamodel datamodel) throws IOException;


    public ResponseDto getConsoleLogs(Datamodel datamodel) throws  IOException;

}
