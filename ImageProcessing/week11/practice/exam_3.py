import numpy as np

item = [[1,2,3,4],
        [5,6,7,8],
        [9,10,11,12],
        [13,14,15,16]]
item = np.array(item)

def Spatial2Frequency1(block, n=4) :
    y, x = block.shape
    dst = np.zeros((y, x))
    v, u = dst.shape

    for v_ in range(v) :
        for u_ in range(u) :
            temp = 0
            for y_ in range(y) :
                for x_ in range(x) :
                    temp += block[y_, x_] * np.cos(((2*x_+1) * u_ * np.pi) / (2*n)) * np.cos(((2*y_+1) * v_ * np.pi) / (2*n))
                    # print(np.cos(((2*x_+1) * u_ * np.pi) / (2*n)))
            # print("S2F : ", temp)
            dst[v_, u_] = C(u_, n=n) * C(v_, n=n) * temp
            # if v_ == 1 and u_ == 1 :
            #     print("S2F : ",temp)


    return dst


def C(w, n=8) :
    if w == 0 :
        return (1/n) ** 0.5
    else :
        return (2/n) ** 0.5
    

    
def DCT(block, n=4):
    ######################################
    # TODO                               #
    # DCT 완성                            #
    # 4중 for문으로 구현 시 감점 예정          #
    ######################################
    v, u = block.shape
    y, x = np.mgrid[0:n, 0:n]
    dst = np.zeros(block.shape)
    for v_ in range(v):
        for u_ in range(u):
            cosx = np.cos(((2 * x + 1)* u_ * np.pi)/(2 * n))
            cosy = np.cos(((2 * y + 1)* v_ * np.pi)/(2 * n))
            # print(((2 * x)+ 1)* u_ * np.pi)
            # print("cosx : \n",cosx)
            # print("cosy : \n",cosy)
            # print(block)
            temp = block * cosx * cosy
            # print("temp : ", temp.shape)
            # print("DCT : ",temp)
            dst[v_, u_] = C(u_, n=n) * C(v_, n=n) * np.sum(temp)

            # if v_ == 1 and u_ == 1 :
            #     print("DCT : ",temp)
            
    return np.round(dst)

if __name__ == "__main__" :
    s2f = Spatial2Frequency1(item, n=4)
    print(s2f)
    dct = DCT(item, n=4)
    print(dct)