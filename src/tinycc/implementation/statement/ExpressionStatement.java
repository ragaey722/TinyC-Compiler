package tinycc.implementation.statement;

import tinycc.diagnostic.Diagnostic;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.expression.Expression;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.*;

public class ExpressionStatement extends Statement {
	Expression expression;
	Locatable loc;
	public ExpressionStatement(Locatable loc, Expression expression){
		this.expression=expression;
		this.loc=loc;
	}
	@Override
	public String toString() {

		return expression.toString();
	}

	public void checktype( Diagnostic d , Scope s, Type rt, boolean isloop, boolean def ){
		
		expression.checktype(d, s);
	}


	public void generateCode(MipsAsmGen out, CompilationScope Scope,boolean isfunc, TextLabel endL, TextLabel jcon,int decs ){
	expression.generateR(out , Scope , new Regs());	
	}


}
