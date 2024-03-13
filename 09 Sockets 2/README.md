# 09 Exercises: Sockets Part 2

## 9.1 Excluding from broadcast

Start by implementing the Simple Chat System from the presentation (or download it [here](/09%20Sockets%202/Examples/Simple%20Chat%20System/)).

Run it and ensure everything is working as expected.

Notice that whenever a Client sends a message, they receive that same message from the Server.

This is not necessarily the best behaviour. We want to exclude the sender from the broadcast, so only other Clients receive that message.

To do so, we need to keep track of who sends the message, and send the message to everyone else.


