# Histogram 실습 (바 그래프)

import matplotlib.pyplot as plt
import cv2
import numpy as np

src = cv2.imread('.\\imgs\\fruits.jpg', cv2.IMREAD_GRAYSCALE)
hist = cv2.calcHist([src], [0], None, [256], [0, 256])
# hist.flatten()을 쓰면 calcHist의 결과를 2차원에서 1차원으로 바꿔줌
histFlatten = hist.flatten()
# 대신 그만큼 X의 길이를 정해줘야겠지용 그걸 np.arange로 구해줍시다
binX = np.arange(len(histFlatten))
plt.bar(binX, histFlatten, width=1, color='g')
plt.title('histogram bar')
plt.xlabel('pixel intensity')
plt.ylabel('pixel num')
plt.show()