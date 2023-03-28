import cv2

src = cv2.imread("ImageProcessing/week4/practice/Lena.png", cv2.IMREAD_GRAYSCALE)
h, w = src.shape[:2]

half_src = cv2.resize(src, (h//2, w//2))
quarter_src = cv2.resize(src, (h//4, w//4))

cv2.imshow('original', src)
cv2.imshow('half', half_src)
cv2.imshow('quarter', quarter_src)
cv2.waitKey()
cv2.destroyAllWindows()