package BrowserSatckApis.DemoApis.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Datamodel {

    private String accesskey;
    private String username;
    private String sessionID;
}
