package kz.roma.forts_market_data.service.threads;

import kz.roma.forts_market_data.domain_model.Trades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

@Service
@Scope("prototype")

public class ParseMessageTrades implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ParseMessageTrades.class);

    @Autowired
    private ApplicationContext applicationContext;

    private CyclicBarrier cyclicBarrier;

    private Exchanger<Trades> tradesExchanger;

    private HashMap<String, Object> tradesHashMap;

    public Exchanger<Trades> getTradesExchanger() {
        return tradesExchanger;
    }

    public void setTradesExchanger(Exchanger<Trades> tradesExchanger) {
        this.tradesExchanger = tradesExchanger;
    }

    public void setTradesHashMap(HashMap<String, Object> tradesHashMap) {
        this.tradesHashMap = tradesHashMap;
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        parseToObject(tradesHashMap);
    }

    private void parseToObject(HashMap<String, Object> tradesHashMap) {
        try {
            Trades trades = applicationContext.getBean(Trades.class);
            trades.setTradeNumber((Long) tradesHashMap.get("id_deal"));
            trades.setRepId((Long) tradesHashMap.get("replId"));
            trades.setIsin((String) tradesHashMap.get("isin"));
            trades.setAmount((Integer) tradesHashMap.get("xamount"));
            logger.debug("Object with rowId: " + trades.getRepId() + " is parsed ");
            logger.debug("Object before exchange in parse thread: " + trades.toString());
            trades = tradesExchanger.exchange(trades);
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }


}

