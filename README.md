CS1501-Assignments
==================

Vector Comparator, Binary Expression Evaluator/Normalizer (Fall 2013)

###Assignment 1
Compare's the difference between two text files as the angle between their multidimensional vectors. 
Does this by tokenizing every word in each file (a word is defined as a consecutive alpha-numeric grouping 
[punctuation is ignored]), adding them to a TreeMap (automatically sorted map structure), 
and then computing the summation of their vectors and the angle between them.

###Assignment 2
Accepts a file of binary expressions (and initial truth values of each atom) and displays them as a tree. 
The expressions are then normalized (disjunctively ) and evaluated according to the initial, given truth values. 

###Assignment 3
Solver of the 8puzzle problem using an A* algorithm. Both utilizing the Manhattan and Hamming priority functions for a best-first search.

###Assignment 4
Working with undirectional graphs and weighted edges. Read a "network" (graph) from a file with V vertices and E edges. Get user input in order to change the network: connections (edges) become active or inactive, "latency" (weight on edge) changes, and reports can be created (shortest paths, Eulerian tours/paths, MSTs).

###Assignment 5
This project was focused on compression. We implemented Burrows-Wheeler encoding/decoding and Move-to-Front encoding/decoding. The Burrows-Wheeler compression utilized a circular suffix array data structure which we built from scratch. Move-to-Front compression utitilzed the Huffman class from Princeton. Move-to-Front encode/decoding runs in O(n). Burrows-Wheeler encode also runs in O(n + lg(n)) (for sorting), while decoding runs in O(n^2). The circular suffix array is created using linear space. 