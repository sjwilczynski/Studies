#
# pca_example.py
#

import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
from sklearn import decomposition
from sklearn import datasets

# wczytajmy dane dotyczace win
wines = datasets.load_wine()

# same punkty (178 punktow 13 wymiarowych) sa w .data
points = wines.data

# klasy
wines_types = wines.target

# nazwy klas
wines_names = wines.target_names


# pca 3d
pca = decomposition.PCA(n_components=3)
points_reduced=points;
pca.fit(points_reduced)
points_reduced = pca.transform(points_reduced)


fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')

 
import numpy.random as rnd
 
for wt in range(0,wines_types.max()+1):
    points_tmp=points_reduced[wines_types == wt];
    xs = points_tmp[:,0]
    ys = points_tmp[:,1]
    zs = points_tmp[:,2]
    ax.scatter(xs, ys, zs, label=wines_names[wt])

ax.legend()
plt.show()
 
