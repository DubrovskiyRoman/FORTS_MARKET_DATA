package kz.roma.forts_market_data.dao.usd_rates;

import kz.roma.forts_market_data.domain_model.UsdRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsdRatesDaoImpl implements UsdRatesDao {
    @Autowired
    private UsdRatesRepo usdRatesRepo;

    @Override
    public void save(UsdRates usdRates) {
        usdRatesRepo.save(usdRates);
    }
}
