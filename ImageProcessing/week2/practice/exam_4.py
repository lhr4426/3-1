# 특정 픽셀 접근하기 3
# 이번에는 직접 파일을 불러와보기!

import cv2

# 경로 쓸때 \\ 써야됨 (윈도우 기준. 맥은 걍 되는듯)
img = cv2.imread('.\\imgs\\baby.jpg', flags=cv2.IMREAD_GRAYSCALE)

cv2.imshow('img', img)
cv2.imshow('img2', img[:100, :300])

cv2.waitKey()
cv2.destroyAllWindows()
