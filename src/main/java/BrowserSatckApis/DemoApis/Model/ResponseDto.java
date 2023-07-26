package BrowserSatckApis.DemoApis.Model;

import lombok.Data;

@Data
public class ResponseDto {

    private int responseCode;
    private Object responseObject;
    private String message;
    private String status;

}
