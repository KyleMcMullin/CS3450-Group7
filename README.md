<H2>Dan’s Frappes and Raps</H2>

This app creates a place for customers to buy frappe’s and the owner to keep track of inventory, and orders and pay employees.

<H3>Workspace Layout</H3>

The documentation and resources for this project will be kept in the "docs" folder. This includes use case diagrams, the project plan, database diagrams, and more as the project progresses.

The mobile app project will be stored in the folder “androidapp”

The Firebase project will be based from https://console.firebase.google.com/u/0/project/cs3450-group7/overview


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

<H3>System Testing Instructions</H3>

Start the program by following the setup instructions, then click the green play button on Android Studio. This will turn on the emulator and load the app onto it. When prompted to login, choose one of the following three options, with the username and password being the same:
manager
employee
customer


*Other development notes, as needed*

