import sys

import matplotlib.pyplot as plt
import numpy as np


T = 1000
start_ind = 0
F = np.eye(4)
delta_t = 0.1
F[0,2] = delta_t
F[1,3] = delta_t
Q = 0.001*np.eye(4)
R = np.array([1,0,0,50]).reshape(2,2)
B = np.array([0, -0.5*delta_t**2,0,-delta_t]).reshape(4,1)
u = 9.8
H = np.array([1,0,0,0,0,1,0,0]).reshape(2,4)
def next_x(X,F,B,u,Q):
	w = np.random.multivariate_normal(np.zeros(4), Q, 1).reshape(4,1)
	return np.dot(F,X) + np.dot(B,u) + w
	
	
X = np.array([0,50000, 10, 50]).reshape(4,1)
Y = np.array([0,100]).reshape(2,1)

xs = np.empty((4,T))
for i in range(T):
	xs[:,i] = X.ravel()
	X = next_x(X,F,B,u,Q)
	
err = np.random.multivariate_normal(np.zeros(2), R, T).T
ys = np.dot(H,xs) + err
print(xs.shape, ys.shape)


plt.figure()
plt.scatter(xs[0,start_ind:],xs[1,start_ind:],c='r',s=3, label='True values')
plt.scatter(ys[0,start_ind:],ys[1,start_ind:],c='b',s=3, label='Observed values')

#kalman filter

kalman_xs = np.zeros((4,T),dtype=np.float64)
kalman_x = np.array([0,50000, 10, 50],dtype=np.float64).reshape(4,1)
kalman_y = np.zeros((2,1),dtype=np.float64)
kalman_xs[:,0] = kalman_x.ravel()
kalman_cov = np.zeros((4,4),dtype=np.float64)
kalman_s = np.zeros((2,2),dtype=np.float64)
kalman_k = np.zeros((4,2),dtype=np.float64)
#print('Lots of shapes y:{} s:{} k:{} cov:{} x:{}'.format(kalman_y.shape, kalman_s.shape, kalman_k.shape, kalman_cov.shape, kalman_x.shape))

for i in range(T-1):
	#filtering step
	kalman_y = ys[:,i, None] - np.dot(H, kalman_x)
	kalman_s = R + np.dot(np.dot(H,kalman_cov),H.T)
	kalman_k = np.dot(np.dot(kalman_cov, H.T), np.linalg.inv(kalman_s))
	kalman_x += np.dot(kalman_k, kalman_y)
	kalman_cov -= np.dot(np.dot(kalman_k, H), kalman_cov)
	print(np.sum(np.abs(kalman_s)), np.sum(np.abs(kalman_cov)), np.sum(np.abs(kalman_k)), np.sum(np.abs(kalman_y)), np.sum(np.abs(np.linalg.inv(kalman_s))))
	#prediction step
	kalman_x = np.dot(F,kalman_x) + np.dot(B,u)
	kalman_cov = np.dot(np.dot(F,kalman_cov),F.T) + Q
	kalman_xs[:,i+1] = kalman_x.ravel()
	#print('Lots of shapes y:{} s:{} k:{} cov:{} x:{}'.format(kalman_y.shape, kalman_s.shape, kalman_k.shape, kalman_cov.shape, kalman_x.shape))
	
	
err_kal = np.sum((xs[:2,:]-kalman_xs[:2,:])**2,axis=0)
err_y = np.sum((xs[:2,:]-ys)**2,axis=0)

print('Mean squared error for real and observed: {}'.format(np.mean(np.sum((xs[:2,:]-ys)**2,axis=0))))
print('Mean squared error for real and kalman: {}'.format(np.mean(np.sum((xs[:2,:]-kalman_xs[:2,:])**2,axis=0))))

plt.scatter(kalman_xs[0,start_ind:], kalman_xs[1,start_ind:], c='g', s=3, label='Values from kalman filter')
plt.legend()

plt.figure(2)
plt.plot(np.arange(T), err_kal, label = 'err_kal')
plt.plot(np.arange(T), err_y, label = 'err_y')
plt.legend()
plt.show()	

	
