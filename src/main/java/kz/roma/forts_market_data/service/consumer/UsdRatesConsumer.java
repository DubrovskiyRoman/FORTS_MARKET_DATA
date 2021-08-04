package kz.roma.forts_market_data.service.consumer;

import kz.roma.forts_market_data.config.RabbitConfig;
import kz.roma.forts_market_data.domain_model.UsdRates;
import kz.roma.forts_market_data.service.threads.ParseMessageUsdRate;
import kz.roma.forts_market_data.service.threads.SaveUsdRatesThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;

@Component
public class UsdRatesConsumer {
    private static Logger logger = LoggerFactory.getLogger(UsdRates.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private CyclicBarrier cyclicBarrier;

    @Autowired
    private Exchanger<UsdRates> exchanger;

    @Autowired
    private ParseMessageUsdRate parseObjectToUsdRate;

    @RabbitListener(queues = RabbitConfig.usdRatesQueue)
    public void consumeInstrMessage(HashMap<String, Object> usdRatesHashMap) {
        logger.debug("Trades Message from Queue: " + usdRatesHashMap.toString());
        ParseMessageUsdRate parseObjectToUsdRate = applicationContext.getBean(ParseMessageUsdRate.class);
        parseObjectToUsdRate.setCyclicBarrier(cyclicBarrier);
        parseObjectToUsdRate.setUsdRatesExchanger(exchanger);
        parseObjectToUsdRate.setUsdRatesMap(usdRatesHashMap);
        logger.debug("Sent UsdRates message to parser: ");

        SaveUsdRatesThread saveUsdRatesThread = applicationContext.getBean(SaveUsdRatesThread.class);
        saveUsdRatesThread.setCyclicBarrier(cyclicBarrier);
        saveUsdRatesThread.setExchanger(exchanger);

        executorService.execute(parseObjectToUsdRate);
        executorService.execute(saveUsdRatesThread);


    }
}
