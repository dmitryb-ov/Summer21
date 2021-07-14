package com.rest.app.orionrestapplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryDto extends BaseDto {
    private Long username;
    private String ip;
    private String requestType;
    private Date created;
    private Date updated;
    private String className;
    private String methodName;
}
