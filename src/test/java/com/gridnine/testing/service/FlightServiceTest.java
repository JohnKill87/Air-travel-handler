package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.builder.FlightBuilder;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class FlightServiceTest {

    FlightService flightService = new FlightServiceImpl();
    FlightConstructor flightConstructor = new FlightConstructor();

    @Test
    void getFlightsSegmentsDepartingBeforeTheCurrentTime() {
        List<Flight> flights = FlightBuilder.createFlights();
        List<Flight> expectedToBeDeleted = FlightBuilder.getFlightsDepartingBeforeTheCurrentTime();
        List<Flight> flightsResult = flightService.getFlightsSegmentsDepartingBeforeTheCurrentTime(flights);

        Condition<Flight> departingBeforeTheCurrentTimeCondition = new Condition<>(
                flight -> flight
                        .getSegments()
                        .stream()
                        .anyMatch(
                                segment -> segment.getDepartureDate()
                                        .isBefore(LocalDateTime.now())
                        ), "Departure in the past for some segments"
        );

       flightConstructor.assertions(flights, flightsResult, departingBeforeTheCurrentTimeCondition);
       flightConstructor.assertions(flights, flightsResult, expectedToBeDeleted);
    }

    @Test
    void getFlightsSegmentsArrivingEarlierThanDepartureDate() {
        List<Flight> flights = FlightBuilder.createFlights();
        List<Flight> expectedToBeDeleted = FlightBuilder.getFlightsArrivingEarlierThanDepartureDate();
        List<Flight> flightsResult = flightService.getFlightsSegmentsArrivingEarlierThanDepartureDate(flights);

        Condition<Flight> arrivingEarlierThanDepartureDateCondition = new Condition<>(
                flight -> flight
                        .getSegments()
                        .stream()
                        .anyMatch(
                                segment -> segment.getArrivalDate()
                                        .isBefore(segment.getDepartureDate())
                        ), "Arrival before the departure in some segments"
        );

        flightConstructor.assertions(flights, flightsResult, arrivingEarlierThanDepartureDateCondition);
        flightConstructor.assertions(flights, flightsResult, expectedToBeDeleted);
    }

    @Test
    void getFlightsWithTransfersOverTwoHours() {
        List<Flight> flights = FlightBuilder.createFlights();
        List<Flight> expectedToBeDeleted = FlightBuilder.getFlightsWithTransfersOverTwoHours();
        List<Flight> flightsResult = flightService.getFlightsWithTransfersOverTwoHours(flights);

        flightConstructor.assertions(flights, flightsResult, expectedToBeDeleted);
    }
}