package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import org.assertj.core.api.Condition;
import org.assertj.core.api.ListAssert;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FlightConstructor {

    public void assertions(List<Flight> flights, List<Flight> flightsResult, Condition<Flight> condition) {
        ListAssert<Flight> assertFlights = assertThat(flights);
        ListAssert<Flight> assertFlightsResult = assertThat(flightsResult);
        int flightsSize = flights.size();
        int flingsResultSize = flightsResult.size();
        int expectedFlightsSize = flightsSize - flingsResultSize;

        assertFlights
                .filteredOn(condition)
                .isNotEmpty()
                .size().isEqualTo(expectedFlightsSize);

        assertFlightsResult
                .filteredOn(condition)
                .isEmpty();

        assertFlightsResult
                .isNotEmpty()
                .size().isEqualTo(flightsSize - expectedFlightsSize);
    }

    public void assertions(List<Flight> flights, List<Flight> flightsResult, List<Flight> expectedToBeDeleted) {
        ListAssert<Flight> assertFlightsResult = assertThat(flightsResult);
        int flightsSize = flights.size();
        int expectedFlightsResultSize = flightsSize - expectedToBeDeleted.size();

        assertFlightsResult
                .hasSize(expectedFlightsResultSize)
                .doesNotContainAnyElementsOf(expectedToBeDeleted);
    }
}
