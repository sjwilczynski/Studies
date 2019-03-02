import heapq

class PriorityQueue:
    def __init__(self):
        self.elements = []

    def empty(self):
        return len(self.elements) == 0

    def push(self, item, priority):
        heapq.heappush(self.elements, (priority, item))

    def pop(self):
        return heapq.heappop(self.elements)


class Vertex:
    def __init__(self, node):
        self.id = node
        self.neighbors = {}

    def __str__(self):
        tmp = str(self.id) + ': '
        for key,value in self.neighbors.items():
            tmp += '( ' + str(key) +', ' + str(value) +' ) '
        return tmp

    def add_neighbor(self, neighbor, weight):
        self.neighbors[neighbor] = weight


class Graph:
    def __init__(self, n):
        self.vertices = {}
        for i in range(n):
            self.vertices[i] = Vertex(i)

    def add_edge(self, frm, to, cost):
        self.vertices[frm].add_neighbor(to, cost)
        self.vertices[to].add_neighbor(frm, cost)

    def erase_edge(self, frm, to):
        del self.vertices[frm].neighbors[to]
        del self.vertices[to].neighbors[frm]

    def __str__(self):
        tmp = ''
        for vertex in self.vertices.values():
            tmp += str(vertex) +'\n'
        return tmp

    def a_star(self, start, goal, heuristic):
        g = {}
        f = {}
        visited = []
        f[start] = heuristic[start]
        g[start] = 0
        Q = PriorityQueue()
        Q.push(start, f[start])
        while not Q.empty():
            current = Q.pop()
            curr = current[1]
            fscore = current[0]
            if curr in visited:
                continue
            visited.append(curr)
            print(str(curr) + ' ' + str(fscore))
            if curr == goal: return
            for neighbor in self.vertices[curr].neighbors.items():
                next_curr = neighbor[0]
                weight = neighbor[1]
                if next_curr in visited:
                    continue
                if next_curr not in g or g[curr] + weight < g[next_curr]:
                    g[next_curr] = g[curr] + weight
                    f[next_curr] = g[next_curr] + heuristic[next_curr]
                    Q.push(next_curr, f[next_curr])

if __name__ == '__main__':
    import sys
    import math
    heuristic = []
    nodes, edges, start, goal = [int(i) for i in input().split()]
    G = Graph(nodes)
    for i in input().split():
        heuristic.append(int(i))
    for i in range(edges):
        x, y, c = [int(j) for j in input().split()]
        G.add_edge(x,y,c)
    G.a_star(start,goal,heuristic)
    #test Graph
    #print(str(G))
    #G.erase_edge(0,2)
    #print(str(G))


