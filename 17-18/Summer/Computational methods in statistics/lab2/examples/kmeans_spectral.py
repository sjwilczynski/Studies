#
# kmeans_sepctral.py
#

import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets.samples_generator import make_blobs
from sklearn.cluster import KMeans
from sklearn.cluster import SpectralClustering
from sklearn.datasets import make_moons


# make moons = "Make two interleaving half circles"
points, cl_true = make_moons(200, noise=.05, random_state=0)

x=points[:,0]
y=points[:,1]

plt.figure(1)
plt.scatter(x,y, color='blue', s=50);
plt.title("Dane")

print("Kliknij w obrazek..")
plt.waitforbuttonpress()



plt.figure(2)
kmeans = KMeans(n_clusters=2)
kmeans.fit(points)
clusters_kmeans = kmeans.predict(points)
 

plt.scatter(x,y, c=clusters_kmeans, s=50);


# narysuj centra klastrow
centers=kmeans.cluster_centers_
plt.scatter(centers[:, 0], centers[:, 1], c='black', s=200, alpha=0.5);


plt.title("kmeans")
print("Kliknij w obrazek..")
plt.waitforbuttonpress()
plt.figure(3)

 

model = SpectralClustering(n_clusters=2, affinity='nearest_neighbors', assign_labels='kmeans')
labels = model.fit_predict(points)
plt.scatter(points[:, 0], points[:, 1], c=labels, s=50, cmap='viridis');
plt.title("Spectral Clustering")



plt.show()
