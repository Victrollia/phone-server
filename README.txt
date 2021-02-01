Student: Victoria Cadogan

Phone TCP Protocol Description:

This protocol takes place in the form of a client server architecture. The PhoneServer will keep listening for any connections from the clients. The PhoneServer contains a Contact class that will be used to store or get contacts. This connection will take place in the port 2014. The Server will handle the connection by creating an array list, where it will store the name and phone number. Upon a successful connection the Server will send to the client "100 OK". Upon an invalid input, "400 Bad Request" will be printed to the client. It is a multi-threaded server so more than one client can be connected.
