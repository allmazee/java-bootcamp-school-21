import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int minGrades = 0;
        int i = 1;
        for (; i <= 18; i++) {
            int minGrade = 100;
            String weekStr = scanner.nextLine();
            if (weekStr.equals("42")) break;
            if (!weekStr.equals("Week " + i)) {
                scanner.close();
                handleError();
            }
            int grade = 0;
            for (int j = 0; j < 5; j++) {
                grade = scanner.nextInt();
                if (grade < 1 || grade > 9) {
                    scanner.close();
                    handleError();
                }
                if (minGrade > grade) minGrade = grade;
            }
            scanner.nextLine();
            minGrades = addMinGrade(minGrades, minGrade);
        }
        printGraph(minGrades, i);
        scanner.close();
    }

    static void handleError() {
        System.err.println("Illegal Argument");
        System.exit(-1);
    }

    static int addMinGrade(int grade, int nextGrade) {
        int temp = grade;
        while (temp > 0) {
            nextGrade *= 10;
            temp /= 10;
        }
        return grade + nextGrade;
    }

    static void printGraph(int minGrades, int weeks) {
        for (int i = 1; i < weeks; i++) {
            String weekStr = "Week " + i + " ";
            int grade = minGrades % 10;
            minGrades /= 10;
            System.out.print(weekStr);
            for (int j = 0; j < grade; j++) {
                System.out.print("=");
            }
            System.out.println(">");
        }
    }
}