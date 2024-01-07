package com.mk.busdemo.util;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mk.busdemo.client.response.JourneyResponse;
import com.mk.busdemo.client.response.StopResponse;
import com.mk.busdemo.dto.BusStopDto;
import com.mk.busdemo.dto.JourneyDto;


public final class DataUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    public static String convertObjectToString(Object domainObject) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(domainObject);
    }

    public static <T> T readValue(String data, Class<T> mapTarget) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(data, mapTarget);
    }


    /*
    - SECTION FOR CREATING TEST DATA.
     */
    public static JourneyResponse createJourneyResponseTestData() {
        return new JourneyResponse(
                List.of(new JourneyDto(1, 1, 11),
                        new JourneyDto(1, 1, 11),
                        new JourneyDto(2, 1, 11),
                        new JourneyDto(3, 1, 11),
                        new JourneyDto(3, 1, 11),
                        new JourneyDto(4, 1, 11),
                        new JourneyDto(4, 1, 11),
                        new JourneyDto(5, 1, 11),
                        new JourneyDto(5, 2, 11),
                        new JourneyDto(6, 2, 11),
                        new JourneyDto(6, 2, 11),
                        new JourneyDto(7, 1, 11),
                        new JourneyDto(7, 2, 11),
                        new JourneyDto(8, 2, 11),
                        new JourneyDto(8, 1, 11),
                        new JourneyDto(9, 2, 11),
                        new JourneyDto(9, 1, 11),
                        new JourneyDto(10, 1, 11),
                        new JourneyDto(10, 1, 11),
                        new JourneyDto(11, 1, 11),
                        new JourneyDto(11, 1, 11))
        );
    }


    public static StopResponse createStopsTestData() {
        return new StopResponse(
                List.of(new BusStopDto(11, "Gamla stan", 110)
                ));
    }


}
