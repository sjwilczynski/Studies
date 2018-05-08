#
# class_svm.py
# 

import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets.samples_generator import make_blobs
from sklearn.cluster import KMeans
from sklearn import svm

# parametry rozkladu normalnego 2d

normal1_mean=[4, 6]

normal1_cov = [[1.5,2],
               [2,4]];

               
ile_pktow1 = 400

# pkty z rozkladu N(normal1_mean , normal1_cov)
x1,y1 = np.random.multivariate_normal(normal1_mean , normal1_cov, ile_pktow1).T


#inny rozklad normalny 2d
normal2_mean=[3, 8]

normal2_cov = [[4,1.5],
               [1.5,1]];
               
ile_pktow2 = 400

x2,y2 = np.random.multivariate_normal(normal2_mean , normal2_cov, ile_pktow2).T

plt.figure(1)


# do uczenia wezmieny tylko czesc 70 procent
ile1=int(0.7 * ile_pktow1)
ile2=int(0.7 * ile_pktow2)

# wspolrzedne x do jednego wektora, tak samo y

X_train=np.concatenate((x1[:ile1],x2[:ile2]))
Y_train=np.concatenate((y1[:ile1],y2[:ile2]))

# a teraz do jednej macierzy (tak wymaga svm)
points_train =np.zeros((ile1+ile2,2))
points_train[:,0]=X_train
points_train[:,1]=Y_train 

#plt.scatter(x1, y1,  color='blue' )
#plt.scatter(x2, y2,  color='red' )


# klasy powyzszych punktow ( zera i jedynki)
classes_train = np.ones(ile1+ile2,dtype='int')
classes_train[:ile1]=0;

# narysujmy punkty, na ktorych bedziemy "sie uczyc"
plt.scatter(X_train, Y_train, c=classes_train, alpha=0.25)

print("Kliknij w obrazek..")
plt.waitforbuttonpress()

# pozostale punkty - na nich bedziemy testowac

X_validate=np.concatenate((x1[ile1:],x2[ile2:]))
Y_validate=np.concatenate((y1[ile1:],y2[ile2:]))

# de facto znamy prawdziwe klasy
# (to tylko po to, by pozniej sprawdzic jak dobrze zostaly sklasyfikowane):
classes_validate = np.ones(len(X_validate),dtype='int')
#change this to something normal
print(-ile1, classes_validate[:len(x1)-ile1]) 
print(classes_validate[:-ile1])
classes_validate[:len(x1)-ile1] = np.zeros(len(x1)-ile1,dtype='int')


points_validate =np.zeros((len(X_validate),2))
points_validate[:,0]=X_validate
points_validate[:,1]=Y_validate
 
# obiekt SVM
clf = svm.SVC(kernel='linear')
clf.fit(points_train, classes_train)

# wspolczynniki hiperplaszczyzny (u nas: 2d = prosta)
coefficients = clf.coef_

w = clf.coef_[0]
a = -w[0] / w[1]
xx = np.linspace(X_train.min(), X_train.max())
b=- (clf.intercept_[0]) / w[1]

yy = a * xx +b

# narysujmy prosta klasyfikujaca
plt.plot(xx,yy, 'g-')


print("Kliknij w obrazek..")
plt.waitforbuttonpress()

# klasyfikuj punkty points_validate
classes_predicted = clf.predict(points_validate)

# i je narysuj 
plt.scatter(X_validate,Y_validate, c=classes_predicted)

print("classes_predicted = ", classes_predicted.shape)
print("classes_validate = ", classes_validate.shape)


# teraz policzymy classification rate (i narysujmy zle sklasyfikowane)

rate=0;
nr=0;
for cl_p,cl_v in zip(classes_predicted,classes_validate):
    if(cl_p==cl_v):
        rate=rate+1
    else:
        print(" ZLY : ",points_validate[nr,0],points_validate[nr,1])
        plt.scatter(points_validate[nr,0],points_validate[nr,1], color='red', alpha=0.27, s=70)
    nr=nr+1
        
rate2=rate/len(classes_predicted)
print("wszystkich = %d, dobrze = %d " % (len(classes_predicted), rate))
print("classification rate = ", rate2)


plt.show() 
 
