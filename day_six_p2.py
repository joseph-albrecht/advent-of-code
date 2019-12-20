import collections

def create_orbit_map(orbits):
    mapping = collections.defaultdict(str)
    for body, satellite in orbits:
        mapping[satellite] = body

    return mapping

def orbitting_list(orbits, body):
    if orbits[body] == "COM":
        return ["COM"]
    orbit_list = [orbits[body], ]
    orbit_list += orbitting_list(orbits, orbits[body])
    return orbit_list

def nearest_body(list1, list2):

    for body in list1:
        if body in list2:
            return body

orbits = open("day_six.input", 'r').read().split('\n')
orbits = list(map(lambda x: x.split(")"), orbits))

orbit_map = create_orbit_map(orbits)

my_orbit_list = orbitting_list(orbit_map, "YOU")
santa_orbit_list = orbitting_list(orbit_map, "SAN")

common_body = nearest_body(my_orbit_list, santa_orbit_list)

print(my_orbit_list.index(common_body) + santa_orbit_list.index(common_body))


