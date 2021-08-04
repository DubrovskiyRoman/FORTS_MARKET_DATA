package kz.roma.forts_market_data.dao.trade;

import kz.roma.forts_market_data.domain_model.Trades;

public interface TradesDao {
    void saveTrades(Trades trades);
}
