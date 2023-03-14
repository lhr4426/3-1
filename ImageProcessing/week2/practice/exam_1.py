# float에서는 1, int에서는 255가 최대라는 것을 잊지 말기!

import cv2
import numpy as np

float_0 = np.zeros((200, 200), dtype=np.float32)
float_1 = np.ones((200, 200), dtype=np.float32)

uint_0 = np.zeros((200, 200), dtype=np.uint8)
uint_1 = np.ones((200, 200), dtype=np.uint8)
uint_255 = np.full((200, 200), 255, dtype=np.uint8)

cv2.imshow('float0', float_0)
cv2.imshow('float1', float_1)
cv2.imshow('uint0', uint_0)
cv2.imshow('uint1', uint_1)
cv2.imshow('uint255', uint_255)

cv2.waitKey()
cv2.destroyAllWindows()






