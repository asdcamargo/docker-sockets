package decamargo.tutorials.java.socket.ws;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRest {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "OK - Running!";
    }
}
