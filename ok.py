import os

for i in range(100):
	call = 'touch database' + str(i+ 1) + '.txt'
	os.system(call)
	fin = open('database' + str(i+1) + '.txt','w')
	for i in range(50):
		fin.write('testing\n')
	fin.close()
