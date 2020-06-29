import os
import numpy as np
import random
import os
import matplotlib.pyplot as plt

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
        text = text.replace(',,',',')
        print(text)
        f.write(text+'\n')

    f.close()

def open_board():
    board = np.zeros((8,8),dtype = int)
    with open('Board.csv','r') as f:
        data = f.readlines()

    for i in range(8):
        line = data[i]
        new_line = line.split(',')
        board[i] = new_line

    return board

    
def start_game():
    
    save_board(new_board())

    p1 = 'White'
    if random.randint(0,1) == 0:
        p1 = 'Black'

    p2 = 'Barbra'
    if random.randint(0,1) == 0:
        p2 = 'Charles'


    f = open('Colour.csv','w')
    f.write(p2+','+p1+'\n')
    f.close()

def run_move():
    with open('Colour.csv','r') as f:
        data = f.readlines()

    board = open_board()

    who_goes = data[0].split(',')

    print(who_goes[0]+' move')

    if who_goes[0] == 'Charles':
        move = run_Charles()
        
        
    elif who_goes[0] == 'Barbra':
        pass
    
    else:
        print('Unknown Player')

    check = check_move(move,board)

    if check[0] == True:
        old = board[move[0][0]][move[0][1]]
        print(old)
        board[move[0][0]][move[0][1]] = 0
        board[move[1][0]][move[1][1]] = old

        print(board)

        save_board(board)
        
    else:
        print(check[1]+' '+who_goes[0])

    
    

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


    if new[0] < 0 or new[0] > 7 or new[1] < 0 or new[1] > 7:
        return [False,'Move off Board']
    

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

        

    elif abs(piece) == 2: #rook
        viables = []
        Y = [0,1,2,3,4,5,6,7]
        Y.remove(old[0])
        X = [0,1,2,3,4,5,6,7]
        X.remove(old[1])

        for num in Y:
            viables.append([num,old[1]])
        for num in X:
            viables.append([old[0],num])



    elif abs(piece) == 3: #knight
        all_viables = [[old[0]+1,old[1]+2],[old[0]-1,old[1]+2],
                   [old[0]-2,old[1]+1],[old[0]-2,old[1]-1],
                   [old[0]+1,old[1]-2],[old[0]-1,old[1]-2],
                   [old[0]+2,old[1]-1],[old[0]+2,old[1]+1]]

        viables = []
        for via in all_viables:
            if via[0]<0 or via[1]<0:
                pass
            else:
                viables.append(via)


    elif abs(piece) == 4: #bishop
        viables = []
        Y = old[0]
        X = old[1]


        offset = X - Y
        addset = X + Y
        viables = []
        #Leading Diag

        for i in range(8):
            X1 = i
            Y1 = X1 - offset
            if Y1 <= 7 and Y1 >= 0 and X1 != X:
                viables.append([Y1,X1])

        #Non-Leading Diag
        
        for i in range(8):
            X1 = i
            Y1 = addset - X1
            if Y1 <= 7 and Y1 >= 0 and X1 != X:
                viables.append([Y1,X1])

    elif abs(piece) == 5: #queen

        #Bishop
        viables = []
        Y = old[0]
        X = old[1]


        offset = X - Y
        addset = X + Y
        viables = []
        #Leading Diag

        for i in range(8):
            X1 = i
            Y1 = X1 - offset
            if Y1 <= 7 and Y1 >= 0 and X1 != X:
                viables.append([Y1,X1])

        #Non-Leading Diag
        
        for i in range(8):
            X1 = i
            Y1 = addset - X1
            if Y1 <= 7 and Y1 >= 0 and X1 != X:
                viables.append([Y1,X1])

        #Rook
        Y = [0,1,2,3,4,5,6,7]
        Y.remove(old[0])
        X = [0,1,2,3,4,5,6,7]
        X.remove(old[1])

        for num in Y:
            viables.append([num,old[1]])
        for num in X:
            viables.append([old[0],num])
            

    elif abs(piece) == 6: #King
        Y = old[0]
        X = old[1]
        all_viables = [[Y,X+1],[Y+1,X],
                   [Y,X-1],[Y-1,X],
                   [Y+1,X+1],[Y+1,X-1],
                   [Y-1,X+1],[Y-1,X-1]]

        viables = []
        for via in all_viables:
            if via[0]<0 or via[1]<0:
                pass
            else:
                viables.append(via)

            

    else:
        return [False,'Invalid Piece']

    '''
    #show viables

    show_board = board.copy()
    print(old)
    show_board[old[0]][old[1]] = 100
    print(viables)
    for pos in viables:
        show_board[pos[0]][pos[1]] = 200
        
    plt.matshow(show_board)
    plt.show()
    '''

        
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
run_move()

