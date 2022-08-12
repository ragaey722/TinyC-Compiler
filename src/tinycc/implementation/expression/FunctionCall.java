package tinycc.implementation.expression;

import java.util.List;

import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Func;
import tinycc.implementation.type.Pointer;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.GPRegister;
import tinycc.mipsasmgen.*;
import tinycc.parser.Token;

public class FunctionCall extends Expression {
	Expression calle;
	List<Expression> arguments;
	Token token;

	public FunctionCall(Token token, Expression calle, List<Expression> arguments) {
		this.calle = calle;
		this.arguments = arguments;
		this.token = token;
	}

	@Override
	public String toString() {
		String s = "Call[" + calle.toString();
		for (Expression e : arguments)
			s += "," + e.toString();
		s += "]";
		return s;
	}

	@Override
	public Type checktype(Diagnostic d, Scope s) {
		if (calle instanceof Variable) {
			Variable v = (Variable) calle;
			Type t = s.lookup(v.getVar());
			if (t == null)
				d.printError(token, " Function not declared ");
			else if (!(t instanceof Func))
				d.printError(token, "refrence doesn't have function type");
			else {
				Func f = (Func) t;
				if (f.par.size() != arguments.size())
					d.printError(token, "call doesn't provide the same number of args");
				else {
					for (int q = 0; q < f.par.size(); q++) {
						Type r = arguments.get(q).checktype(d, s);
						Type l = f.par.get(q);
						if (l.equals(r))
							continue;
						else if (l.isIntegar() && r.isIntegar())
							continue;
						else if (l.isPointer() && r.isPointer()) {
							Pointer lpt = (Pointer) l;
							Pointer rpt = (Pointer) r;
							if (lpt.isVoidPtr() || rpt.isVoidPtr())
								continue;
							else
								d.printError(token, "not valid Pointers Assignment for func par");
						} else if (l.isPointer() && r.isIntegar()) {
							if (arguments.get(q).toString() == "Const_0")
								continue;
							else
								d.printError(token, "not valid NUll ptr Assignment for func par");
						} else
							d.printError(token, " not Valid Assignment for func call ");
					}
					return this.finalType = f.getRet();
				}

			}
		} else
			d.printError(token, "wrong function refrence");
		return this.finalType = null;
	}

	public boolean isLassignable() {
		return false;
	}

	@Override
	public void generateR(MipsAsmGen out, CompilationScope scope, Regs r) {
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.RA, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.T0, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.T1, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.T2, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.T3, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.T4, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.T5, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.T6, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.T7, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.T8, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.T9, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.A0, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.A1, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.A2, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.A3, null,0 , GPRegister.SP);

		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.V0, null,0 , GPRegister.SP);
		
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		out.emitInstruction(MemoryInstruction.SW, GPRegister.V1, null,0 , GPRegister.SP);
			int q=0;
		for(Expression exp : arguments )
		{ exp.generateR(out, scope, r);
			GPRegister dest = null;
			switch (q) {
				case 0:
					dest= GPRegister.A0;
					break;
					case 1:
					dest= GPRegister.A1;
					break;
					case 2:
					dest= GPRegister.A2;
					break;
					case 3:
					dest= GPRegister.A3;
					break;		
			
				default:
					break;
			}


			out.emitInstruction(RegisterInstruction.ADD, dest , GPRegister.ZERO, r.getfront());	

			q++;
		}
		//TextLabel called = out.makeTextLabel(token.getText());
		//out.emitInstruction(JumpInstruction.JAL, called );
		//out.emitInstruction(insn, target);
		out.emitInstruction(MemoryInstruction.LW, GPRegister.V1, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.V0, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );


		out.emitInstruction(MemoryInstruction.LW, GPRegister.A3, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.A2, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.A1, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.A0, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.T9, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.T8, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.T7, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.T6, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.T5, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );
		
		out.emitInstruction(MemoryInstruction.LW, GPRegister.T4, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.T3, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.T2, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );


		out.emitInstruction(MemoryInstruction.LW, GPRegister.T1, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.T0, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );

		out.emitInstruction(MemoryInstruction.LW, GPRegister.RA, null,0 , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 4 );


		
	}

	@Override
	public void generateL(MipsAsmGen out, CompilationScope scope, Regs r) {

	}

	public int getSize() {
		return finalType.getSize();
	}

}
