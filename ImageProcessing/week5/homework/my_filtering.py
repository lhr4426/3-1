import cv2
import numpy as np

def my_get_Gaussian2D_mask(msize, sigma=1):
    #########################################
    # ToDo
    # 2D gaussian filter 만들기
    #########################################
    y, x = np.mgrid[-(msize[0]//2):(msize[0]//2)+1, -(msize[1]//2):(msize[1]//2)+1]
    '''
    y, x = np.mgrid[-1:2, -1:2]
    y = [[-1,-1,-1],
         [ 0, 0, 0],
         [ 1, 1, 1]]
    x = [[-1, 0, 1],
         [-1, 0, 1],
         [-1, 0, 1]]
    '''
    # 파이 => np.pi 를 쓰시면 됩니다.
    # 2차 gaussian mask 생성
    gaus2D = (1/(2*np.pi*(sigma**2)))*np.exp(-(x**2 + y**2)/2*(sigma**2))
    # mask의 총 합 = 1
    gaus2D /= np.sum(gaus2D)

    return gaus2D

def my_get_Gaussian1D_mask(msize, sigma=1):
    #########################################
    # ToDo
    # 1D gaussian filter 만들기
    #########################################
    x = np.full((1, msize), [range(-(msize // 2), (msize // 2) + 1)])
    '''
    x = np.full((1, 3), [-1, 0, 1])
    x = [[ -1, 0, 1]]

    x = np.array([[-1, 0, 1]])
    x = [[ -1, 0, 1]]
    '''

    # 파이 => np.pi 를 쓰시면 됩니다.
    gaus1D = (1/((2*np.pi)**1/2)*sigma)*np.exp(-(x**2)/2*(sigma**2))

    # mask의 총 합 = 1
    gaus1D /= np.sum(gaus1D)
    return gaus1D

def my_mask(ftype, fshape, sigma=1):
    if ftype == 'average':
        print('average filtering')
        ###################################################
        # TODO                                            #
        # mask 완성                                       #
        ###################################################
        mask = np.ones(fshape)
        mask = mask / np.sum(mask)

        #mask 확인
        print(mask)

    elif ftype == 'sharpening':
        print('sharpening filtering')
        ##################################################
        # TODO                                           #
        # mask 완성                                      #
        ##################################################

        base_mask = np.zeros(fshape)
        base_mask[fshape[0]//2, fshape[1]//2] = 2  # 중간값을 2로 해주는거임. 따라서 평균마스크만 만들면 됨
        aver_mask = np.ones(fshape)
        aver_mask = aver_mask / np.sum(aver_mask)
        mask = base_mask - aver_mask

        #mask 확인
        print(mask)

    elif ftype == 'gaussian2D':
        print('gaussian filtering')
        ##################################################
        # TODO                                           #
        # mask 완성                                      #
        ##################################################
        mask = my_get_Gaussian2D_mask(fshape, sigma=sigma)
        #mask 확인
        print(mask)

    elif ftype == 'gaussian1D':
        print('gaussian filtering')
        ##################################################
        # TODO                                           #
        # mask 완성                                      #
        ##################################################
        mask = my_get_Gaussian1D_mask(fshape, sigma=sigma)
        #mask 확인
        print(mask)

    return mask

def my_zero_padding(src, pad_shape):
    (h, w) = src.shape
    (p_h, p_w) = pad_shape
    pad_img = np.zeros((h+2*p_h, w+2*p_w))
    pad_img[p_h:p_h+h, p_w:p_w+w] = src
    return pad_img

def my_filtering(src, mask):
    #########################################################
    # TODO                                                  #
    # dst 완성                                              #
    # dst : filtering 결과 image                            #
    #########################################################
    h, w = src.shape
    m_h, m_w = mask.shape
    pad_img = my_zero_padding(src, (m_h//2, m_w//2))
    dst = np.empty((h, w))
    
    """
    반복문을 이용하여 filtering을 완성하기
    """
    for row in range(h):
        for col in range(w):
            val = np.sum(pad_img[row:row+m_h, col:col+m_w] * mask) 
            val = np.clip(val, 0, 255) #범위를 0~255로 조정
            dst[row,col] = val

    dst = (dst+0.5).astype(np.uint8) #uint8의 형태로 조정

    return dst

if __name__ == '__main__':
    src = cv2.imread('ImageProcessing/week3/homework/Lena.png', cv2.IMREAD_GRAYSCALE)

    # # 3x3 filter
    # average_mask = my_mask('average', (3, 3))
    # sharpening_mask = my_mask('sharpening', (3, 3))

    # #원하는 크기로 설정
    # #dst_average = my_filtering(src, 'average', (5,5))
    # #dst_sharpening = my_filtering(src, 'sharpening', (5,5))

    # # 11x13 filter
    # #dst_average = my_filtering(src, 'average', (5,3), 'repetition')
    # #dst_sharpening = my_filtering(src, 'sharpening', (5,3), 'repetition')
    # #dst_average = my_filtering(src, 'average', (11,13))
    # #dst_sharpening = my_filtering(src, 'sharpening', (11,13))


    # dst_average = my_filtering(src, average_mask)
    # dst_sharpening = my_filtering(src, sharpening_mask)

    # # Gaussian filter
    # gaussian2d_mask = my_mask('gaussian2D', (3, 3), sigma=1)
    # gaussian1d_mask = my_mask('gaussian1D', 3, sigma=1)

    # dst_gaussian2d = my_filtering(src, gaussian2d_mask)

    # dst_gaussian1d = my_filtering(src, gaussian1d_mask.T)
    # dst_gaussian1d = my_filtering(dst_gaussian1d, gaussian1d_mask)


    # cv2.imshow('original', src)
    # cv2.imshow('average filter', dst_average)
    # cv2.imshow('sharpening filter', dst_sharpening)
    # cv2.imshow('gaussian2D filter', dst_gaussian2d)
    # cv2.imshow('gaussian1D filter', dst_gaussian1d)

    # --------------------------------------------------

    # # average filtering 
    
    # average_mask_3x3 = my_mask('average', (3, 3))
    # average_mask_3x5 = my_mask('average', (3, 5))
    # average_mask_5x5 = my_mask('average', (5, 5))

    # dst_3x3 = my_filtering(src, average_mask_3x3)
    # dst_3x5 = my_filtering(src, average_mask_3x5)
    # dst_5x5 = my_filtering(src, average_mask_5x5)

    # cv2.imshow('original', src)
    # cv2.imshow('3x3', dst_3x3)
    # cv2.imshow('3x5', dst_3x5)
    # cv2.imshow('5x5', dst_5x5)

    # --------------------------------------------------

    # # sharpening filtering

    # sharpening_mask_3x3 = my_mask('sharpening', (3, 3))
    # sharpening_mask_3x5 = my_mask('sharpening', (3, 5))
    # sharpening_mask_5x5 = my_mask('sharpening', (5, 5))

    # dst_3x3 = my_filtering(src, sharpening_mask_3x3)
    # dst_3x5 = my_filtering(src, sharpening_mask_3x5)
    # dst_5x5 = my_filtering(src, sharpening_mask_5x5)

    # cv2.imshow('original', src)
    # cv2.imshow('3x3', dst_3x3)
    # cv2.imshow('3x5', dst_3x5)
    # cv2.imshow('5x5', dst_5x5)

    # # --------------------------------------------------

    # 2D gaussian filtering 

    # gaussian_mask_3x3_1 = my_mask('gaussian2D', (3, 3), sigma=1)
    # gaussian_mask_3x3_05 = my_mask('gaussian2D', (3, 3), sigma=0.5)
    # gaussian_mask_5x5_1 = my_mask('gaussian2D', (5, 5), sigma=1)
    # gaussian_mask_5x5_3 = my_mask('gaussian2D', (5, 5), sigma=3)
    # gaussian_mask_7x7_1 = my_mask('gaussian2D', (7, 7), sigma=1)
    # gaussian_mask_7x7_01 = my_mask('gaussian2D', (7, 7), sigma=0.1)

    # dst_3x3_1 = my_filtering(src, gaussian_mask_3x3_1)
    # dst_3x3_05 = my_filtering(src, gaussian_mask_3x3_05)
    # dst_5x5_1 = my_filtering(src, gaussian_mask_5x5_1)
    # dst_5x5_3 = my_filtering(src, gaussian_mask_5x5_3)
    # dst_7x7_1 = my_filtering(src, gaussian_mask_7x7_1)
    # dst_7x7_01 = my_filtering(src, gaussian_mask_7x7_01)

    # cv2.imshow('original', src)
    # cv2.imshow('3x3, sigma = 1', dst_3x3_1)
    # cv2.imshow('3x3, sigma = 0.5', dst_3x3_05)
    # cv2.imshow('5x5, sigma = 1', dst_5x5_1)
    # cv2.imshow('5x5, sigma = 3', dst_5x5_3)
    # cv2.imshow('7x7, sigma = 1', dst_7x7_1)
    # cv2.imshow('7x7, sigma = 0.1', dst_7x7_01)

    # --------------------------------------------------

    # # 1D gaussian filtering

    # gaussian1d_mask_3_1 = my_mask('gaussian1D', 3, sigma=1)
    # gaussian1d_mask_3_05 = my_mask('gaussian1D', 3, sigma=0.5)
    # gaussian1d_mask_5_1 = my_mask('gaussian1D', 5, sigma=1)
    # gaussian1d_mask_5_3 = my_mask('gaussian1D', 5, sigma=3)
    # gaussian1d_mask_7_1 = my_mask('gaussian1D', 7, sigma=1)
    # gaussian1d_mask_7_01 = my_mask('gaussian1D', 7, sigma=0.1)

    # dst_3x3_1 = my_filtering(src, gaussian1d_mask_3_1.T)
    # dst_3x3_1 = my_filtering(dst_3x3_1, gaussian1d_mask_3_1)

    # dst_3x3_05 = my_filtering(src, gaussian1d_mask_3_05.T)
    # dst_3x3_05 = my_filtering(dst_3x3_05, gaussian1d_mask_3_05)

    # dst_5x5_1 = my_filtering(src, gaussian1d_mask_5_1.T)
    # dst_5x5_1 = my_filtering(dst_5x5_1, gaussian1d_mask_5_1)

    # dst_5x5_3 = my_filtering(src, gaussian1d_mask_5_3.T)
    # dst_5x5_3 = my_filtering(dst_5x5_3, gaussian1d_mask_5_3)

    # dst_7x7_1 = my_filtering(src, gaussian1d_mask_7_1.T)
    # dst_7x7_1 = my_filtering(dst_7x7_1, gaussian1d_mask_7_1)
    
    # dst_7x7_01 = my_filtering(src, gaussian1d_mask_7_01.T)
    # dst_7x7_01 = my_filtering(dst_7x7_01, gaussian1d_mask_7_01)


    # cv2.imshow('original', src)
    # cv2.imshow('3x3, sigma = 1', dst_3x3_1)
    # cv2.imshow('3x3, sigma = 0.5', dst_3x3_05)
    # cv2.imshow('5x5, sigma = 1', dst_5x5_1)
    # cv2.imshow('5x5, sigma = 3', dst_5x5_3)
    # cv2.imshow('7x7, sigma = 1', dst_7x7_1)
    # cv2.imshow('7x7, sigma = 0.1', dst_7x7_01)
    cv2.waitKey()
    cv2.destroyAllWindows()
