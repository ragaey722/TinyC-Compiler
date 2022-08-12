package tinycc.implementation.statement;


import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.MipsAsmGen;
import tinycc.mipsasmgen.TextLabel;

/**
 * The main statement class (see project description)
 *
 * You can change this class but the given name of the class must not be
 * modified.
 */
public abstract class Statement {

	/**
	 * Creates a string representation of this statement.
	 *
	 * @remarks See project documentation.
	 * @see StringBuilder
	 */
	@Override
	public abstract String toString();
	
	public abstract void checktype( Diagnostic d , Scope s, Type rt, boolean isloop, boolean def );

	public abstract void generateCode(MipsAsmGen out, CompilationScope Scope,boolean isfunc,TextLabel endL, TextLabel jcon,int decs );
}
