import os

for  i in range (100):
	call = 'touch  test' + str(i + 1)  +  '.txt'
	os.system(call)
	
