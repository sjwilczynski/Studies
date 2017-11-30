import sys
import math

class Vertex:
    def __init__(self, node, value):
        self.id = node
        self.value = value
        self.neighbors = []

    def __str__(self):
        tmp = str(self.id) + ', ' + str(self.value) + ': '
        for neigh in self.neighbors:
            tmp += str(neigh) + ' '
        return tmp

    def add_neighbor(self, neighbor):
        self.neighbors.append(neighbor)


class Tree:
    def __init__(self, depth, branching, leafs):
        self.counter = 0
        self.vertices = []
        #self.vertices.append(None)
        self.depth = depth
        self.branching = branching
        n = int((branching**(depth+1)-1)/(branching-1))
        for i in range(n):
            self.vertices.append(Vertex(i, None))
        for i in range(n-branching**depth):
            for j in range (1,branching+1):
                self.vertices[i].add_neighbor(branching*i+j)
        for i in range(1, branching**depth+1):
            self.vertices[-i].value = leafs[-i]

    def __str__(self):
        tmp = ''
        for vertex in self.vertices:
            tmp += str(vertex) +'\n'
        return tmp

    def alphabetaprun(self, node, depth, alpha, beta, is_max):
        self.counter += 1
        #alpha - best already explored option along path to root for max
        #beta - best already explored option along path to root for min
        #print(node)
        if depth == self.depth:
            return self.vertices[node].value
        if is_max:
            v = -sys.maxsize
            for neighbor in self.vertices[node].neighbors:
                #print(node, neighbor)
                v = max(v, self.alphabetaprun(neighbor, depth+1, alpha, beta, False))
                alpha = max(v, alpha)
                if alpha >= beta:
                    break
            return v
        else:
            v = sys.maxsize
            for neighbor in self.vertices[node].neighbors:
                v = min(v, self.alphabetaprun(neighbor, depth+1, alpha, beta, True))
                beta = min(v, beta)
                if alpha >= beta:
                    break
            return v

if __name__ == '__main__':
    depth, branching = [int(i) for i in input().split()]
    leafs = [int(i) for i in input().split()]
    T = Tree(depth,branching,leafs)
    #print(str(T))
    print(T.alphabetaprun(0,0,-sys.maxsize, sys.maxsize, True), T.counter)


