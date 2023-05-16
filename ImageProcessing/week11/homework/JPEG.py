import numpy as np
import cv2
import time

def Quantization_Luminance(scale_factor):
    luminance = np.array(
        [[16, 11, 10, 16, 24, 40, 51, 61],
         [12, 12, 14, 19, 26, 58, 60, 55],
         [14, 13, 16, 24, 40, 57, 69, 56],
         [14, 17, 22, 29, 51, 87, 80, 62],
         [18, 22, 37, 56, 68, 109, 103, 77],
         [24, 35, 55, 64, 81, 104, 113, 92],
         [49, 64, 78, 87, 103, 121, 120, 101],
         [72, 92, 95, 98, 112, 100, 103, 99]])
    return luminance * scale_factor

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

    h_dst = h + h_pad
    w_dst = w + w_pad

    dst = np.zeros((h_dst, w_dst))
    dst[:h, :w] = src
    blocks = []
    for row in range(h_dst // n) :
        for col in range(w_dst // n) :
            block = dst[row*n : (row+1)*n, col*n:(col+1)*n]
            blocks.append(block)


    return np.array(blocks)

def C(w, n=8) :
    if w == 0 :
        return (1/n) ** 0.5
    else :
        return (2/n) ** 0.5

def block_C(ws, n=8) :
    # 2차원 배열을 받아서
    # 각 원소에 대해 C와 같은 연산을 수행할 것
    # 그러기 위해서 C를 조건식이 아니라 연속된 식처럼 변경함
    return_list = np.round(ws/(ws + 1E-6))
    # 이러면 0인 경우에는 0으로 되고, 0이 아니면 1로 변경됨
    return_list = (1 - return_list) * (1 / n) ** 0.5 + (return_list) * (2 / n) ** 0.5
    # 앞 부분은 0이면 사용되는 식, 뒷 부분은 0이 아닐 때 사용되는 식이다.

    return return_list
    


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
            dst[v_, u_] = C(u_) * C(v_) * np.sum(block * np.cos(((2 * x + 1)* u_ * np.pi)/(2 * n)) * np.cos(((2 * y + 1)* v_ * np.pi)/(2 * n)))
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





def my_zigzag_scanning(block, mode='encoding', block_size=8):
    ######################################
    # TODO                               #
    # my_zigzag_scanning 완성             #
    ######################################  

    row = 0
    col = 0
    turnning_point = 0
    idx = 0
    up = True
    half = False

    encoding_list = []
    decoding_list = np.zeros((block_size, block_size))

    while True :
        if mode == 'encoding':
            encoding_list.append(block[row, col])
        elif mode == 'decoding':
            decoding_list[row, col] = block[idx]
            if block[idx] == "EOB" or idx == len(block) - 1:
                return decoding_list
            
        idx += 1        

        if not half :
            if row == 0 and col == turnning_point and up:
                col += 1
                turnning_point += 1
                up = False
                continue
            elif col == 0 and row == turnning_point and not up :
                if turnning_point == block_size-1:
                    half = True
                    turnning_point = 1
                    col += 1
                    up = True
                    continue
                else : 
                    row += 1
                    turnning_point += 1
                    up = True
                    continue
                
        else :
            if col == block_size-1 and row == turnning_point and up :
                if turnning_point == block_size-1:
                    return encoding_list
                else :
                    row += 1
                    turnning_point += 1
                    up = False
                continue
            elif row == block_size-1 and col == turnning_point and not up :
                col += 1
                turnning_point += 1
                up = True
                continue
            elif row == block_size-1 and col == block_size-1 :
                break
        
        if up :
            row -= 1
            col += 1
        else :
            row += 1
            col -= 1       
         
    if mode == 'encoding' :
        for i in range(len(encoding_list), -1, -1) :
            if encoding_list[i] == 0 :
                encoding_list.pop(i)
            else :
                encoding_list.append('EOB')
                break
        return encoding_list
    





def block2img(blocks, src_shape, n = 8):
    ###################################################
    # TODO                                            #
    # block2img 완성                                   #
    # 복구한 block들을 image로 만들기                     #
    ###################################################

    (h, w) = src_shape
    if h % n != 0:
        h_pad = n - h%n
    else:
        h_pad = 0

    if w % n != 0:
        w_pad = n - w%n
    else:
        w_pad = 0

    dst = np.zeros((h+h_pad, w+w_pad), dtype=np.uint8)
    
    idx = 0
  
    for row in range(h//n):
        for col in range(w//n):
            dst[n*row:(row+1)*n,n*col:(col+1)*n] = blocks[idx]
            idx +=1 

    
    dst = dst[:h,:w]
    return dst

def Encoding(src, n=8,scale_factor=1):
    #################################################################################################
    # TODO                                                                                          #
    # Encoding 완성                                                                                  #
    # Encoding 함수를 참고용으로 첨부하긴 했는데 수정해서 사용하실 분은 수정하셔도 전혀 상관 없습니다.              #
    #################################################################################################
    print('<start Encoding>')
    # img -> blocks
    blocks = img2block(src, n=n)
    print("block = \n",src[150:158,89:97])


    #subtract 128
    blocks -= 128
    b = np.double(src[150:158,89:97])-128
    print("b = \n",b)

    #DCT
    blocks_dct = []
    for block in blocks:
        blocks_dct.append(DCT(block, n=n))
    blocks_dct = np.array(blocks_dct)

    # print DCT results
    bd = DCT(b,n=8)
    print("bd = \n",bd)


    #Quantization + thresholding
    Q = Quantization_Luminance(scale_factor)
    QnT = np.round(blocks_dct / Q)
    #print Quantization results
    bq = bd  / Q
    print("bq = \n",bq)

    # zigzag scanning
    zz = []
    for i in range(len(QnT)):
        zz.append(my_zigzag_scanning(QnT[i]))

    return zz, src.shape, bq

def Decoding(zigzag, src_shape,bq, n=8,scale_factor=1):
    #################################################################################################
    # TODO                                                                                          #
    # Decoding 완성                                                                                  #
    # Decoding 함수를 참고용으로 첨부하긴 했는데 수정해서 사용하실 분은 수정하셔도 전혀 상관 없습니다.              #
    #################################################################################################
    print('<start Decoding>')

    # zigzag scanning
    blocks = []
    for i in range(len(zigzag)):
        blocks.append(my_zigzag_scanning(zigzag[i], mode='decoding', block_size=n))
    blocks = np.array(blocks)


    # Denormalizing
    Q = Quantization_Luminance(scale_factor=scale_factor)
    blocks = blocks * Q
    # print results Block * Q
    bq2 = bq * Q
    print("bq2 = \n",bq2)

    # inverse DCT
    blocks_idct = []
    for block in blocks:
        blocks_idct.append(DCT_inv(block, n=n))
    blocks_idct = np.array(blocks_idct)

    #print IDCT results
    bd2 = DCT_inv(bq2,n=8)
    print("bd2 = \n",bd2)

    # add 128
    blocks_idct += 128

    # print block value
    b2 = np.round(bd2 + 128)
    print("b2 = \n",b2)

    # block -> img
    dst = block2img(blocks_idct, src_shape=src_shape, n=n)

    return dst, b2



def main():
    scale_factor = 1
    start = time.time()
    src = cv2.imread('ImageProcessing/week11/homework/caribou.tif', cv2.IMREAD_GRAYSCALE)

    comp, src_shape,bq = Encoding(src, n=8,scale_factor=scale_factor)
    np.save('comp.npy', comp)
    np.save('src_shape.npy', src_shape)
    # print(comp)
    comp = np.load('comp.npy', allow_pickle=True)
    src_shape = np.load('src_shape.npy')
    recover_img, b2 = Decoding(comp, src_shape, bq,n=8,scale_factor=scale_factor)
    print("scale_factor : ",scale_factor,"differences between original and reconstructed = \n",src[150:158,89:97]-b2)
    # print(recover_img)
    total_time = time.time() - start
    #
    print('time : ', total_time)
    if total_time > 12:
        print('감점 예정입니다.')
    print(recover_img.shape)
    cv2.imshow('src', src)
    cv2.imshow('20211924', recover_img)
    cv2.waitKey()
    cv2.destroyAllWindows()


if __name__ == '__main__':
    main()
