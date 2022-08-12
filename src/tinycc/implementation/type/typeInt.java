package tinycc.implementation.type;

import tinycc.mipsasmgen.MemoryInstruction;

public class typeInt extends IntegarTypes {


	private static	typeInt single_instance = null;

	private typeInt()
    {
    }
	public static typeInt getInstance()
    {
        if (single_instance == null)
            single_instance = new typeInt();
 
        return single_instance;
    }



	@Override
	public String toString() {
		return "Type_int";
	}

	public  boolean isChar(){
		return false;
	}

	
	public boolean isInt(){
		return true;
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
