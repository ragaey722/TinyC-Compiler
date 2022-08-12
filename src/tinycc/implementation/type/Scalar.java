package tinycc.implementation.type;

public abstract class Scalar extends Type {

    @Override
	public abstract String toString();

	
	public boolean isScalar(){
        return true;
    }

	
	public boolean isVoid(){
        return false;
    }
    public boolean isFunc(){
        return false;
    }

    public boolean isCompelete(){
        return true;
    }

    
}
