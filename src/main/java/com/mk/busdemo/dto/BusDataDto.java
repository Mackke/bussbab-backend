package com.mk.busdemo.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusDataDto {
    private int line;
    private List<StopDto> stops;
    private int amountOfStops;

    public BusDataDto(int line, List<StopDto> stops) {
        this.line = line;
        this.stops = stops;
    }
}
