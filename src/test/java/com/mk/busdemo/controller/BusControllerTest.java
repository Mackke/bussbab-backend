package com.mk.busdemo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mk.busdemo.client.BusAPIClient;
import com.mk.busdemo.client.response.JourneyResponse;
import com.mk.busdemo.client.response.StopResponse;
import com.mk.busdemo.dto.BusListResponse;
import com.mk.busdemo.util.DataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class BusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusAPIClient client;

    private final String baseUrl = "/app/v1/busdata";

    @Test
    public void givenExistingBuslineData_whenGetBusData_thenReturnOkAndSize10() throws Exception {
        //given
        JourneyResponse journeyResponse = DataUtil.createJourneyResponseTestData();
        StopResponse stopResponse = DataUtil.createStopsTestData();

        when(client.getJourneys()).thenReturn(journeyResponse);
        when(client.getBusStops()).thenReturn(stopResponse);

        //when
        String contentAsString = mockMvc.perform(get(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

       BusListResponse response = DataUtil.readValue(contentAsString, BusListResponse.class);

       //then
        assertFalse(response.getBuses().isEmpty(), "false");
        assertEquals(response.getBuses().size(), 10);

    }
}
