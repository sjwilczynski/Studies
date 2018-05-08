#
# gradient descent for linear regression
# 

import numpy as np
import matplotlib.pyplot as plt
import sys


def grad_step(a,b,x,y,step = 0.01):
	n = x.shape[0]
	grad_a = -2.0/n * np.dot(x, y - (a*x +b))
	grad_b = -2.0/n * np.sum(y - (a*x +b))
	#print('Grads\n', grad_a, '\n', grad_b)
	return a - step * grad_a, b - step * grad_b
	
def stochastic_grad_step(a,b,x,y,step=0.01):
	n = x.shape[0]
	i  = np.random.randint(0,n)
	grad_a = -2.0/n * np.dot(x[i], y[i] - (a*x[i] +b))
	grad_b = -2.0/n * np.sum(y[i] - (a*x[i] +b))
	#print('Grads\n', grad_a, '\n', grad_b)
	return a - step * grad_a, b - step * grad_b
	
def gradient_descent(x,y, epsilon = 0.001):
	a,b = A,B
	losses = []
	prev_loss = sys.maxsize
	curr_loss = 0
	steps = 0
	while np.abs(curr_loss - prev_loss) >  epsilon:
		losses += [loss(a,b,x,y)]
		a,b = grad_step(a,b,x,y, 0.01)
		steps += 1
		prev_loss = curr_loss
		curr_loss = loss(a,b,x,y)
	return a,b,losses, steps
	
def stochastic_gradient_descent(x,y, epsilon = 0.001):
	a,b = np.random.randn(2)
	losses = []
	prev_loss = sys.maxsize
	curr_loss = 0
	steps = 0
	while np.abs(curr_loss - prev_loss) >  epsilon or steps % 100:
		losses += [loss(a,b,x,y)]
		a,b = stochastic_grad_step(a,b,x,y, 1.0)
		steps += 1
		prev_loss = curr_loss
		curr_loss = loss(a,b,x,y)
	return a,b,losses, steps


num_points1 = 1000
mean=[4, 6]
cov = [[1.5,2],[2,4]];

x,y = np.random.multivariate_normal(mean , cov, num_points1).T
#print(x.shape, y.shape)
A,B = 0.01*np.random.randn(2)
#print(a,b)

def loss(a,b,x,y):
	return 1.0/x.shape[0] * np.sum((y - (a*x+b))**2)

#proximal for lambda penalty - as in the lecture	
def proximal(x, lam, step):
	#print( x, np.sign(x), (np.abs(x) -  lam*step), np.sign(x) * np.maximum((np.abs(x) -  lam*step), [0,0]))
	return np.sign(x) * np.maximum((np.abs(x) -  lam*step), [0,0])
	
def proximal_grad_step(a,b,x,y,step = 0.01, lam = 0.001):
	n = x.shape[0]
	grad_a = -2.0/n * np.dot(x, y - (a*x +b))
	grad_b = -2.0/n * np.sum(y - (a*x +b))
	#print('Grads\n', grad_a, '\n', grad_b)
	return proximal(np.array([a - step * grad_a, b - step * grad_b]), lam, step) 
		
def proximal_gradient_descent(x,y, epsilon = 0.001, lam=0):
	a,b = A,B
	losses = []
	prev_loss = sys.maxsize
	curr_loss = 0
	steps = 0
	while np.abs(curr_loss - prev_loss) >  epsilon:
		losses += [loss(a,b,x,y)]
		a,b = proximal_grad_step(a,b,x,y, 0.01, lam)
		steps += 1
		prev_loss = curr_loss
		curr_loss = loss(a,b,x,y)
	return a,b,losses, steps
	

xx = np.linspace(min(x), max(x))
a,b, losses, steps_count = proximal_gradient_descent(x,y, 0.001, 3.1)
print('Steps count for PGD: {}\nThe loss was {}'.format(steps_count, losses[-1]))
plt.figure(1)
plt.scatter(x,y,c='b')
plt.plot(xx, a*xx+b, 'r', label='PGD')	
plt.figure(2)
plt.plot(np.arange(len(losses)), losses, label='Loss for PGD')
plt.legend()


a,b, losses, steps_count = gradient_descent(x,y)
print('Steps count for GD: {}\nThe loss was {}'.format(steps_count, losses[-1]))
plt.figure(1)
plt.scatter(x,y,c='b')
plt.plot(xx, a*xx+b, 'g', label='GD')	
plt.legend()
plt.figure(2)
plt.plot(np.arange(len(losses)), losses, label='Loss for GD')
plt.legend()
plt.show()




