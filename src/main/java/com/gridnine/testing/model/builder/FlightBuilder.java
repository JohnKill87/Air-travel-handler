package com.gridnine.testing.model.builder;

import com.gridnine.testing.model.Flight;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightBuilder {
    private static List<Flight> createdFlights;

//    Departure to the current point in time.
    private static final List<Flight> flightsDepartingBeforeTheCurrentTime = new ArrayList<>();

//    Segments with an arrival date earlier than the departure date.
    private static final List<Flight> flightsArrivingEarlierThanDepartureDate = new ArrayList<>();

//    Flights where the total time spent on the ground exceeds two hours
    private static final List<Flight> flightsWithTransfersOverTwoHours = new ArrayList<>();


    public static List<Flight> getCreatedFlights() {
        return (createdFlights == null)? createFlights() : createdFlights;
    }

    public static List<Flight> getFlightsDepartingBeforeTheCurrentTime() {
        return checkFlightAvailability(flightsDepartingBeforeTheCurrentTime);
    }

    public static List<Flight> getFlightsArrivingEarlierThanDepartureDate() {
        return checkFlightAvailability(flightsArrivingEarlierThanDepartureDate);
    }

    public static List<Flight> getFlightsWithTransfersOverTwoHours() {
        return checkFlightAvailability(flightsWithTransfersOverTwoHours);
    }

    private static List<Flight> checkFlightAvailability(List<Flight> flights) {
        if (flights.isEmpty()) {
            createFlights();
        }
        return flights;
    }

    private static Flight createListOfFlightsDepartingBeforeTheCurrentTime(Flight flight) {
        flightsDepartingBeforeTheCurrentTime.add(flight);
        return flight;
    }

    private static Flight createListOfFlightsArrivingEarlierThanDepartureDate(Flight flight) {
        flightsArrivingEarlierThanDepartureDate.add(flight);
        return flight;
    }

    private static Flight createListOfFlightsWithTransfersOverTwoHours(Flight flight) {
        flightsWithTransfersOverTwoHours.add(flight);
        return flight;
    }

    public static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);

        return Arrays.asList(
            //A normal flight with two hour duration
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
            //A normal multi segment flight
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
            //A flight departing in the past
            createListOfFlightsDepartingBeforeTheCurrentTime(
            createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow)),
            //A flight that departs before it arrives
            createListOfFlightsArrivingEarlierThanDepartureDate(
            createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6))),
            //A flight with more than two hours ground time
            createListOfFlightsWithTransfersOverTwoHours(
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6))),
            //Another flight with more than two hours ground time
            createListOfFlightsWithTransfersOverTwoHours(
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7))));
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}
