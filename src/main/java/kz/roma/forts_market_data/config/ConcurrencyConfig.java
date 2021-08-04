package kz.roma.forts_market_data.config;

import kz.roma.forts_market_data.domain_model.Trades;
import kz.roma.forts_market_data.domain_model.UsdRates;
import kz.roma.forts_market_data.service.threads.ShutdownExecutorServiceThreads;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.*;

@Configuration
public class ConcurrencyConfig {
    @Bean
    @Scope("prototype")
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(2);
    }

    @Bean
    @Scope("prototype")
    public CyclicBarrier cyclicBarrier() {
        return new CyclicBarrier(2, new ShutdownExecutorServiceThreads(executorService()));
    }

    @Bean
    @Scope ("prototype")
    public Exchanger<Trades> exchangerTrades(){
        return new Exchanger();
    }

    @Bean
    @Scope ("prototype")
    public Exchanger<UsdRates> exchangerRates(){
        return new Exchanger();
    }
}
