package tinycc.implementation.statement;

import tinycc.diagnostic.Diagnostic;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.*;

public class Error extends Statement {
	Locatable loc;
	public Error(Locatable loc){
		this.loc = loc;
	}

	@Override
	public String toString() {
		
		return "<error>";
	}

	public void checktype( Diagnostic d , Scope s, Type rt, boolean isloop, boolean def ){
	}

	public void generateCode(MipsAsmGen out, CompilationScope Scope,boolean isfunc, TextLabel endL, TextLabel jcon,int decs ){
		IllegalStateException s = new IllegalStateException();
		throw s; 
	}

}
