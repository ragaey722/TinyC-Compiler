package tinycc.implementation.statement;

import tinycc.diagnostic.Diagnostic;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.*;

public class ContinueStatment extends Statement {
	Locatable loc;
	public ContinueStatment(Locatable loc){
		this.loc =loc;
	}

	@Override
	public String toString() {
		
		return "Continue";
	}

	public void checktype( Diagnostic d , Scope s, Type rt, boolean isloop, boolean def ){
		if(!isloop)
		d.printError(loc,"Continue outside while");
	}

	public void generateCode(MipsAsmGen out, CompilationScope Scope,boolean isfunc, TextLabel endL, TextLabel jcon,int decs ){
		out.emitInstruction(BranchInstruction.BEQ, GPRegister.ZERO, jcon);	
	}

}
