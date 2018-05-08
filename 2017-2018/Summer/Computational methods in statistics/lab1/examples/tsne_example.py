#
# tsne_example.py
#

import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
from sklearn.manifold import TSNE
from sklearn import decomposition
#or from sklearn.decomposition import PCA
from sklearn import datasets


ile=1000

#wczytajmy zestaw danych "digits"

literki = datasets.load_digits()

#wezmy tylko 'ile' punktow (wszystkich jest 115008)
points = literki.data[:ile]
literki_types = literki.target[:ile]
literki_names = literki.target_names

print("points.shape=",points.shape) 
print("Liczymy PCA..")
pca = decomposition.PCA(n_components=3)
points_reduced=points;
pca.fit(points_reduced)
points_reduced = pca.transform(points_reduced)

print("PCA ok.")
print("points.shape_reduced=",points_reduced.shape)
 


fig = plt.figure(1)
ax = fig.add_subplot(111, projection='3d')


 
import numpy.random as rnd
 
for wt in range(0,literki_types.max()+1):
    points_tmp=points_reduced[literki_types == wt];
    xs = points_tmp[:,0]
    ys = points_tmp[:,1]
    zs = points_tmp[:,2]
    ax.scatter(xs, ys, zs, label=literki_names[wt])
plt.title('PCA')
ax.legend()

print("Kliknij w obrazek..")
plt.waitforbuttonpress()
 
 
print("Liczymy t-SNE ...")
fig2 = plt.figure(2)
ax2 = fig2.add_subplot(111, projection='3d')
tsne = TSNE(n_components=3, random_state=0)
points_reduced_tsne = tsne.fit_transform(points)
print("t-SNE ok.")

print("points_reduced_tsne=",points_reduced_tsne.shape)

for wt in range(0,literki_types.max()+1):
    points_tmp=points_reduced_tsne[literki_types == wt];
    xs = points_tmp[:,0]
    ys = points_tmp[:,1]
    zs = points_tmp[:,2]
    ax2.scatter(xs, ys, zs, label=literki_names[wt])

plt.title('t-SNE')
ax2.legend()


plt.show()
 
