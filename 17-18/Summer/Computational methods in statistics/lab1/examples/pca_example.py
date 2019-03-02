#
# pca_example0.py
#

import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
from sklearn import decomposition
from sklearn import datasets

 
#parametry rozkladu normalnego 3d
normal_mean=[4, 6, 5]
normal_cov = [[14,16,114],
               [16,21,14],
               [14,14,26]]
               
ile_pktow = 500

#losuj punkty z rozkladu N(normal_mean, normal_cov), przypis je do x,y,z

x,y,z = np.random.multivariate_normal(normal_mean , normal_cov, ile_pktow).T

# 3d
fig = plt.figure(1)
ax = fig.add_subplot(111, projection='3d')
ax.scatter(x, y, z, color='blue' )

print("Kliknij w obrazek..")
plt.waitforbuttonpress()
 

#wrzucimy wszystkie punkty do jednej macierzy (tak chce wbudowana funkcja do PCA)
M=np.zeros([ile_pktow,3])

M[:,0]=x
M[:,1]=y
M[:,2]=z

#przygotuje obiekt pca, n_components=2 -> redukcja do 2d
pca = decomposition.PCA(n_components=2)
points_reduced=M;

#samo pca wykonywane jest tutaj:
pca.fit(points_reduced)
points_reduced = pca.transform(points_reduced)

#narysujmy zredukowane punkty (2d)
plt.figure(2)
plt.plot(points_reduced[:,0],points_reduced[:,1],'ro')


print("Kliknij w obrazek..")
plt.waitforbuttonpress()



fig2 = plt.figure(3)
ax2 = fig2.add_subplot(111, projection='3d')

#punkty 2d -> z powrotem do oryg. przestrzeni 3d

reconstr_points=pca.inverse_transform(points_reduced)
print("r shape", reconstr_points.shape)
xr=reconstr_points[:,0]
yr=reconstr_points[:,1]
zr=reconstr_points[:,2]

ax2.scatter(x, y, z, color='blue',alpha=0.1)
ax2.scatter(xr, yr, zr, color='red')

plt.show()
