package tinycc.implementation.type;

import tinycc.mipsasmgen.MemoryInstruction;

public class Pointer extends Scalar {
	Type pointsTo;
	public Pointer(Type pointsTo){
		this.pointsTo = pointsTo;
	}

	@Override
	public String toString() {
		
		return "Pointer[" + pointsTo.toString() +"]";
	}


	public boolean isPointer(){
		return true;
	}

	public boolean isIntegar()
    {
        return false;
    }

	public  boolean isChar(){
		return false;
	}

	
	public boolean isInt(){
		return false;
	}

	public boolean isVoidPtr(){
		return 	this.pointsTo.isVoid();
	}

	@Override
	public boolean equals(Object o ){
		if(this == o)
            return true;
		if(! (o instanceof Pointer))
		return false;
		Pointer p = (Pointer)o;

		return this.pointsTo.equals(p.pointsTo);
	}

	@Override
    public int hashCode()
    {
          
        return 42;
    }

public Type getPointed(){
	return this.pointsTo;
}

@Override
	public int getSize() {
		return 4;		
	}
	@Override
	public MemoryInstruction getStore() {
	
		return MemoryInstruction.SW ;
	}
	@Override
	public MemoryInstruction getLoad() {
		return MemoryInstruction.LW ;
	}





}
