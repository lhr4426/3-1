// 문제 : 입력받은 문장의 단어 개수 출력

import java.util.Scanner;

public class homework_4 {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String test = sc.nextLine();
        String[] split_test = test.split(" ");

        System.out.println("원문 : " + test + " | 단어 개수 : " + split_test.length);
    }

}
