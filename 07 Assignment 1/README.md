# 07 Assignment 1 - Vinyl Library

## The system
You must design and implement a simple application for a Vinyl Library. 

A `Vinyl` has at least a `title`,  `artist`, `release year`, and a lending `state`. The Vinyl can be in different states depending on availability. 

A Vinyl that is not *borrowed*, and has no reservation is said to be *available*.

You can reserve a Vinyl is it is *available*, or if it is *borrowed* and not already *reserved* (there is no reservation list, only one person at the time can have a reservation). 

You can borrow a Vinyl if it is *available*, or *reserved* by you.
 
A Vinyl can only be removed from the library once it has no reservation and is *available*. 

If the Vinyl is *borrowed*, *reserved* or *both*, then trying to remove it sets a flag so that the Vinyl cannot be reserved again. The person who has reserved the Vinyl will still be able to borrow it, before it is finally removed upon return.

## The Assignment
Create a GUI where the user can:
 - See a list of Vinyls with their information, including their states.
 - Select one Vinyl and **Reserve**, **Borrow** or **Return** it.
 - **Remove** a Vinyl (mark it to be removed until it is allowed to actually be removed). 
 - Optionally, you may include the information that a Vinyl is about to be removed.
 - Optionally, you may create a window to add a new Vinyl.

To simulate other users in the system, create a `Runnable` class that randomly performs the above actions at random intervals.

Make sure that the GUI updates automatically, to reflect the changes from all users.

## Requirements
 - You must use the [MVVM Design Pattern](https://viaucdk-my.sharepoint.com/:p:/g/personal/mivi_viauc_dk/ERq-HZanan1Il1qIAgibr28Bvv_fs64vBv-Q48cMdCEstA?rtime=EY6Qnpo23Eg). 
 - You must use the [Observer Design Pattern](https://viaucdk-my.sharepoint.com/:p:/g/personal/mivi_viauc_dk/EW35KX6HbzpOj9uJJOxxF00BQxuuh_EeSaFIzDn5nzYDNw?e=kHo1Xg).
 - You must use the [State Design Pattern](https://viaucdk-my.sharepoint.com/:p:/g/personal/mivi_viauc_dk/EXeIwrpqCK1Loz0nLvJO0vABIJ7RTkgwu8lwE_mVMxd7lQ?e=twxGB9) for the different states of a Vinyl. 
 - You must use [Threads](https://viaucdk-my.sharepoint.com/:p:/g/personal/mivi_viauc_dk/EbYBFs9lT9RMvkFDfAi-XToB1_R-Mc1jidlOCN8rGuDixA?e=doN9da) for the simulation and still be able to use the system at the same time (in the GUI)
 - You must create a UML State Machine Diagram for the Vinyl states (preferably in Astah).
 - You must create a UML Class Diagram for the final solution (preferably in Astah). 

## Hints
There are many ways to solve this assignment, and the purpose is not to have a functionally impressive system.

Focus on the patterns, and the concepts being taught, to create clean code.

There are some general issues that you might run into:

 - If you use separate windows, how can you tell which item has been selected in one view to another view? We do not want to give one ViewController or ViewModel direct access to another VierController/ViewModel! Consider using a shared `Session` object to hold such information.
 - How do you keep track of who is borrowing and reserving the Vinyls? You probably need some sort of user ID to attach when someone is interacting with the Vinyls.
 - How will you ensure that modifications to the Model are shown in the View? Your ViewModel will most likely contain a List of objects retrieved from the Model. How can you react to those objects changing? Is there anything in JavaFX that can notify us of such changes?

## Format
You are allowed to work in groups, but you must hand in individually. 

Hand in everything in a single zip-file with:

 - UML Class Diagram 
 - UML State Machine Diagram
 - Source code for all classes
 - Related resources like `.FXML` files, and if used, external `.jar` files

## Deadline
See Itslearning.

## Evaluation
Your hand-in will be used during the exam, but will not be evaluated separately.

Feedback available upon request.
