import heapq
from enum import Enum
import sys


class PriorityQueue:
    def __init__(self):
        self.elements = []

    def empty(self):
        return len(self.elements) == 0

    def push(self, item, priority):
        heapq.heappush(self.elements, (priority, item))

    def pop(self):
        return heapq.heappop(self.elements)


class Goal(Enum):
    Right = "RIGHT"
    Left = "LEFT"
    Up = "UP"
    Down = "DOWN"


class Field(Enum):
    Empty = 'E'
    Horizontal = 'H'
    Vertical = 'V'
    HV = 'HV'


class Player:
    def __init__(self, id, coord):
        #global max_depth, players, count, board, moves, player_count, my_id
        self.coord = coord
        self.id = id
        if player_count == 2:
            self.walls_left = 10
        else:
            self.walls_left = 6
        if id == 0:
            self.goal = Goal.Right
        elif id == 1:
            self.goal = Goal.Left
        elif id == 2:
            self.goal = Goal.Down


def heuristic(coord, goal):
    if goal == Goal.Right:
        return 8 - coord[0]
    elif goal == Goal.Left:
        return coord[0]
    elif goal == Goal.Down:
        return 8 - coord[1]
    elif goal == Goal.Up:
        return coord[1]


def is_goal_field(curr, goal):
    if goal == Goal.Right:
        return curr[0] == 8
    elif goal == Goal.Left:
        return curr[0] == 0
    elif goal == Goal.Up:
        return curr[1] == 0
    elif goal == Goal.Down:
        return curr[1] == 8


def is_valid_field(neigh):
    return 0 <= neigh[0] <= 8 and 0 <= neigh[1] <= 8


def is_wall_between(coord, neigh):
    #global max_depth, players, count, board, moves, player_count, my_id
    direction = coord_to_direction(neigh, coord)
    if direction == "UP":
        cond1 = (is_valid_field(coord) and board[coord[0]][coord[1]] in [Field.Horizontal, Field.HV])
        cond2 = (is_valid_field((coord[0] - 1, coord[1])) and board[coord[0] - 1][coord[1]] in [Field.Horizontal, Field.HV])
        return cond1 or cond2
    elif direction == "DOWN":
        cond1 = (is_valid_field((coord[0], coord[1] + 1)) and board[coord[0]][coord[1] + 1] in [Field.Horizontal, Field.HV])
        cond2 = (is_valid_field((coord[0] - 1, coord[1] + 1)) and board[coord[0] - 1][coord[1] + 1] in [Field.Horizontal, Field.HV])
        return cond1 or cond2
    elif direction == "RIGHT":
        cond1 = (is_valid_field((coord[0] + 1, coord[1] - 1)) and board[coord[0] + 1][coord[1] - 1] in [Field.Vertical, Field.HV])
        cond2 = (is_valid_field((coord[0] + 1, coord[1])) and board[coord[0] + 1][coord[1]] in [Field.Vertical, Field.HV])
        return cond1 or cond2
    elif direction == "LEFT":
        cond1 = (is_valid_field((coord[0], coord[1] - 1)) and board[coord[0]][coord[1] - 1] in [Field.Vertical, Field.HV])
        cond2 = (is_valid_field((coord[0], coord[1])) and board[coord[0]][coord[1]] in [Field.Vertical, Field.HV])
        return cond1 or cond2


def neighbors(coord):
    #global max_depth, players, count, board, moves, player_count, my_id
    n2 = []
    dirs = [(-1, 0), (0, -1), (1, 0), (0, 1)]
    n1 = [(coord[0] + x[0], coord[1] + x[1]) for x in dirs]
    for neigh in n1:
        if is_valid_field(neigh) and not is_wall_between(coord, neigh):
            n2.append(neigh)
    return n2


def coord_to_direction(pos_end, pos_start):
    x = pos_end[0] - pos_start[0]
    y = pos_end[1] - pos_start[1]
    coord_to_dir = {(-1, 0): "LEFT", (0, -1): "UP", (1, 0): "RIGHT", (0, 1): "DOWN"}
    return coord_to_dir[(x, y)]


def a_star(player):
    if is_goal_field(player.coord, player.goal):
        return [None, 0]
    path_blocked = True
    #print("Coords on start: ", player.coord, file=sys.stderr)
    pos = ()
    g = {}
    f = {}
    prev = {}
    visited = [[False] * 9 for _ in range(9)]
    f[player.coord] = heuristic(player.coord, player.goal)
    g[player.coord] = 0
    q = PriorityQueue()
    q.push(player.coord, f[player.coord])
    while not q.empty():
        current = q.pop()
        pos = current[1]
        if visited[pos[0]][pos[1]]:
            continue
        visited[pos[0]][pos[1]] = True
        if is_goal_field(pos, player.goal):
            path_blocked = False
            break
        for neighbor in neighbors(pos):
            next_field = neighbor
            if next_field in visited:
                continue
            if next_field not in g or g[pos] + 1 < g[next_field]:
                g[next_field] = g[pos] + 1
                f[next_field] = g[next_field] + heuristic(next_field, player.goal)
                q.push(next_field, f[next_field])
                prev[next_field] = pos

    score = 0
    # print(pos, file=sys.stderr)
    while prev[pos] != player.coord:
        score -= 1
        pos = prev[pos]
    #print("Coords on  exit:", player.coord, file=sys.stderr)
    # return (prev,coordsToDirection(pos,player.coord), score)
    if path_blocked:
        return ["BLOCKED", 0]
    else:
        return [coord_to_direction(pos, player.coord), score]


def next_player(id):
    #global max_depth, players, count, board, moves, player_count, my_id
    if player_count == 2:
        return (id + 1) % 2
    else:
        next_id = (id + 1) % 3
        # is player alive
        if players[next_id].walls_left == -1:
            return (next_id + 1) % 3
        else:
            return next_id


def make_move(move):
    #global max_depth, players, count, board, moves, player_count, my_id
    if move is None:
        return
    # move in direction
    if len(move) == 2:
        pid = move[0]
        direction = move[1]
        if direction == "RIGHT":
            players[pid].coord = (players[pid].coord[0]+1, players[pid].coord[1])
        elif direction == "LEFT":
            players[pid].coord = (players[pid].coord[0]-1, players[pid].coord[1])
        elif direction == "UP":
            players[pid].coord = (players[pid].coord[0], players[pid].coord[1]-1)
        elif direction == "DOWN":
            players[pid].coord = (players[pid].coord[0], players[pid].coord[1]+1)
    else:
        # put wall
        if board[move[0]][move[1]] == Field.Empty:
            board[move[0]][move[1]] = Field(move[2])
        else:
            board[move[0]][move[1]] = Field('HV')



def unmake_move(move):
    #global max_depth, players, count, board, moves, player_count, my_id
    if move is None:
        return
    opp_dirs = {"RIGHT": "LEFT", "LEFT": "RIGHT", "UP": "DOWN", "DOWN": "UP"}
    if len(move) == 2:
        new_move = [0, "UP"]
        new_move[0] = move[0]
        new_move[1] = opp_dirs[move[1]]
        make_move(new_move)
    else:
        if board[move[0]][move[1]] in [Field.Horizontal, Field.Vertical]:
            board[move[0]][move[1]] = Field.Empty
        elif move[2] == 'H':
            board[move[0]][move[1]] = Field.Vertical
        elif move[2] == 'V':
            board[move[0]][move[1]] = Field.Horizontal



def is_valid_move(move, curr_id):
    #print("In ism1: ", board[2][6], file=sys.stderr)
    #global max_depth, players, count, board, moves, player_count, my_id
    (x, y) = players[curr_id].coord
    #print("Before is_valid ",move, players[curr_id].coord, file=sys.stderr)
    if len(move) == 2:
        if move[0] != curr_id:
            return False
        dirs = {"LEFT": (-1, 0), "UP": (0, -1), "RIGHT": (1, 0), "DOWN": (0, 1)}
        x_new = x + dirs[move[1]][0]
        y_new = y + dirs[move[1]][1]
        #print("After is_valid: ",players[curr_id].coord, file=sys.stderr)
        return (x_new, y_new) in neighbors((x, y))
    else:
        # check wall crossing
        #print("In ism2: ", board[2][6], file=sys.stderr)
        if move[2] == 'H':
            if move[0] == 8:
                return False
            if board[move[0]][move[1]] in [Field.Horizontal, Field.HV]:
                return False
            if move[0] > 0 and board[move[0] - 1][move[1]] in [Field.Horizontal, Field.HV]:
                return False
            if move[0] < 7 and board[move[0] + 1][move[1]] in [Field.Horizontal, Field.HV]:
                return False
            if move[1] > 0 and board[move[0] + 1][move[1] - 1] in [Field.Vertical, Field.HV]:
                return False
        else:
            if move[1] == 8:
                return False
            if board[move[0]][move[1]] in [Field.Vertical, Field.HV]:
                return False
            if move[1] > 0 and board[move[0]][move[1] - 1] in [Field.Vertical, Field.HV]:
                return False
            if move[1] < 7 and board[move[0]][move[1] + 1] in [Field.Vertical, Field.HV]:
                return False
            if move[0] > 0 and board[move[0] - 1][move[1] + 1] in [Field.Horizontal, Field.HV]:
                return False
        # check if a path for any player wasn't blocked
        for player in players:
            if is_alive(player):
                #print(player.coord, player.walls_left, file=sys.stderr)
                if a_star(player)[0] == "BLOCKED":
                    return False
    #print("MOVE OK: ",move, players[curr_id].coord, sys.stderr)
    return True


def is_alive(player):
    return player.walls_left != -1


def compute_score():
    #global max_depth, players, count, board, moves, player_count, my_id
    scores = [0] * player_count
    for player in players:
        if is_alive(player):
            scores[player.id] = a_star(player)[1]  # +player.walls_left
    #print("Scores: ",scores,file=sys.stderr)
    return 2 * scores[my_id] - sum(scores)


def find_best_move(curr_id, alpha, beta, depth, move):
    make_move(move)
    if depth == max_depth:
        score = compute_score()
        #print("Positions: ", players[0].coord, players[1].coord, players[2].coord, file=sys.stderr)
        #print("Move and scores: ",move, a_star(players[0]), a_star(players[1]), a_star(players[2]),file=sys.stderr)
        unmake_move(move)
        #print("Positions: ", players[0].coord, players[1].coord, players[2].coord, file=sys.stderr)
        return [score, None]
    best_move = None
    if curr_id == my_id:
        v = -sys.maxsize
        for next_move in moves:
            #print("Before: ", next_move, players[curr_id].coord, file=sys.stderr)
            if is_valid_move(next_move, curr_id):
                new_v = find_best_move(next_player(curr_id), alpha, beta, depth + 1, next_move)[0]
                #print("inside2: ", next_move, players[curr_id].coord, file=sys.stderr)
                if v < new_v:
                    v = new_v
                    best_move = next_move
                    #print(best_move, v, file=sys.stderr)
                alpha = max(v, alpha)
                if alpha >= beta:
                    break
            #print("After: ", next_move, players[curr_id].coord, file=sys.stderr)
        unmake_move(move)
        return [v, best_move]
    else:
        v = sys.maxsize
        for next_move in moves:
            if is_valid_move(next_move, curr_id):
                v = min(v, find_best_move(next_player(curr_id), alpha, beta, depth + 1, next_move)[0])
                beta = min(v, beta)
                if alpha >= beta:
                    break
        unmake_move(move)
        return [v, best_move]


if __name__ == '__main__':

    max_depth = 2
    count = 0
    width, height, player_count, my_id = [int(i) for i in input().split()]
    board = [[Field.Empty] * width for _ in range(height)]
    players = []
    moves = [[id, dir] for id in range(player_count) for dir in ["RIGHT", "LEFT", "UP", "DOWN"]] + [[x, y, ori] for x in
                                                                                                    range(width) for y
                                                                                                    in range(height) for
                                                                                                    ori in ['V']]
    # initializing
    for i in range(player_count):
        x, y, walls_left = [int(j) for j in input().split()]
        players.append(Player(i, (x, y)))
    wall_count = int(input())
    for i in range(wall_count):
        wall_x, wall_y, wall_orientation = input().split()
        wall_x = int(wall_x)
        wall_y = int(wall_y)
        if board[wall_x][wall_y] == Field.Empty:
            board[wall_x][wall_y] = Field(wall_orientation)
        else:
            board[wall_x][wall_y] = Field('HV')
    result = find_best_move(my_id, -sys.maxsize, sys.maxsize, 0, None)[1]
    if len(result) == 2:
        print(result[1])
    else:
        print(result[0],result[1],result[2], sep=" ")

    # game loop
    while True:
        for i in range(player_count):
            x, y, walls_left = [int(j) for j in input().split()]
            players[i].coord = (x, y)
            players[i].walls_left = walls_left
        wall_count = int(input())
        for i in range(wall_count):
            # wall_x: x-coordinate of the wall
            # wall_y: y-coordinate of the wall
            # wall_orientation: wall orientation ('H' or 'V')
            wall_x, wall_y, wall_orientation = input().split()
            wall_x = int(wall_x)
            wall_y = int(wall_y)
            if board[wall_x][wall_y] == Field.Empty:
                board[wall_x][wall_y] = Field(wall_orientation)
            else:
                board[wall_x][wall_y] = Field('HV')


        # action: LEFT, RIGHT, UP, DOWN or "putX putY putOrientation" to place a wall
        result = find_best_move(my_id, -sys.maxsize, sys.maxsize, 0, None)[1]
        if len(result) == 2:
            print(result[1])
        else:
            print(result[0], result[1], result[2], sep=" ")

