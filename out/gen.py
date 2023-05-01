import random
import string

filename = "messages.txt"

num = int(input())

file = open("messages.txt", "w")

letters = string.ascii_lowercase

for _ in range(num):
    file.write(''.join(random.choice(letters) for _ in range(10)))
    file.write('\n')