package com.mk.busdemo.client.response;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.mk.busdemo.dto.JourneyDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "ResponseData")
public class JourneyResponse {

    @JacksonXmlProperty(localName = "JourneyPatternPointOnLine")
    private List<JourneyDto> journeys;
}