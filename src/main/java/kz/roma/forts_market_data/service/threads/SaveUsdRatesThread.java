package kz.roma.forts_market_data.service.threads;

import kz.roma.forts_market_data.dao.usd_rates.UsdRatesDao;
import kz.roma.forts_market_data.domain_model.UsdRates;
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
public class SaveUsdRatesThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(SaveUsdRatesThread.class);

    @Autowired
    UsdRatesDao usdRatesDao;

    @Autowired
    private ApplicationContext applicationContext;

    private CyclicBarrier cyclicBarrier;
    private Exchanger<UsdRates> exchanger;

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public Exchanger<UsdRates> getExchanger() {
        return exchanger;
    }

    public void setExchanger(Exchanger<UsdRates> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            UsdRates usdRates = applicationContext.getBean(UsdRates.class);
            usdRates = exchanger.exchange(usdRates);
            logger.debug("Object after exchange in save to DB thread: " + usdRates.toString());

            saveUsdRates(usdRates);
            System.out.println("After UsdRates exchange:" + usdRates.toString());
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void saveUsdRates (UsdRates usdRates){
        usdRatesDao.save(usdRates);
    }
}
