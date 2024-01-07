package com.mk.busdemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import com.mk.busdemo.client.BusAPIClient;
import com.mk.busdemo.client.response.JourneyResponse;
import com.mk.busdemo.dto.BusDataDto;
import com.mk.busdemo.dto.JourneyDto;
import com.mk.busdemo.util.DataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public class BusServiceTest {

    private BusService busService;

    @MockBean
    private BusAPIClient client;

    @BeforeEach
    void setUp() {
        busService = new BusService(client);
    }

    @Test
    public void givenJourneyApiReturnNull_whenGetBusData_thenReturnRunTimeException() {
        //when
        when(client.getJourneys()).thenReturn(null);

        //then
        assertThrows(RuntimeException.class, () -> busService.getBusData());
    }

    @Test
    public void givenStopApiReturnNull_whenGetBusData_thenReturnRunTimeException() {
        //given
        JourneyResponse response = new JourneyResponse(
                List.of(new JourneyDto(123, 1, 10234))
        );

        //when
        when(client.getJourneys()).thenReturn(response);
        when(client.getBusStops()).thenReturn(null);

        //then
        assertThrows(RuntimeException.class, () -> busService.getBusData());
    }

    @Test
    public void givenApiData_whenGetBusData_returnCalculatedBusData() {
        //given
        when(client.getJourneys()).thenReturn(DataUtil.createJourneyResponseTestData());
        when(client.getBusStops()).thenReturn(DataUtil.createStopsTestData());

        //when
        List<BusDataDto> result = busService.getBusData();

        //then
        assertFalse(result.isEmpty(), String.valueOf(false));
        assertEquals(result.size(), 10);
    }

}
