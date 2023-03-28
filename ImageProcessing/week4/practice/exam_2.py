# 직접 크기변경 만들어보기

import numpy as np
import cv2


def my_downsampling(src, gap) :
    (h, w) = src.shape

    src = cv2.GaussianBlur(src, (5, 5), 1)

    dst = np.zeros((h//gap, w//gap), dtype=np.uint8)
    for row in range(h//gap) :
        for col in range(w//gap) :
            dst[row,col] = src[row*gap, col*gap]

    return dst

def my_upsampling(src, gap) :
    (h, w) = src.shape

    dst = np.zeros((h*gap, w*gap), dtype=np.uint8)
    for row in range(h*gap) :
        for col in range(w*gap) :
            dst[row, col] = src[row//gap, col//gap]

    return dst

if __name__ == '__main__' :
    src = cv2.imread("ImageProcessing/week4/practice/Lena.png", cv2.IMREAD_GRAYSCALE)

    half_src = my_downsampling(src, 2)
    quarter_src = my_downsampling(src, 4)
    octa_src = my_downsampling(src, 8)

    cv2.imshow('original', src)
    cv2.imshow('half', half_src)
    cv2.imshow('quarter', quarter_src)
    cv2.imshow('octa', octa_src)
    cv2.waitKey()
    cv2.destroyAllWindows()