package com.cpe.fire.domain.gps;

import java.util.Random;

public class GPSGenerator{
    private static final double RHONE_MIN_LATITUDE = 45.349673;
    private static final double RHONE_MAX_LATITUDE = 45.992504;
    private static final double RHONE_MIN_LONGITUDE = 4.466378;
    private static final double RHONE_MAX_LONGITUDE = 5.592079;

    public static double[] generateRandomCoordinates() {
        double randomLatitude = generateRandomCoordinate(RHONE_MIN_LATITUDE, RHONE_MAX_LATITUDE);
        double randomLongitude = generateRandomCoordinate(RHONE_MIN_LONGITUDE, RHONE_MAX_LONGITUDE);
        return new double[]{randomLatitude, randomLongitude};
    }
    private static double generateRandomCoordinate(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
}
