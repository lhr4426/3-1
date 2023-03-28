import cv2
import numpy as np

"""
해당 부분에 여러분 정보 입력해주세요.
한밭대학교 컴퓨터공학과  20211924 이혜림
"""

def my_bilinear(src, scale):
    #########################
    # TODO                  #
    # my_bilinear 완성      #
    #########################
    (h, w) = src.shape
    h_dst = int(h * scale + 0.5)
    w_dst = int(w * scale + 0.5)

    dst = np.zeros((h_dst, w_dst))


    # bilinear interpolation 적용
    for row in range(h_dst):
        for col in range(w_dst):
            y = row / scale
            x = col / scale

            m = int(np.floor(y))
            n = int(np.floor(x))

            t = y - m
            s = x - n


            """
            픽셀 위치가 이미지를 넘어서는 경우를 막기위해서 조건문을 사용
            각 조건문을 생각하여 코드를 완성하기
            Hint: 4가지에 대한 경우를 생각해야함
            1. m+1, n+1 모두 이미지를 넘어서는 경우
            2. m+1이 이미지를 넘어서는 경우 
            3. n+1이 이미지를 넘어서는 경우
            4. 그외
            """

            if m+1 >= h and n+1 >= w :
                value = src[h-1, w-1]
            elif m+1 >= h and n+1 < w :
                value = ((1-s) * src[h-1, n]) + (s * src[h-1, n+1])
            elif m+1 < h and n+1 >= w :
                value = ((1-t) * src[m, w-1]) + (t * src[m+1, w-1])
            else :
                value = ((1-t)* (1-s) * src[m, n]) + ((1-t) * s * src[m, n+1]) + (t * (1-s) * src[m+1, n]) + (t * s * src[m+1, n+1])
            

            value = int(value)
            dst[row, col] = value

    return dst

if __name__ == '__main__':
    src = cv2.imread('ImageProcessing/week4/homework/Lena.png', cv2.IMREAD_GRAYSCALE)

    # scale = 3추
    # #이미지 크기 171x171로 변경
    # my_dst_mini = my_bilinear(src, 1/scale)
    # my_dst_mini = my_dst_mini.astype(np.uint8)

    scale = 5
    #이미지 크기 102x102로 변경
    my_dst_mini = my_bilinear(src, 1/scale)
    my_dst_mini = my_dst_mini.astype(np.uint8)

    #이미지 크기 512x512로 변경(Lena.png 이미지의 shape는 (512, 512))
    my_dst = my_bilinear(my_dst_mini, scale)
    my_dst = my_dst.astype(np.uint8)

    # 출력 윈도우에 학번과 이름을 써주시기 바립니다.
    # cv2.imshow('[20211924 이혜림] original', src)
    # 위와 같이 출력 시도할 시 한글로 인해 표기가 안되는 문제 발생
    # 따라서 [학번 영문이름] 으로 출력하였습니다.
    cv2.imshow('[20211924 LeeHyeRim] original', src)
    cv2.imshow('[20211924 LeeHyeRim] my bilinear mini', my_dst_mini)
    cv2.imshow('[20211924 LeeHyeRim] my bilinear', my_dst)

    cv2.waitKey()
    cv2.destroyAllWindows()