# Pixel wise operation 실습하기
# 1. 더하기(add) / 빼기(substract)

import cv2

src = cv2.imread('.\\imgs\\fruits.jpg', cv2.IMREAD_GRAYSCALE)

# 밑의 코드는 되게 이상하게 나옴. 왜? -> 그 이유는 exam_10에
add_src = src + 128
sub_src = src - 128

cvadd_src = cv2.add(src, 128)
cvsub_src = cv2.subtract(src, 128)

print("add")
print("original : {}".format(src[128,128]))
print("src + 128 : {}".format(add_src[128,128]))
print("cv2.add : {}".format(cvadd_src[128,128]))

print("sub")
print("original : {}".format(src[200,128]))
print("src - 128 : {}".format(sub_src[200,128]))
print("cv2.subtract : {}".format(cvsub_src[200,128]))

cv2.imshow('fruit', src)
cv2.imshow('add 128', add_src)
cv2.imshow('sub 128', sub_src)
cv2.imshow('cv-add 128', cvadd_src)
cv2.imshow('cv-sub 128', cvsub_src)


cv2.waitKey()
cv2.destroyAllWindows()