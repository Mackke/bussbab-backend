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
                        new JourneyDto(1, 1, 12),
                        new JourneyDto(1, 1, 13),
                        new JourneyDto(2, 1, 21),
                        new JourneyDto(2, 1, 22),
                        new JourneyDto(2, 1, 23),
                        new JourneyDto(3, 1, 31),
                        new JourneyDto(3, 1, 32),
                        new JourneyDto(3, 1, 33),
                        new JourneyDto(3, 1, 34),
                        new JourneyDto(4, 1, 41),
                        new JourneyDto(4, 1, 42),
                        new JourneyDto(4, 1, 43),
                        new JourneyDto(4, 1, 44),
                        new JourneyDto(4, 1, 45),
                        new JourneyDto(5, 1, 51),
                        new JourneyDto(5, 2, 52),
                        new JourneyDto(5, 1, 53),
                        new JourneyDto(5, 1, 54),
                        new JourneyDto(5, 1, 55),
                        new JourneyDto(5, 2, 56),
                        new JourneyDto(6, 1, 61),
                        new JourneyDto(6, 1, 62),
                        new JourneyDto(6, 2, 63),
                        new JourneyDto(6, 2, 64),
                        new JourneyDto(7, 1, 71),
                        new JourneyDto(7, 2, 72),
                        new JourneyDto(8, 2, 81),
                        new JourneyDto(8, 1, 82),
                        new JourneyDto(8, 2, 83),
                        new JourneyDto(8, 2, 84),
                        new JourneyDto(8, 1, 85),
                        new JourneyDto(9, 2, 91),
                        new JourneyDto(9, 2, 92),
                        new JourneyDto(9, 2, 93),
                        new JourneyDto(9, 1, 94),
                        new JourneyDto(10, 1, 101),
                        new JourneyDto(10, 2, 102),
                        new JourneyDto(10, 2, 103),
                        new JourneyDto(10, 1, 104),
                        new JourneyDto(10, 1, 105),
                        new JourneyDto(11, 1, 112),
                        new JourneyDto(11, 2, 113),
                        new JourneyDto(11, 1, 114),
                        new JourneyDto(11, 1, 115))
        );
    }


    public static StopResponse createStopsTestData() {
        return new StopResponse(
                List.of(new BusStopDto(11, "Gamla stan", 110),
                        new BusStopDto(12, "Slussen", 120),
                        new BusStopDto(13, "Maria torg", 130),
                        new BusStopDto(21, "Farsta Centrum", 210),
                        new BusStopDto(22, "Kungsängen", 220),
                        new BusStopDto(23, "Huddinge station", 230),
                        new BusStopDto(31, "TC", 310),
                        new BusStopDto(32, "Rådmansgatan", 320),
                        new BusStopDto(33, "Odenplan", 330),
                        new BusStopDto(34, "Alvik", 340),
                        new BusStopDto(41, "Vårsta Centrum", 410),
                        new BusStopDto(42, "Gjuteriporten", 420),
                        new BusStopDto(43, "Jovisgatan", 430),
                        new BusStopDto(44, "Håga", 440),
                        new BusStopDto(45, "Rånövägen", 450),
                        new BusStopDto(51, "Åkeshovsvägen", 510),
                        new BusStopDto(52, "Odenplan", 520),
                        new BusStopDto(53, "Brommaplan", 530),
                        new BusStopDto(54, "Tillflykten", 540),
                        new BusStopDto(55, "Hagalund", 550),
                        new BusStopDto(56, "Ekerö kyrkväg", 560),
                        new BusStopDto(61, "Danderyds sjukhus", 610),
                        new BusStopDto(62, "Rimbo station", 620),
                        new BusStopDto(63, "Karlberg", 630),
                        new BusStopDto(64, "Grindstugan", 640),
                        new BusStopDto(71, "Norrsand", 710),
                        new BusStopDto(72, "Largbacken", 720),
                        new BusStopDto(81, "Grisslehamns skola", 810),
                        new BusStopDto(82, "Grisslehamns kapell", 820),
                        new BusStopDto(83, "Grisslehamns färjeläge", 830),
                        new BusStopDto(84, "Grisslehamns centrum", 840),
                        new BusStopDto(85, "Kolskär", 850),
                        new BusStopDto(91, "Hackstavägen", 910),
                        new BusStopDto(92, "Söraskolan", 920),
                        new BusStopDto(93, "Skånsta", 930),
                        new BusStopDto(94, "Storvreten", 940),
                        new BusStopDto(101, "Skeppsmyra affär", 1010),
                        new BusStopDto(102, "Skeppsmyra by", 1020),
                        new BusStopDto(103, "Skeppsmyra östra", 1030),
                        new BusStopDto(104, "Skeppsmyra norra", 1040),
                        new BusStopDto(105, "Skeppsmyra södra", 1050),
                        new BusStopDto(112, "Rådmanby affär", 1120),
                        new BusStopDto(113, "Rådmanby by", 1130),
                        new BusStopDto(114, "Rådmanby skola", 1140),
                        new BusStopDto(115, "Rådmanby kyrka", 1150)
                ));
    }


}
