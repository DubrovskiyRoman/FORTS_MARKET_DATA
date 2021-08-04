package kz.roma.forts_market_data.domain_model;


import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Document(collection = "trades")
public class Trades {
    @Id
    private String Id;
    @Field(value = "rep_id")
    private Long repId;
    @Field (value = "trade_number")
    private Long tradeNumber;
    @Field (value = "isin")
    private String isin;
    @Field (value = "deal_amount")
    private Integer amount;
    @Field (value = "deal_price")
    private Long price;

    public Long getRepId() {
        return repId;
    }

    public void setRepId(Long repId) {
        this.repId = repId;
    }

    public Long getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(Long tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Trades{" +
                "Id='" + Id + '\'' +
                ", repId=" + repId +
                ", tradeNumber=" + tradeNumber +
                ", isin='" + isin + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
