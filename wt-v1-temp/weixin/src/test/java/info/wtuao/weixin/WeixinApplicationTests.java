package info.wtuao.weixin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeixinApplicationTests {

    @Test
    public void contextLoads() {
        Integer i = new Integer(12);
        Integer j = new Integer(12);
        System.out.println(i == j);
    }


    public static void main(String[] args) {
        //都是包装类，都是new时，false
/*        Integer i = new Integer(12);
        Integer j = new Integer(12);
        System.out.println(i == j);*/

        //都是包装类，没有new时，在-128~127之间会true
/*        Integer i = 12;
        Integer j = 12;

        System.out.println(i == j);*/

        //都是包装类，没有new时，在-128~127之外会false
/*        Integer i = 1111;
        Integer j = 1111;
        System.out.println(i == j);*/

        //一边包装类，一边整型，会自动拆箱
        Integer i = new Integer(112);
        int j = 112;
        System.out.println(i == j);

        "".trim();
    }

    @Test
    public void test1 () {
        /*sort(2,1,4);
        System.err.println(factorial(3));
        String str = "  nihao  ";
        System.err.println(str.length());
        str = myTrim(str);
        System.err.println(str.length());*/

    }

    public void sort (int a, int b, int c) {
        int temp = 0;

        if (a > b) {
            temp = b;
            b = a;
            a = temp;
        }

        if (a > c) {
            temp = c;
            c = a;
            a = temp;
        }

        if (b > c) {
            temp = c;
            c = b;
            b = temp;
        }

        System.out.println(a+","+b+","+c);
    }

    /**
     * 求阶乘
     * @param n
     * @return
     */
    public int factorial (int n) {
        if (n == 1) {
            return 1;
        }

        return n + factorial(n - 1);
    }


    public String myTrim(String str) {
        int start = 0;
        int end = str.length()-1;

        char[] c = str.toCharArray();

        while (true) {
            if (c[start] == ' ') {
                start++;
            }else {
                break;
            }
        }

        while (true) {
            if (c[end] == ' ') {
                end--;
            } else {
                break;
            }
        }

        return str.substring(start, end+1);
    }

    public Integer[] uniq (int[] array) {
        List<Integer> list = new ArrayList<>();
        //需要判断数组是不是大于2位，这个就不写了
        list.add(array[0]);
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[i]  == array[j]) {
                    break;
                }
            }

            list.add(array[i]);
        }

        return (Integer[]) list.toArray();
    }
}

