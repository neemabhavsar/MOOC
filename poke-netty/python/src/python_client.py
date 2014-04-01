import comm_pb2
import struct
import sys
import socket
import time

#Build for SignUp 
request = comm_pb2.Request()
request.header.routing_id = request.header.PING
request.header.originator = "client"
request.header.tag = "header for signup"
request.header.time = long(time.time())
request.header.toNode = "zero" # Sending to node with node.id "zero"
request.body.signUp.email = "abc@sjsu.edu"
request.body.signUp.password = "1234"
request.body.signUp.fname = "abc"
request.body.signUp.lname = "DEF"

s = request.SerializeToString()
packed_len = struct.pack('>L', len(s))

#Socket Connection
sock=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
server_address=('localhost',5570)
print('connecting to %s port %s' %server_address)
sock.connect(server_address)
print 'sending message'

sock.sendall(packed_len + s)
print('closing socket')
sock.close()
