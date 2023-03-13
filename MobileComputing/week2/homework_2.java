// 문제 : int를 String으로 변환하는 다양한 방법들

public class homework_2 {

    public static void main(String args[]) {
        int test_int = 10;

        // 방법 1. Integer 래퍼 클래스에서 제공하는 함수 쓰기
        String result_1 = Integer.toString(test_int);
        System.out.println("값 : " + result_1 + " | 타입 : " + result_1.getClass().getName());

        // 방법 2. String에서 제공하는 함수 쓰기
        String result_2 = String.valueOf(test_int);
        System.out.println("값 : " + result_2 + " | 타입 : " + result_2.getClass().getName());

        // 방법 3. 먼저 int를 char array로 바꾸고, 그걸 다시 String으로 바꾸기
        char[] temp = ("" + test_int).toCharArray();
        String result_3 = new String(temp);
        System.out.println("값 : " + result_3 + " | 타입 : " + result_3.getClass().getName());


    }
}
