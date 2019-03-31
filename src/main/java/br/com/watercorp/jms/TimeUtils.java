package br.com.watercorp.jms;

public final class TimeUtils {

    private TimeUtils() {
    }

    public static long getTimeBetweenTimesInSeconds(long startTime, long endTime) {
        long diff = endTime - startTime;

        return diff / 1000 % 60;
    }

    public long getTimeBetweenTimesInMinutes(long startTime, long endTime) {
        long diff = endTime - startTime;

        return diff / (60 * 1000) % 60;
    }
}
