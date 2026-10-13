# Plan for the Auto

This is the file where the plan for the auto can go.

## States

These are the states that will run during the auto

### Start state
This state is the starting state in the state machine, as such it will be the
state that is entered by starting the autonomous.

What happens in this state:

* A few things should be initialized

How to exit state:

* After auto is initialized than move on to check for April tags state

### Check April Tags state
This state is in charge of detecting the April tags to find the
position of the robot on the field.

What happens in this state:

* Activate April tag detection for a few seconds

How to exit state:

* After a few seconds of not finding anything or after
successfully scanning the April tags and storing the area the robot is in