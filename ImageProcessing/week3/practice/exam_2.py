# 넘파이에 있는 sum 쓰면 대박 빠름

import numpy as np
import time

if __name__ == '__main__' :
    v1 = np.full((1000, 1000), 3)
    v2 = np.ones((1000, 1000))

    numpy_start = time.time()
    print(np,sum(v1*v2))
    print(time.time() - numpy_start) # 진행된 시간

    sum = 0
    for_start = time.time()
    for i in range(1000) :
        for j in range(1000) :
            sum += v1[i,j] * v2[i,j]

    print(sum)
    print(time.time() - for_start)