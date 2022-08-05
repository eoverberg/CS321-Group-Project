# CS321-Group-Project (Group 9: Cameron Beck, Emily Burns, Joshua Cantrell(IdioticProphet), Erik Overberg)



## Getting started

1. Add your public SSH key to your account.
dont have one? Run this command and spam enter until it's done
```
ssh-keygen
```
It should then be located in `%USERPROFILE%\\.ssh\\id_rsa.pub`(Windows) `~/.ssh/id_rsa.pub`(Linux) (dont name it)
Add the key [here](https://gitlab.com/-/profile/keys)
    - Dont set expiration
    - Title it whatever your computer's name is
    - For multiple computers, either copy your private key to .ssh/id_rsa or ssh-keygen and add that. 

2. install git and java
3. git clone git@gitlab.com:cs321-group/cs321-group-project.git
4. You have the project now. Good luck!


## How we will probably deal with new features or any changes
1. Create an Issue, title it whatever feature you're adding, you can find it on the left side of your screen looking at the repo (or here https://gitlab.com/cs321-group/cs321-group-project/-/issues)
2. Assign yourself to it, we probably wont really use milestones or labels unless I go crazy one day.
3. Due Dates are useful, probably. Idk if it actually notifies you.
4. Create a local branch and work on it *OR* create a merge request and work off that branch

### If you create a local branch
1. Create brnach add files
```
git branch issue-name-or-something
git checkout issue-name-or-something
git add . //if you have files
git commit -a -m "did the thing with the issue"
git push
```
2. Now navigate to your branch on gitlab, there should be a create merge request button, click it, and in your description you can put "Closes #x" replace x with your issue number, found at the top of the page, or at the end of the url. For example,
https://gitlab.com/cs321-group/cs321-group-project/-/issues/1
is issue #1, so I would put Closes #1 in my description

3. Once it's done mark the pull request as ready, ping whoever is doing reviews, and they'll merge it!

### If you create merge request first
1. Click Create merge question on the issue page
2. Click "Check out branch" button on the screen
3. Copy the first set of commands and run them.
4. Forget the rest of the commands, make your changes
5. Whenever you're done with your code do:
```
git add .   // if you have new files
git commit -a -m "wrote code"   // Making the text more descriptive will help our grade and aid us in writing docs
git push
```
6. You're done!, make sure to mark the issue as ready on the gitlab, it should give you a link in your CLI



If there's anything else that you run into, I'll add it here so we all have a command reference.

### Link to Team TO-do's

https://docs.google.com/document/d/1GJ-j878ql_xxWqJB5B4GEoD9rIKlTqVw1Ug70EIWkO8/edit?usp=sharing

### Note
You may see an alias "IdioticProphet", that's just me (Joshua Cantrell) under a more public alias.
