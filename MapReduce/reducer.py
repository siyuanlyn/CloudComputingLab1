#!/usr/bin/python
import sys
import os

def main(argv):
	li = {}
	#while True:
	for line in sys.stdin:
		#line = sys.stdin.readline()
		#if len(line) == 0:
			#break
		info = line.split('\t')
		title = info[0]
		view = info[1][14:]
		date = info[1][0:8]
		time = info[1][8:14]
		if not title in li:
			li[title] = {date:int(view)}
		else:
			if date in li[title]:
				li[title][date] += int(view)
			else:
				li[title][date] = int(view)
	sumli = {}
	for article in li:
		sum = 0
		for date in li[article]:
			sum += li[article][date]
		sumli[article] = sum
	for article in sumli:
		if sumli[article] > 100000:
			print str(sumli[article]) + '\t' + article + '\t',
			for d in li[article]:
				print d + ": " + str(li[article][d]) + '\t',
			print

if __name__ == "__main__":
    main(sys.argv)
