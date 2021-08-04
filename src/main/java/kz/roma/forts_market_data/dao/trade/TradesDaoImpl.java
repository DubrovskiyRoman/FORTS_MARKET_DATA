package kz.roma.forts_market_data.dao.trade;

import kz.roma.forts_market_data.domain_model.Trades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class TradesDaoImpl implements TradesDao {
    @Autowired
    private TradesRepo tradesRepo;

    @Override
    public void saveTrades(Trades trades) {
        tradesRepo.save(trades);
    }
}
