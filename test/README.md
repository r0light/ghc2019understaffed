# hashcode-java-template

![hash code logo](hashcode.png)

This repository provides a very simple Eclipse project Java template for [Google Hash Code challenges](https://hashcode.withgoogle.com/) and uses the [practice problem](https://github.com/jboockmann/hashcode-practice-problem) to demonstrate the usage of this template.


### Prerequisites

Make sure to install the required java sdk and jdk, i.e., version 1.8. The template was developed and tested under java 1.8, however, later java versions should work as well. 


### Getting Started

Clone this repository and pull the associated git submodule, which contains the resources of the pizza practice problem. You can do so by running `git submodule update --init --recursive` within the freshly cloned repository. Subsequently, you can import the project into Eclipse by clicking on `File > Import > Existing Projects into Workspace` and specifying the folder in which you have previously cloned this repository.


### High-Level Architecture
The previous Google HashCode problems followed a simple structure: you were provided with a text file that encoded a concrete problem instance and should produce a text file that contains your solution proposal. The respective format of the problem/input and solution/output file was detailed in the accompanying project description. This project template assumes that each problem can be solved by combining the following components in a particular way: a `Parser` is used to transform the input file into a `Problem` instance. This problem can then be interpreted and solved by a `Solver` producing a `Solution` that can be written as an output file using a `Writer`. Hence, the flow can be visualized as follows:

```
input > Parser > Problem > Solver > Solution > Writer > output
```


### How to solve a new problem

In order to use the template to solve a new problem you have to provide classes extending the above mentioned components. You might not need to adapt components that are generic, e.g., the `Writer`. In general, you would start as follows:

1. Implement a `Parser` and a `Problem` class to fit your needs, i.e., by adding attributes and methods to each class. Hint: JUnit test cases are helpful during the development of the `Parser` class; you can find an example in the implementation of the practice problem.
2. Implement one or multiple `Solver` classes.
3. Implement a `Main` class that connects the output with the input of the respective components. You may want to take a look at the `Main` class of the implementation of the practice problem.
 