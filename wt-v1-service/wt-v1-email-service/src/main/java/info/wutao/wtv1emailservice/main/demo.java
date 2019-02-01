package info.wutao.wtv1emailservice.main;

import java.util.HashMap;
import java.util.Map;

public class demo {
    public static void main(String[] args) {

        print(4);
    }

    public static void print(int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.print(" ");
            }
            for (int z = N - i; z > 0; z--) {
                System.out.print("* ");
            }
            System.out.println();

        }
    }


}
