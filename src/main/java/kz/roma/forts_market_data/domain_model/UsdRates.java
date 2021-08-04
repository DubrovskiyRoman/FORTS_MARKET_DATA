package kz.roma.forts_market_data.domain_model;

import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Scope("prototype")
@Document(collection = "usd_rates")
public class UsdRates {
    @Id
    private String id;
    @Field(value = "rep_id")
    private Integer repId;
    @Field(value = "repl_rev_id")
    private long replRevId;
    @Field(value = "repl_act")
    private Integer relAct;
    @Field(value = "valut_id")
    private Integer valutId;
    @Field(value = "rate_id")
    private double rate;
    @Field(value = "local_date_time")
    private LocalDateTime localDateTime;

    public void setRepId(Integer repId) {
        this.repId = repId;
    }


    public long getReplRevId() {
        return replRevId;
    }

    public void setReplRevId(long replRevId) {
        this.replRevId = replRevId;
    }

    public long getRelAct() {
        return relAct;
    }

    public void setRelAct(Integer relAct) {
        this.relAct = relAct;
    }

    public long getValutId() {
        return valutId;
    }

    public void setValutId(Integer valutId) {
        this.valutId = valutId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "UsdRates{" +
                "id=" + id +
                ", repId=" + repId +
                ", replRevId=" + replRevId +
                ", relAct=" + relAct +
                ", valutId=" + valutId +
                ", rate=" + rate +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
