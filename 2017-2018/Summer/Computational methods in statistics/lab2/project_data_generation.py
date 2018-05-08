#
# generate data for the project
# 

import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D


def generate_data(n,d, ro, option, debug=False):
	mean = np.zeros(d)
	if option == 'independent':
		cov = np.identity(d)
		if debug:
			print('Mean and covaraince {} \n {}'.format(mean, cov))
		return np.random.multivariate_normal(mean, cov, n)
	if option == 'same_corr':
		cov = np.ones((d,d))*ro + np.identity(d) * (1-ro)
		if debug:
			print('Mean and covaraince {} \n {}'.format(mean, cov))
		return np.random.multivariate_normal(mean, cov, n)
	if option == 'auto_corr':
		cov = [[ro**np.abs(i-j) for i in range(d)] for j in range(d)]
		if debug:
			print('Mean and covaraince {} \n {}'.format(mean, cov))
		return np.random.multivariate_normal(mean, cov, n)
	
	raise Exception('Wrong option')
		
def generate_response(X,beta, debug = False):
	p = np.exp(np.dot(X,beta))/(1+np.exp(np.dot(X,beta)))
	if debug:
		print('Probability\n', p)
	return np.random.binomial(1, p).reshape(-1,1)
	
d = 3 #num_features
n = 30 #num_samples
	

beta = np.random.uniform(1,3,d)
X = generate_data(n,d,0.9,'independent', True)
Y = generate_response(X,beta, True)
print('Generated data \n--------------------------------------- \nX : {}, \n Beta : {}, \n Y : {} \n'.format(X,beta,Y))

n=100

options = ['independent','same_corr', 'auto_corr']
colors = ['r','g','b']
fig = plt.figure(1)
ax = fig.add_subplot(111,projection='3d')
color_to_opt = {colors[0]:options[0], colors[1]:options[1], colors[2]:options[2]}
Xs = [generate_data(n,d,0.9,options[0]), generate_data(n,d,0.9,options[1]), generate_data(n,d,0.9,options[2])]
for X, color in zip(Xs,colors):
	ax.scatter(X[:,0], X[:,1], X[:,2], c=color, marker='o', label = color_to_opt[color])
plt.legend()
plt.show()

 
