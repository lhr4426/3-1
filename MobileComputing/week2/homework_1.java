// 문제 : 문장 입력받아서 1번 이상 나온 알파벳과 횟수 출력

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class homework_1 {
    public static void main (String args[]){

        // 스캐너 객체 만들기
        Scanner sc = new Scanner(System.in);

        // 다음에 입력받는 문자열 받기
        String user_text = sc.nextLine();

        // 문자열을 String 이 아니라 char 배열로 변경 (하나하나 인덱스으로 접근하려고)
        char [] char_text = user_text.toCharArray();

        System.out.println("원본 텍스트 : " + user_text);

        // 한 글자와 그 글자의 숫자를 키-값 형태로 저장하려고
        HashMap<Character, Integer> char_map = new HashMap<>();

        // char 배열에서 꺼내서 하나하나 반복
        for (char one_char : char_text) {

            // 만약에 해시맵에 방금 꺼낸 한 글자가 없으면 새로 만들어줘야됨 (첫 등장이니까)
            if(char_map.containsKey(one_char) == false) {
                char_map.put(one_char, 1);
            }
            // 있으면? 숫자 하나 늘려서 해시맵 업데이트 해줘야 됨
            else {
                Integer temp = char_map.get(one_char);
                temp++;
                char_map.put(one_char, temp);
            }
        }

        // 해시맵에서 키만 뽑아다가 Set으로 만듬
        Set<Character> keys = char_map.keySet();
        // 그걸 반복문 돌릴 수 있게끔 Iterator로 설정
        Iterator<Character> iter = keys.iterator();

        // Iterator로 키 쭉 돌기
        while(iter.hasNext()) {
            Character key = iter.next();
            System.out.println(key + " : " + char_map.get(key));
        }

    }
}
