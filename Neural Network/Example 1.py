import sys
import numpy as np
import matplotlib

print('Python:', sys.version)
print('Numpy:', np.__version__)
print('Matplotlib:', matplotlib.__version__)

#example 
inputs = [[1,2,3,2.5], #using a batch of 3, 3 inputs at a time
          [7,3,3,-2.5],
          [-1,3,-3,5]]

weights1 = [[0.2,0.8,-0.5,1], #4 weights for 3 nodes
           [1,7,-0.5,3],
           [3,0.8,-0.5,4]]

biases1 = [2,3,0.5]

weights2 = [[0.5,0.8,-0.5], #3 weights for 3 nodes
           [1,7,-0.9],
           [3,0.8,-0.5]]

biases2 = [2,3,1]

layer1_outputs = np.dot(inputs,np.array(weights1).T) + biases1
layer2_outputs = np.dot(layer1_outputs,np.array(weights2).T) + biases2
print(layer2_outputs)
#end of example


