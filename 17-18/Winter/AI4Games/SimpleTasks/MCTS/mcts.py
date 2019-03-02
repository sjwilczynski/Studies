import sys
import math

class Vertex:
    def __init__(self, score=0, n_visits=0):
        self.score = score
        self.n_visits = n_visits
        self.children = {}

def ucb1_score(parent_node, child_node, constant):
    return child_node.score/child_node.n_visits + constant*math.sqrt(math.log(parent_node.n_visits)/child_node.n_visits)

def find_best_playouts(root,c):
    curr_node = root
    result = ''
    while curr_node.children:
        best_move = ''
        best_score = -sys.maxsize
        for (move, child) in curr_node.children.items():
            new_score = ucb1_score(curr_node, child, c)
            if new_score > best_score or (new_score==best_score and move < best_move):
                best_move = move
                best_score = new_score
        result += best_move
        curr_node = curr_node.children[best_move]
    return result


def build_tree(playout, score, root):
    curr_node = root
    while playout != '':
        curr_node.score += score
        curr_node.n_visits += 1
        move = playout[0]
        if move in curr_node.children:
            curr_node = curr_node.children[move]
            playout = playout[1:]
        else:
            curr_node.children[move] = Vertex(score, 1)
            return

if __name__ == '__main__':

    root = Vertex()
    n, c = input().split()
    n = int(n)
    c = float(c)
    for i in range(n):
        playout, score = input().split()
        score = float(score)
        build_tree(playout, score, root)

    #print(ucb1_score(root, root.children['a'], c), ucb1_score(root, root.children['b'], c))
    #print(root.children['b'].children, file=sys.stderr)
    #node = root.children['a']
    #print( node.children['a'].score, node.children['a'].n_visits, node.children['b'].score, node.children['b'].n_visits, file=sys.stderr)
    #print( ucb1_score(node, node.children['a'], c), ucb1_score(node, node.children['b'], c), file=sys.stderr)
    print(find_best_playouts(root,c))
