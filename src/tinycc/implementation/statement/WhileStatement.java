package tinycc.implementation.statement;

import tinycc.diagnostic.Diagnostic;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.expression.*;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.BranchInstruction;
import tinycc.mipsasmgen.GPRegister;
import tinycc.mipsasmgen.MipsAsmGen;
import tinycc.mipsasmgen.TextLabel;
import tinycc.parser.Token;

public class WhileStatement extends Statement {

	Expression expression;
	Statement body;
	Expression invariant; Expression term ; Token loopBound;
	Locatable loc;
	public WhileStatement(Locatable loc, Expression expression , Statement body, Expression invariant, Expression term , Token loopBound){
		this.expression=expression;
		this.body=body;
		this.invariant=invariant;
		this.term=term;
		this.loopBound=loopBound;
		this.loc=loc;
	}


	@Override
	public String toString() {
		String s ="While";
			if(loopBound!=null)
			s+="_"+loopBound.getText();
			s+="["+expression.toString()+","+ body.toString();
			if(invariant!=null)
			s+="," + invariant.toString();
			if(term!=null)
			s+="," + term.toString();

			s+="]";

		
		return s;
	}

	public void checktype( Diagnostic d , Scope s, Type rt, boolean isloop, boolean def ){
		if(!expression.checktype(d, s).isScalar())
		d.printError(loc, "While Condition is not scalar");
		body.checktype(d, s,rt,true,def);
	}	


	public void generateCode(MipsAsmGen out, CompilationScope Scope,boolean isfunc, TextLabel endL, TextLabel jcon,int decs ){
		TextLabel L , T , end ; 
		L= out.makeUniqueTextLabel();
		T = out.makeUniqueTextLabel();
		end = out.makeUniqueTextLabel();
	out.emitInstruction(BranchInstruction.BEQ, GPRegister.ZERO , T );
	out.emitLabel(L);
	body.generateCode(out, Scope, isfunc, end , T,decs);
	out.emitLabel(T);
	expression.generateR(out , Scope , new Regs());
	out.emitInstruction(BranchInstruction.BNE, GPRegister.T0, L);
	out.emitLabel(end);
	}



}
