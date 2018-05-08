import numpy as np
x = np.arange(10)
print(x[:-2])


x = np.arange(20).reshape(2,10)
y = np.array([2,4]).reshape(2,1)
print(np.multiply(y,x))

print( x, '\n', np.mean(x, axis = 0). reshape(-1,1))
