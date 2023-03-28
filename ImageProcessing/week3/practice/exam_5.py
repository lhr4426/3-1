# Shapening filter 사용해보기 (3*3)
# 중앙값을 키우고 거기서 Average filter를 뺌

import numpy as np
import cv2

def my_sharpening_filter_3x3(src) :
    mask = np.array([[-1/9, -1/9, -1/9],
                     [-1/9, 17/9, -1/9],
                     [-1/9, -1/9, -1/9]])
    print(mask)

    dst = cv2.filter2D(src, -1, mask)
    return dst

if __name__ == '__main__' :
    src = cv2.imread('ImageProcessing/week3/practice/Lena.png', cv2.IMREAD_GRAYSCALE)
    dst = my_sharpening_filter_3x3(src)

    cv2.imshow('original', src)
    cv2.imshow('sharpening filter', dst)
    cv2.waitKey()
    cv2.destroyAllWindows()

    