Java code for a live capture of audio and instant transformation into a frequency spectrogram. 

Fast Fourier Transform code is within the main class Graph.java

Complex.java is a class designed to handle and perform a series of operations with complex numbers

In the method captureAudio() in Graph.java it is necessary to specify the correct Mixer as it varies from computer to computer. Line 99 grabs the information on the available mixers and Line 112 sets the desired one.

Audio Capture code used is a modification done over the file AudioCapture02.java provided and explained by Richard G. Baldwin at http://www.developer.com/java/other/article.php/1579071/Java-Sound-Getting-Started-Part-2-Capture-Using-Specified-Mixer.htm

The Energy information on the Spectrogram is displayed as color scale