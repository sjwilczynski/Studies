
# coding: utf-8

# In[71]:

import sys
import matplotlib.pyplot as plt
import numpy as np
#get_ipython().magic(u'pylab inline')


T = 100
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
dot_size = 10 #for plotting
custom_figsize = (15,7)
print('F:\n{}\nB:\n{}\nu:\n{}\nQ:\n{}\nR:\n{}\nH:\n{}\n'.format(F,B,u,Q,R,H))


# The model is:
# $$
# X_k = FX_{k-1} + Bu + w_k, \text{where w_k from N(0,Q)} \\
# Y_k = HX_{k} + v_k, \text{where v_k from N(0,R)}
# $$
# 
# Kalman filter:
# 1. Initialization
#     * $\hat{X}_{1|0} = \mu_0$
#     * $\Sigma_{1|0} = \Sigma_0$
# 2. Prediction
#     * $\hat{X}_{k|k-1} = F\hat{X}_{k-1|k-1} + BU$
#     * $\Sigma_{k|k-1} = F\Sigma_{k-1|k-1}F^T + Q$
# 3. Filtration
#     * $\hat{Y}_k = Y_k - H\hat{X}_{k|k-1}$
#     * $S_k = R + H\Sigma_{k|k-1}H^T$
#     * $K_k = \Sigma_{k|k-1}H^TS_k^{-1}$
#     * $\hat{X}_{k|k} = \hat{X}_{k|k-1} + K_k\hat{Y}_k$
#     * $\Sigma_{k|k} = \Sigma_{k|k-1} - K_kH\Sigma_{k|k-1}$

# In[72]:

def mse(v1,v2):
    return np.mean(np.sum((v1-v2)**2,axis=0))


def generate_trajectory(T, starting_point):
    X = np.array(starting_point).reshape(4,1)
    Y = np.array(starting_point[:2]).reshape(2,1)

    xs = np.empty((4,T))
    for i in range(T):
        xs[:,i, None] = np.array(X)
        w = np.random.multivariate_normal(np.zeros(4), Q, 1).reshape(4,1)
        X = np.dot(F,X) + np.dot(B,u) + w

    err = np.random.multivariate_normal(np.zeros(2), R, T).T
    ys = np.dot(H,xs) + err
    return xs,ys
    
def kalman_filter(ys, starting_point, Q, R):
    kalman_xs = np.zeros((4,T),dtype=np.float64)
    kalman_xs[:,0] = np.array(starting_point,dtype=np.float64)
    kalman_xs_filtered = np.zeros((4,T),dtype=np.float64)
    kalman_xs_filtered[:,0] = np.array(starting_point,dtype=np.float64)
    kalman_covs = np.zeros((4,4,T),dtype=np.float64)
    kalman_covs_filtered = np.zeros((4,4,T),dtype=np.float64)
    kalman_covs[:,:,0] = np.zeros_like(Q)
    kalman_covs_filtered[:,:,0] = np.zeros_like(Q)
    kalman_s = np.zeros((2,2),dtype=np.float64)
    kalman_k = np.zeros((4,2),dtype=np.float64)
    kalman_y = np.zeros((2,1),dtype=np.float64)
    
    kalman_x = np.array(kalman_xs[:,0]).reshape(4,1)
    kalman_cov = np.array(kalman_covs[:,:,0])

    for i in range(1,T):
        #prediction step
        kalman_x = np.dot(F,kalman_x) + np.dot(B,u)
        kalman_cov = np.dot(np.dot(F,kalman_cov),F.T) + Q
        
        #update #in our notation X_{i|i-1}
        kalman_xs[:,i, None] = np.array(kalman_x)
        kalman_covs[:,:,i] = np.array(kalman_cov)
        
        #filtering step
        kalman_y = ys[:,i, None] - np.dot(H, kalman_x)
        kalman_s = R + np.dot(np.dot(H,kalman_cov),H.T)
        kalman_k = np.dot(np.dot(kalman_cov, H.T), np.linalg.inv(kalman_s))
        kalman_x += np.dot(kalman_k, kalman_y)
        kalman_cov -= np.dot(np.dot(kalman_k, H), kalman_cov)
        # in our notation X{i|i}
        kalman_covs_filtered[:,:,i] = np.array(kalman_cov)
        kalman_xs_filtered[:,i,None] = np.array(kalman_x)
        
    return kalman_xs, kalman_covs, kalman_xs_filtered, kalman_covs_filtered
    
def rts_smoothing(ys, kalman_xs, kalman_covs, kalman_xs_filtered, kalman_covs_filtered):
    rts_xs = np.zeros_like(kalman_xs)
    rts_ls = np.zeros_like(kalman_covs)
    rts_covs = np.zeros_like(kalman_covs)
    rts_xs[:,T-1,None] = np.array(kalman_xs[:,T-1,None])
    rts_cov = kalman_covs[:,:,0]
    rts_l = np.zeros((4,4),dtype=np.float64)
    rts_x = np.array(kalman_xs[:,T-1,None])

    for i in range(T-2,-1,-1):
        cov_filtered = kalman_covs_filtered[:,:,i]
        cov = kalman_covs[:,:,i+1]
        rts_l = np.dot(cov_filtered, np.dot(F.T, np.linalg.inv(cov)))
        rts_x = kalman_xs_filtered[:,i,None] + np.dot(rts_l, rts_x - kalman_xs[:,i+1,None])
        rts_cov = cov_filtered + np.dot(rts_l, np.dot(rts_cov - cov, rts_l.T))
        
        #update - we will need it for EM
        rts_xs[:,i,None] = np.array(rts_x)
        rts_covs[:,:,i] = np.array(rts_cov)
        rts_ls[:,:,i] = np.array(rts_l)
        
    return rts_xs, rts_covs, rts_ls
    
def em_algorithm(ys, starting_point, max_steps, epsilon):
	
	Q = np.eye(4)#5*np.random.randn(4,4)
	R = np.eye(2)#5*np.random.randn(2,2)
	steps = 0
	diff = 1000
	while steps < max_steps: # and diff > epsilon 
		prev_Q = np.array(Q)
		prev_R = np.array(R)
		kalman_xs, kalman_covs, kalman_xs_filtered, kalman_covs_filtered = kalman_filter(ys, starting_point, Q, R)
		rts_xs, rts_covs, rts_ls = rts_smoothing(ys, kalman_xs, kalman_covs, kalman_xs_filtered, kalman_covs_filtered)
		
		# faster in a matrix notation??
		# E(x_k | Y_{1:T})
		expected_xs = rts_xs
		# E(x_kx_k^t| Y_{1:t})
		expected_dot = # zle: np.array([np.dot(x,x.T) + sigma for (x,sigma) in zip(expected_xs, rts_covs)])
		print(expected_dot.shape)
		ind = 1
		print(expected_dot[:,:,ind], '\n',  np.dot(expected_xs[:,ind,None], expected_xs[:,ind,None].T) + rts_covs[:,:,ind])
		# E(x_kx_{k+1}^T| Y_{1:T})
		#expected_dot_shift = 
		
		steps+=1
	
	
	return Q,R


# In[73]:

starting_point = [0,100, 10, 50] 


xs,ys = generate_trajectory(T,starting_point)


#kalman filter
kalman_start = np.random.multivariate_normal(starting_point, np.eye(4) * 10)
kalman_xs, kalman_covs, kalman_xs_filtered, kalman_covs_filtered = kalman_filter(ys, kalman_start, Q, R)
rts_xs, rts_covs, rts_ls = rts_smoothing(ys, kalman_xs, kalman_covs, kalman_xs_filtered, kalman_covs_filtered)

err_kal = np.sum((xs[:2,:]-kalman_xs[:2,:])**2,axis=0)
err_y = np.sum((xs[:2,:]-ys)**2,axis=0)
err_rts = np.sum((xs[:2,:]-rts_xs[:2,:])**2,axis=0)

print('Mean squared error for real and observed: {}'.format(mse(xs[:2,:],ys)))
print('Mean squared error for real and kalman: {}'.format(mse(xs[:2,:], kalman_xs[:2,:])))
print('Mean squared error for real and rts: {}'.format(mse(xs[:2,:], rts_xs[:2,:])))



em_algorithm(ys, starting_point, 1, 0.01)

plt.figure(1, figsize=custom_figsize)
plt.title('Trajectories')
plt.scatter(xs[0,start_ind:],xs[1,start_ind:],c='r',s=dot_size, label='True values')
plt.scatter(ys[0,start_ind:],ys[1,start_ind:],c='b',s=dot_size, label='Observed values')
plt.scatter(kalman_xs[0,start_ind:], kalman_xs[1,start_ind:], c='g', s=dot_size, label='Values from kalman filter')
plt.scatter(rts_xs[0,start_ind:], rts_xs[1,start_ind:], c='y', s=dot_size, label='Values from RTS')
plt.legend()

plt.figure(2, figsize=custom_figsize)
plt.title('Errors')
plt.plot(np.arange(T), err_kal, label = 'Squared error for kalman filter')
plt.plot(np.arange(T), err_y, label = 'Squared error for observed values')
plt.plot(np.arange(T), err_rts, label = 'Squared error for RTS')
plt.legend()
#plt.show()


# In[ ]:



