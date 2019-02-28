
first = True
dct = {}

with open('b_lovely_landscapes.txt') as fp:
    for line in fp:
        if first:
            first = False
            continue
        # print (line)
        split = line.split(" ")
        split.pop()
        split.pop()
        for tag in split:
            if tag in dct.keys():
                dct[tag] = dct[tag] + 1
            else:
                dct[tag] = 1
print (dct)