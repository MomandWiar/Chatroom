# ChatRoom Server

A simple Java-based chatroom server that allows multiple clients to connect and exchange messages in real-time.

## Description

This chatroom server application is built in Java and provides a basic framework for hosting a chatroom. It allows multiple clients to connect and communicate with each other by sending text messages.

## Features

- Multiple clients can connect to the server simultaneously
- Real-time messaging between clients
- Nickname assignment for each client
- Graceful handling of client disconnections
- Server shutdown capability

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed
- `nc` command (for Mac) or `ncat` command (for Windows) for connecting as a client

### Usage

To join the chatroom as a client, you can use the following commands:

- **Mac**: Open a terminal and use the `nc` command followed by the server's IP address and port number. For example:

```sh
nc <server-ip> <port-number>
```

- **Windows**: Open a terminal and use the `ncat` command followed by the server's IP address and port number. For example:

```sh
nc <server-ip> <port-number>
```

Once connected, you can choose a nickname and start sending messages. To exit the chat, simply type "exit" as a message.

To stop the server, you need to go to the location where the server is started (usually the main class file) and prompt the word "stop". This will initiate the server shutdown process, closing all client connections and releasing resources gracefully.

## Contributing

Contributions are welcome! If you have any ideas, suggestions, or improvements, please submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

Feel free to modify the code and adapt it to your specific needs. For more detailed information, please refer to the source code and inline comments.
