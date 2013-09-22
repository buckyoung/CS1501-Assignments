Buck Young - bcy3@pitt.edu - 1045 - 9/20/13

=============== HOW-TO ================
This is a two-part system: 1) The ExpressionDisplayer and 2) The ExpressionEvaluator:

1)
The ExpressionDisplayer accepts a data filename as a command line argument:
> java ExpressionDisplayer filename.txt

The data file should be a list of fully-parenthesized binary expressions, one per line. Example:
(!(AvB))
(A ^ ((!B)vC))
etc.

The Displayer will then create a tree display of each expression in the file. In the console, the
Displayer will wait for your input to continue on. It will ask if you would like to disjunctively
normalize the expression -- type 'y' to see a new tree created or 'n' to move onto the next expression
in the file.
> Normalize? y or n :


In the console, the Displayer will wait for you after each step. 
> Continue? y or n : 

Simply type a lower-case 'y' to continue onto the next expression. If you would not like to continue,
write a lower-case 'n'. The Displayer will continue in this manner until the end of the file is reached.
> EOF reached




2)
The ExpressionEvaluator is similar in functionality to the Displayer except that it will not give you the
option to normalize an expression. It will, however, provide a truth value for the expression. This truth
value is based on initial conditions provided by the input text file. Thus, the data file must be arranged
a bit differently:

The data file for the ExpressionEvaluator must include truth values for all 26 possible ATOM's (A - Z). After-
ward, the data file will contain the binary expressions which you want to evaluate. Example:
A true
B false
C false
[...]
Z true
(!(AvB))
(A ^ ((!B)vC))
etc.

The ExpressionEvaluator will continue in much the same manner that the Displayer does (waiting for you after
each expression) with one small difference. It will show, in the console, the the final truth-evaluation of
the expression. Example:
1. (!(AvB)) = false

This will continue until the end of file is reached.



=============== ERROR HANDLING ================
This system will gracefully handle three error cases:
1) Illegal Binary Operator: if the expression contains a symbol which is not understood by the parser.
2) Illegal Character: if the expression has encountered a strange sequence of characters -- usually accredited to 
the misplacement of a parenthesis.
3) Parens Error: the number of left-parens (opening) does not equal the number of right-parens (closing).




=============== Algorithms and Data Structures ================
This system uses and reuses many data structures. The main structure in use is a Node. This Node-structure can be represented 
visually by:

 -----------
/   parent  \    : The parent Node of this Node
|-----------|
|   symbol  |    : The symbol of this Node "v" or "^" or "!" or any ATOM, A-Z
|-----------|
|   value   |    : The truth-value of this node (takes into consideration the 
|-----------|                                      value of its children and its own operation)
\  Lc | Rc  /    : The Left-child and Right-child of this Node
 -----------

These Nodes are the main way that the expression is manipulated and evaluated.





Another large system in place is the Normalizer. The process of disjunctive-normalization is a bit involved, but it can be 
broken down into three steps:

1) Distribute Negation based on DeMorgans Laws

2) Remove any double-negation

3) Distribute '^' (and) over 'v' (or).

This normalizes the tree as a disjunction of conjunctions. All three of these steps involve using the Stack data structure
to push every Node of a tree onto and then pop them off one-by-one in order to examine and manipulate them as necessary.

Some of the algorithms involved here are recursive in nature, as each Node is a potential sub-tree of other Nodes.



One final system I would like to discuss is the TreeBuilder. This used during the initial process of turning an expression (string)
into a useful group of Nodes (a tree). This algorithm is recursive, as it breaks the expression down to its most inner-parens and 
creates the smallest sub-tree possible before moving "up" and finally returning the root of the entire tree. The smallest sub-
expression possible is a unary or binary operation. Example:
!A
A v B

The tree builder breaks everything down to these basic elements and then pushes the ATOMS onto one stack and the OPERATORS onto 
another stack. Then it pops the operator and sets its children by popping the ATOM stack, returning a reference to the root of 
this small sub-tree. This sub-root gets push onto the calling functions ATOM stack (the "result" stack is really a more 
descriptive name for it once we start to pull back in this way) and the process continues recursively in this manner. When it is
all said-and-done, we have a fully formed tree sitting on the result stack (conceptually, at least... in reality, we simply have
a single node which is the root of the entire tree).





=============== Known Problems ================
There are no known problems with the system. It functions as expected.
