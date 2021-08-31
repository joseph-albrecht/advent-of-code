import collections
def create_orbit_map(orbits):
    mapping = collections.defaultdict(set)
    for body, satellite in orbits:
        mapping[body].add(satellite)

    return mapping

def number_of_orbits(orbits, body="COM", bodies=0):
    orbital_count = 0
    orbital_count += bodies
    for satellite in orbits[body]:
        orbital_count += number_of_orbits(orbits, satellite, bodies + 1)

    return orbital_count
    

orbits = open("day_six.input", 'r').read().split('\n')
orbits = list(map(lambda x: x.split(")"), orbits))

orbit_map = create_orbit_map(orbits)
print(number_of_orbits(orbit_map))

