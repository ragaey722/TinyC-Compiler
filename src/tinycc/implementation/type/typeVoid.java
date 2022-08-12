package tinycc.implementation.type;

import tinycc.mipsasmgen.MemoryInstruction;

public class typeVoid extends Type {



	private static	typeVoid single_instance = null;

	private typeVoid()
    {
    }
	public static typeVoid getInstance()
    {
        if (single_instance == null)
            single_instance = new typeVoid();
 
        return single_instance;
    }

	@Override
	public String toString() {
		return "Type_void";
	}


	public boolean isScalar(){
		return false;
	}

	
	public boolean isVoid(){
		return true;
	}

	
	public boolean isIntegar(){
		return false;
	}

	
	public boolean isPointer(){
		return false;
	}

	
	public boolean isChar(){
		return false;
	}
	
	public boolean isInt(){

		return false;
	}
	public boolean isFunc(){
        return false;
    }

	public boolean isCompelete(){
        return false;
    }
	@Override
	public int getSize() {
		return 0;
	}
	@Override
	public MemoryInstruction getStore() {
		return null;
	}
	@Override
	public MemoryInstruction getLoad() {
		return null;
	}


}
