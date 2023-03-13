// 문제 : double을 String으로 변환

import java.util.Scanner;

public class homework_3 {
    public static void main(String args[]) {
        // 스캐너 객체 만들기
        Scanner sc = new Scanner(System.in);
        // 문자열 받기
        String test = sc.nextLine();
        // Double 래퍼 클래스에서 제공하는 함수 쓰기
        Double result = Double.parseDouble(test);

        System.out.println("값 : " + result + " | 타입 : " + result.getClass().getName());
    }

}
