
public class Complex implements FieldElement{
    private final double re;   // the real part
    private final double im;   // the imaginary part

    /**
     * Initializes a complex number from the specified real and imaginary parts.
     *
     * @param real the real part
     * @param imag the imaginary part
     */
    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    /**
     * Returns a string representation of this complex number.
     *
     * @return a string representation of this complex number,
     *         of the form 34 - 56i.
     */
    public String toString() {
        if (im == 0) return re + "";
        if (re == 0) return im + "i";
        if (im <  0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }

    /**
     * Returns the absolute value of this complex number.
     * This quantity is also known as the <em>modulus</em> or <em>magnitude</em>.
     *
     * @return the absolute value of this complex number
     */
    public double abs() {
        return Math.hypot(re, im);
    }

    /**
     * Returns the phase of this complex number.
     * This quantity is also known as the <em>angle</em> or <em>argument</em>.
     *
     * @return the phase of this complex number, a real number between -pi and pi
     */
    public double phase() {
        return Math.atan2(im, re);
    }

    /**
     * Returns the sum of this complex number and the specified complex number.
     *
     * @param  that the other complex number
     * @return the complex number whose value is {@code (this + that)}
     */
    public Complex add(Complex that) {
        double real = this.re + that.re;
        double imag = this.im + that.im;
        return new Complex(real, imag);
    }

    /**
     * Returns the result of subtracting the specified complex number from
     * this complex number.
     *
     * @param  that the other complex number
     * @return the complex number whose value is {@code (this - that)}
     */
    public Complex subtract(Complex that) {
        double real = this.re - that.re;
        double imag = this.im - that.im;
        return new Complex(real, imag);
    }

    /**
     * Returns the product of this complex number and the specified complex number.
     *
     * @param  that the other complex number
     * @return the complex number whose value is {@code (this * that)}
     */
    public Complex mult(Complex that) {
        double real = this.re * that.re - this.im * that.im;
        double imag = this.re * that.im + this.im * that.re;
        return new Complex(real, imag);
    }

    /**
     * Returns the product of this complex number and the specified scalar.
     *
     * @param  alpha the scalar
     * @return the complex number whose value is {@code (alpha * this)}
     */
    public Complex scale(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }


    /**
     * Returns the complex conjugate of this complex number.
     *
     * @return the complex conjugate of this complex number
     */
    public Complex conjugate() {
        return new Complex(re, -im);
    }

    /**
     * Returns the reciprocal of this complex number.
     *
     * @return the complex number whose value is {@code (1 / this)}
     */
    public Complex reciprocal() {
        double scale = re*re + im*im;
        if(scale==0) throw new IllegalArgumentException("Cannot return reciprocal of zero");
        return new Complex(re / scale, -im / scale);
    }

    /**
     * Returns the real part of this complex number.
     *
     * @return the real part of this complex number
     */
    public double re() {
        return re;
    }

    /**
     * Returns the imaginary part of this complex number.
     *
     * @return the imaginary part of this complex number
     */
    public double im() {
        return im;
    }

    /**
     * Returns the result of dividing the specified complex number into
     * this complex number.
     *
     * @param  that the other complex number
     * @return the complex number whose value is {@code (this / that)}
     */
    public Complex divide(Complex that) {
    	if(that.re==0 && that.im==0) throw new IllegalArgumentException("Division by zero");
        return this.mult(that.reciprocal());
    }

    /**
     * Returns the complex exponential of this complex number.
     *
     * @return the complex exponential of this complex number
     */
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    /**
     * Returns the complex sine of this complex number.
     *
     * @return the complex sine of this complex number
     */
    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    /**
     * Returns the complex cosine of this complex number.
     *
     * @return the complex cosine of this complex number
     */
    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    /**
     * Returns the complex tangent of this complex number.
     *
     * @return the complex tangent of this complex number
     */
    public Complex tan() {
        return sin().divide(cos());
    }
    

    /**
     * Unit tests the {@code Complex} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
    }


    

    public int characteristic() {
		return 0;
	}

	public FieldElement add(FieldElement f) {
		return this.add(f);
	}

	public FieldElement subtract(FieldElement f) {
		return this.subtract(f);
	}

	public FieldElement mult(FieldElement f) {
		return this.mult(f);
	}

	public FieldElement divide(FieldElement f) {
		return this.divide(f);
	}

	
	public FieldElement inverseElement() {
		return new Complex(1,0).divide(this);
	}

	public FieldElement negationElement() {
		return new Complex(-1,0).mult(this);
	}
	
	public FieldElement zeroElement(){
		return new Complex(0,0);
	}

	public FieldElement unitElement(){
		return new Complex(1,0);
	}

	@Override
	public boolean nonZeroElement() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FieldElement cloneElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addElement(FieldElement f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void multElement(FieldElement f) {
		// TODO Auto-generated method stub
		
	}

}
