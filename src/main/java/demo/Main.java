package demo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextLine();
        String str = in.nextLine();
        int count = 0;
        int res = 0;
        int u = 0, v = 0;
        for(int i = 0; i < n; i++) {
            u = in.nextInt();
            v = in.nextInt();
            if((str.charAt(u - 1) == 'R' && str.charAt(v - 1) == 'B') || (str.charAt(u - 1) == 'B' && str.charAt(v - 1) == 'R')) {
                res++;
            }
        }
        System.out.println(res);
    }
}