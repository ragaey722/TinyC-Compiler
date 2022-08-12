package tinycc.implementation.expression;

import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.*;

/**
 * The main expression class (see project description)
 *
 * You can change this class but the given name of the class must not be
 * modified.
 */
public abstract class Expression {

	Type finalType =null;

	/**
	 * Creates a string representation of this expression.
	 *
	 * @remarks See project documentation.
	 * @see StringBuilder
	 */
	@Override
	public abstract String toString();

	public abstract Type checktype( Diagnostic d , Scope s );

	public abstract boolean isLassignable() ;

	public Type getType(){
		return this.finalType;
	}

	public abstract void generateR(MipsAsmGen out, CompilationScope scope, Regs r );
	public abstract void generateL(MipsAsmGen out, CompilationScope scope, Regs r );
	public abstract int getSize();
}
