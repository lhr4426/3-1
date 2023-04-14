# LoG 구현하기

import numpy as np
import cv2
from my_filtering_true import my_filtering

def get_LoG_fliter(fsize, sigma=1) :
    y, x = np.mgrid[-(fsize // 2):(fsize // 2) + 1, -(fsize // 2):(fsize // 2) + 1]

    LoG = (((x ** 2 + y ** 2) / sigma ** 4) - (2 / sigma ** 2)) * np.exp(-((x ** 2 + y ** 2) / (2 * sigma ** 2)))

    LoG = LoG - (LoG.sum()/fsize**2)
    return LoG

def main() :
    src = cv2.imread('ImageProcessing/week6/practice/Lena.png', cv2.IMREAD_GRAYSCALE)
    src = src / 255
    LoG_filter = get_LoG_fliter(fsize=9, sigma=1)

    dst = my_filtering(src, LoG_filter)
    print(dst.max(), dst.min())

    dst = np.abs(dst)
    dst = dst - dst.min()
    dst = dst / dst.max()

    cv2.imshow('dst', dst)
    cv2.waitKey()
    cv2.destroyAllWindows()

if __name__ == '__main__' :
    main()