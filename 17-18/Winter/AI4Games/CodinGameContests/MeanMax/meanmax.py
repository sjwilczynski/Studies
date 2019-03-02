import math
import sys
import numpy as np


class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def distance(self, p):
        return math.sqrt((self.x - p.x) ** 2 + (self.y - p.y) ** 2)

    @staticmethod
    def dist_from_to(p, q):
        return math.sqrt((q.x - p.x) ** 2 + (q.y - p.y) ** 2)

    @staticmethod
    def find_opposite(my_reaper, wreck):
        vx = wreck.x - my_reaper.x
        vy = wreck.y - my_reaper.y
        return Point(my_reaper.x - vx, my_reaper.y - vy)


class Wreck(Point):
    def __init__(self, x, y, radius, water):
        super().__init__(x, y)
        self.water = water
        self.radius = radius

    def score(self, point):
        return self.water / self.distance(point)


class Unit(Point):
    def __init__(self, id, type, x, y, vx, vy, radius, mass, friction):
        self.type = type
        self.id = id
        super().__init__(x, y)
        self.vx = vx
        self.vy = vy
        self.radius = radius
        self.mass = mass
        self.friction = friction


class Tanker(Unit):
    def __init__(self, id, type, x, y, vx, vy, radius, mass, friction, water, capacity):
        super().__init__(id, type, x, y, vx, vy, radius, mass, friction)
        self.water = water
        self.capacity = capacity


class Looter(Unit):
    def __init__(self, id, type, playerid, x, y, vx, vy, radius, mass, friction):
        super().__init__(id, type, x, y, vx, vy, radius, mass, friction)
        self.playerid = playerid


class Reaper(Looter):
    def __init__(self, id, type, playerid, x, y, vx, vy, radius, mass, friction):
        super().__init__(id, type, playerid, x, y, vx, vy, radius, mass, friction)

    def adjust_throttle(self, wreck):
        d = np.array([wreck.x - self.x, wreck.y - self.y])
        acc = 2 * (d - np.array([self.vx, self.vy]))
        return self.mass * acc

    def score_from_others(self, wreck, wrecks):
        return sum([nwreck.water / nwreck.distance(wreck) ** 2 for nwreck in wrecks])

    def score(self, wreck, wrecks, i):
        if wreck.water == 0:
            return -sys.maxsize
        else:
            d = np.linalg.norm(np.array([wreck.x - self.x - self.vx, wreck.y - self.y - self.vy])) / 300
            return wreck.water / d + self.score_from_others(wreck,
                                                            wrecks[:i] + wrecks[(i + 1):]) / max(
                len(wrecks), 1)

    def find_best_move(self, wrecks):
        best_score = -sys.maxsize
        best_id = -1
        for i in range(len(wrecks)):
            wreck = wrecks[i]
            new_score = self.score(wreck, wrecks, i)
            if new_score > best_score:
                best_score = new_score
                best_id = i
        return best_id


class Destroyer(Looter):
    def __init__(self, id, type, playerid, x, y, vx, vy, radius, mass, friction):
        super().__init__(id, type, playerid, x, y, vx, vy, radius, mass, friction)

    def score_from_others(self, tanker, tankers):
        return sum([ntanker.water / ntanker.distance(tanker) ** 2 for ntanker in tankers])

    def score(self, tanker, tankers, my_reaper):
        if tanker.water < 2 or tanker.distance(Point(0, 0)) > 5000:
            return -sys.maxsize
        else:
            return tanker.water / Point.dist_from_to(my_reaper, tanker)  # + self.score_from_others(tanker, tankers[:i]+tankers[(i+1):])

    def find_best_move(self, tankers, reapers, my_reaper_id):
        best_score = -sys.maxsize
        best_id = -1
        for i in range(len(tankers)):
            tanker = tankers[i]
            new_score = self.score(tanker, tankers, reapers[my_reaper_id])
            if new_score > best_score:
                best_score = new_score
                best_id = i
        return best_id

    def grenade(self, rage, reapers, my_reaper_id, wrecks):
        for reaper in reapers:
            if self.distance(reaper) < 1900 and Point.dist_from_to(reaper, reapers[my_reaper_id]) > 1200 and rage > 60:
                for wreck in wrecks:
                    if reaper.distance(wreck) < wreck.radius and wreck.water >= 2:
                        a1 = np.sign(reaper.x)
                        a2 = np.sign(reaper.y)
                        print("SKILL " + str(reaper.x - a1) + ' ' + str(reaper.y - a2))
                        return True
        return False


class Doof(Looter):
    def __init__(self, id, type, playerid, x, y, vx, vy, radius, mass, friction):
        super().__init__(id, type, playerid, x, y, vx, vy, radius, mass, friction)
        self.count = 0

    def find_best_move(self, reapers, my_reaper_id):
        best_id = -1
        best_dist = sys.maxsize
        for i in range(len(reapers)):
            reaper = reapers[i]
            distance = self.distance(reaper)
            if i != my_reaper_id and distance < best_dist:
                best_dist = distance
                best_id = i
        return best_id

    def oil(self, rage, reapers, my_reaper_id, wrecks):
        if self.count > 0:
            self.count -= 1
        for reaper in reapers:
            if self.distance(reaper) < 1900 and Point.dist_from_to(reaper, reapers[my_reaper_id]) > 2000 and rage > 30:
                for wreck in wrecks:
                    if Point.dist_from_to(reaper, wreck) < wreck.radius and wreck.water > 2 and self.count == 0:
                        print("SKILL " + str(reaper.x) + ' ' + str(reaper.y))
                        self.count = 3
                        return True
        return False


# constans
OIL_TYPE = 6
TAR_TYPE = 5
WRECK_TYPE = 4
TANKER_TYPE = 3
DOOF_TYPE = 2
DESTROYER_TYPE = 1
REAPER_TYPE = 0
REAPER_FRICTION = 0.2
DOOF_FRICTION = 0.25
DESTROYER_FRICTION = 0.3
TANKER_FRICTION = 0.4

turns = 0
# game loop
while True:
    turns += 1
    reapers = []
    wrecks = []
    destroyers = []
    tankers = []
    doofs = []
    oils = []
    scores = [int(input()), int(input()), int(input())]
    rages = [int(input()), int(input()), int(input())]
    unit_count = int(input())
    for i in range(unit_count):
        unit_id, unit_type, player_id, mass, radius, x, y, vx, vy, extra, extra_2 = input().split()
        # print(unit_id, unit_type, player_id, mass, radius, x, y, vx, vy, extra, extra_2, file=sys.stderr)
        unit_id = int(unit_id)
        unit_type = int(unit_type)
        player_id = int(player_id)
        mass = float(mass)
        radius = int(radius)
        x = int(x)
        y = int(y)
        vx = int(vx)
        vy = int(vy)
        extra = int(extra)
        extra_2 = int(extra_2)

        if unit_type == OIL_TYPE:
            oils.append(Point(x, y))
        if unit_type == WRECK_TYPE:
            wrecks.append(Wreck(x, y, radius, extra))
        if unit_type == REAPER_TYPE:
            reapers.append(Reaper(unit_id, unit_type, player_id, x, y, vx, vy, radius, mass, REAPER_FRICTION))
            if player_id == 0:
                my_reaper_id = len(reapers) - 1
                my_reaper = reapers[-1]
        if unit_type == DESTROYER_TYPE:
            destroyers.append(Destroyer(unit_id, unit_type, player_id, x, y, vx, vy, radius, mass, DESTROYER_FRICTION))
            if player_id == 0:
                my_destroyer = destroyers[-1]
        if unit_type == TANKER_TYPE:
            tankers.append(
                Tanker(unit_id, unit_type, x, y, vx, vy, radius, mass, TANKER_FRICTION, extra, extra_2))
        if unit_type == DOOF_TYPE:
            doofs.append(Doof(unit_id, unit_type, player_id, x, y, vx, vy, radius, mass, DOOF_FRICTION))
            if player_id == 0:
                my_doof = doofs[-1]

    # skip all wrecks that are disabled by oil
    for oil in oils:
        for wreck in wrecks:
            if oil.distance(wreck) < 1000:
                wrecks.remove(wreck)

    wreck_id = my_reaper.find_best_move(wrecks)
    if turns < 7:
        print('WAIT')
    elif wreck_id == -1:
        print(0, 0, 100)
    else:
        wreck = wrecks[wreck_id]
        throttle = my_reaper.adjust_throttle(wreck)
        print(wreck.x, wreck.y, my_reaper.x, my_reaper.y, my_reaper.vx, my_reaper.vy, REAPER_FRICTION,
              wreck.distance(my_reaper), math.sqrt(my_reaper.vx ** 2 + my_reaper.vy ** 2),
              wreck.distance(my_reaper) / math.sqrt(my_reaper.vx ** 2 + my_reaper.vy ** 2), file=sys.stderr)
        d = np.array([my_reaper.x, my_reaper.y]) + throttle
        d = d.astype(np.int32)
        print(d[0], d[1], int(min(np.linalg.norm(throttle), 300)))

    if not my_destroyer.grenade(rages[0], reapers, my_reaper_id, wrecks):
        tanker_id = my_destroyer.find_best_move(tankers, reapers, my_reaper_id)
        if turns < 7:
            print('WAIT')
        elif tanker_id == -1:
            print(0, 0, 100)
        else:
            print(tankers[tanker_id].x, tankers[tanker_id].y, 300)

    if not my_doof.oil(rages[0], reapers, my_reaper_id, wrecks):
        reaper = reapers[my_doof.find_best_move(reapers, my_reaper_id)]
        print(reaper.x, reaper.y, 300)
