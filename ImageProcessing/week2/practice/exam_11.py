# Histogram 실습 (선 그래프)

import matplotlib.pyplot as plt
import cv2

# 중요!!!! 이미지 calcHist에 넣을 때 꼭 배열형태로 넣어야됨!!!!!!!!!!!!!!!
src = cv2.imread('.\\imgs\\fruits.jpg', cv2.IMREAD_GRAYSCALE)
hist = cv2.calcHist([src], [0], None, [256], [0, 256])

# hist 함수를 쓰면 (256, 1)의 2차원 배열이 나옴. [[10], [20]] 등등..
# 0이 10번, 1이 20번이라는 뜻으로 대강 이해하면 될듯

plt.plot(hist, color='r')
plt.title('histogram plot')
plt.xlabel('pixel intensity')
plt.ylabel('pixel num')
plt.show()