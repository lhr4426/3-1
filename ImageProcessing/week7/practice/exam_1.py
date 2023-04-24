import numpy as np
import cv2

def transformation_forward(src, M):
    h, w, c = src.shape
    dst = np.zeros((h, w, 3))

    h_, w_ = dst.shape[:2]
    count = dst.copy()
    
    print("forward calc")
    for row in range(h) :
        for col in range(w) :
            xy_prime = np.dot(M, np.array([[col, row, 1]]).T)
            x_ = xy_prime[0, 0]
            y_ = xy_prime[1, 0]

            if x_ < 0 or y_ < 0 or x_ >= w_ or y_ >= h_ :
                continue

            dst[int(y_), int(x_), :] += src[row, col, :]
            count[int(y_), int(x_), :] += 1

    dst = (dst / count)
    return dst.astype(np.uint8)

def main() :
    src = cv2.imread("ImageProcessing/week7/practice/Lena.png")
    
    theta = -10
    translation = [[1, 0, 100],
                   [0, 1, 100],
                   [0, 0, 1]]
    
    rotation = [[np.cos(theta), -np.sin(theta), 0],
                [np.sin(theta), np.cos(theta), 0],
                [0, 0, 1]]

    scaling = [[2, 0, 0],
               [0, 2, 0],
               [0, 0, 1]]

    M = translation

    forward = transformation_forward(src, M)

    cv2.imshow("input", src)
    cv2.imshow("forward", forward)
    cv2.waitKey()
    cv2.destroyAllWindows()

if __name__ == '__main__' :
    main()