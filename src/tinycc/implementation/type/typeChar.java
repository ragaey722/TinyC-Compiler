package tinycc.implementation.type;

import tinycc.mipsasmgen.MemoryInstruction;

public class typeChar extends IntegarTypes {


	private static	typeChar single_instance = null;

	private typeChar()
    {
    }
	public static typeChar getInstance()
    {
        if (single_instance == null)
            single_instance = new typeChar();
 
        return single_instance;
    }

	@Override
	public String toString() {

		return "Type_char";
	}

		

	public  boolean isChar(){
		return true;
	}

	
	public boolean isInt(){
		return false;
	}


	@Override
	public int getSize() {
	
		return 1;
	}
	@Override
	public MemoryInstruction getStore() {
	
		return MemoryInstruction.SB ;
	}
	@Override
	public MemoryInstruction getLoad() {
		return MemoryInstruction.LB ;
	}



}
