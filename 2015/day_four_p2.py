

def decrease_test(password):
    for d1, d2 in zip(str(password)[:-1], str(password)[1:]):
        if int(d1) > int(d2):
            return False
    return True

def repeat_short_test(password):
    repeat = None
    streak = 0
    for d1, d2 in zip(str(password)[:-1], str(password)[1:]):
        if int(d1) == int(d2):
            if repeat == d1:
                streak +=1
            else:
                streak = 2
                repeat = d1
        elif streak == 2:
            return True
        else:
            streak = 0
            repeat = None 

    if streak == 2:
        return True
    else:
        return False

password_count = 0

for n in range(165432, 707912 + 1):
    if decrease_test(n) and repeat_short_test(n):
        print(n)
        password_count += 1

print(password_count)
