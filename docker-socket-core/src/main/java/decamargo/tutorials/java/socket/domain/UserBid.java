package decamargo.tutorials.java.socket.domain;

import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBid {

    private Long id;
    private String user;
    private double value;
    private Date timestamp;
    private String dateStr;
}
