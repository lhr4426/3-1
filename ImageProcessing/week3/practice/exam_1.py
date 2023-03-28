import numpy as np

if __name__ == '__main__' :
    v1 = np.full((3, 3), 5) # 3*3 크기를 5로 채우기
    v2 = np.ones((3,3)) # 3*3 크기를 1로 채우기

    print(v1 + v2)
    print(v1 * v2) # 행렬곱 아니고 요소곱임
    print(np.sum(v1 * v2))