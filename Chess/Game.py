import os
import numpy as np
import random

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

def start_game():
    
    save_board(new_board())

    p1 = 'White'
    p2 = 'White'
    if random.randint(0,1) == 0:
        p1 = 'Black'
    else:
        p2 = 'Black'

    f = open('Colour.csv','w')
    f.write('Barbra,'+p1+'\n')
    f.write('Charles,'+p2)
    f.close()

start_game()
