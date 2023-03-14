# 컬러 이미지 다뤄보기

import cv2
import numpy as np

src = np.zeros((300, 300, 3), dtype=np.uint8)

# B, G, R (원색)
src[0:100, 0:100, 0] = 255 # Blue
src[0:100, 100:200, 1] = 255 # Green
src[0:100, 200:300, 2] = 255 # Red

# B + R : Magenta
src[100:200, 0:100, 0] = 255
src[100:200, 0:100, 2] = 255

# B + G : Cyan
src[100:200, 100:200, 0] = 255
src[100:200, 100:200, 1] = 255

# G + R : Yellow
src[100:200, 200:300, 1] = 255
src[100:200, 200:300, 2] = 255

# B + G + R : White
src[200:300, 0:100, 0] = 255
src[200:300, 0:100, 1] = 255
src[200:300, 0:100, 2] = 255

# B/2 + G/2 + R/2 : Gray
src[200:300, 100:200, :] = 128

cv2.imshow('src', src)
cv2.waitKey()
cv2.destroyAllWindows()