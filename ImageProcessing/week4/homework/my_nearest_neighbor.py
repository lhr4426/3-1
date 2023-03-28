# nearest neighbor interpolation 실습

import cv2
import numpy as np

def my_nearest_neighbor(src, scale) :
    (h, w) = src.shape
    h_dst = int(h * scale + 0.5)
    w_dst = int(w * scale + 0.5)

    dst = np.zeros((h_dst, w_dst), np.uint8)
    for row in range(h_dst) :
        for col in range(w_dst) :
            # 원본 이미지보다 밖에 있을 경우를 위함
            r = min(int(row / scale + 0.5), h - 1)
            c = min(int(col / scale + 0.5), h - 1)
            dst[row, col] = src[r, c]

    return dst 

if __name__ == '__main__' :
    src = cv2.imread('ImageProcessing/week4/practice/Lena.png', cv2.IMREAD_GRAYSCALE)

    # scale = 3
    # my_dst_mini = my_nearest_neighbor(src, 1/scale).astype(np.uint8)
    # my_dst = my_nearest_neighbor(my_dst_mini, scale).astype(np.uint8)

    scale = 5
    my_dst_mini = my_nearest_neighbor(src, 1/scale).astype(np.uint8)
    my_dst = my_nearest_neighbor(my_dst_mini, scale).astype(np.uint8)

    cv2.imshow('[20211924] LeeHyeRim] original', src)
    cv2.imshow('[20211924] LeeHyeRim] my nearest mini', my_dst_mini)
    cv2.imshow('[20211924] LeeHyeRim] my nearest', my_dst)

    cv2.waitKey()
    cv2.destroyAllWindows()