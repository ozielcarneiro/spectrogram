/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 *
 * @author ozielcarneiro
 */
public class Complex {

    private double real;
    private double imag;

    public Complex() {
        real = 0;
        imag = 0;
    }

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public static Complex multiply(Complex a, Complex b) {
        return new Complex((a.real * b.real - a.imag * b.imag), 
                (a.real * b.imag + a.imag * b.real));
    }

    public static Complex divide(Complex a, Complex b) {
        return new Complex((a.real * b.real + a.imag * b.imag) / (b.real * b.real + b.imag * b.imag), 
                (-(a.real * b.imag) + a.imag * b.real) / (b.real * b.real + b.imag * b.imag));
    }

    public static Complex add(Complex a, Complex b) {
        return new Complex(a.real + b.real, a.imag + b.imag);
    }

    public static Complex subtract(Complex a, Complex b) {
        return new Complex(a.real - b.real, a.imag - b.imag);
    }
    
    /**
     * Implementation of Euler's Formula using radians for angle measurements
     * @param a complex number
     * @return resulting complex number
     */
    
    public static Complex exp(Complex a){
        double real = Math.exp(a.real)*Math.cos(a.imag);
        double imag = Math.exp(a.real)*Math.sin(a.imag);
        return new Complex(real, imag);
    }
    
    public static Complex[] fft(double[] data){
        int n = data.length;
        Complex[] transform = new Complex[n];
        //N = length(x);
        //if N==1
        //y = x;
        //else
        //y = zeros(size(x));
        //even = x(1:2:N-1);
        //odd = x(2:2:N);
        //y(1:N/2) = fft_test(even);
        //y(N/2+1:N) = fft_test(odd);
        //for k=0:N/2-1
        //t = y(k+1);
        //y(k+1) = t + exp(-2*pi*1i*k/N)*y(k+1+N/2);
        //y(k+1+N/2) = t - exp(-2*pi*1i*k/N)*y(k+1+N/2);
        //end
        //end
        if(n==1){
            transform[0] = new Complex(data[0],0);
        }else{
            double[] even = new double[n/2];
            double[] odd = new double[n/2];
            for (int i = 0; i < n/2; i++) {
                even[i] = data[i*2];
                odd[i]  = data[i*2+1];
            }
            Complex[] aux = fft(even);
            System.arraycopy(aux, 0, transform, 0, aux.length);
            aux = fft(odd);
            System.arraycopy(aux, 0, transform, n/2, aux.length);
            for (int i = 0; i < n/2; i++) {
                Complex t1 = transform[i];
                Complex t2 = Complex.multiply(Complex.exp(new Complex(0,-2*Math.PI*i/n)),transform[i+n/2]);
                transform[i] = Complex.add(t1, t2);
                transform[i+n/2] = Complex.subtract(t1, t2);
            }
        }
        return transform;
    }
    
    public void norm(double n){
        real = real*n;
        imag = imag*n;
    }
    
    public static Complex[] norm(Complex[] data, double n){
        for (int i = 0; i < data.length; i++) {
            data[i].norm(n);
        }
        return data;
    }
    
    public static double[] absFFT(double[] data){
        return abs(fftShift(fft(data)));
    }
    
    public static Complex[] fftShift(Complex[] transform){
        int n = transform.length;
        Complex[] out = new Complex[n];
        for (int i = 0; i < n/2; i++) {
            out[i] = transform[i+n/2];
            out[i+n/2] = transform[i];
        }
        return out;
    }

    public double getAbs(){
        return Math.sqrt(real*real+imag*imag);
    }
    
    public static double[] abs(Complex[] data){
        int n = data.length;
        double[] out = new double[n];
        for (int i = 0; i < n; i++) {
            out[i] = data[i].getAbs();
        }
        return out;
    }
    
    /**
     * @return the real
     */
    public double getReal() {
        return real;
    }

    /**
     * @param real the real to set
     */
    public void setReal(double real) {
        this.real = real;
    }

    /**
     * @return the imag
     */
    public double getImag() {
        return imag;
    }

    /**
     * @param imag the imag to set
     */
    public void setImag(double imag) {
        this.imag = imag;
    }
    
    @Override
    public String toString(){
        if(imag>=0) {
            return ""+real+" + "+imag+"i";
        }
        else {
            return ""+real+" "+imag+"i";
        }
    }
    
    public static void main(String[] args) {
        Complex x = new Complex(-1, -1);
        double[] data = {0,0.7071,1,0.7071,0,-0.7071,-1,-0.7071};
        Complex[] trans = fft(data);
        trans = fftShift(trans);
        double[] out = absFFT(data);
        for (int i = 0; i < trans.length; i++) {
            System.out.println(out[i]);   
        }
    }
}
