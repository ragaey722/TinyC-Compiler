package tinycc.implementation.statement;

import tinycc.diagnostic.Diagnostic;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Scope;
import tinycc.implementation.expression.Expression;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.*;

public class AssumeStatment extends Statement {
	Expression condition ;
	Locatable loc;
	public AssumeStatment(Locatable loc, Expression condition ){
		this.condition=condition;
		this.loc=loc;
	}

	@Override
	public String toString() {
		
		return "Assume["+this.condition.toString()+"]";
	}

	public void checktype( Diagnostic d , Scope s, Type rt, boolean isloop, boolean def ){
	}

	public void generateCode(MipsAsmGen out, CompilationScope Scope,boolean isfunc, TextLabel endL, TextLabel jcon,int decs ){
		
	}


}
