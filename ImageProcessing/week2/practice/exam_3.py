# 특정 픽셀 접근하기 2
# 슬라이싱 됨!

import cv2
import numpy as np

src1 = np.zeros((200, 200)) # 기본값은 float임
src2 = np.zeros((200, 200), dtype=np.uint8)

src1[:, 100:200] = 1.
src2[:, 100:200] = 255

print(src1[50,50], src1[150, 150])
print(src2[50, 50], src2[150, 150])

print(src1[100, 95:105])
print(src2[100, 95:105])

cv2.imshow('float', src1)
cv2.imshow('uint8', src2)

cv2.waitKey()
cv2.destroyAllWindows()