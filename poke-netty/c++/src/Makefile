.PHONY: all clean

all: client_cpp    

clean : 
	rm -f client_cpp

client_cpp: cpp_client.cc 
	pkg-config --cflags protobuf  # fails if protobuf is not installed
	c++ cpp_client.cc comm.pb.cc -o client_cpp `pkg-config --cflags --libs protobuf`





