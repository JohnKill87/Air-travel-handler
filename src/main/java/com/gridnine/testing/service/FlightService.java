package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> getFlightsSegmentsDepartingBeforeTheCurrentTime(List<Flight> flights);
    List<Flight> getFlightsSegmentsArrivingEarlierThanDepartureDate(List<Flight> flights);
    List<Flight> getFlightsWithTransfersOverTwoHours(List<Flight> flights);
    void showFlights (String description, List<Flight> flights);
}
