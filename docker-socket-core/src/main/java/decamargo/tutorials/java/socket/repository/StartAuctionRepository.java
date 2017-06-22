package decamargo.tutorials.java.socket.repository;

import decamargo.tutorials.java.socket.domain.StartAuctionRequest;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface StartAuctionRepository extends CrudRepository<StartAuctionRequest, Long> {

    List<StartAuctionRequest> findByRunningTrue();
}
