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
import tinycc.mipsasmgen.*;
import tinycc.mipsasmgen.TextLabel;

public class IfStatement extends Statement {
	Expression condition; Statement consequence; Statement alternative;
	Locatable loc;
	public IfStatement( Locatable loc, Expression condition , Statement consequence, Statement alternative ){
		this.condition=condition;
		this.consequence=consequence;
		this.alternative=alternative;
		this.loc =loc;
	}

	@Override
	public String toString() {
		String s = "If[" + condition.toString() +","+ consequence.toString();
		if(alternative!=null)
		{ s+="," +alternative.toString();
		}
		s+="]";
		return s;
	}

	public void checktype( Diagnostic d , Scope s, Type rt, boolean isloop, boolean def ){
			if(!condition.checktype(d, s).isScalar())
			d.printError(loc, "If Condition is not scalar");
			consequence.checktype(d, s,rt,isloop,def);
			if(alternative!=null)
			alternative.checktype(d, s,rt,isloop,def);
		

		}
		
		public void generateCode(MipsAsmGen out, CompilationScope Scope,boolean isfunc, TextLabel endL, TextLabel jcon,int decs ){
			TextLabel F , N;
			F= out.makeUniqueTextLabel();
			N=out.makeUniqueTextLabel();
			condition.generateR(out , Scope , new Regs());
			out.emitInstruction(BranchInstruction.BEQ, GPRegister.T0 , F );
			consequence.generateCode(out, Scope, isfunc,endL,jcon,decs);
			out.emitInstruction(BranchInstruction.BEQ, GPRegister.ZERO, N);
			out.emitLabel(F);
			if(alternative!=null)
			alternative.generateCode(out, Scope, isfunc,endL,jcon,decs);
			out.emitLabel(N);
		}	


	}


