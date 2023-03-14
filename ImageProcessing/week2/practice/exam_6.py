# 컬러 이미지 만들어보기

import cv2
import numpy as np

# openCV에서는 RGB 순서가 아니라 BGR 순서임을 잊지말기!
src = np.zeros((300, 300, 3), dtype=np.uint8)
src[0, 0] = [1, 2, 3]
src[0, 1] = [4, 5, 6]
src[1, 0] = [7, 8, 9]

print(src.shape)
print(src[0,0,0], src[0,0,1], src[0,0,2])
print(src[0,0])
print(src[0])
print(src)

cv2.imshow('src', src)
cv2.waitKey()
cv2.destroyAllWindows()