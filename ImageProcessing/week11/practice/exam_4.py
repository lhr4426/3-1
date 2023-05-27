import numpy as np

def zigzag_scan(block, mode='encoding', block_size=8) :
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
            
if __name__ == "__main__" :
    item = np.arange(16)
    item = item.reshape(4,4)

    encoding_result = zigzag_scan(item, mode='encoding', block_size=4)
    # encoding_result = [0, 1, 4, 8, 5, 2, 3, 6, 9, 12, 13, 10, 7, 11, 14, 15]
    decoding_result = zigzag_scan(encoding_result, mode='decoding', block_size=4)
    print(encoding_result)
    print(decoding_result)


