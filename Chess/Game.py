import os
import numpy as np
import random
import os

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

def run_game():
    '''
    f = open('Colour.csv','r')
    data = f.readlines()
    f.close()
    Colour = []
    for line in data:
        Colour.append(str(line).split(','))

    
    '''

    Move = run_Charles()
    

def run_Charles():

    os.system('python Charles.py')
    f = open('CharlesMove.csv','r')
    Charles_Out = []
    for line in f.readlines():
        Charles_Out.append(str(line).split(','))
    f.close()

    for i in range(2):
        for j in range(2):
            Charles_Out[i][j] = int(float(Charles_Out[i][j]))

    print(Charles_Out)

    return Charles_Out

def check_move(move,board):

    old = move[0]
    new = move[1]

    piece = board[old[0],old[1]]

    if piece > 0:
        col = 'White'
    else:
        col = 'Black'

    if board[new[0],new[1]] > 0 and col == 'White':
        return [False,'Moving onto own piece']
    if board[new[0],new[1]] < 0 and col == 'Black':
        return [False,'Moving onto own piece']

    if abs(piece) == 1: #pawn

        if col == 'Black':
            
            if old[0] == 1:
                viables = [[old[0]+1 ,old[1]],
                           [old[0]+2 ,old[1]]]
            else:
                viables = [[old[0]-1 ,old[1]]]
                
        elif col == 'White':
            
            if old[0] == 6:
                viables = [[old[0]-1 ,old[1]],
                           [old[0]-2 ,old[1]]]
            else:
                viables = [[old[0]+1 ,old[1]]]

        if compare(new,viables) == False:
            return [False,'Illegal Move']
        else:
            return [True,'']

            
        
    
def compare(pos,viable_pos):
    
    for v_pos in viable_pos:
        if pos[0] == v_pos[0] and pos[1] == v_pos[1]:
            return True

    return False
    
    

start_game()
run_game()
