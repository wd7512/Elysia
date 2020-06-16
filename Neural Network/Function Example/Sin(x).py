import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation
import random
import os

class Layer_Dense:
    def __init__(self, n_inputs, n_neurons):
        self.weights = 0.1 * np.random.randn(n_inputs, n_neurons) #smaller calculations
        self.biases = np.zeros((1,n_neurons))
    def forward(self,inputs): #takes inputs
        self.output = ReLU(np.dot(inputs, self.weights) + self.biases)

        

def ReLU(inputs):
    return np.maximum(0,inputs)



X = np.arange(0,2*np.pi,0.001)
Y = np.sin(X)

# one input value, 2x8 hidden, 1 output
def random_brain():
    Lay1 = Layer_Dense(1,8)
    Lay2 = Layer_Dense(8,8)
    Lay3 = Layer_Dense(8,1)
    Brain = [Lay1,Lay2,Lay3]
    return Brain

def get_data():
    Tot_inputs = len(X)
    Batch_Size = 64
    Batch_index = [random.randint(0,Tot_inputs-1) for i in range(Batch_Size)]
    Batch_inputs = [[Y[a]] for a in Batch_index]
    
    return Batch_inputs


def run(Batch_inputs,Brain):
    Brain[0].forward(Batch_inputs)
    Brain[1].forward(Brain[0].output)
    Brain[2].forward(Brain[1].output)
    
    return Brain[2].output

def test(Brain):
    test_X = get_data()
    real_Y = [1+np.sin(a) for a in test_X]
    Y = run(test_X,Brain)
    score = sum(np.exp(abs(Y-real_Y)))

    return score

def disp(Brain,Save_Name):
    test_X = [[a] for a in np.arange(0,2*np.pi,np.pi/10)]
    real_Y = [1+np.sin(a) for a in test_X]
    Y = run(test_X,Brain)
    score = sum(np.exp(abs(Y-real_Y)))
    

    
    plt.plot(test_X,Y)
    plt.plot(test_X,real_Y)
    plt.title(Save_Name+' Score = '+str(score))
    plt.savefig(Save_Name)
    plt.clf()

    return

def evolve(Brain):
    for lay in Brain:
        w = np.shape(lay.weights)
        lay.weights = lay.weights  - 0.001 * np.random.randn(w[0], w[1])
        #lay.weights = lay.weights  * (1 - 0.005 * np.random.randn(w[0], w[1]))
        b = np.shape(lay.biases)
        lay.biases = lay.biases - 0.001 * np.random.randn(b[0], b[1])
        #lay.biases = lay.biases * (1 - 0.005 * np.random.randn(b[0], b[1]))

    return Brain

def clear(keyword):
    files = os.listdir()
    for f in files:
        if keyword in f:
            os.remove(f)
            
clear('Gen')
Population = 5000
All_Brains = [random_brain() for i in range(Population)]
Generations = 500
Old_Score = 68

for i in range(Generations):
    All_Scores = [test(a) for a in All_Brains]
    Best_index = All_Scores.index(min(All_Scores))
    Best_Score = All_Scores[Best_index]
    Best_Brain = All_Brains[Best_index]
    disp(Best_Brain,str(i)+'Gen.jpg')
    print(All_Scores[Best_index])

    epsilon = round((50 - 0.1 * i) * Population  / 100)
    print('Epsilon: '+ str(epsilon))

    Qual_Brains = []
    for i in range(Population):
        score = All_Scores[i]
        if score < Old_Score:
            Qual_Brains.append(All_Brains[i])

    Old_Score = Best_Score

    print('Qualified: '+str(len(Qual_Brains)))

    if Qual_Brains == 0:
        pass
    else:

        All_Brains = [evolve(Best_Brain) for i in range(Population - epsilon - 1 - len(Qual_Brains))]
        All_Brains = All_Brains + [random_brain() for i in range(epsilon)] + Qual_Brains
        All_Brains.append(Best_Brain)

    

    

