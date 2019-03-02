#
# Compare proximal and standard gradient descent for linear regression problem
# 

import numpy as np
import matplotlib.pyplot as plt
import sys


n = 1000
d = 30

x = np.random.randn(n,d)
beta = np.random.randint(0,10,d)
print(x.shape)

print('Beta is {}'.format(beta))

y = np.dot(x,beta).reshape(n,1)
print(y.shape)

def loss(a,x,y):
	return 1.0/n * np.sum((y - np.dot(x,a))**2)
	
#proximal for lambda penalty - as in the lecture	
def proximal(a, step, lam):
	#print(a.shape, np.multiply(np.sign(a), np.maximum((np.abs(a) -  lam*step), np.zeros((d,1)))).shape)
	return np.multiply(np.sign(a), np.maximum((np.abs(a) -  lam*step), np.zeros((d,1))))
	
def proximal_grad_step(a,x,y,step = 0.01, lam = 0.001):
	return proximal(grad_step(a,x,y,step), step, lam) 
	
def grad_step(a,x,y,step = 0.01):
	n = x.shape[0]
	grad_a = -2.0/n * np.dot(x.T, y - np.dot(x,a))
	#print('Grads\n', grad_a, '\n', grad_b)
	return a - step * grad_a
	
def gradient_descent(x,y, epsilon = 0.001):
	a = np.random.randn(d).reshape(d,1)
	losses = []
	prev_loss = sys.maxsize
	curr_loss = 0
	steps = 0
	while np.abs(curr_loss - prev_loss) >  epsilon:
		losses += [loss(a,x,y)]
		a = grad_step(a,x,y, 0.01)
		steps += 1
		prev_loss = curr_loss
		curr_loss = loss(a,x,y)
	return a,losses, steps
	
def proximal_gradient_descent(x,y, epsilon = 0.001, lam = 0):
	a = np.random.randn(d).reshape(d,1)
	losses = []
	prev_loss = sys.maxsize
	curr_loss = 0
	steps = 0
	while np.abs(curr_loss - prev_loss) >  epsilon:
		losses += [loss(a,x,y)]
		a = proximal_grad_step(a,x,y, 0.01, lam)
		steps += 1
		prev_loss = curr_loss
		curr_loss = loss(a,x,y)
	return a,losses, steps

	
a,losses,steps_count = gradient_descent(x,y)
print('Steps count for GD: {}\nThe loss was {}'.format(steps_count, losses[-1]))
plt.figure(1)
plt.plot(np.arange(len(losses)), losses, label='Loss for GD')
a,losses,steps_count = proximal_gradient_descent(x,y, lam = 0.01)
print('Steps count for PGD: {}\nThe loss was {}'.format(steps_count, losses[-1]))
plt.figure(1)
plt.plot(np.arange(len(losses)), losses, label='Loss for PGD')
plt.legend()


#zeros on most coordinates
x = np.random.randn(n,d)
beta = 10*np.random.randn(d)
ind = np.random.permutation(np.arange(d))[:-d//8]
beta[ind] = 0

print('Beta is {}'.format(beta))

y = np.dot(x,beta).reshape(n,1)

a,losses,steps_count = gradient_descent(x,y)
print('Number of nonzero coeff: {}'.format(np.sum(a!=0)))
print('Steps count for GD: {}\nThe loss was {}'.format(steps_count, losses[-1]))
plt.figure(2)
plt.plot(np.arange(len(losses)), losses, label='Loss for GD')
a,losses,steps_count = proximal_gradient_descent(x,y, lam = 1)
print('Number of nonzero coeff: {}'.format(np.sum(a!=0)))
print('Steps count for PGD: {}\nThe loss was {}'.format(steps_count, losses[-1]))
plt.figure(2)
plt.plot(np.arange(len(losses)), losses, label='Loss for PGD')
plt.legend()

plt.show()
	
	


