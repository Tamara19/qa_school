package ru.lesson7;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TradeInfo {
    private String id;
    private String instrumentId;
    private Long priceAlert;
    private String status;
    private String secName;
    private String ticker;
    private String secType;
    private String createdAt;
    private String completedAt;
    private String canceledAt;
}
