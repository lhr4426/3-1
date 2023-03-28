# 라플라시안 이미지 피라미드 (residual image의 모임)

import numpy as np
import cv2


def my_downsampling(src, gap) :
    (h, w) = src.shape

    blur_src = cv2.GaussianBlur(src, (5, 5), 1)
    res = src - blur_src

    dst = np.zeros((h//gap, w//gap), dtype=np.uint8)
    for row in range(h//gap) :
        for col in range(w//gap) :
            dst[row,col] = src[row*gap, col*gap]

    return res, dst

def my_upsampling(src, gap, residual) :
    (h, w) = src.shape

    dst = np.zeros((h*gap, w*gap), dtype=np.uint8)
    for row in range(h*gap) :
        for col in range(w*gap) :
            intensity = src[row//gap, col//gap] + residual[row,col]
            dst[row, col] = intensity

    return dst

if __name__ == '__main__' :
    src = cv2.imread("ImageProcessing/week4/practice/Lena.png", cv2.IMREAD_GRAYSCALE)
    src = src.astype(np.float32)

    down_dst, up_dst = [], []
    repeat, gap = 2, 2

    img = src.copy()
    for i in range(repeat) :
        res, img = my_downsampling(img, gap)
        down_dst.append(res)
    down_dst.append(img)

    up_dst.append(img)
    for i in range(repeat) :
        up_dst.append(my_upsampling(up_dst[i], gap, down_dst[repeat - i - 1]))
    
    for i in range(len(down_dst)) :
        down = down_dst[i]
        down = ((down - down.min())/ (down.max() - down.min()) * 255).astype(np.uint8)
        cv2.imshow("laplacian {} downsampling".format(i), down)

    for i in range(len(up_dst)) :
        up = up_dst[i]
        up = ((up - up.min()) / (up.max() - up.min()) * 255).astype(np.uint8)
        cv2.imshow("laplacian {} upsampling".format(i), up)
    
    cv2.waitKey()
    cv2.destroyAllWindows()