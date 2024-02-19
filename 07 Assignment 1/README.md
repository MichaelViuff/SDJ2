# 07 Assignment 1 - Vinyl Library

## The system
You must design and implement a simple application for a Vinyl Library. A Vinyl has at least a title,  artist, year, and a lending state. The Vinyl can be in different states depending on availability, reservation, borrowing or a combined borrowing and reservation. 

You can borrow a Vinyl either if it is not reserved by someone else or are already borrowed or if it is reserved by you (reserved). You cannot reserve a Vinyl if it already contains a reservation (there is no reservation list, only one person at the time can reserve the Vinyl). However, you are allowed to reserve a Vinyl if it is available (no reservation and not borrowed) or if it is borrowed and do not have another reservation.

A Vinyl can be removed from the library if it has no reservation and are not borrowed (available). If the Vinyl is borrowed, reserved or both, then the removing sets a flag such that the Vinyl cannot be reserved any longer. The Vinyl is not fully removed until it has been returned and do not have a reservation (the person who may have reserved the Vinyl will still be able to borrow it, before it is finally removed)

## The assignment
 - From a GUI with at least two windows, you 
    - Show a list of Vinyls including their states. (To make it simple, you can assume that there are no more than 10 Vinyls.)
    - Select one Vinyl and Reserve, Borrow or Return it. When reserving, also show to whom. 
    - Remove a Vinyl (mark it to be removed until its state allows it to be fully removed). Make sure that this is reflected in the GUI as soon as it has been removed from the model.
    - Optionally, you can include the information that a Vinyl is about to be removed (before it is fully removed)
    - Optionally, you can have a window to add a new Vinyl, but hard-coded values are also ok.
 - From two threads you simulate “Bob” and “Wendy” reserving, borrowing and returning a Vinyl (in the model). You can randomly choose the methods in a loop, with proper thread sleeping time.
    - Make sure that the GUI updates automatically, to reflect the changed states for the Vinyl.

## Requirements
 - You must use MVVM with at least two windows. Further, you must use shared ViewState objects to access the selected Vinyl in another view than the view where it has been selected (do not give one ViewController or ViewModel direct access to another VierController/ViewModel).
 - You must use the Observer design pattern as part of the solution.
 - You must use the State design pattern for the different states of a Vinyl. It is required to make a state machine diagram for the Vinyl (in Astah).
 - It is required to make a class diagram for the final full solution (in Astah). In the diagram you must be able clearly to identify the different MVVM parts, the Observer pattern, the State pattern and the classes related to the threads.
 - You must use threads for the simulation and still be able to manually use the system (in the GUI)

## Deadline
See Itslearning.

## Format
It is ok to work in groups, but you have to hand individually. Hand in everything in a single zip-file with:

1)	UML Class Diagram 
2)	State Machine Diagram
3)	Source code for all classes
4)	Related resources like `.FXML` files, and if used, external `.jar` files

## Evaluation
Your hand-in will be registered and counts for one of the exam requirements.

