Project Overview
This Java-based project implements a Naive Bayes classifier to categorize emails into two classes: 'Spam' and 'Not-Spam' (Ham). Utilizing a collection of pre-classified emails, the program learns to differentiate between these classes and applies its understanding to classify new, unseen emails.

Features:
Binary Classification: Classifies emails as either 'Spam' or 'Not-Spam'.
Word Presence Analysis: Uses the presence or absence of words in the training set, ignoring their frequency in individual emails.
Two-Phase Operation: Consists of a training phase and a testing phase.

How It Works
Training Phase
Input: Two text files with pre-classified emails (Spam and Not-Spam).
Process:
Parses and examines each email in the training set.
Compiles data on the number of instances and the occurrence of each word in the emails for both classes.
Develops a 'vocabulary' from all unique words in the training set.

Testing Phase
Input: Two new text files with unclassified emails for testing.
Process:
Each email in the testing set is classified into one of the two categories using the Maximum A Posteriori (MAP) hypothesis.
Calculates and reports the log-probability of each email belonging to both classes.
Predicts the class of each email and verifies the accuracy of the prediction.
Output: Number of emails correctly classified in the testing set.

Getting Started
Prerequisites: Ensure Java is installed on your system.
Installation: Download the source code from this repository.

Running the Program:
Place your training and testing files in the designated folders.
Compile and run the Java program.
View the classification results and accuracy metrics.

Contributions
Feel free to fork this project, submit issues, and send pull requests for improvements!
