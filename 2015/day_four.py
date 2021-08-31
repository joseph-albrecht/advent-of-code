

def decrease_test(password):
    for d1, d2 in zip(str(password)[:-1], str(password)[1:]):
        if int(d1) > int(d2):
            return False
    return True

def repeat_test(password):
    for d1, d2 in zip(str(password)[:-1], str(password)[1:]):
        if int(d1) == int(d2):
            return True
    return False

password_count = 0

for n in range(165432, 707912 + 1):
    if decrease_test(n) and repeat_test(n):
        password_count += 1

print(password_count)
