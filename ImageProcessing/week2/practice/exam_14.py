# exam_13에서 만든 5x5_img 파일의 히스토그램을 표현해보자
# my_calcHist라는 함수를 직접 만들어서!

import numpy as np
import matplotlib.pyplot as plt

def my_calcHist(img) :
    h, w = img.shape[:2]
    hist = np.zeros((10), dtype=np.int64) # 0~9에 대한 픽셀값만 측정
    for row in range(h) :
        for col in range(w) :
            intensity = img[row, col]
            hist[intensity] += 1
    
    return hist

if __name__ == '__main__' :
    src = np.array([[3,1,3,5,4],
                    [9,8,3,5,6],
                    [2,2,3,8,7],
                    [5,4,6,5,4],
                    [1,0,0,2,6]])
    hist = my_calcHist(src)
    binX = np.arange(len(hist))
    plt.bar(binX, hist, width=0.8, color='g')
    plt.title('histogram')
    plt.xlabel('pixel intensity')
    plt.ylabel('pixel num')
    plt.show()