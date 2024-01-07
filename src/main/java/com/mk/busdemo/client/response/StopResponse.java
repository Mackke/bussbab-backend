package com.mk.busdemo.client.response;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.mk.busdemo.dto.BusStopDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "ResponseData")
public class StopResponse {

    @JacksonXmlProperty(localName = "StopPoint")
    private List<BusStopDto> stops;
}

