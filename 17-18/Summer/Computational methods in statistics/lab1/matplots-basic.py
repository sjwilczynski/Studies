import numpy as np
import matplotlib.pyplot as plt
import matplotlib.image as mpimg

x = np.arange(-4,4, 0.1)
y1 = np.sin(x)
y2 = np.cos(x)

plt.figure(1)
plt.plot(x,y1, 'ro', label='Wykres sinus')
plt.plot(x,y2, 'go', label='Wykres cosinus')
plt.xlabel('os x')
plt.grid()
plt.legend() #to show label

#label is attached by legend to last subplot
plt.figure(2)
plt.subplot(2,1,1)
plt.plot(x,y1, 'ro', label='Wykres sinus')
plt.subplot(2,1,2)
plt.plot(x,y2, 'go', label='Wykres cosinus')
plt.ylabel('os y')
plt.legend()

#pictures
A = np.random.rand(50,50)
plt.figure(3)
plt.imshow(A, cmap='gray')

img = mpimg.imread('baloons3.png')
plt.figure(4)
plt.imshow(img)
print('Size of baloons image : {}'.format(img.shape))

plt.figure(5)
M = np.reshape(img[:,:,0], (img.shape[0],img.shape[0]))
plt.imshow(M, cmap='gray')

# SVD: M = UDV
U,d,V = np.linalg.svd(M)
D = np.diag(d)
print('Sizes of elements returned by SVD are {} {} {}'.format(U.shape,d.shape,V.shape))
k = 50
M2 = np.dot(U[:,:k], np.dot(D[:k, :k],V[:k,:]))
plt.imshow(M2, cmap = 'gray')

print('Conversion error for first {} eigenvalues : {}'.format(k,np.sum((M-M2)**2)))
print('Sum of unused eigenvalues is {}'.format(np.sum(d[k:]**2)))
