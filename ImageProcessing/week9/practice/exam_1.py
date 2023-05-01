import numpy as np
import cv2
import time

def my_filtering(src, filter, pad_type='zero') :
    (h, w) = src.shape
    (f_h, f_w) = filter.shape
    src_pad = my_zero_padding(src, (f_h//2, f_w//2), pad_type)
    dst = np.zeros((h,w))

    for row in range(h) :
        for col in range(w) :
            val = np.sum(src_pad[row:row+f_h, col:col+f_w] * filter)
            dst[row, col] = val
    
    return dst

def my_zero_padding(src, pad_shape):
    (h, w) = src.shape
    (p_h, p_w) = pad_shape
    pad_img = np.zeros((h+2*p_h, w+2*p_w))
    pad_img[p_h:p_h+h, p_w:p_w+w] = src
    return pad_img

def add_SnP_noise(src, prob) :
    h, w = src.shape
    noise_prob = np.random.rand(h, w)
    dst = np.zeros((h, w), dtype=np.uint8)
    for row in range(h) :
        for col in range(w) :
            if noise_prob[row, col] < prob:
                dst[row, col] = 0
            elif noise_prob[row, col] > 1- prob:
                dst[row, col] = 255
            else :
                dst[row, col] = src[row, col]

    return dst

