package kz.roma.forts_market_data.service.consumer;

import kz.roma.forts_market_data.config.RabbitConfig;
import kz.roma.forts_market_data.domain_model.Trades;
import kz.roma.forts_market_data.service.threads.ParseMessageTrades;
import kz.roma.forts_market_data.service.threads.SaveTradesThread;
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
public class TradesConsumer {
    private static Logger logger = LoggerFactory.getLogger(TradesConsumer.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private CyclicBarrier cyclicBarrier;

    @Autowired
    private Exchanger<Trades> exchanger;

    @Autowired
    private ParseMessageTrades parseMessageTrades;

    @Autowired
    private SaveTradesThread saveTradesThread;


    @RabbitListener(queues = RabbitConfig.tradesQueue)
    public void consumeTradesMessage(HashMap<String, Object> tradesHashMap) {
        logger.debug("Trades Message from Queue: " + tradesHashMap.toString());
        ParseMessageTrades parseMessageTrades = applicationContext.getBean(ParseMessageTrades.class);
        parseMessageTrades.setTradesHashMap(tradesHashMap);
        parseMessageTrades.setTradesExchanger(exchanger);
        logger.debug("Sent Trades message to parser and save in DB: " + tradesHashMap.toString());
        parseMessageTrades.setCyclicBarrier(cyclicBarrier);

        SaveTradesThread saveTradesThread = applicationContext.getBean(SaveTradesThread.class);
        saveTradesThread.setCyclicBarrier(cyclicBarrier);
        saveTradesThread.setExchanger(exchanger);

        executorService.execute(parseMessageTrades);
        executorService.execute(saveTradesThread);

    }
}
