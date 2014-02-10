#!/usr/bin/python
import os
import sys

def main(argv):
	ex_page_tag = ('Media', 'Special', 'Talk', 'User', 'User_talk', 'Project', 'Project_talk', 'File', 'File_talk', 'MediaWiki', 'MediaWiki_talk', 'Template', 'Template_talk', 'Help', 'Help_talk', 'Category', 'Category_talk', 'Portal', 'Wikipedia', 'Wikipedia_talk')
	ex_extension_tag = ('.jpg','.gif','.png','.JPG','.GIF','.PNG','.txt','.ico')
	ex_boilerplate_tag = ('404_error/','Main_Page','Hypertext_Transfer_Protocol','Favicon.ico','Search')
	lowercase_character = ('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z')
	list = []
	#sum = 0
	#with open("201306-gz+pagecounts-20130601-000001.txt", "r") as input_file:
	input_file = os.environ["map_input_file"]	
	# info = input_file[0:input_file.index(".")]
	info = input_file.split("pagecounts-")
	# info_array = info.split('-')
	# date = info_array[2]
	date = info[1][0:8]
	# date = "20130601"
	time = info[1][9:15]
	# time = "000000"
	#while True :
	for line in sys.stdin:
		#line = input_file.readline()
		#line = sys.stdin.readline()
		seg = line.rstrip('\n').rstrip('\r').split(" ")
		#if len(line) == 0:
		#	break
		#sum += int(seg[2])
		if not seg[0] == "en":
			continue
		if seg[1].startswith(ex_page_tag):
			continue
		if seg[1][0] in lowercase_character:
			continue
		if seg[1].endswith(ex_extension_tag):
			continue
		if seg[1] in ex_boilerplate_tag:
			continue
		list.append({'title':seg[1], 'view':seg[2], 'date':date, 'time':time})
	#sorted_list=sorted(list,key=lambda x:int(x['view']),reverse=True)
	#with open("output", "a") as output_file:
	for element in list:
		print(element['title']+'\t'+ element['date']+element['time']+element['view'])

if __name__=='__main__':
    main(sys.argv)









