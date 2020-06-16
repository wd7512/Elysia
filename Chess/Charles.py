import os
import numpy as np
import random

def open_board():
    board = np.zeros((8,8),dtype = int)
    f = open('Board.csv','r')
    data = f.readlines()
    f.close()
    for i in range(8):
        line = data[i]
        new_line = line.split(',')
        board[i] = new_line

    return board

def random_move(board):
    pieces = []
    blanks = []
    
    for i in range(8):
        for j in range(8):
            if board[i][j] == 0:
                blanks.append([i,j])
            else:
                pieces.append([i,j])

    old_pos = random.choice(pieces)
    new_pos = random.choice(blanks)

    out = np.zeros((2,2))
    out[0] = old_pos
    out[1] = new_pos

    return out

def output_move(move):
    f = open('CharlesMove.csv','w')
    for line in move:
        new_line = str(line).replace(' ',',')
        new_line = new_line.replace('[','')
        new_line = new_line.replace(']','')
        f.write(new_line+'\n')

    f.close()
    
    
a = random_move(open_board())
output_move(a)
