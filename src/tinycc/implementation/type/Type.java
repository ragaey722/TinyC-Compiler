package tinycc.implementation.type;

import tinycc.mipsasmgen.*;;
/**
 * The main type class (see project description)
 *
 * You can change this class but the given name of the class must not be
 * modified.
 */
public abstract class Type {

	/**
	 * Creates a string representation of this type.
	 *
	 * @remarks See project documentation.
	 * @see StringBuilder
	 */
	@Override
	public abstract String toString();

	
	public abstract boolean isScalar();

	
	public abstract boolean isVoid();

	
	public abstract boolean isIntegar();

	
	public abstract boolean isPointer();

	
	public abstract boolean isChar();

	
	public abstract boolean isInt();

	public abstract boolean isFunc();

	public abstract boolean isCompelete();

	public abstract int getSize();

	public abstract MemoryInstruction getStore ();

	public abstract MemoryInstruction getLoad ();

}
