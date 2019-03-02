import numpy as np
import sys
import matplotlib.pyplot as plt

#Estimating PI
# first way
ys = []
n = 1000
u1 = np.random.uniform(0,1,n)**2
u2 = np.random.uniform(0,1,n)**2
ys = 4*(u1+u2 <= 1)
	
means = np.cumsum(ys)/np.arange(1, n+1, dtype=np.float64)
plt.figure(1)
plt.scatter(range(n), means, s=1)
plt.axhline(y=np.pi, xmin=0, xmax=n, linewidth=2, color = 'k')


k = 500
u1 = np.random.uniform(0,1,k*n)**2
u2 = np.random.uniform(0,1,k*n)**2
ys = 4*(u1+u2 <= 1)
res = []
for i in range(k):
	res += [np.mean(ys[i*n:(i+1)*n])]
print('First way\nMean: {}'.format(np.mean(res)))
print('Standard deviation: {}'.format(np.std(res)))
print('Variance: {}'.format(np.var(res)))


# second way
ys = []
u1 = np.random.uniform(0,1,n)**2
ys = 4*np.sqrt(1-u1)
	
means = np.cumsum(ys)/np.arange(1, n+1, dtype=np.float64)
plt.figure(2)
plt.scatter(range(n), means, s=1)
plt.axhline(y=np.pi, xmin=0, xmax=n, linewidth=2, color = 'k')


u1 = np.random.uniform(0,1,n*k)**2
ys = 4*np.sqrt(1-u1)
res = []
for i in range(k):
	res += [np.mean(ys[i*n:(i+1)*n])]
print('Second way\nMean: {}'.format(np.mean(res)))
print('Standard deviation: {}'.format(np.std(res)))
print('Variance: {}'.format(np.var(res)))

#d=20
#plt.figure(3)
#plt.plot(np.arange(1,d), [np.pi**i / (np.prod(np.arange(1,i)* 2**(2*i))) for i in range(1,d)]) 



#d-dimensional sphere
d=15
u1 = np.random.uniform(0,1,(d,n,k))**2
ys = u1.sum(axis=0) <= 1
res = 2**d * np.mean(ys, axis=0)
print(np.mean(res))
print(np.std(res))
print(np.pi**(d/2) / np.prod(np.arange(1,d//2+1)))

# Gibbs for knapsack problem

K=2000
n=100
w = np.arange(n)**2
x = np.array([0]*n)
beta=100
iterations = 100



for i in range(iterations):
	j = np.random.randint(0,n)
	y = np.array(x)
	y[j] = 1 - y[j]
	if np.dot(y,w) <= K:
		alpha = np.exp(-beta*w[j]) if x[j]==1 else 1
		if np.random.uniform(0,1) < alpha:
			x[j] = 1 - x[j]


print(np.dot(w,x))
print(np.sum(x))

#plt.show()
