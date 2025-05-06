package ex05;

import java.util.Scanner;

public class Program {
    public static final String[] DAYS = {" MO", " TU", " WE", " TH", " FR", " SA", " SU"};
    public static final int DAYS_WEEK = 7;
    public static final int DAYS_MONTH = 30;
    public static final int MAX_CLASSES_WEEK = 10;
    public static final int FIRST_DAY_INDEX = 1;
    public static final int START_HOUR = 1;
    public static final int FINISH_HOUR = 6;
    public static final int MAX_NAME_LENGTH = 10;
    public static final int MAX_NUMBER_STUDENTS = 10;

    public static String[] addNewString(String[] arrayOfStrings, String newString) {
        String[] newArrayOfStrings = new String[arrayOfStrings.length + 1];
        for (int i = 0; i < arrayOfStrings.length; i++) {
            newArrayOfStrings[i] = arrayOfStrings[i];
        }
        newArrayOfStrings[arrayOfStrings.length] = newString;
        return newArrayOfStrings;
    }

    public static int getDayIndex(String day) {
        for (int i = 0; i < DAYS.length; i++) {
            if (day.equals(DAYS[i])) {
                return i;
            }
        }
        return -1;
    }

    public static int getNameIndex(String name, String[] arrayOfStudents) {
        for (int i = 0; i < arrayOfStudents.length; i++) {
            if (arrayOfStudents[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static void printTable(int[][][] timetable, String[] names) {
        int index = 0;

        System.out.print("          ");

        for (int i = 0; i < DAYS_MONTH; i++) {
            for (int j = 0; j < FINISH_HOUR - START_HOUR; j++) {
                if (timetable[i][j][0] != 0) {
                    index = i % DAYS_WEEK + FIRST_DAY_INDEX == 7 ? 0 : i % DAYS_WEEK + FIRST_DAY_INDEX;
                    System.out.printf("%1d:00%3s%3d|", (j + 1), DAYS[index], (i + 1));
                }
            }
        }

        System.out.println(" ");

        for (int i = 0; i < names.length; i++) {
            System.out.printf("%10s", names[i]);

            for (int j = 0; j < DAYS_MONTH; j++) {
                for (int k = 0; k < FINISH_HOUR - START_HOUR; k++) {
                    if (timetable[j][k][i] < -1) {
                        System.out.printf("%10d|", -1);
                    } else if (timetable[j][k][i] > 1) {
                        System.out.printf("%10d|", 1);
                    } else if (timetable[j][k][i] == 1) {
                        System.out.printf("%10s|", "");
                    }
                }
            }
            System.out.println(" ");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] arrayOfStudents = new String[0];

        while (!line.equals(".")) {
            if (line.length() <= MAX_NAME_LENGTH && arrayOfStudents.length < MAX_NUMBER_STUDENTS) {
                arrayOfStudents = addNewString(arrayOfStudents, line);
            } else {
                System.err.println("IllegalArgument");
                scanner.close();
                System.exit(-1);
            }

            line = scanner.nextLine();
        }

        int[][][] timetable = new int[DAYS_MONTH][FINISH_HOUR - START_HOUR][arrayOfStudents.length];
        int hour, day, index;
        String name;
        int countClasses = 0;

        while (scanner.hasNextInt()) {
            hour = scanner.nextInt();
            day = getDayIndex(scanner.nextLine());

            if (day < 0 || hour < START_HOUR || hour > FINISH_HOUR - 1 || countClasses > MAX_CLASSES_WEEK) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }

            for (int i = 0; i < arrayOfStudents.length; i++) {
                for (int j = day; j <= DAYS_MONTH; j += 7) {
                    if (j > FIRST_DAY_INDEX) {
                        timetable[j - FIRST_DAY_INDEX][hour - START_HOUR][i] = 1;
                    }
                }
            }

            countClasses++;
        }

        name = scanner.next();
        name = scanner.next();

        while (!name.equals(".")) {
            index = getNameIndex(name, arrayOfStudents);

            if (index < 0) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }

            hour = scanner.nextInt();
            day = scanner.nextInt();
            line = scanner.next();

            if (timetable[day - 1][hour - START_HOUR][index] == 1) {
                if (line.equals("HERE")) {
                    timetable[day - 1][hour - START_HOUR][index] = 10;
                } else if (line.equals("NOT_HERE")) {
                    timetable[day - 1][hour - START_HOUR][index] = -10;
                } else {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
            } else {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }

            name = scanner.next();
        }

        scanner.close();

        printTable(timetable, arrayOfStudents);
    }
}

/*
John
Mike
 .
2 MO
4 WE
.
Mike 2 28 NOT_HERE
John 4 9 HERE
Mike 4 9 HERE
.
*/
