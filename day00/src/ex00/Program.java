public class Program {
    public static void main(String[] args) {
        int a = -479589;
        if (a < 0) a = -a;
        if (a < 100000 || a > 999999) System.exit(1);
        int res = 0;
        while (a > 0) {
           res += (a % 10);
           a /= 10;
        }
        System.out.println(res);
    }
}
