# 이미지 컬러 변환해보기 (BGR -> RGB)

import cv2

src = cv2.imread('.\\imgs\\Lena.png')
rgb = cv2.cvtColor(src, cv2.COLOR_BGR2RGB)

cv2.imshow('src', src)
cv2.imshow('rgb', rgb)

print('[BGR] {}'.format(src[0,0]))
print('[RGB] {}'.format(rgb[0,0]))

cv2.waitKey()
cv2.destroyAllWindows()