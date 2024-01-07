package com.mk.busdemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JourneyDto {

    @JsonProperty(value = "LineNumber")
    @JacksonXmlProperty(localName = "LineNumber")
    private int lineNumber;

    @JsonProperty(value = "DirectionCode")
    @JacksonXmlProperty(localName = "DirectionCode")
    private int directionCode;

    @JsonProperty(value = "JourneyPatternPointNumber")
    @JacksonXmlProperty(localName = "JourneyPatternPointNumber")
    private int journeyPatternPointNumber;
}
