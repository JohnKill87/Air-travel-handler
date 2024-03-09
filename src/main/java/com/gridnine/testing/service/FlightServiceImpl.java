package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.builder.Segment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of a service for receiving various flight segments.
 */
public class FlightServiceImpl implements FlightService{

    /**
     * Service method for obtaining a departure segment up to the current point in time.
     * @return {@link Flight}
     */
    @Override
    public List<Flight> getFlightsSegmentsDepartingBeforeTheCurrentTime(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .anyMatch(segment -> segment.getDepartureDate()
                                .isAfter(LocalDateTime.now())))
                .collect(Collectors.toList());
    }

    /**
     * Service method for receiving a segment with an arrival date earlier than the departure date.
     * @return {@link Flight}
     */
    @Override
    public List<Flight> getFlightsSegmentsArrivingEarlierThanDepartureDate(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .noneMatch(segment -> segment.getArrivalDate()
                                .isBefore(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }

    /**
     * Service method for obtaining a flight segment where the total time spent on the ground exceeds two hours.
     * @return {@link Flight}
     */
    @Override
    public List<Flight> getFlightsWithTransfersOverTwoHours(List<Flight> flights) {
        return flights.stream()
                .filter(FlightServiceImpl::doesNotHaveMoreThanTwoHoursOnTheGround)
                .collect(Collectors.toList());
    }

    /**
     * Service method for displaying flight segments in the console.
     */
    @Override
    public void showFlights (String description, List<Flight> flights) {
        String delimiter = "============================================";
        System.out.println(delimiter);
        System.out.println("Segments of flights");
        System.out.println(description);
        System.out.println(delimiter);
        flights.forEach(System.out::println);
        System.out.println(delimiter);
    }

    /**
     * Service method for calculating flights where the total time spent on the ground does not exceed two hours.
     * @return {@link Boolean}
     */
    private static boolean doesNotHaveMoreThanTwoHoursOnTheGround(Flight flight) {
        Stream<Segment> segmentsSorted = flight
                .getSegments()
                .stream()
                .sorted();

        Iterator<Segment> segmentIterator = segmentsSorted.iterator();
        if (!segmentIterator.hasNext()) {
            return true;
        }

        long totalTimeOnGroundSeconds = 0L;
        Segment segment = segmentIterator.next();
        while (segmentIterator.hasNext()) {
            Segment nextSegment = segmentIterator.next();
            totalTimeOnGroundSeconds +=
                    nextSegment.getDepartureDate()
                            .toEpochSecond(ZoneOffset.UTC) - segment.getArrivalDate()
                            .toEpochSecond(ZoneOffset.UTC);
            if (totalTimeOnGroundSeconds > 2 * 3_600) {
                return false;
            }
            segment = nextSegment;
        }
        return true;
    }
}
