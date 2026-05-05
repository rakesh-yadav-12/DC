import socket
import threading
import time
import struct

def server_thread_func():
    try:
        print("\n**** INTERPROCESS COMMUNICATION ****\n")
        print("*** SERVER PROCESS STARTED ***\n")
        
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server_socket:
            server_socket.bind(('localhost', 1200))
            server_socket.listen(1)
            
            client_socket, client_address = server_socket.accept()
            
            with client_socket:
                # Get client IP and port
                client_ip = client_address[0]
                client_port = client_address[1]
                print(f"* Client connected with IP address /{client_ip} and port number {client_port}\n")
                
                # Receive numbers using a simple protocol (4 bytes each)
                a_data = client_socket.recv(4)
                a = struct.unpack('!i', a_data)[0]
                
                b_data = client_socket.recv(4)
                b = struct.unpack('!i', b_data)[0]
                
                print("SERVER RECEIVED")
                print(f"Number 1 ===> {a}")
                print(f"Number 2 ===> {b}")
                print()
                
                result = a + b
                
                # Send result back to client
                client_socket.send(struct.pack('!i', result))
                
                print(f"SERVER HAS EXECUTED REQUEST AND SENT RESULT {result} TO CLIENT\n")
                print("SERVER PROCESS EXITING...\n")
                
    except Exception as e:
        print(f"Server Exception: {e}")

def client_thread_func():
    try:
        # Small delay to ensure server starts first
        time.sleep(1)
        
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client_socket:
            client_socket.connect(('localhost', 1200))
            
            # Get input from user
            a = int(input("ENTER VALUE OF NUMBER 1:\n"))
            print(f"Number 1 ===> {a}")
            print()
            
            b = int(input("ENTER VALUE OF NUMBER 2:\n"))
            print(f"Number 2 ===> {b}")
            print()
            
            # Send numbers to server using a simple protocol
            client_socket.send(struct.pack('!i', a))
            client_socket.send(struct.pack('!i', b))
            
            # Receive result from server
            result_data = client_socket.recv(4)
            result = struct.unpack('!i', result_data)[0]
            
            print("CLIENT RECEIVED RESULT FROM SERVER")
            print(f"THE ADDITION OF {a} AND {b} IS {result}\n")
            
    except Exception as e:
        print(f"Client Exception: {e}")

# Create and start threads
server_thread = threading.Thread(target=server_thread_func)
client_thread = threading.Thread(target=client_thread_func)

server_thread.start()
client_thread.start()

server_thread.join()
client_thread.join()