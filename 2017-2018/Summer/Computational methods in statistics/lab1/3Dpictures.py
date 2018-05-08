import numpy as np
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
from mpl_toolkits.mplot3d import Axes3D
from sklearn import decomposition

#3D pictures

fig = plt.figure(1)
ax = fig.add_subplot(111,projection='3d')
n=50
xs = np.random.rand(n)*20 - 15
ys = np.random.rand(n)*30 - 20
zs = np.random.rand(n)*40 - 30
ax.scatter(xs,ys,zs)


fig = plt.figure(2)
ax = fig.add_subplot(111,projection='3d')
n=70
for c, m in [('r','o'), ('b','^')]:
	xs = np.random.rand(n)*20 - 15
	ys = np.random.rand(n)*30 - 20
	zs = np.random.rand(n)*40 - 30
	ax.scatter(xs,ys,zs, c=c, marker=m)

#PCA

mean = [4,6,5]
cov = [[14,16,14],[16,21,14],[14,14,26]]
n = 500 
x,y,z = np.random.multivariate_normal(mean,cov,n).T
fig = plt.figure(3)
ax = fig.add_subplot(111,projection='3d')
ax.scatter(x,y,z)
M = np.array([x,y,z]).reshape(n,3)
pca = decomposition.PCA(n_components=2)
M2 = pca.inverse_transform(pca.fit_transform(M))
ax.scatter(M2[:,0], M2[:,1], M2[:,2])

plt.show()
