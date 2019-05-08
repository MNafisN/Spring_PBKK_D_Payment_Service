package me.pyradian.ojackpayment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "transactions")
public class  Transaction extends BaseEntity{
    @Field("transaction_type")
    protected String transactionType;
    @Field("transaction_id")
    @Indexed(direction = IndexDirection.ASCENDING)
    protected String transactionId;
    @Field("transaction_cashflow")
    protected String cashflow; // CREDIT, DEBIT
    @Field("transaction_status")
    protected String status; // confirmed, canceled, pending

    @JsonProperty("transaction_type")
    public String getTransactionType() {
        return transactionType;
    }

    @JsonProperty("transaction_id")
    public String getTransactionId() {
        return transactionId;
    }

    public String getCashflow() {
        return cashflow;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
