# 11 Assignment 2 - MVVM and Observer and Sockets

## The system:
You must design and implement a simple Client/Server system, supporting multiple Clients. 
Possible systems include:
- Vinyl Library
- Simple Chat
- Tic-tac-toe
- Collaborative Whiteboard
- File-sharing system

## The assignment: 
Implement the chosen system.

## Requirements
 - The Server must be able to support multiple concurrent Clients.
 - The Client must be able to: 
   - send messages to the server
   - receive messages from the server

What messages you send depends on your choice of application. Messages can be used to ask the Server to perform an action.

- You must use Java [Sockets](https://viaucdk-my.sharepoint.com/:p:/g/personal/mivi_viauc_dk/Ee3l0wPlAm5OmFJZwH65SBgBnjs-xIeNIXSELiK-TK52hA?e=c1aTSM) to connect the Client and Server
- You must use the [MVVM Design Pattern](https://viaucdk-my.sharepoint.com/:p:/g/personal/mivi_viauc_dk/ERq-HZanan1Il1qIAgibr28Bvv_fs64vBv-Q48cMdCEstA?rtime=EY6Qnpo23Eg)
 - You must use the [Observer Design Pattern](https://viaucdk-my.sharepoint.com/:p:/g/personal/mivi_viauc_dk/EW35KX6HbzpOj9uJJOxxF00BQxuuh_EeSaFIzDn5nzYDNw?e=kHo1Xg) . 
- You must use the [Singleton (and/or Multiton) Design Pattern](https://viaucdk-my.sharepoint.com/:p:/g/personal/mivi_viauc_dk/EZHvva3YUOZMkN9iu-nGoNEBzYBtuVDubc87C9s4Tk5u5A?e=xiTOW8)
- You must use the [Strategy Design Pattern](https://viaucdk-my.sharepoint.com/:p:/g/personal/mivi_viauc_dk/EZHvva3YUOZMkN9iu-nGoNEBzYBtuVDubc87C9s4Tk5u5A?e=xiTOW8)
- You must create a UML Class Diagram for the final solution (preferably in Astah).

## Hints
Like in the previous assignment, the purpose is not to have a functionally impressive system.

Focus on the patterns, and the concepts being taught, to create clean code.

Depending on your choice of system, including all design patterns may be difficult. 

The Vinyl Library from the previous assignment can be changed into a Client/Server system, and there are several instances where the Singleton, Multiton and Strategy pattern can be used, for example:

 - Change the Factories to Singletons
 - Introduce localization (translations) and store text values for different languages in a Multiton
 - Create a functionality to change how dates are displayed, and swap ways using the Strategy pattern.

The other systems can probably implement the patterns in similiar ways, but if you are stuck, feel free to invent imaginary problems that you solve with these patterns.



## Format
You are allowed to work in groups, but you must hand in individually. 

Hand in everything in a single zip-file with:

 - UML Class Diagram 
 - Source code for all classes
 - Related resources like `.FXML` files, and if used, external `.jar` files


## Deadline
See Itslearning.

## Evaluation
Your hand-in will be used during the exam, but will not be evaluated separately.

Feedback available upon request.
