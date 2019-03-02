import sys
import numpy as np
import time

factory_count = int(input())  # the number of factories
link_count = int(input())  # the number of links between factories
shortest_distances = np.array([sys.maxsize // 2] * factory_count ** 2).reshape((factory_count, factory_count))
np.fill_diagonal(shortest_distances, 0)
neighbor_distances = np.array([sys.maxsize // 2] * factory_count ** 2).reshape((factory_count, factory_count))
np.fill_diagonal(neighbor_distances, 0)
next_on_route = np.zeros((factory_count, factory_count), dtype=np.int32)
for i in range(link_count):
    factory_1, factory_2, distance = [int(j) for j in input().split()]
    shortest_distances[factory_1, factory_2] = distance
    shortest_distances[factory_2, factory_1] = distance
    neighbor_distances[factory_1, factory_2] = distance
    neighbor_distances[factory_2, factory_1] = distance
    next_on_route[factory_1, factory_2] = factory_2
    next_on_route[factory_2, factory_1] = factory_1
for u in range(factory_count):
    for v1 in range(factory_count):
        for v2 in range(factory_count):
            if shortest_distances[v1, v2] > shortest_distances[v1, u] + shortest_distances[u, v2] + 1:
                shortest_distances[v1, v2] = shortest_distances[v1, u] + shortest_distances[u, v2] + 1
                next_on_route[v1, v2] = u

print(shortest_distances, file=sys.stderr)
print(neighbor_distances, file=sys.stderr)
print(next_on_route, file=sys.stderr)

FACTORY_TYPE = "FACTORY"
TROOP_TYPE = "TROOP"
BOMB_TYPE = "BOMB"


class Factory:
    def __init__(self, id, owner, cyborgs, income):
        self.owner = owner
        self.cyborgs = cyborgs
        self.income = income
        self.id = id

    def score(self, target_id):
        d = shortest_distances[self.id, target_id]
        target_factory = factories[target_id]
        for bomb in bombs:
            if bomb.target_id == target_id and d <= bomb.remaining:
                return 0
        if d < 10 and self.id != target_id:
            target_factory_future = future_state[d + 1, target_id]
            if target_factory.owner == 0:
                if target_factory_future.owner == 0:
                    return (target_factory.income + 0.1) / (
                        d ** 2 * max(target_factory.cyborgs, 1))
                elif target_factory_future.owner == -1:
                    return (target_factory.income + 0.1) / (d ** 2 * 10)
            elif target_factory.owner == -1:
                if target_factory_future.owner == -1:
                    return (target_factory.income + 0.1) / (d ** 2 * 10)
            elif target_factory.owner == 1:
                if target_factory_future.owner == -1:
                    return (target_factory.income + 0.1) / (d ** 2 * max(target_factory_future.cyborgs, 1))
        return 0

    def inc_score(self):
        if self.income == 3:
            return 0
        d = sys.maxsize
        for i in range(len(factories)):
            d_new = neighbor_distances[self.id, i]
            if factories[i].owner == -1 and d_new < d:
                d = d_new
        for troop in troops:
            if troop.owner == -1 and troop.target_id == self.id:
                return 0
        my_inc = 0
        enemy_inc = 0
        for fac in factories:
            if fac.owner == 1:
                my_inc += fac.income
            elif fac.owner == -1:
                enemy_inc += fac.income
        diff = max(0, enemy_inc - my_inc)
        print("Diff in income: ", diff, file=sys.stderr)
        return (1 + diff) / (10 ** 1.6 * d)

    def perform_moves(self):
        msg = ""
        if future_state[5, self.id].owner == -1:
            return msg
        else:
            sparable_units = self.cyborgs - needed_for_defense[self.id]
            if sparable_units > 0:
                type = [('score', float), ('id', int)]
                moves = np.empty(len(factories) + 1, dtype=type)
                for i in range(len(factories)):
                    moves[i] = (self.score(i), i)
                moves[len(factories)] = (self.inc_score(), -1)
                moves = np.sort(moves)[::-1]
                print(str(self.id) + " MOVES: ", moves, file=sys.stderr)
                if moves[0][0] == 0:
                    mid = self.nearest_neighbor_income()
                    if mid != -1:
                        return 'MOVE {} {} {};'.format(self.id, mid, 10)
                for move in moves:
                    if sparable_units == 0 or move[0] == 0:
                        return msg
                    if move[1] == -1:
                        if sparable_units >= 10:
                            msg += 'INC {};'.format(self.id)
                            sparable_units -= 10
                            continue
                        else:
                            return msg + 'WAIT;'
                    target_id = move[1]
                    if move[1] == -1:
                        continue
                    target_factory = factories[target_id]
                    d = shortest_distances[self.id, target_id]
                    if target_factory.owner == -1:
                        cyb_count = max(sparable_units, 0)
                        t = Troop(0, 1, self.id, target_id, cyb_count, d + 1)
                        self.update_after_send_from(cyb_count)
                        self.update_after_send_to(t)
                        msg += 'MOVE {} {} {};'.format(self.id, next_on_route[self.id, target_factory.id],
                                                       cyb_count)
                        sparable_units -= cyb_count
                    elif target_factory.owner == 0:
                        cyb_count = min(sparable_units, target_factory.cyborgs + 1)
                        print(self.id, target_id, sparable_units, target_factory.cyborgs + 1, file=sys.stderr)
                        t = Troop(0, 1, self.id, target_id, cyb_count, d + 1)
                        self.update_after_send_from(cyb_count)
                        self.update_after_send_to(t)
                        msg += 'MOVE {} {} {};'.format(self.id, next_on_route[self.id, target_factory.id], cyb_count)
                        sparable_units -= cyb_count
                    else:
                        print(d + 1, target_id, file=sys.stderr)
                        cyb_count = min(max(future_state[d + 1, target_id].cyborgs + 1, 0), sparable_units)
                        t = Troop(0, 1, self.id, target_id, cyb_count, d + 1)
                        self.update_after_send_from(cyb_count)
                        self.update_after_send_to(t)
                        msg += 'MOVE {} {} {};'.format(self.id, next_on_route[self.id, target_factory.id],
                                                       cyb_count)
                        sparable_units -= cyb_count
            return msg

    def update_after_send_from(self, cyb_count):
        for i in range(1, 11):
            fs = future_state[i, self.id]
            if fs.owner == 1:
                if fs.cyborgs >= cyb_count:
                    fs.cyborgs -= cyb_count
                else:
                    future_state[i, self.id] = Factory(self.id, -1, cyb_count - fs.cyborgs, self.income)
            if fs.owner == -1:
                fs.cyborgs += cyb_count

    def update_after_send_to(self, troop):
        for i in range(troop.remaining, 11):
            fs = future_state[i, troop.target_id]
            if fs.owner == 1:
                fs.cyborgs += troop.cyborgs
            else:
                if troop.cyborgs <= fs.cyborgs:
                    fs.cyborgs -= troop.cyborgs
                else:
                    future_state[i, troop.target_id] = Factory(troop.target_id, 1, troop.cyborgs - fs.cyborgs,
                                                               fs.income)

    def send_all_to_neighbor(self):
        d = sys.maxsize
        best_id = -1
        for mid in my_factories:
            dist = neighbor_distances[mid, self.id]
            if dist < d and mid != self.id:
                d = dist
                best_id = mid
        return 'MOVE {} {} {};'.format(self.id, best_id, self.cyborgs + self.income)

    def nearest_neighbor_income(self):
        d = sys.maxsize
        best_id = -1
        for mid in my_factories:
            dist = neighbor_distances[mid, self.id]
            if dist < d and mid != self.id and factories[mid].income < 3:
                d = dist
                best_id = mid
        return best_id

    @classmethod
    def update(cls, fac, troop=None):
        if troop is None:
            if fac.owner != 0:
                return Factory(fac.id, fac.owner, fac.cyborgs + fac.income, fac.income)
            else:
                return Factory(fac.id, fac.owner, fac.cyborgs, fac.income)
        if troop.owner == fac.owner:
            return Factory(fac.id, fac.owner, fac.cyborgs + troop.cyborgs, fac.income)
        else:
            if troop.cyborgs <= fac.cyborgs:
                return Factory(fac.id, fac.owner, fac.cyborgs - troop.cyborgs, fac.income)
            else:
                return Factory(fac.id, troop.owner, troop.cyborgs - fac.cyborgs, fac.income)

    @classmethod
    def find_best(cls, source_id):
        score = 0
        best_id = -1
        for mid in my_factories:
            d = neighbor_distances[mid, source_id]
            target_factory = factories[mid]
            new_score = (target_factory.income + 0.1) / (d ** 2 * 10)
            if new_score > score:
                score = new_score
                best_id = mid
        return best_id


class Troop:
    def __init__(self, id, owner, source, target, cyborgs, remaining):
        self.owner = owner
        self.source_id = source
        self.target_id = target
        self.cyborgs = cyborgs
        self.remaining = remaining
        self.id = id


class Bomb:
    bombs_count = 2

    def __init__(self, id, owner, source, target, remaining):
        self.owner = owner
        self.source_id = source
        self.target_id = target
        self.remaining = remaining
        self.id = id

    @classmethod
    def is_target(cls, target_id):
        for bomb in bombs:
            if bomb.target_id == target_id:
                return True
        return False

    @classmethod
    def send_bomb(cls):
        if cls.bombs_count == 0:
            return 'WAIT;'
        beste_id = -1
        bestm_id = -1
        best_score = 0
        for efac_id in enemy_factories:
            efac = factories[efac_id]
            for mfac_id in my_factories:
                d = neighbor_distances[mfac_id, efac_id]
                if d < 10:
                    new_score = efac.income ** 3 * max(0, future_state[d + 1, efac_id].cyborgs) / d
                    if efac.income < 2 or Bomb.is_target(efac.id) or future_state[d + 1, efac_id].owner == 1:
                        new_score = 0
                    if new_score > best_score:
                        best_score = new_score
                        beste_id = efac_id
                        bestm_id = mfac_id
        if beste_id == -1:
            return 'WAIT;'
        else:
            target_factory = factories[beste_id]
            source_factory = factories[bestm_id]
            for bomb in bombs:
                if bomb.target_id == target_factory.id:
                    return 'WAIT;'
            cls.bombs_count -= 1
            return 'BOMB {} {};'.format(source_factory.id, target_factory.id)


future_state = np.empty((12, factory_count), dtype=Factory)
needed_for_defense = []
enemy_bomb = np.empty(2, dtype=Bomb)
enemy_bomb[0] = Bomb(-1, -1, -1, -1, -1)
enemy_bomb[1] = Bomb(-1, -1, -1, -1, -1)
enemy_bomb_count = 0


def create_future_state():
    future_state[0, :] = [fac for fac in factories]
    for troop in troops:
        if factories[troop.target_id].owner == 1 and troop.remaining < 6:
            needed_for_defense[troop.target_id] -= troop.owner * troop.cyborgs
    for i in range(len(factories)):
        needed_for_defense[i] -= factories[i].income
        needed_for_defense[i] = max(needed_for_defense[i], 0)

    for i in range(1, 11):
        for j in range(len(factories)):
            future_state[i, j] = Factory.update(future_state[i - 1, j])
        for troop in troops:
            if troop.remaining == i:
                target_fac = future_state[i, troop.target_id]
                future_state[i, troop.target_id] = Factory.update(target_fac, troop)


# game loop
turns = 0
while True:
    enemy_bomb_turn = 0
    needed_for_defense = np.zeros(factory_count, dtype=np.int32)
    start = time.time()
    turns += 1
    entity_count = int(input())  # the number of entities (e.g. factories and troops)
    factories = []
    troops = []
    bombs = []
    for _ in range(entity_count):
        entity_id, entity_type, arg_1, arg_2, arg_3, arg_4, arg_5 = input().split()
        entity_id = int(entity_id)
        arg_1 = int(arg_1)
        arg_2 = int(arg_2)
        arg_3 = int(arg_3)
        arg_4 = int(arg_4)
        arg_5 = int(arg_5)
        if entity_type == FACTORY_TYPE:
            factories.append(Factory(entity_id, arg_1, arg_2, arg_3))
        elif entity_type == TROOP_TYPE:
            troops.append(Troop(entity_id, arg_1, arg_2, arg_3, arg_4, arg_5))
        elif entity_type == BOMB_TYPE:
            bombs.append(Bomb(entity_id, arg_1, arg_2, arg_3, arg_4))
            if arg_1 == -1:
                enemy_bomb_turn += 1
                if enemy_bomb_count == 0:
                    id = Factory.find_best(arg_2)
                    d = neighbor_distances[arg_2, id]
                    enemy_bomb[enemy_bomb_count] = Bomb(entity_id, arg_1, arg_2, id, d)
                    enemy_bomb_count += 1
                elif enemy_bomb_turn == 1:
                    enemy_bomb[0].remaining -= 1

    create_future_state()
    print(needed_for_defense, file=sys.stderr)

    my_factories = [i for i in range(len(factories)) if factories[i].owner == 1]
    enemy_factories = [i for i in range(len(factories)) if factories[i].owner == -1]
    neutral_factories = [i for i in range(len(factories)) if factories[i].owner == 0]
    my_troops = [i for i in range(len(troops)) if troops[i].owner == 1]
    enemy_troops = [i for i in range(len(troops)) if troops[i].owner == -1]
    message = ''
    for fac_id in my_factories:
        fac = factories[fac_id]
        if enemy_bomb[0].target_id == fac_id and enemy_bomb[0].remaining == 1:
            message += fac.send_all_to_neighbor()
            enemy_bomb[0].remaining -= 1
        else:
            message += fac.perform_moves()
    if turns > 5:
        message += Bomb.send_bomb()
    end = time.time()
    b = enemy_bomb[0]
    print("bomb: ", b.target_id, b.remaining, file=sys.stderr)
    print('TIME: ', end - start, file=sys.stderr)
    print(message + 'MSG BOOM')