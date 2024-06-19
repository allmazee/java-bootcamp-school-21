import java.util.Scanner;

public class Program {
    private static final int MAX_STUDENTS = 10;
    private static final int MAX_NAME_LENGTH = 10;
    private static final int MAX_LESSONS = 40;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] students = new String[MAX_STUDENTS];
        int[] lessonsTime = new int[MAX_LESSONS];
        int[] daysOfWeek = new int[MAX_LESSONS];
        int[][] calendar = new int[MAX_LESSONS][3];
        int[][] attendance = new int[MAX_LESSONS][4];
        int studentsCount = 0;
        int lessonsCount = 0;
        int attendanceCount = 0;
        int calendarCount = 0;
        studentsCount = loadStudents(scanner, students, studentsCount);
        lessonsCount = loadLessons(scanner, lessonsTime, daysOfWeek, lessonsCount);
        attendanceCount = loadAttendance(scanner, students, lessonsTime, attendance, attendanceCount);
        calendarCount = initializeCalendar(lessonsCount, daysOfWeek, calendar, calendarCount, lessonsTime);
        printCalendar(calendarCount, calendar, studentsCount, students, attendanceCount, attendance);
    }

    private static int loadStudents(Scanner scanner, String[] students, int studentsCount) {
        for (int i = 0; i < MAX_STUDENTS; i++) {
            String student = scanner.nextLine();
            if (student.length() > MAX_NAME_LENGTH) handleError();
            if (student.equals(".")) break;
            students[i] = student;
            studentsCount++;
        }
        return studentsCount;
    }

    private static int loadLessons(Scanner scanner, int[] lessonsTime, int[] daysOfWeek, int lessonsCount) {
        for (int i = 0; i < MAX_LESSONS; i++) {
            String inputLine = scanner.nextLine();
            if (inputLine.equals(".")) break;
            char[] inputChars = inputLine.toCharArray();
            lessonsTime[i] = inputChars[0] - '0';
            daysOfWeek[i] = getNumberOfDay(inputChars[2], inputChars[3]);
            lessonsCount++;
        }
        return lessonsCount;
    }

    private static int loadAttendance(Scanner scanner, String[] students, int[] lessonsTime, int[][] attendance, int attendanceCount) {
        for (int i = 0; i < MAX_LESSONS; i++) {
            if (scanner.hasNext(".")) break;
            String student = scanner.next();
            if (!isValidStudent(students, student)) handleError();
            int time = scanner.nextInt();
            if (!isValidTime(lessonsTime, time)) handleError();
            int date = scanner.nextInt();
            if (!isValidDate(date)) handleError();
            String attended = scanner.nextLine();
            if (isValidAttendance(attended)) handleError();
            attendance[i][0] = getStudentId(students, student);
            attendance[i][1] = time;
            attendance[i][2] = date;
            attendance[i][3] = getAttendance(attended);
            attendanceCount++;
        }
        return attendanceCount;
    }

    private static int initializeCalendar(int lessonsCount, int[] daysOfWeek, int[][] calendar, int calendarCount, int[] lessonsTime) {
        for (int date = 1; date <= 30; date++) {
            int day = date % 7 + 1;
            for (int i = 0; i < lessonsCount; i++) {
                if (day == daysOfWeek[i]) {
                    calendar[calendarCount][0] = lessonsTime[i];
                    calendar[calendarCount][1] = day;
                    calendar[calendarCount][2] = date;
                    calendarCount++;
                }
            }
        }
        return calendarCount;
    }

    private static void printCalendar(int calendarCount, int[][] calendar, int studentsCount, String[] students, int attendanceCount, int[][] attendance) {
        System.out.format("%10s", "");
        for (int i = 0; i < calendarCount; i++) {
            String space = " ";
            if (calendar[i][2] < 10) space = "  ";
            System.out.print(calendar[i][0] + ":00 " + getDayOfWeek(calendar[i][1]) + space + calendar[i][2] +"|");
        }
        System.out.println();
        for (int i = 0; i < studentsCount; i++) {
            int width = MAX_NAME_LENGTH - students[i].length();
            System.out.format("%" + width + "s", "");
            System.out.print(students[i]);
            for (int j = 0; j < calendarCount; j++) {
                for (int k = 0; k < attendanceCount; k++) {
                    if (attendance[k][0] == i && attendance[k][1] == calendar[j][0] && attendance[k][2] == calendar[j][2]) {
                        if (attendance[k][3] == 1) {
                            System.out.format("%9s", "");
                            System.out.print("1");
                        } else {
                            System.out.format("%8s", "");
                            System.out.print("-1");
                        }
                        break;
                    } else if (k >= attendanceCount - 1) {
                        System.out.format("%10s", "");
                    }
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }

    private static int getNumberOfDay(char c1, char c2) {
        String day = c1 + "" + c2;
        switch (day) {
            case "MO":
                return 1;
            case "TU":
                return 2;
            case "WE":
                return 3;
            case "TH":
                return 4;
            case "FR":
                return 5;
            case "SA":
                return 6;
            case "SU":
                return 7;
            default:
                return 0;
        }
    }

    private static String getDayOfWeek(int day) {
        switch (day) {
            case 1:
                return "MO";
            case 2:
                return "TU";
            case 3:
                return "WE";
            case 4:
                return "TH";
            case 5:
                return "FR";
            case 6:
                return "SA";
            case 7:
                return "SU";
            default:
                return "";
        }
    }

    private static void handleError() {
        System.err.println("Illegal Argument");
        System.exit(-1);
    }

    private static boolean isValidStudent(String[] students, String student) {
        for (String name : students) {
            if (student.equals(name)) return true;
        }
        return false;
    }

    private static boolean isValidTime(int[] lessonsTime, int time) {
        for (int lessonTime : lessonsTime) {
            if (lessonTime == time) return true;
        }
        return false;
    }

    private static boolean isValidDate(int date) {
        return date >= 1 && date <= 30;
    }

    private static boolean isValidAttendance(String attendance) {
        return attendance.equals("HERE") || attendance.equals("NOT_HERE");
    }

    private static int getStudentId(String[] students, String student) {
        for (int i = 0; i < students.length; i++) {
            if (students[i].equals(student)) return i;
        }
        return -1;
    }

    private static int getAttendance(String attendance) {
        if (attendance.equals(" HERE")) return 1;
        else if (attendance.equals(" NOT_HERE")) return -1;
        return 0;
    }
}
