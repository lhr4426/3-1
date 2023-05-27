import cv2
import numpy as np

def Spatial2Frequency1(block, n=8) :
    y, x = block.shape
    dst = np.zeros((y, x))
    v, u = dst.shape

    for v_ in range(v) :
        for u_ in range(u) :
            temp = 0
            for y_ in range(y) :
                for x_ in range(x) :
                    temp += block[y_, x_] * np.cos(((2*x_+1) * u_ * np.pi) / (2*n)) * np.cos(((2*y_+1) * v_ * np.pi) / (2*n))
            dst[v_, u_] = C(u_, n=n) * C(v_, n=n) * temp

    return dst

def C(w, n=8) :
    if w == 0 :
        return (1/n) ** 0.5
    else :
        return (2/n) ** 0.5
    
if __name__ == '__main__' :
    block_size = 4
    src = np.ones((block_size, block_size))

    print('Spatial2Frequency1')
    dst = Spatial2Frequency1(src, n=block_size)
    print(dst)