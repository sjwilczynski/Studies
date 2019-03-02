#
#eigs_image.py
#

import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import numpy as np
import numpy.linalg as LA
 

def reconstr_matrix(U,D,V,k):

    rec_mat=np.dot(U[:,:k],np.dot(D[:k,:k],V[:k,:]))
    
    return rec_mat
    
    
#wczytaj obrazek
img3=mpimg.imread("baloons3.png")   


#wezmy tylko jeden kanal
M=img3[:,:,0]

#glowna czesc programu: wykonaj svd
U,d,V=LA.svd(M)

#d jest wektorem, zamieniamy w macierz diagonalna
D=np.diag(d)
    
ile=5;
nr=1;

how_much_rec=5;

rr=reconstr_matrix(U,D,V,how_much_rec) 

error_x = []
error_y = []
eigen_sum = []

plt.figure(1);

for i in range(0,ile):
    for j in range(0, ile):
        plt.subplot(ile,ile,nr);
        nr=nr+1
        #zrekonstruuj macierz uzywajac how_much_rec wektorow wlasnych
        rr=reconstr_matrix(U,D,V,how_much_rec)   
        #liczymy bledy     
        error_x.append(how_much_rec)
        err = ((M-rr)**2).sum();
        error_y.append(err)
        eigen_sum.append((d[how_much_rec:len(d)]**2).sum())
        #rysuj rr
        plt.imshow(rr, cmap=plt.get_cmap('gray'))
        plt.title("ile = " + str(how_much_rec))
        plt.axis('off')
        how_much_rec+=2;
    
#osobny rys. z bledami
plt.figure(2)
plt.plot(error_x,np.zeros(len(error_x)), color='red');
plt.plot(error_x,error_y, color='blue')
#plt.figure(3)
plt.plot(error_x,np.zeros(len(error_x)), color='red');
plt.plot(error_x,eigen_sum, color='green')       
plt.show()
