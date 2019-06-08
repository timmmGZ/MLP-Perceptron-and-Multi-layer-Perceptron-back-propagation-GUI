# Perceptron.java 
In this example, dataset are in hyperplane, please select print1Normal(p);     or     print2SearchBestRate(p); in main()  
这个例子的数据集是在超平面，在main函数里请选择 print1Normal(p);或者print2SearchBestRate(p);
# PerceptronWithUI.java 
This is a 2D Coordinate System example, I don't create test set, just only shows how train set works in a visual way, the program works as picture, the train set is created randomly by default, please choose the screen size(x/yLimit), number of points etc. by yourself  
这是一个二维坐标系的例子，我没有打算弄测试集，只是让训练集可视化，训练集是初始随机生成的，可以尝试自己选择屏幕大小，点的数量等等  
![image](https://github.com/timmmGZ/Perceptron/blob/master/image.png)
# MLP.java
This is Multi-Layer Perception with 2 layers, you could choose the number of neurons in main(), also try to modify the numOfPoints from 0 to 100000
```
MLP p = new MLP(50);//50 neurons
```
And choose the shape you want in "public void initializeParameters(int n) {}", just choose one of them.
```
// createCircle(50, 100, -30);
// createTwoCircle(30, 100, -30, 50, -100, 20);
// createRectangle();
createHeart(2.3, -80, -20);
```
Let see some result:  
About first rectangle example, you may ask why the 4 lines are already surrounding the rectangle, but the modification in prediction panel(bottom panel) looks like it has delay.  
Also about two circles example, you may ask why the lines are already surrounding the small circle on right side, but small circle appear so late in prediction panel.  
Answer is that is because not only w1 matters(w1 builds the lines), but also w2(w2 decides the importance of each line) matters too! for k layers(k>2) MLP, it is the same idea, w(k) decides the importance of each k-1 hidden layer outputs, the higher k, the better result in prediction panel, but process of training will be much slower!
![image](https://github.com/timmmGZ/MachineLearning-Perceptron/blob/master/images/rectangle4neurons.gif)
![image](https://github.com/timmmGZ/MachineLearning-Perceptron/blob/master/images/rectangle50neurons.gif)
![image](https://github.com/timmmGZ/MachineLearning-Perceptron/blob/master/images/Circle15neurons.gif)
![image](https://github.com/timmmGZ/MachineLearning-Perceptron/blob/master/images/Circle50neurons.gif)
![image](https://github.com/timmmGZ/MachineLearning-Perceptron/blob/master/images/Circle50neuronsWithfewVertices.gif)
![image](https://github.com/timmmGZ/MachineLearning-Perceptron/blob/master/images/heart50neurons.gif)
