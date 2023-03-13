# 운영체제 2주차 실습
## 연습문제

|문제|답|
|---|---|
|echo로 3*(5+7) 출력하기|echo $((3*(5+7)))|
|ls 명령어의 파일 타입 확인|file $(which ls)|
|echo {b..d}_{d..b}의 결과|b_d,b_c,b_b,c_d,c_c,c_b,d_d,d_c,d_b|
|echo {b..d} {d..b}의 결과|b, c, d, d, c, b|
|현재/부모 디렉토리 제외하고 모든 파일 표시|echo .[!.]* *|
|""와 '' 차이|double quote는 $,\\,' 는 무시 못함. single quote는 무시함|
|/media/share에서 대문자로 시작하고 .c나 .h로 끝나면서 그 앞 문자열의 길이가 4 이상인 모든 파일을 ./prac 디렉토리로 이동하기 위한 명령어|cp /media/share/[[:upper:]]???*.[ch] ./prac|
|셸에서 실행파일을 찾기위한 환경변수|PATH|
|test01.txt에서부터 test16.txt까지 모두 출력|cat test{01..16}.txt|

