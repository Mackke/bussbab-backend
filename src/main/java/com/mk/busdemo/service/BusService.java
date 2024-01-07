package com.mk.busdemo.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.mk.busdemo.client.BusAPIClient;
import com.mk.busdemo.client.response.JourneyResponse;
import com.mk.busdemo.client.response.StopResponse;
import com.mk.busdemo.dto.BusDataDto;
import com.mk.busdemo.dto.BusStopDto;
import com.mk.busdemo.dto.JourneyDto;
import com.mk.busdemo.dto.StopDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BusService {
    private static final Logger LOG = LoggerFactory.getLogger(BusService.class);

    private final BusAPIClient busAPIClient;

    /**
     * Loads BusData for the top 10 Buses with the most stops
     * Since loading of the Data is quite heavy due to huge amount of data, simple cache is used to store the result.
     * That way the frontend does not have to load too long for subsequent calls.
     * Cache can be used here without worries, because according to the docs of the API
     * the data is only updated once per day
     *
     * @return List of BusDataDTOs
     */
    @Cacheable(value = "BusDataCache")
    public List<BusDataDto> getBusData() {

        JourneyResponse journeyResponse = Optional.ofNullable(execWithTime(busAPIClient::getJourneys, "GetJourneys"))
                .orElseThrow(() -> new RuntimeException("JourneyResponse must not be null"));

        StopResponse busStopResponse = Optional.ofNullable(execWithTime(busAPIClient::getBusStops, "GetBusStops"))
                .orElseThrow(() -> new RuntimeException("BusStopResponse must not be null"));

        var lineNrToStopNr = mapLineToStops(journeyResponse);
        var stopNrToName = mapStopsToName(busStopResponse);
        var lineToStops = mergeLineWithStops(lineNrToStopNr, stopNrToName);
        return createBusData(lineToStops);
    }


    private List<BusDataDto> createBusData(Map<Integer, List<StopDto>> lineToStops) {
        return lineToStops.entrySet().stream()
                .map(entry -> new BusDataDto(entry.getKey(), entry.getValue()))
                .peek(busData -> busData.setAmountOfStops(busData.getStops().size()))
                .sorted(Comparator.comparing(BusDataDto::getAmountOfStops).reversed())
                .limit(10)
                .toList();
    }

    private Map<Integer, List<StopDto>> mergeLineWithStops(Map<Integer, List<Integer>> lineNrToStops,
                                                           Map<Integer, String> stopNrToName) {
        return lineNrToStops.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(stopNr -> new StopDto(stopNr, stopNrToName.get(stopNr)))
                                .collect(Collectors.toList())));
    }

    private Map<Integer, String> mapStopsToName(StopResponse busStopResponse) {
        return busStopResponse.getStops()
                .stream()
                .collect(Collectors.toMap(BusStopDto::getStopPointNumber, BusStopDto::getStopPointName));
    }

    //Might have to filter stops for a single direction since there are duplicate stops
    //But this was not explained in the assignment pdf so left it like this.
    private Map<Integer, List<Integer>> mapLineToStops(JourneyResponse journeyResponse) {
        return journeyResponse.getJourneys()
                .stream()
                .collect(Collectors.groupingBy(JourneyDto::getLineNumber,
                        Collectors.mapping(JourneyDto::getJourneyPatternPointNumber, Collectors.toList())));
    }


    //Just a helper method to log the execution time of a given function
    private <T> T execWithTime(Supplier<T> func, String name) {
        var start = System.currentTimeMillis();
        T result = func.get();
        var end = System.currentTimeMillis() - start;
        LOG.info("{} - execution took {} ms", name, end);
        return result;
    }
}
