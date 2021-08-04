package kz.roma.forts_market_data.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String fortsMarketDataExch = "forts_marketData";

    public static final String usdRatesQueue = "usd_rates_queue";
    public static final String tradesQueue = "trades_queue";


    public static final String marketDataRoutingKEY = "FORTS.MARKETDATA.USD_RATES";
    public static final String tradesRoutingKEY = "FORTS.MARKETDATA.TRADES";


    @Bean
    public Queue usdRatesQueue(){
        return new Queue(usdRatesQueue, false);
    }

    @Bean
    public Queue tradesQueue(){
        return new Queue(tradesQueue, false);
    }

    @Bean
    public TopicExchange fortsMarketDataExch () {
        return new TopicExchange(fortsMarketDataExch, false, false);
    }

    @Bean
    public Binding FortsMarketDataExchBindUsdRatesQueue(Queue usdRatesQueue, TopicExchange fortsMarketDataExch) {
        return BindingBuilder.bind(usdRatesQueue).to(fortsMarketDataExch).with( marketDataRoutingKEY);
    }

    @Bean
    public Binding FortsMarketDataExchBindTradesQueue(Queue tradesQueue, TopicExchange fortsMarketDataExch) {
        return BindingBuilder.bind(tradesQueue).to(fortsMarketDataExch).with(tradesRoutingKEY);
    }


    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}

