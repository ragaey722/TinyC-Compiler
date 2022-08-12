/**
 * 
 */
package tinycc.implementation;

import java.util.ArrayList;
import java.util.List;

import tinycc.diagnostic.Diagnostic;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.expression.BinaryExpression;
import tinycc.implementation.expression.CharConst;
import tinycc.implementation.expression.CondExp;
import tinycc.implementation.expression.Expression;
import tinycc.implementation.expression.FunctionCall;
import tinycc.implementation.expression.IntConst;
import tinycc.implementation.expression.StringConst;
import tinycc.implementation.expression.UnaryExpression;
import tinycc.implementation.expression.Variable;
import tinycc.implementation.statement.AssertStatment;
import tinycc.implementation.statement.AssumeStatment;
import tinycc.implementation.statement.Block;
import tinycc.implementation.statement.BreakStatment;
import tinycc.implementation.statement.ContinueStatment;
import tinycc.implementation.statement.Declaration;
import tinycc.implementation.statement.Error;
import tinycc.implementation.statement.ExpressionStatement;
import tinycc.implementation.statement.IfStatement;
import tinycc.implementation.statement.ReturnStatement;
import tinycc.implementation.statement.Statement;
import tinycc.implementation.statement.WhileStatement;
import tinycc.implementation.type.Func;
import tinycc.implementation.type.Pointer;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.typeChar;
import tinycc.implementation.type.typeInt;
import tinycc.implementation.type.typeVoid;
import tinycc.mipsasmgen.MipsAsmGen;
import tinycc.parser.*;

/**
 * @author Ragaey
 *
 */
public class ASTFactoryImp implements ASTFactory {

	Scope globalScope ;
	Diagnostic d;
	External head;
	External current;

	public ASTFactoryImp(Diagnostic d){
		this.d = d;
		this.globalScope = new Scope();
		head = new Externaldc(null, null);
		current =head;
	}

	@Override
	public Statement createBlockStatement(Locatable loc, List<Statement> statements) {
		Statement block = new Block(loc,statements);
		return block;
	}

	@Override
	public Statement createBreakStatement(Locatable loc) {
		Statement b = new BreakStatment(loc);
		return b;
	}

	@Override
	public Statement createContinueStatement(Locatable loc) {
		Statement con = new ContinueStatment(loc);
		return con;
	}

	@Override
	public Statement createDeclarationStatement(Type type, Token name, Expression init) {
		 Statement dec = new Declaration(type, name , init);
		return dec;
	}

	@Override
	public Statement createErrorStatement(Locatable loc) {
		Statement error = new Error(loc);
		return error;
	}

	@Override
	public Statement createExpressionStatement(Locatable loc, Expression expression) {
		Statement exp = new ExpressionStatement(loc, expression);
		return exp;
	}

	@Override
	public Statement createIfStatement(Locatable loc, Expression condition, Statement consequence,
			Statement alternative) {
		Statement If = new IfStatement(loc, condition, consequence, alternative);
		return If;
	}

	@Override
	public Statement createReturnStatement(Locatable loc, Expression expression) {
		Statement ret = new ReturnStatement(loc,expression);
		return ret;
	}

	@Override
	public Statement createWhileStatement(Locatable loc, Expression condition, Statement body, Expression invariant,
			Expression term, Token loopBound) {
		Statement While = new WhileStatement(loc,condition, body, invariant,term,loopBound);
		return While;
	}

	@Override
	public Statement createAssumeStatement(Locatable loc, Expression condition) {
		Statement assume = new AssumeStatment(loc,condition);
		return assume;
	}

	@Override
	public Statement createAssertStatement(Locatable loc, Expression condition) {
		Statement a = new AssertStatment(loc,condition);
		return a;
	}

	@Override
	public Type createFunctionType(Type returnType, List<Type> parameters) {
		Type f = new Func(returnType, parameters); 
		return f;
	}

	@Override
	public Type createPointerType(Type pointsTo) {
		Type point = new Pointer(pointsTo);
		return point;
	}

	@Override
	public Type createBaseType(TokenKind kind) {
		Type base = null;
		switch(kind){
			case CHAR :base = typeChar.getInstance();
			break;
			case INT: base = typeInt.getInstance() ;
			break;
			case VOID: base = typeVoid.getInstance();
			break;
			default:
			break;
		}
	
		return base;
	}

	@Override
	public Expression createBinaryExpression(Token operator, Expression left, Expression right) {
		Expression exp = new BinaryExpression(operator, left, right);
		return exp ;
	}

	@Override
	public Expression createCallExpression(Token token, Expression callee, List<Expression> arguments) {
		Expression exp = new FunctionCall(token,callee, arguments);
		return exp;
	}

	@Override
	public Expression createConditionalExpression(Token token, Expression condition, Expression consequence,
			Expression alternative) {
		Expression exp = new CondExp(condition, consequence, alternative);
		return exp;
	}

	@Override
	public Expression createUnaryExpression(Token operator, boolean postfix, Expression operand) {
		Expression exp = new UnaryExpression(operator,postfix, operand);
		return exp;
	}

	@Override
	public Expression createPrimaryExpression(Token token) {
		Expression base= null;
		switch(token.getKind()){
			case IDENTIFIER : base = new Variable(token);
			break;
			case NUMBER : base = new IntConst(token); 
			break;
			case CHARACTER : base = new CharConst(token);
			break;
			case STRING : base = new StringConst(token);
			break;
			default:
			break;
		}
		return base;
	}

	@Override
	public void createExternalDeclaration(Type type, Token name) {
		External dc = new Externaldc(type, name);
		current.setnext(dc);
		current=dc;
	}

	@Override
	public void createFunctionDefinition(Type type, Token name, List<Token> parameterNames, Statement body) {
			createExternalDeclaration(type, name);
		External fundef = new FuncDef(type, name, parameterNames, body);
		current.setnext(fundef);
		current=fundef;

	}

	public void checkSemantics(){
		current=head.getnext();
		List<String> defined = new ArrayList<String>();
		while(current!=null)
		{ current.checktype(d, globalScope, defined);
			current=current.getnext();
			
		}

	}

	public void generateCode(final MipsAsmGen out){
		current = head.getnext();
		List<String> generated = new ArrayList<String>();
		while(current!=null)
		{ current.generateCode(out, generated);
			current=current.getnext();
			
		}

	}


}
