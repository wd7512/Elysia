import os
import numpy as np

def new_board():
    board = np.zeros((8,8),dtype = int)
    board[0] = [-2,-3,-4,-5,-6,-4,-3,-2]
    board[1] = [-1,-1,-1,-1,-1,-1,-1,-1]
    board[6] = [1,1,1,1,1,1,1,1]
    board[7] = [2,3,4,5,6,4,3,2]

    return board
    
def save_board(board):
    f = open('Board.csv','w')

    for line in board:
        text = str(line).replace(' ',',')
        text = text.replace(']','')
        text = text.replace('[','')
        f.write(text+'\n')

    f.close()

save_board(new_board())
