package com.service.paymentservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public class BaseEntity {
    @Id
    private String id;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public String getId() {
        return id;
    }

    @JsonProperty("created_date")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }

    @JsonProperty("last_modified_date")
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

//    public void setLastModifiedDate(Date lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//    }
}