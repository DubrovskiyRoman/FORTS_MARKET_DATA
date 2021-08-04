package kz.roma.forts_market_data.service.threads;


import kz.roma.forts_market_data.dao.trade.TradesDao;
import kz.roma.forts_market_data.domain_model.Trades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

@Service
@Scope("prototype")
public class SaveTradesThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(SaveTradesThread.class);

    @Autowired
    private TradesDao tradesDao;

    @Autowired
    private ApplicationContext applicationContext;

    private CyclicBarrier cyclicBarrier;
    private Exchanger<Trades> exchanger;


    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public Exchanger<Trades> getExchanger() {
        return exchanger;
    }

    public void setExchanger(Exchanger<Trades> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            Trades trades = applicationContext.getBean(Trades.class);
            trades = exchanger.exchange(trades);
            logger.debug("Object after exchange in save to DB thread: " + trades.toString());
            saveTradesThread(trades);
            logger.debug("object with rowId: " + trades.getRepId() + " is saved in DB");
            cyclicBarrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveTradesThread(Trades trades) {
        tradesDao.saveTrades(trades);
    }
}
