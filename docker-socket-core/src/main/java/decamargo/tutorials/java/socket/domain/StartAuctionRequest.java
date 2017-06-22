package decamargo.tutorials.java.socket.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Entity(name = "Auctions")
public class StartAuctionRequest {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "GOOD", length = 180, nullable = false)
    private String good;

    @Column
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date startTime;

    @Column
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    @Temporal(TemporalType.DATE)
    private Date endTime;

    @Column(name = "MINIMUM_BID")
    private double minimumBid;

    @Column(name = "RUNNING")
    private boolean running;
}
