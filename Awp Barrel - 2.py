import matplotlib.pyplot as plt
import numpy as np


diam = 1.1
circum = np.pi * diam
length = 28.75

diam = circum / 2

width = 0.5 #change this
final_ang = np.pi / 12




def f(diam,width,length,X):

    
    b = (np.pi * diam) / (2*width)
    a = (np.tan(final_ang) - b) / length
    c = -(b*width + width**2 * a/2)

    
    out = a/2 * X**2 + b * X + c
    


    print(a)
    print(b)
    print(c)
    
    
    
    return out % diam

X = np.arange(0,length,0.001)
Y = f(diam,width,length,X)

'''
plt.plot(X,Y,'o',markersize = 1)
plt.axis('equal')
plt.show()
'''
data = zip(X,Y)

count = 0
b = 0
for a in data:
    
    if count % 2 == 0:
        plt.plot(a[0],a[1],'ro',markersize = 1)
    else:
        plt.plot(a[0],a[1],'bo',markersize = 1)

    if (b-a[1]) >= width / 2:
        count = count + 1

    b = a[1]

plt.axis('equal')
plt.show()
