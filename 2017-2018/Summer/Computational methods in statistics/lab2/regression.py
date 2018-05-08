#
# different kinds of regression
# 

import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets.samples_generator import make_blobs
from sklearn import linear_model


normal1_mean=[4, 6]
normal1_cov = [[1.5,2],[2,4]];

num_points1 = 400

x1,y1 = np.random.multivariate_normal(normal1_mean , normal1_cov, num_points1).T
print(x1.shape, y1.shape)

clf = linear_model.LinearRegression()
clf.fit(x1.reshape(-1,1),y1)

a,b = clf.coef_[0], clf.intercept_
print(a,b)
plt.figure(1)
plt.scatter(x1,y1)
xx = np.linspace(min(x1),max(x1))
plt.plot(xx, a*xx+b, 'r')

## try different methods

num_points2 = num_points1//4
normal2_mean=[2, 15]

normal2_cov = [[1,0],
               [0,1]];

x2,y2 = np.random.multivariate_normal(normal2_mean , normal2_cov, num_points2).T
clfs = [linear_model.LinearRegression(),linear_model.Ridge(), linear_model.Lasso(alpha=1), linear_model.RANSACRegressor()]

x = np.concatenate((x1,x2))
y = np.concatenate((y1,y2))

for classifier in clfs:
	classifier.fit(x.reshape(-1,1),y)
	
colors = ['black','green','yellow','red']
color_to_name = {colors[0]:'Linear', colors[1]:'Ridge', colors[2]:'Lasso', colors[3]:'RANSAC'}


plt.figure(2)
plt.scatter(x,y, c='blue')
xx = np.linspace(min(x),max(x))
for classifier, color in zip(clfs, colors):
	plt.plot(xx, classifier.predict(xx.reshape(-1,1)), c=color, label=color_to_name[color])
	plt.title('Different kinds of regression - comparision with many outliers')
	plt.legend()
plt.show() 



 
