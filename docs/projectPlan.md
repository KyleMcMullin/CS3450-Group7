Dan’s Frappes and Raps

-------- Project Overview --------
This project aims to build a mobile ordering system for a Frappuccino cafe.
The app will allow customers to place orders and add money to their account. The app will also allow employees to take/track customizable orders, collect money, enter hours worked, allow employees to make and deliver orders, and allow the manager/store owner to track inventory and pay employees.

This app will be written in Kotlin while using Jetpack Compose and using Firebase’s database. 

-------- Team Organization --------
Project Manager: (may change through course of project)
Designers and Developers: Abigail Allen, Andrew Wilkinson, Hamilton Hardy, and Kyle McMullin

-------- Software Development Process --------
The development will be broken up into five phases. Each phase will be a little like a Sprint in an Agile method and a little like an iteration in a Spiral process. Specifically, each phase will be like a Sprint, in that work to be done will be organized into small tasks, placed into a “backlog”, and prioritized. Then, using on time-box scheduling, the team will decide which tasks the phase (Sprint) will address. The team will use a Scrum Board to keep track of tasks in the backlog, those that will be part of the current Sprint, those in progress, and those that are done.
Each phase will also be a little like an iteration in a Spiral process, in that each phase will include some risk analysis and that any development activity (requirements capture, analysis, design, implementation, etc.) can be done during any phase. Early phases will focus on understanding (requirements capture and analysis) and subsequent phases will focus on design and implementation. Each phase will include a retrospective.

Phase: Iteration
1: Requirements Capture
2: Analysis, Architectural, UI, and DB Design
3: Implementation, and Unit Testing
4: More Implementation and Testing

We will use Unified Modeling Language (UML) to document user goals, structural concepts, component interactions, and behaviors. Communication policies, procedures, and tools
(Describe your communication policies and procedures.)

-------- Communication Policies, Procedures, and Tools --------

Discord - Main channel for communication. Used for group calls, file sharing, and other collaborative activities.
Google Drive - Storage for files needing collaborative effort and review for Milestone 1.
Android Studio - Primary development platform for the application
Jetpack Compose - Development framework for the app
Kotlin - Programming language utilized by Jetpack Compose
Firebase - Database management
Github - Storage of the project. 
Draw.io - Use case diagram creator. 

-------- Risk Analysis --------
Login
Firebase Database
	Likelihood - Low
	Severity - Very High
	Consequences - Customer balance provenance corrupted, limited trust in new transactions and account information
	Work Around - None; System loses functionality without ability to track transactions.
User Interface (UI)
	Purchasing Page
		Likelihood - Low
		Severity - Mid
		Consequences - Customers unable to purchasing product online
		Work Around - Call or Walk in to order; Limited in scale and decreases revenue. 
	Menu Page
		Likelihood - Low
		Severity - Mid
		Consequences - Customers unable to view menu
		Work Around - Digital menu stored in other location, physical menu stored in store, Call or walk in to order
	Manager page
		Inventory
			Likelihood - Low
			Severity - Low
			Consequences - manager unable to view inventory of items
			Workaround - Do without, encrypted database still updates quantities based on employee order tracker
		Payroll
			Likelihood - Low
			Severity - High
			Consequences - manager unable to pay their employees, manager also unable to view employees hours
			Workaround - give employees a check for their hours, or cash. Use a pen and paper to write down employee hours. 
	Employees page
		Order tracker
			Likelihood - Low
			Severity - High
			Consequences - Employees have no way to track incoming orders
			Workaround - walk in customers order with pen and paper
		Payroll
			Likelihood- Low
			Severity- Mid
			Consequences- employees can no longer enter payroll hours
			Workaround - Write down hours in pen and paper

-------- Configuration Management --------
See the README.md in the Git repository.


