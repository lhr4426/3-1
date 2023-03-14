# 특정 픽셀 접근하기 4

import cv2

img = cv2.imread('.\\imgs\\baby.jpg', flags=cv2.IMREAD_GRAYSCALE)

cv2.imshow('img', img)
cv2.imshow('img2', img[100:200, 150:450])

cv2.waitKey()
cv2.destroyAllWindows()