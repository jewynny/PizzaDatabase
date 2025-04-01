# Project Report
Group Information

Janelle Gwynn Hamoy

Hassan Fawaz


## Implementation Description
The implementation for the Pizza Store code uses a combination of Java and SQL to act as an interface for Pizza Stores, managers, drivers, and regular users/customers. The interface provides a menu that a user can interact with, as well as specific features that managers and drivers can access. The main program menu allows all users to view and update their profiles, view the menu, add an order, view/change older orders, update users, and update the menu. Because an option is accessible from the main menu does not mean the current user is guaranteed access. This implementation also verifies whether a user is authorized to access a menu option as well as validates inputs to ensure proper usage of the program.

## Query Descriptions
### Within Function CreateUser:

A query to ensure the the new user’s login is unique

<img width="567" alt="Screenshot 2025-04-01 at 11 24 40 AM" src="https://github.com/user-attachments/assets/1299560d-8392-445c-b0ad-a594fe57f9ea" />

An update query to add the newly created user into the database

<img width="394" alt="Screenshot 2025-04-01 at 11 28 00 AM" src="https://github.com/user-attachments/assets/ce278351-06c2-4e79-8d8e-5e42912f9f35" />



### Function LogIn:

A query to ensure that the user loging in exists as a user and username and password are both correct

<img width="396" alt="Screenshot 2025-04-01 at 11 29 22 AM" src="https://github.com/user-attachments/assets/d579e5a9-e6a9-454f-8e8e-a5b8786df775" />


### Function viewProfile

A query to output the information of the current authorised user (whoever is logged in currently)

<img width="420" alt="Screenshot 2025-04-01 at 11 29 56 AM" src="https://github.com/user-attachments/assets/29e94fea-a889-4116-a252-357449a02df4" />


### Function updateProfile
<img width="440" alt="Screenshot 2025-04-01 at 11 30 24 AM" src="https://github.com/user-attachments/assets/09b35c68-a92b-4796-92f1-86e85e06ee9d" />

Given these options, the following queries are:
Update favorite item option

<img width="418" alt="Screenshot 2025-04-01 at 11 31 37 AM" src="https://github.com/user-attachments/assets/46306a74-f380-4282-bd0e-aeb56039213e" />

In the update favorite item option, the favorite item of the current authorized user is displayed

<img width="419" alt="Screenshot 2025-04-01 at 11 31 58 AM" src="https://github.com/user-attachments/assets/effe733e-5556-439e-be23-740c5946173f" />


In the update favorite item option, the user is prompted to type out their favorite item from the given list (the query is also ran to ensure that it produces a query output)

<img width="417" alt="Screenshot 2025-04-01 at 11 33 33 AM" src="https://github.com/user-attachments/assets/712d183f-a9a7-4742-9028-e4d6e2d0e2a9" />

Same query as photo above, just inside input validation

Lastly, the favorite item of the current authorized user is updated
Update phone number option

<img width="436" alt="Screenshot 2025-04-01 at 11 35 05 AM" src="https://github.com/user-attachments/assets/1d1ba9c1-e005-470a-93f7-0f401b9c28a1" />

Outputs the current phone number of the current authorized user

<img width="427" alt="Screenshot 2025-04-01 at 11 37 42 AM" src="https://github.com/user-attachments/assets/cc59ea93-e057-41ab-ace4-5f7478c874d0" />

Phone number of the current authorized user is updated  
Update password

<img width="443" alt="Screenshot 2025-04-01 at 11 38 19 AM" src="https://github.com/user-attachments/assets/972ec1fb-50d6-411a-9587-b4ddccbf24c3" />

The password is directly updated as per the authorized user’s input

<img width="448" alt="Screenshot 2025-04-01 at 11 42 01 AM" src="https://github.com/user-attachments/assets/4f9cd3db-15ec-4fdd-b88c-f94a3148322f" />

### Function viewMenu

All menu items are outputted

<img width="312" alt="Screenshot 2025-04-01 at 11 42 21 AM" src="https://github.com/user-attachments/assets/b492663e-b930-4fd1-b5e8-b1cae6fd3d2a" />

Menu items are outputted and filtered by type (depending on what the user picks)

<img width="340" alt="Screenshot 2025-04-01 at 11 42 40 AM" src="https://github.com/user-attachments/assets/90297869-ef9c-4f40-be88-8cbb6a504eda" />


The following cases are, outputting by maximum price determined by the user, outputting by ascending order of price, and lastly outputting by descending order of price.

<img width="334" alt="Screenshot 2025-04-01 at 11 43 31 AM" src="https://github.com/user-attachments/assets/48b08d10-08a5-48b5-aaaf-2afce2c7ee78" />


### Function placeOrder
To place an order, the user had to go through several steps. The first step was choosing a store. We displayed a list of all available stores along with their store IDs. The user was then asked to enter the store ID they wanted to order from. We checked this input against the database to ensure the store existed. If the store ID was valid, the process continued. Otherwise, the order was canceled.

<img width="458" alt="Screenshot 2025-04-01 at 11 43 43 AM" src="https://github.com/user-attachments/assets/8c0b3eff-bb92-4e04-835f-3659ff399e81" />

Once the store was selected, we created two lists—one for item names and one for their corresponding quantities. We then displayed all the items available at that store, along with their prices. The user could start ordering by entering an item name followed by the quantity they wanted. The ordering process continued until the user typed "done" to finish.

<img width="330" alt="Screenshot 2025-04-01 at 11 44 42 AM" src="https://github.com/user-attachments/assets/1adb02b1-5c5d-4785-b7ec-b6d0405e18c6" />

To ensure accuracy, we added several checks. We verified that the selected item existed in the database and that the entered quantity was a positive number. If the user tried to order an item that wasn’t available or entered a quantity of zero or less, an error message was displayed, and they were asked to enter valid information.

<img width="349" alt="Screenshot 2025-04-01 at 11 45 02 AM" src="https://github.com/user-attachments/assets/ae1111ee-223c-476d-9415-9710b821d114" />

After the user finished selecting items, we calculated the total cost of the order. This was done by multiplying each item's quantity by its price, which we had stored in our lists. 

<img width="356" alt="Screenshot 2025-04-01 at 11 45 38 AM" src="https://github.com/user-attachments/assets/30d0ed2b-40bb-4bee-bf78-2fd749d13dfd" />



Finally, we stored the order information in the database. To generate a unique order ID, we first checked if the FoodOrder table was empty. If it was, we started with a default order ID of 10000. Otherwise, we retrieved the highest existing order ID from the database, added one to it, and used that as the new order ID. This ensured that every new order had a unique identifier. Once the order ID was generated, we inserted the order details—including the user’s login, store ID, total price, order status, and timestamp—into the FoodOrder table. Since a user could order multiple items at once, we used a loop to insert each ordered item into the ItemsInOrder table. This ensured that every item in the order was linked to the correct order ID, along with the respective quantity.

<img width="401" alt="Screenshot 2025-04-01 at 11 45 51 AM" src="https://github.com/user-attachments/assets/7f53b78c-737f-46dc-a710-73572eb7dae9" />

### Function viewAllOrders

<img width="462" alt="Screenshot 2025-04-01 at 11 46 19 AM" src="https://github.com/user-attachments/assets/1cdf56cd-4e48-47b5-a989-54cffc6ea9b4" />

First query, verify that the user is a customer. 
Second query, output the order information for the customer and only the customer if the current user is a customer. 
Third query, if the current user is not a customer (so a manager or driver), output all orders.

### Function viewRecentOrders

<img width="448" alt="Screenshot 2025-04-01 at 11 46 50 AM" src="https://github.com/user-attachments/assets/abe833b5-9b27-493d-92a3-c7436e0ddd6d" />

Output the 5 most recent orders of the current authorized user.

### Function viewOrderInfo

First gather the order ID

<img width="279" alt="Screenshot 2025-04-01 at 11 47 26 AM" src="https://github.com/user-attachments/assets/6b7a8196-1def-49ba-bbce-c63eb55005bc" />

Then check if the current user is a customer

<img width="442" alt="Screenshot 2025-04-01 at 11 47 54 AM" src="https://github.com/user-attachments/assets/d27fcbce-cc60-4916-a78f-fd5f360bde2e" />

If the current user is a customer, they can only view the order only if its theirs

If the current user is not a customer, they can view any order regardless of who placed it

<img width="461" alt="Screenshot 2025-04-01 at 11 48 07 AM" src="https://github.com/user-attachments/assets/f777b2eb-3e12-46ea-9645-ce5c744f6120" />


### Function viewStores

Simple query to output information about every store

<img width="458" alt="Screenshot 2025-04-01 at 11 48 22 AM" src="https://github.com/user-attachments/assets/1a13d364-4d6a-4802-ad14-ac9a8b750fb9" />

### Function updateOrderStatus:

This query checks the role of the user, and sees if he is a manager or a driver. If they are, it sets a boolean is customer to false, which we use to see if they can update the order status.

<img width="453" alt="Screenshot 2025-04-01 at 11 48 58 AM" src="https://github.com/user-attachments/assets/7e8eadf9-7f76-4d40-b588-c1f43a51a8f4" /> 

This query prints all the orders from the sql table. Afterwards the user can input an order number in which the query afterwards checks if it is a valid orderID by comparing it to the database. 	

<img width="457" alt="Screenshot 2025-04-01 at 11 49 14 AM" src="https://github.com/user-attachments/assets/2b2f4980-0aa7-4e1e-842d-0c6ab53f7a5f" />

This step updates the query , more specifically the order status. It does this by changing the order status of an order by checking the orderID. It then prints out the order status to show that it is updated.

<img width="458" alt="Screenshot 2025-04-01 at 11 50 08 AM" src="https://github.com/user-attachments/assets/fb9e4676-073e-41ea-9dba-217d66b30d6e" />

### Function updateMenu:

<img width="443" alt="Screenshot 2025-04-01 at 11 50 32 AM" src="https://github.com/user-attachments/assets/71065c1c-a0a7-4e94-b6be-28b807a54c7c" />

Query to check if the current user is a manager

<img width="462" alt="Screenshot 2025-04-01 at 11 51 22 AM" src="https://github.com/user-attachments/assets/2cfd9d20-1ab8-4e6a-b82a-7390dfae3e94" />

First read in input for the item to be updated, and then use a query to output information about that item

<img width="439" alt="Screenshot 2025-04-01 at 11 52 13 AM" src="https://github.com/user-attachments/assets/6409a827-d44c-411c-bea1-0b9b70332e9e" />

Then a menu about which part of the item to update

<img width="462" alt="Screenshot 2025-04-01 at 11 52 39 AM" src="https://github.com/user-attachments/assets/0796f456-5188-4d6a-8927-1026cf1b53e0" />

An update query to change the ingredients of a specified item
An update query to change the item’s type

<img width="427" alt="Screenshot 2025-04-01 at 11 52 26 AM" src="https://github.com/user-attachments/assets/6c58262c-5f54-4bbc-bcd7-c96ee4b4be83" />
An update query to change the item’s price


An update query to change an items description

<img width="448" alt="Screenshot 2025-04-01 at 11 58 46 AM" src="https://github.com/user-attachments/assets/7ee385bc-ee22-432c-aea2-7f293d4453e9" />

At the end of the function, output the updated query by printing all information
### Function updateUser:

<img width="452" alt="Screenshot 2025-04-01 at 11 58 59 AM" src="https://github.com/user-attachments/assets/e8ee9700-01cc-4139-9ec6-0720dc8b5df2" />


Checks if the user is a manager. Does this by comparing the authorisedUser input which is passed in that contains the role of user.  If it is, returns a value > 0, which we used to set a boolean true or false. 

<img width="463" alt="Screenshot 2025-04-01 at 11 59 11 AM" src="https://github.com/user-attachments/assets/2cffa88d-c2d6-42d4-a32b-68ca28bfdffd" />

If the manager wants to change a username it first checks in the database if the user exists. If it does, it sets a int userExits > 0 which gives out an error that user exists, thus the manager cannot change a username to one that already exists. 
The next query checks and updates the user login to the one the manager specifies and stores it in the database. 

<img width="465" alt="Screenshot 2025-04-01 at 11 59 33 AM" src="https://github.com/user-attachments/assets/5cf70e5b-952c-41af-8d28-4c339ad2e02e" />



This query updates the user to a new role specified by the manager and stores it in the database.


# Good User Interface

We made it easier for users to explore features. For example, when viewing the menu or more specifically when searching for by type, we made it use a drop down menu where the user can choose a choice for the types. This made it easier to use than typing all the information inside manually.



We made the code more robust, by first making the creating account process safer and more user friendly. Firstly, every account requires a 10 digit phone number, if anything else is entered, it will ask to re-enter it. Moreover, the correct formatting is shown in front of them. Moreover, when entering a password to create an account, the user is prompted to enter the password twice, to ensure they enter the correct password. If they do not match, it will ask the user to try again. The user is also given more options when creating an account such as being able to exit at any time by typing in exit at any point. Moreover, all new user username will be automatically lowercase, and stored that way. This is so that when a user enters the wrong capitalization for the username it will not matter. (Ex. Hfawaz or hfawaz both work).






During the logging in phase, the user input for username is automatically lowercase for them. Moreover, they are given the chance to exit at any time by typing exit.

Certain edge cases were dealt with. For example, when placing an order, the limit must be an integer greater than 0. Any other input would be invalid. Moreover, for every part of the order, all the options are displayed for the user to make it clear to follow. Moreover, the user is allowed to exit at any time during this process.



Moreover, for order status the user is given a drop down menu for possible causes that occurred with the order. This way the user does not have to manually type the conditions. 



# Performance Tuning


The index on orderID in the FoodOrder table is useful because orderID is frequently used in searches, such as when retrieving order details. Without an index, the database has to scan through every row to find the right order, which takes longer. With an index, it can quickly jump to the correct order, making searches much faster. 

The index on storeID in the Store table helps when checking if a store exists before placing an order. Since storeID is often used to filter or match data, having an index allows the database to quickly locate the store instead of scanning the entire table. 

The index on itemName in the Items table is useful because menu items are frequently searched when customers place orders. Since the itemName column is used to check item availability and prices, indexing it helps speed up these lookups, improving performance when adding items to an order. 

For all these changes, the speed up  becomes (O(log n)) rather than linear time (O(n)), making queries much faster.



# Problems/Findings
One of the challenges we faced was managing access control. We needed to make sure that customers couldn’t access manager or driver-only features. The simplest solution was to pass an argument called authorizedUser into every function that needed it. This allowed us to check the user’s role before granting access. If the user was a manager or driver, they could proceed. If not, the system would deny access, ensuring that only the right users could perform certain actions. This method made it easy to control permissions across the system.
At first, when a user wanted to select a favorite item, we had all the possible food options hardcoded. However, as we continued working on the project, we realized that managers could add new food items to the menu. This made our original approach invalid since it wouldn't include newly added foods. To fix this, we decided to display all available food items from the database whenever a user wanted to update their favorite item. The user could then enter their choice, and we would check if that item existed in the database before updating their favorite food accordingly. This made the system more flexible and ensured it always reflected the current menu.


#Contributions
Hassan Fawaz: Handled special cases in the code, like preventing negative order quantities. Added input checks, such as confirming passwords and ensuring phone numbers have the correct number of digits. Worked on user registration, login, browsing the menu, managing profiles, placing orders, and viewing stores.
Janelle Gwynn Hamoy: Added dropdown menus to make navigation easier. Created indexing for better performance. Managed access control, giving managers and drivers more permissions than customers. Worked on updating food details for managers and allowing drivers and managers to update order statuses. Developed order lookup features, including viewing order history, the last five orders, and searching for specific orders.
Together: Debugged issues and tested edge cases. Helped each other identify and fix missing parts in the project.
