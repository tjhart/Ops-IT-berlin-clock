package com.ubs.opsit.interviews;

import static org.apache.commons.lang.StringUtils.leftPad;
import static org.apache.commons.lang.StringUtils.rightPad;

public class BerlinClock implements TimeConverter {

    private static final char MAJOR_LAMP_ON = 'R';
    private static final char LAMP_OFF = 'O';
    private static final char MINOR_LAMP_ON = 'Y';
    public static final int STANDARD_ROW_LENGTH = 4;
    public static final int MINUTE_ROW_1_LENGTH = 11;
    public static final int UNIT_GROUP = 5;
    private static final int[] QUARTER_HOUR_MARKERS = {2, 5, 8};

    @Override
    public String convertTime(String aTime) {
        String[] timeComponents = aTime.split(":");
        return seconds(Integer.valueOf(timeComponents[2])) + "\n" +
                hours(Integer.valueOf(timeComponents[0])) + "\n" +
                minutes(Integer.valueOf(timeComponents[1]));
    }

    private char seconds(int seconds) {
        return seconds % 2 == 1 ? LAMP_OFF : MINOR_LAMP_ON;
    }

    private String hours(int hours) {
        return makeLampRow(STANDARD_ROW_LENGTH, hours / UNIT_GROUP, MAJOR_LAMP_ON) + "\n" +
                makeLampRow(STANDARD_ROW_LENGTH, hours % UNIT_GROUP, MAJOR_LAMP_ON);
    }

    private String minutes(int minutes) {
        char[] row1 = makeLampRow(MINUTE_ROW_1_LENGTH, minutes / UNIT_GROUP, MINOR_LAMP_ON).toCharArray();
        for (int quarterHourMarker : QUARTER_HOUR_MARKERS) {
            if (row1[quarterHourMarker] == MINOR_LAMP_ON) {
                row1[quarterHourMarker] = MAJOR_LAMP_ON;
            }
        }
        return new String(row1) + "\n" +
                makeLampRow(STANDARD_ROW_LENGTH, minutes % UNIT_GROUP, MINOR_LAMP_ON);
    }

    private String makeLampRow(int rowLength, int lampsOn, char onColor) {
        String row = leftPad("", lampsOn, onColor);
        return rightPad(row, rowLength, LAMP_OFF);
    }
}
