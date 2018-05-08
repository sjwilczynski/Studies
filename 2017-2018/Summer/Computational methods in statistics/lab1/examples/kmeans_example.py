#
# kmeans0.py
#

import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets.samples_generator import make_blobs
from sklearn.cluster import KMeans


# make_blobs = wylosuj 500 punktow z 4 centr.

points, cl_true = make_blobs(n_samples=500, centers=4, cluster_std=0.70, random_state=0)

x=points[:,0]
y=points[:,1]

plt.figure(1)
plt.scatter(x,y, color='blue', s=50);


print("Kliknij w obrazek..")
plt.waitforbuttonpress()

plt.figure(2)

# obiekt KMeans (potestuj rozne n_clusters)
kmeans = KMeans(n_clusters=6)

# wykonaj kmeans
kmeans.fit(points)
clusters_kmeans = kmeans.predict(points)

# narysuj punkty o kolorach wyzn. przez kmeans
plt.scatter(x,y, c=clusters_kmeans, s=50);

print("Kliknij w obrazek..")
plt.waitforbuttonpress()

# narysuj centra klastrow
centers=kmeans.cluster_centers_
plt.scatter(centers[:, 0], centers[:, 1], c='black', s=200, alpha=0.5);


plt.show()
