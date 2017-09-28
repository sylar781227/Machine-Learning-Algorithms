Execution of the code :

Contain two main class one to process and one to run the neural network.

Preprocess.java- to run the data pre processing:
Takes 2 input :
input and the output of the file:
/Users/janmejayasahoo/Documents/workspace/BackPropagationAlgorithm/src/iris.txt
/Users/janmejayasahoo/Documents/workspace/BackPropagationAlgorithm/src/processiris.csv

MainNN: to execute the backprop algorithm:

Takes 4 argument
 
trainPath<processed data> 
learningRate
maxError
maxIterations

The number of hidden layer has to be set in the code line no 43 of MainNN

 finalTrainData.getNumAttributes(),2,2,1)
where 2,2 represent two hidden layer of 2 neuron each;
1 represent the output neuron;
sample changes : 
3 layer with 2 neuron each
2,2,2 
3 layer with 3 neuron each
3,3,3
       

/Users/janmejayasahoo/Documents/workspace/BackPropagationAlgorithm/src/processedAdult.csv
0.5
0.5
100
