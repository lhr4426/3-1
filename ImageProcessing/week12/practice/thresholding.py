import cv2
import numpy as np
import matplotlib.pyplot as plt


def apply_threshold(img, th=120):
    dst = np.zeros(img.shape, img.dtype)
    #################################################
    # TODO                                          #
    # Apply threshold                               #
    #################################################

    dst[img >= th] = 255
    dst[img < th] = 0

    return dst

if __name__ == '__main__':
    # img = cv2.imread('ImageProcessing/week12/practice/circles_adaptive_threshold.png', cv2.IMREAD_GRAYSCALE)
    img = cv2.imread('ImageProcessing/week12/practice/rice.png', cv2.IMREAD_GRAYSCALE)

    img_th = apply_threshold(img, th=120)

    cv2.imshow('original image', img)
    cv2.imshow('threshold applied image', img_th)

    plt.hist(img.ravel(), 256, [0, 256])
    plt.show()
    cv2.waitKey(0)
    cv2.destroyAllWindows()


