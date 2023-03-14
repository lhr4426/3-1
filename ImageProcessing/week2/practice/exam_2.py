# 특정 픽셀에 접근해보자
# 헷갈리면 안됨! x,y 아니고 y,x임!

import cv2
import numpy as np

src1 = np.zeros((200, 200)) # 기본값은 float임
src2 = np.zeros((200, 200), dtype=np.uint8)

src1[:, 100:200] = 1.
src2[:, 100:200] = 255

cv2.imshow('float', src1)
cv2.imshow('uint8', src2)

cv2.waitKey()
cv2.destroyAllWindows()