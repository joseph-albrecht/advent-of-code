from pprint import pprint
from functools import reduce
with open("input.real") as f:
    input = f.read()
    lines = input.splitlines()
    games = {i+1: [{C[1]: int(C[0]) for c in s.strip().split(",") for C in [c.strip().split(" ")]}
                   for s in l.split(":")[-1].split(";")]
             for i, l in enumerate(lines)
             }

c_max = {
    "red": 12,
    "green": 13,
    "blue": 14
}

powers = []
for g in games:
    power = {"red": 0, "green": 0, "blue": 0}
    for s in games[g]:
        for c in s:
            if s[c] > power[c]:
                power[c] = s[c]
    powers.append(reduce(lambda x, y: x * y, power.values()))

print(sum(powers))
