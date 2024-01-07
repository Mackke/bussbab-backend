package com.mk.busdemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusStopDto {
    @JsonProperty(value = "StopPointNumber")
    @JacksonXmlProperty(localName = "StopPointNumber")
    private int stopPointNumber;

    @JsonProperty(value = "StopPointName")
    @JacksonXmlProperty(localName = "StopPointName")
    private String stopPointName;

    @JsonProperty(value = "StopAreaNumber")
    @JacksonXmlProperty(localName = "StopAreaNumber")
    private int stopAreaNumber;
}
