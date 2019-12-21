# --- Day 3: Crossed Wires ---

# The gravity assist was successful, and you're well on your way to the Venus
# refuelling station. During the rush back on Earth, the fuel management
# system wasn't completely installed, so that's next on the priority list.

# Opening the front panel reveals a jumble of wires. Specifically, two wires
# are connected to a central port and extend outward on a grid. You trace the
# path each wire takes as it leaves the central port, one wire per line of
# text (your puzzle input).

# The wires twist and turn, but the two wires occasionally cross paths. To
# fix the circuit, you need to find the intersection point closest to the
# central port. Because the wires are on a grid, use the Manhattan distance
# for this measurement. While the wires do technically cross right at the
# central port where they both start, this point does not count, nor does a
# wire count as crossing with itself.

# For example, if the first wire's path is R8,U5,L5,D3, then starting from
# the central port (o), it goes right 8, up 5, left 5, and finally down 3:
# ...........
# ...........
# ...........
# ....+----+.
# ....|....|.
# ....|....|.
# ....|....|.
# .........|.
# .o-------+.
# ...........
# Then, if the second wire's path is U7,R6,D4,L4, it goes up 7, right 6, down
# 4, and left 4:

# ...........
# .+-----+...
# .|.....|...
# .|..+--X-+.
# .|..|..|.|.
# .|.-X--+.|.
# .|..|....|.
# .|.......|.
# .o-------+.
# ...........

# Here are a few more examples:

# R75,D30,R83,U83,L12,D49,R71,U7,L72 U62,R66,U55,R34,D71,R55,D58,R83 =
# distance 159 R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
# U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 = distance 135 What is the Manhattan
# distance from the central port to the closest intersection?

# --- Part Two ---

# It turns out that this circuit is very timing-sensitive; you actually need
# to minimize the signal delay.

# To do this, calculate the number of steps each wire takes to reach each
# intersection; choose the intersection where the sum of both wires' steps is
# lowest. If a wire visits a position on the grid multiple times, use the
# steps value from the first time it visits that position when calculating
# the total value of a specific intersection.

# The number of steps a wire takes is the total number of grid squares the
# wire has entered to get to that location, including the intersection being
# considered. Again consider the example from above:
# ...........
# .+-----+...
# .|.....|...
# .|..+--X-+.
# .|..|..|.|.
# .|.-X--+.|.
# .|..|....|.
# .|.......|.
# .o-------+.
# ...........
# In the above example, the intersection closest to the central port is
# reached after 8+5+5+2 = 20 steps by the first wire and 7+6+4+3 = 20 steps
# by the second wire for a total of 20+20 = 40 steps.

# However, the top-right intersection is better: the first wire takes only
# 8+5+2 = 15 and the second wire takes only 7+6+2 = 15, a total of 15+15 = 30
# steps.

# Here are the best steps for the extra examples from above:

# R75,D30,R83,U83,L12,D49,R71,U7,L72 U62,R66,U55,R34,D71,R55,D58,R83 = 610
# steps R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
# U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 = 410 steps What is the fewest
# combined steps the wires must take to reach an intersection?

def convert_relative_path_to_absolute(steps):
    def take_step(current_node, instruction):
        new_node = current_node[:]
        if instruction[0] == 'U':
            new_node[1] += int(instruction[1:])

        elif instruction[0] == 'D':
            new_node[1] -= int(instruction[1:])

        elif instruction[0] == 'L':
            new_node[0] -= int(instruction[1:])

        elif instruction[0] == 'R':
            new_node[0] += int(instruction[1:])

        return new_node

    absolute_nodes = [[0,0],]
    for step in steps:
        absolute_nodes.append(take_step(absolute_nodes[-1], step))

    return absolute_nodes

def detect_path_crossing(node_pair, node_list):
    xs = sorted([node_pair[0][0], node_pair[1][0]])
    ys = sorted([node_pair[0][1], node_pair[1][1]])

    x_stationary = False
    y_stationary = False
    
    if xs[0] == xs[1]:
        x_stationary = True

    elif ys[0] == ys[1]:
        y_stationary = True

    intersections = []

    for node1, node2 in zip(node_list[:-1], node_list[1:]):
        other_xs = sorted([node1[0], node2[0]])
        other_ys = sorted([node1[1], node2[1]])

        other_x_stationary = False
        other_y_stationary = False
    
        if other_xs[0] == other_xs[1]:
            other_x_stationary = True

        elif other_ys[0] == other_ys[1]:
            other_y_stationary = True

        if x_stationary and other_x_stationary:
            continue

        elif y_stationary and other_y_stationary:
            continue

        elif x_stationary and other_y_stationary:
            if xs[0] in range(other_xs[0], other_xs[1]) and \
               other_ys[0] in range(ys[0], ys[1]):
                
                intersections.append([xs[0], other_ys[1]])

        elif y_stationary and other_x_stationary:
            if ys[0] in range(other_ys[0], other_ys[1]) and \
               other_xs[0] in range(xs[0], xs[1]):
                intersections.append([other_xs[0], ys[1]])

        if [0,0] in intersections:
            intersections.remove([0,0])
    return intersections

def calc_manhattan_distance(coordinates):
    return abs(coordinates[0]) + abs(coordinates[1])
    

wire_one_path, wire_two_path = map(lambda x: x.split(','), open("day_three.input", 'r').read().split('\n'))

wire_one_nodes = convert_relative_path_to_absolute(wire_one_path)
wire_two_nodes = convert_relative_path_to_absolute(wire_two_path)

intersections = []
for nodes in zip(wire_one_nodes[:-1], wire_one_nodes[1:]):
    intersections += detect_path_crossing(nodes, wire_two_nodes)

print(calc_manhattan_distance(min(intersections, key=calc_manhattan_distance)))
