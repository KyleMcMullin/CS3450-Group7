<H2>Dan’s Frappes and Raps</H2>

This app creates a place for customers to buy frappe’s and the owner to keep track of inventory, and orders and pay employees.

<H3>Workspace Layout</H3>

The documentation and resources for this project is kept in the "docs" folder. This includes use case diagrams, the project plan, database diagrams, and more as the project progresses.

The mobile app project is stored in the folder “androidapp”

The Firebase project is based from https://console.firebase.google.com/u/0/project/cs3450-group7/overview


<H3>Version-Control Procedures</H3>

Collaborators will create a branch off of the master branch in Kyle’s Github account under the project ‘CS3450-Group7.’ Each branch will be associated with a Github issue, and said branch will be prefixed with ‘IssueNumber-Name-BriefTitle’. Ie. 1-Kyle-FirstIssue. After local development is completed on said branch, the collaborator will create a Pull Request and change their Issue status to ‘In Review’. After pull request completion, the collaborator will send a link to the Pull Request to the Discord channel #pull-requests. Reviewers will react with a thumbs up if the PR is approved, or comment on the PR and create a Discord thread stating that comments are added. After reworking the PR to deal with comments, the collaborator will post in the thread that a new iteration has been pushed, to which reviewers will react with a thumbs up or continue the process. Once the PR has been merged to main, the collaborator will change their Issue status to ‘Done’.

Complete workflow process:
<li>Github issue is created, assigned to collaborator</li>
<li>Create branch off of master with title ‘IssueNumber-Name-BriefTitle</li>
<li>Change Issue status to In Progress</li>
<li>Do local development on said branch</li>
<li>Create a Pull Request</li>
<li>Change Issue status to ‘In Review’</li>
<li>Link Pull Request to Issue by mentioning in description</li>
<li>Send link to Pull Request in Discord</li>
<li>Until all reviewer comments are resolved</li>
<li>Respond to comments and push iterations from branch to PR</li>
<li>Reply in Discord thread with each new change for review</li>
<li>When PR is approved, merge into main</li>
<li>Set Issue status to Done</li>

*Note: The Issue status changes should happen automatically, however, it is the collaborators responsibility to ensure they do.*


<H3>Tool Stack Description and Setup Procedure</H3>

Kotlin/Jetpack Compose - Modern and convenient framework for Android app development using Kotlin, a language the group is either familiar with or eager to learn. 

Firebase Database - Staying within the Google Development ecosystem and using Firebase to implement directly with the android app.

<H4>User Build Instructions</H4>

<ul>
  <li>Clone the project in gitbash: $ git clone https://github.com/KyleMcMullin/CS3450-Group7</li>
  <li>Download Android Studio from https://developer.android.com/studio/?gclid=Cj0KCQjwjvaYBhDlARIsAO8PkE0v0-l02_3h-vZsmZ_j8CWVdFaTR2ZeVUl0yRLmu1JkYqv6YCv1FVMaAoQhEALw_wcB&gclsrc=aw.ds</li>
  <li>Follow the default installation steps</li>
  <li>Select Open Project and navigate to the ‘app’ folder in the repository you cloned. Select it.</li>
  <li>Wait for the project to load in Android Studio, this may take several minutes.</li>
  <li>In the top task bar, select Tools -> Device Manager.</li>
  <li>Select Create Device.</li>
  <li>Select or search for “Resizable”, then click Next.</li>
  <li>Select API 33 for “Release Name”. If you have not previously done so, you will need to download it. Then click Next.</li>
  <li>Name the device if you wish, make sure startup orientation is Portrait and Graphics are Automatic. Then click Finish.</li>
  <li>Click the green run button in the top right to open the emulator and auto-launch the app</li>
  <li>Use app as expected</li>
</ul>
<H4> Developer Build Instructions </H4>

  <li>Clone the project in gitbash: $ git clone https://github.com/KyleMcMullin/CS3450-Group7</li>
  <li>Download Android Studio from https://developer.android.com/studio/?gclid=Cj0KCQjwjvaYBhDlARIsAO8PkE0v0-l02_3h-vZsmZ_j8CWVdFaTR2ZeVUl0yRLmu1JkYqv6YCv1FVMaAoQhEALw_wcB&gclsrc=aw.ds</li>
  <li>Follow the default installation steps</li>
  <li>Select Open Project and navigate to the ‘app’ folder in the repository you cloned. Select it.</li>
  <li>Wait for the project to load in Android Studio, this may take several minutes.</li>
  <li>In the top task bar, select Tools -> Device Manager.</li>
  <li>Select Create Device.</li>
  <li>Select or search for “Resizable”, then click Next.</li>
  <li>Select API 33 for “Release Name”. If you have not previously done so, you will need to download it. Then click Next.</li>
  <li>Name the device if you wish, make sure startup orientation is Portrait and Graphics are Automatic. Then click Finish.</li>
  <li>Click the green run button in the top right to open the emulator and auto-launch the app</li>
  <li>Use app as expected</li>
</ul>

<H5>Firebase web access instructions</H5>

*All Firebase edits and info can be found on the firebase console
https://console.firebase.google.com/u/1/project/dansfrappesraps/overview*

<H3>Unit Testing Instructions</H3>

Unit tests will cover all use cases laid out in the use case diagrams, ensure functionality of the UI, and ensure functionality of the database based on our risk analysis.

Unit tests:

<H3>Usability testing<H3>
We all ran through the app as users as if we knew nothing of the application, and tested it that way. 

<H3>System Testing Instructions</H3>

Start the program by following the setup instructions, then click the green play button on Android Studio. This will turn on the emulator and load the app onto it. When prompted to login, choose one of the following three options,
manager@dans.com Password: 123456789
employee@dans.com Password: 123456789
customer@dans.com Password: 123456789



If you chose to test the manager side of the app some of the items you can test, that are in the sidebar, are,
The profile page: 
	In the profile page you should be able to see your name, that you are a manager, the email you signed up with, and your current balance.
	You should be able to withdraw money from your balance, and there should be a button in the top right corner that brings you to the "Update User Info" page. 
	Here you will be able to change your name and email in our database.
Manage Payroll: 
	Here you are able to edit an employees number of hours worked and Approve the payment of said employees. You are also able to edit how much they get paid hourly.
Manage Inventory:
	In manage inventory you are able to see all of the inventory that your store has currently. You can filter through them by what type they are,
	Flavor, Add-Ins, Powder, Topping, Coffee, Milk, Sweetener, Drink, and tea.
	When clicking on an item in the list, it will bring you to the "edit item" screen. Here you can change the name, quantity, price per unit, and type of the item.
	Clicking on the blue floating action button in the bottom right will bring you to the "Create new item" screen
	Here you can make your own item and decide its name, how much you want, the price per unit, and the type of the item.
Track Orders:
	On this page you are able to view each of the orders that were made by customers. After clicking checkout on the cart page, the drinks that they ordered are sent
	to this page. 
Manage Menu:
	Here you are able to look at all of the drinks on your menu and if you click one one of them you can change the name, select a new image, and change the default
	amount of each ingredient the drink has. 
	Clicking on the bright red trash icon on the top right of each drink will remove the drink from your menu. 
	Clicking on the blue floating action button at the bottom right of the screen should bring you to a page where you can create a drink for your shop. 
	You will want to set a drink name, image, and how many of each ingredient you want in the new drink
Lastly, the Manage Users page:
	In the manage users page you, as a manager, are able to look at every customer, employee, and other managers in your shop. You should be able to see their name,
	balance, and email address, on top of whether they are an employee or manager, and if they are you can see how much they make hourly. 
	Clicking the blue floating action button at the bottom right should let you create a new user. It will ask for their name, email and whether they are a customer,
	employee, or manager. And clicking "create a user" should add them to the users list.

On the home page you should be able to see the list of drinks that your cafe/restraunt serves. Clicking on one should let you choose a size of drink and customize it.
	Managers are not able to add the drink to their cart though. They can track customers orders with the cart button at the bottom left of the home screen.


If any of these items are not working how they should, email us at dansfraps@dans.com




If you chose to test the employee side of the app, then some of the items you can test are,

Like the Manager page: The profile section: 
	Here It will show your name, the fact that you are an employee at your store, and your email address. It will also allow you to withdraw the money that your
	manager paid you.
	At the top right, the settings button with a cog wheel on it, should let you change your name and email address that is saved in the database.
Payroll:
	As an employee, you should be able to see your name at the top. Followed by how much you are paid hourly, and your account balance. 
	This is also where an employee will be able to input how many hours they have worked and send it to their manager.
Menu and order tracking:
	In the home screen you are able to see all of the drinks your cafe serves. Clicking on them allows you to see what each drink has in them and what size they are.
	The order tracking screen lets you see what orders customers have made, and paid for. You will be able to mark them as complete and delivered when you finish 
	making the drinks



If any of these items are not working how they should be, email us at dansfraps@dans.com


If you chose to test the customer side of the app,

Profile: 
	Shows your name, email address, and your current account balance. You are also able to add more money to your account.
	In the top right is the settings button. It will bring you to a page that you are able to change the name and email address that you have saved.

Menu:
	Here you can customize, or order the default drink. When you click the "Add to cart" button, it will send you back to the menu page and send the drink to your
	cart. 
Cart:
	The cart page has all of the drinks you ordered as well as the amount you ordered, and how much they all cost. Clicking the checkout button will remove the items
	used from the restraunt/cafe's inventory, and take the money out of the user's account. 

If any of these are not working how they should, email us at dansfraps@dans.com
*Other development notes, as needed*

