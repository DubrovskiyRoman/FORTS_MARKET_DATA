package kz.roma.forts_market_data.dao.trade;

import kz.roma.forts_market_data.domain_model.Trades;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TradesRepo extends MongoRepository<Trades, String> {
}
