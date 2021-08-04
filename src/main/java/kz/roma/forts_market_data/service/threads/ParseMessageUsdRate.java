package kz.roma.forts_market_data.service.threads;

import kz.roma.forts_market_data.dao.usd_rates.UsdRatesRepo;
import kz.roma.forts_market_data.domain_model.UsdRates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

@Service
public class ParseMessageUsdRate implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ParseMessageUsdRate.class);
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UsdRatesRepo usdRatesRepo;

    private CyclicBarrier cyclicBarrier;
    private Exchanger<UsdRates> usdRatesExchanger;
    private HashMap<String, Object> usdRatesMap;


    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public Exchanger<UsdRates> getUsdRatesExchanger() {
        return usdRatesExchanger;
    }

    public void setUsdRatesExchanger(Exchanger<UsdRates> usdRatesExchanger) {
        this.usdRatesExchanger = usdRatesExchanger;
    }

    public HashMap<String, Object> getUsdRatesMap() {
        return usdRatesMap;
    }

    public void setUsdRatesMap(HashMap<String, Object> usdRatesMap) {
        this.usdRatesMap = usdRatesMap;
    }

    @Override
    public void run() {
        parseToObject(usdRatesMap);
    }

    private void parseToObject(HashMap<String, Object> usdRatesMap) {
        UsdRates usdRates = applicationContext.getBean(UsdRates.class);
        usdRates.setRepId((Integer) usdRatesMap.get("replID"));
        usdRates.setReplRevId((Integer) usdRatesMap.get("replRev"));
        usdRates.setRelAct((Integer) usdRatesMap.get("replAct"));
        usdRates.setValutId((Integer) usdRatesMap.get("valut_id"));
        usdRates.setRate((Double) usdRatesMap.get("rate"));
        usdRates.setLocalDateTime(LocalDateTime.now());
        System.out.println("Before USD Rates exchange: " + usdRates.toString());
        logger.debug("Object with rowId: " + usdRates.getReplRevId() + " is parsed ");
        logger.debug("Object before exchange in parse thread: " + usdRates.toString());

        try {
            usdRates = usdRatesExchanger.exchange(usdRates);
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
}
