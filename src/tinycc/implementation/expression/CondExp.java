package tinycc.implementation.expression;

import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.MipsAsmGen;

public class CondExp extends Expression {

	Expression condition , consequence , alternative;

	public CondExp(Expression condition , Expression consequence , Expression alternative){
		this.condition=condition;
		this.consequence=consequence;
		this.alternative=alternative;
	}

	@Override
	public String toString() {
	
		return null;
	}

	@Override
	public Type checktype(Diagnostic d, Scope s) {
		return this.finalType= null;
	}

	public boolean isLassignable(){
		return false ;
	}

	@Override
	public void generateR(MipsAsmGen out, CompilationScope scope, Regs r) {
	

	}

	@Override
	public void generateL(MipsAsmGen out, CompilationScope scope, Regs r) {

	}

	public int getSize(){
		return finalType.getSize();
	}


}
