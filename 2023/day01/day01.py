with open("./input.real") as f:
    input = f.read().strip().splitlines()

words = {
    "one": "1",
    "two": "2",
    "three": "3",
    "four": "4",
    "five": "5",
    "six": "6",
    "seven": "7",
    "eight": "8",
    "nine": "9"
    }

    
numbers = []
for line in input:
    word = ""
    number = ""
    beg = 0
    for i, c in enumerate(line):
        if c in [str(a) for a in range(10)]:
            number += c
            beg = 1
        else:
            end = i+1
            for start in range(beg, end):
                if line[start: end] in words:
                    number += words[line[start: end]]
                    beg = i
    numbers.append(int(number[0] + number[-1]))

print(sum(numbers))

for n, l in zip(numbers, input):
    print(n, l)
    
