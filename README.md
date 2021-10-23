                               IVR for Call

Problem Statement
When a user contacts Customer Support, there are a set of options (called IVR - interactive Voice Response) that are presented to the user to help narrow down the issue and reach out to the right Support Agent that can help them.
Eg: IVR session for Telecom:
Please press 1 for Prepaid connection
Please press 2 for Postpaid connection
Please press 3 for Broadband
On pressing an option, eg: 1
Please press 1 for new Prepaid connection
Please press 2 to know your current Bill amount
(and so on)
Press 8 to go back to the previous menu
Op pressing - say 2
Voice says: Your current bill is ₹ 245
Press 8 to go back to the previous menu
Press 9 to go back to the main menu
Please disconnect to end this call
In this question, we are going to develop a simple IVR service that:
Starts a new IVR for a user if there is no IVR in progress
Returns initial set of options for the user
Each option has a numeric identifier to indicate the selected option
At every level of the IVR there can be an option to go back 1 step (fixed at digit 8) and go back to start of the IVR (fixed at digit 9)
actors.User can either choose an option by pressing an appropriate number or can disconnect the call
When the user chooses an option, then system processes the input and moves to the next level of IVR or move back to the previous level (option 8) or first level (option 9)
When the user disconnects the call, IVR will end

Extension scenario (Bonus)
Build retry for the following scenario:


If actors.User waits for more than 1 minute before providing their input.
Candidate can chose to model timeout in the code; or
Can expect a timeout event
provides a wrong input
Retry spec:
Max number of tries = 3
on retry, return the same options with an optional message = "You have not provided the right input. Please try again"
Notes:
IVR menus/levels are not fixed and hence do not hard code IVR menu options.
For the purpose of this question, assume that for a given user, IVR levels and menu items are fixed
Assume that the following events are triggered by calling system:
Start of call
actors.User Input
End of call
IVR may contain
A message
A list of options
Continue with IVR or not



















Evaluation Criteria

Demonstration, Modelling and Extensibility of your Code.
Clean Professional Code.
A demonstration of the functional requirements stated above.
For the demonstration, you can choose any of - CLI, API, Unit tests or even the main method.


Additional Notes

You may use any programming language.
Database is optional. We don’t recommend setting up a db, it takes a lot of time. In-memory stores are acceptable for storing data.
You may access documentation and any other resources as needed.


Interviewer: Arun Kumar
Phone No : 8892620049
