# Histogram stretch 시키기

import numpy as np
import matplotlib.pyplot as plt
import cv2

def my_calcHist_gray(img) :
    h, w = img.shape[:2]
    hist = np.zeros((256, ), dtype=np.uint8)
    for row in range(h) :
        for col in range(w) :
            intensity = img[row, col]
            hist[intensity] += 1
    
    return hist

def my_hist_stretch(src, hist) :
    h, w = src.shape[:2]
    dst = np.zeros((h, w), dtype=np.uint8) # 결과 저장
    min = 256
    max = -1

    # pixel intensity의 min과 max를 구해보자
    for i in range(len(hist)) :
        if hist[i] != 0 and i < min :
            min = i
        if hist[i] != 0 and i > max :
            max = i

    # 히스토그램을 늘리기
    hist_stretch = np.zeros(hist.shape, dtype=np.uint8)
    for i in range(min, max+1) :
        j = int((255 - 0) / (max - min) * (i - min) + 0)
        hist_stretch[j] = hist[i]

    # 늘린대로 픽셀도 바꾸기
    for row in range(h) :
        for col in range(w) :
            dst[row, col] = (255 - 0) / (max - min) * (src[row,col] - min) + 0
        
    return dst, hist_stretch

if __name__ == '__main__' :
    src = cv2.imread('.\\imgs\\fruits.jpg', cv2.IMREAD_GRAYSCALE)
    src_div = cv2.divide(src, 3)

    hist = my_calcHist_gray(src)
    hist_div = my_calcHist_gray(src_div)

    binX = np.arange(len(hist))
    plt.bar(binX, hist, width=0.5, color='g')
    plt.title('original image histogram')
    plt.xlabel('pixel intensity')
    plt.ylabel('pixel num')
    plt.show()

    binX = np.arange(len(hist_div))
    plt.bar(binX, hist_div, width=0.5, color='g')
    plt.title('image/3 histogram')
    plt.xlabel('pixel intensity')
    plt.ylabel('pixel num')
    plt.show()

    dst, hist_stretch = my_hist_stretch(src_div, hist_div)
    binX = np.arange(len(hist_stretch))
    plt.bar(binX, hist_stretch, width=0.5, color='g')
    plt.title('hist stretch histogram histogram')
    plt.xlabel('pixel intensity')
    plt.ylabel('pixel num')
    plt.show()

    cv2.imshow('img/3', src_div)
    cv2.imshow('stretch img', dst)
    cv2.imshow('original img', src)
    cv2.waitKey()
    cv2.destroyAllWindows()
    
