import matplotlib.pyplot as plt
import numpy as np


diam = 1.1
circum = np.pi * diam
legnth = 28.75

width = 1.11
w_exam = [width*a**1.5 for a in range(1,4)]

x_max = 0

for w in w_exam:
    
    x_max = x_max + 2*w
    x_min = x_max - w

    x2_max = x_max + w
    x2_min = x_min + w

    X = np.arange(x_min,x_max,0.01)
    X2 = np.arange(x2_min,x2_max,0.01)

    grad = circum / w

    plt.plot(X,(X-x_min)*grad,'r')
    plt.plot(X2,(X2-x2_min)*grad,'b')

x_max = width

for w in w_exam:
    
    x_max = x_max + 2*w
    x_min = x_max - w

    x2_max = x_max + w
    x2_min = x_min + w

    X = np.arange(x_min,x_max,0.01)
    X2 = np.arange(x2_min,x2_max,0.01)

    grad = circum / w

    plt.plot(X,(X-x_min)*grad,'r')
    plt.plot(X2,(X2-x2_min)*grad,'b')

plt.axis('equal')
plt.show()
