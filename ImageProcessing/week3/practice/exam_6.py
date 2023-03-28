# Zero padding 하기

import cv2
import numpy as np

def my_zero_padding(src, pad_shape) :
    (h,w) = src.shape
    (p_h, p_w) = pad_shape
    pad_img = np.zeros((h+2*p_h, w+2*p_h)) # 2를 곱하는 이유 : 위에도 5 아래도 5, 왼쪽도 5 오른쪽도 5만큼 늘려야되니까
    pad_img[p_h:p_h+h, p_w:p_w+w] = src
    return pad_img

if __name__ == '__main__' :
    src = cv2.imread('ImageProcessing/week3/practice/Lena.png', cv2.IMREAD_GRAYSCALE)

    pad_src = my_zero_padding(src, (5,5))
    
    cv2.imshow('original', src)
    cv2.imshow('padding', pad_src.astype(np.uint8)) # 
    cv2.waitKey()
    cv2.destroyAllWindows()