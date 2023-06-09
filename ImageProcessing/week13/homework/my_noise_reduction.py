import numpy as np
import cv2
import time


def my_get_Gaussian2D_mask(msize, sigma=1):
    y, x = np.mgrid[-(msize // 2):(msize // 2) + 1,
           -(msize // 2):(msize // 2) + 1]

    gaus2D = 1 / (2 * np.pi * sigma**2) * \
             np.exp(-(( x**2 + y**2 )/(2 * sigma**2)))

    gaus2D /= np.sum(gaus2D)
    return gaus2D


def my_normalize(src):
    dst = src.copy()
    dst *= 255
    dst = np.clip(dst, 0, 255)
    return dst.astype(np.uint8)


def my_padding(src, pad_shape):
    (h, w) = src.shape
    (p_h, p_w) = pad_shape
    pad_img = np.zeros((h+2*p_h, w+2*p_w))
    pad_img[p_h:p_h+h, p_w:p_w+w] = src
    return pad_img


def my_filtering(src, mask):
    (h, w) = src.shape
    (m_h, m_w) = mask.shape
    pad_img = my_padding(src, (m_h//2, m_w//2))
    dst = np.zeros((h, w))
    for row in range(h):
        for col in range(w):
            val = np.sum(pad_img[row:row + m_h, col:col + m_w] * mask)
            dst[row, col] = val
    return dst


def add_gaus_noise(src, mean=0, sigma=0.1):
    #src : 0 ~ 255, dst : 0 ~ 1
    dst = src/255
    h, w, c = dst.shape
    noise = np.random.normal(mean, sigma, size=(h, w, c))
    dst += noise
    return my_normalize(dst)


def my_bilateral(src, msize, sigma, sigma_r):
    ####################################################################################################
    # TODO                                                                                             #
    # my_bilateral 완성                                                                                 #
    # mask를 만들 때 4중 for문으로 구현 시 감점(if문 fsize*fsize개를 사용해서 구현해도 감점) 실습영상 설명 참고      #
    ####################################################################################################
    (h, w) = src.shape
    m_s = msize // 2
    img_pad = my_padding(src, pad_shape=(m_s, m_s))
    dst = np.zeros((h, w))

    y, x = np.mgrid[-m_s:m_s + 1, -m_s:m_s + 1]

    for i in range(h):
        print('\r%d / %d ...' %(i,h), end="")
        for j in range(w):
            k = y + i
            l = x + j
            mask = np.exp( -(((i - k)**2) / (2 * sigma**2)) -(((j-l)**2) / (2 * sigma**2)) ) * np.exp( -(((img_pad[i+m_s, j+m_s] - img_pad[k+m_s, l+m_s])**2)/(2*sigma_r**2)) )
            mask = mask/mask.sum()
            dst[i, j] = np.sum(img_pad[i:i + msize, j:j + msize] * mask)
    return dst


def my_median_filtering(src, msize):
    h, w = src.shape

    dst = np.zeros((h, w))
    for row in range(h):
        for col in range(w):
            r_start = np.clip(row - msize // 2, 0, h)
            r_end = np.clip(row + msize // 2, 0, h)

            c_start = np.clip(col - msize // 2, 0, w)
            c_end = np.clip(col + msize // 2, 0, w)
            mask = src[r_start:r_end, c_start:c_end]
            dst[row, col] = np.median(mask)
    ######################################################
    # TODO                                               #
    # median filtering 코드 작성                          #
    ######################################################

    return dst


if __name__ == '__main__':
    src = cv2.imread('ImageProcessing/week13/homework/canoe.png')
    np.random.seed(seed=100)

    noise_image = add_gaus_noise(src, mean=0, sigma=0.1)
    src_noise = noise_image / 255

    src_b = src_noise[:, :, 0]
    src_g = src_noise[:, :, 1]
    src_r = src_noise[:, :, 2]

    src_float32 = np.float32(src_noise)
    src_noise_yuv = cv2.cvtColor(src_float32, cv2.COLOR_BGR2YUV)

    src_y = src_noise_yuv[:, :, 0]
    src_u = src_noise_yuv[:, :, 1]
    src_v = src_noise_yuv[:, :, 2]

    ######################################################
    # TODO                                               #
    # RGB에서 Bilateral, Gaussian, Median filter 진행     #
    ######################################################
    # RGB
    gaus_mask = my_get_Gaussian2D_mask(8,1)

    b_bilateral = my_bilateral(src_b, 5, 3, 5)
    g_bilateral = my_bilateral(src_g, 5, 3, 5)
    r_bilateral = my_bilateral(src_r, 5, 3, 5)

    rgb_bilateral_dst = cv2.merge((b_bilateral, g_bilateral, r_bilateral))


    b_gaussian = my_filtering(src_b, gaus_mask)
    g_gaussian = my_filtering(src_g, gaus_mask)
    r_gaussian = my_filtering(src_r, gaus_mask)
    
    rgb_gaussian_dst = cv2.merge((b_gaussian, g_gaussian, r_gaussian))

    b_median = my_median_filtering(src_b, 5)
    g_median = my_median_filtering(src_g, 5)
    r_median = my_median_filtering(src_r, 5)

    rgb_median_dst = cv2.merge((b_median, g_median, r_median))


    ######################################################
    # TODO                                               #
    # YUV에서 Bilateral, Gaussian, Median filter 진행     #
    ######################################################
    # YUV
    
    y_biliteral = my_bilateral(src_y, 5, 3, 5)
    yuv_bilateral_dst = np.dstack((y_biliteral, src_u, src_v)).astype('float32')

    y_gaussian = my_filtering(src_y, gaus_mask)
    yuv_gaussian_dst = np.dstack((y_gaussian, src_u, src_v)).astype('float32')

    y_median = my_median_filtering(src_y, 5)
    yuv_median_dst = np.dstack((y_median, src_u, src_v)).astype('float32')


    cv2.imshow('original.png', src)
    cv2.imshow('Noise', src_noise)
    cv2.imshow('RGB bilateral', rgb_bilateral_dst)
    cv2.imshow('RGB gaussian', rgb_gaussian_dst)
    cv2.imshow('RGB median', rgb_median_dst)

    cv2.imshow('YUV bilateral', cv2.cvtColor(yuv_bilateral_dst, cv2.COLOR_YUV2BGR))
    cv2.imshow('YUV gaussian', cv2.cvtColor(yuv_gaussian_dst, cv2.COLOR_YUV2BGR))
    cv2.imshow('YUV median', cv2.cvtColor(yuv_median_dst, cv2.COLOR_YUV2BGR))

    cv2.waitKey()
    cv2.destroyAllWindows()

