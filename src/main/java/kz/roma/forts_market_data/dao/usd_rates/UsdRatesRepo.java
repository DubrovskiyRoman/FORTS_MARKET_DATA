package kz.roma.forts_market_data.dao.usd_rates;

import kz.roma.forts_market_data.domain_model.UsdRates;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface UsdRatesRepo extends MongoRepository <UsdRates, String> {
}
