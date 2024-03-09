package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.builder.FlightBuilder;
import com.gridnine.testing.service.FlightService;
import com.gridnine.testing.service.FlightServiceImpl;

import java.util.List;

public class FlightFilterRunner {
    public static void runFilters() {

        FlightService flightService = new FlightServiceImpl();

        List<Flight> flights = FlightBuilder.createFlights();

        flightService.showFlights("Segments of all flights", flights);

        flightService.showFlights("Segments of flights with departures up to the current point in time",
                flightService.getFlightsSegmentsDepartingBeforeTheCurrentTime(flights));

        flightService.showFlights("Flight segments with an arrival date earlier than the departure date",
                flightService.getFlightsSegmentsArrivingEarlierThanDepartureDate(flights));

        flightService.showFlights("Flights where the total time spent on the ground exceeds two hours",
                flightService.getFlightsWithTransfersOverTwoHours(flights));
    }
}
