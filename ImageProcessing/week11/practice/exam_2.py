import cv2
import numpy as np

def img2block(src, n=8):
    ######################################
    # TODO                               #
    # img2block 완성                      #
    # img를 block으로 변환하기              #
    ######################################
    (h, w) = src.shape

    if h % n != 0:
        h_pad = n - h%n
    else :
        h_pad = 0
    if w % n != 0:
        w_pad = n- w%n
    else :
        w_pad = 0

    h_dst = int(h + h_pad)
    w_dst = int(w + w_pad)

    print(h_dst, w_dst)

    dst = np.zeros((h_dst, w_dst))
    dst[:h, :w] = src
    blocks = []
    for row in range(h_dst // n) :
        for col in range(w_dst // n) :
            block = dst[row*n : (row+1)*n, col*n:(col+1)*n]
            blocks.append(block)

    return np.array(blocks)


def block2img(blocks, src_shape, n = 8):
    ###################################################
    # TODO                                            #
    # block2img 완성                                   #
    # 복구한 block들을 image로 만들기                     #
    ###################################################

    (h, w) = src_shape
    dst = np.zeros(src_shape)

    repeat_row = h // n 
    repeat_col = w // n 
    idx = 0

    
    for row in range(repeat_row) :
        for col in range(repeat_col) :
            dst[row*n:(row+1)*n, col*n:(col+1)*n] = blocks[idx]
            idx +=1
    

    return dst


def DCT(block, n=8):
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
            tmp = np.sum(block * np.cos(((2 * x + 1)* u_ * np.pi)/2 * n) * np.cos(((2 * y + 1)* v_ * np.pi)/2 * n))
            dst[v_, u_] = C(u_) * C(v_) * np.sum(tmp)
    return np.round(dst)

def DCT_inv(block, n = 8):
    ###################################################
    # TODO                                            #
    # DCT_inv 완성                                     #
    # DCT_inv 는 DCT와 다름.                            #
    ###################################################
    y, x = block.shape
    dst = np.zeros(block.shape)
    v, u = np.mgrid[0:n, 0:n]
    for y_ in range(y) :
        for x_ in range(x) :
            cosu = np.cos(((2*x_+1)*u*np.pi)/16)
            cosv = np.cos(((2*y_+1)*v*np.pi)/16)
            dst[y_, x_] = np.sum(block * block_C(u) * block_C(v) * cosu * cosv)

    return np.round(dst)


def C(w, n=8) :
    if w == 0 :
        return (1/n) ** 0.5
    else :
        return (2/n) ** 0.5
    
def block_C(ws, n=8) :
    return_list = np.round(ws/(ws + 1E-6))
    return_list = (1 - return_list) * (1 / n) ** 0.5 + (return_list) * (2 / n) ** 0.5

    return return_list
    


if __name__ == "__main__" :
    src = cv2.imread("ImageProcessing/week11/practice/caribou.tif", cv2.IMREAD_GRAYSCALE)
    src_minus_128 = src - 128
    blocks = img2block(src_minus_128, n=8)
    print(blocks)
    # return_blocks = block2img(blocks, src_shape=src.shape, n=8)
    # print(return_blocks)
    DCT_result = []

    for block in blocks :
        DCT_result.append(DCT(block, n=8))

    print(src)
    # print(blocks[0])
    print(DCT_result)

    DCT_inverse = []

    for block in blocks :
        DCT_inverse.append(DCT_inv(block, n=8))

    print(DCT_inverse)
     
