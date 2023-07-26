package src;

import java.util.List;

import src.expression.Expression;
import src.expression.IntValue;
import src.expression.Operation;
import src.expression.VariableExpression;
import src.instruction.Body;
import src.instruction.Instruction;
import src.instruction.InstructionList;
import src.instruction.VariableAffectation;
import src.instruction.VariableDeclaration;
import src.instruction.WhileLoop;
import src.lexer.Lexer;
import src.lexer.Token;
import src.lexer.TokenInstance;
import src.parser.NTSymbol;
import src.parser.Parser;
import src.parser.Rule;
import src.type.PrimaryType;
import src.type.Type;

public class Main {

	public static void main(String[] args) {

		// Personnal token
		Token TK_INT = new Token("TK_INT", "int");


		Lexer lexer = Lexer.createLexer()
			.addToken(TK_INT)
			.addToken(Token.TK_WHILE)
			.addToken(Token.TK_LBRACE)
			.addToken(Token.TK_RBRACE)
			.addToken(Token.TK_LPAREN)
			.addToken(Token.TK_RPAREN)
			.addToken(Token.TK_EQUAL)
			.addToken(Token.TK_SEMICOLON)
			.addToken(Token.TK_PLUS)
			.addToken(Token.TK_MINUS)
			.addToken(Token.TK_STAR)
			.addToken(Token.TK_SLASH)
			.addToken(Token.TK_PERCENT)
			.addToken(Token.TK_EXCLAMATION)
			.addToken(Token.TK_GREATER_EQUAL)
			.addToken(Token.TK_GREATER)
			.addToken(Token.TK_LESS_EQUAL)
			.addToken(Token.TK_LESS)
			.addToken(Token.TK_AMPERSAND)
			.addToken(Token.TK_PIPE)
			.addToken(Token.TK_IDENTIFIER)
			.addToken(Token.TK_INTEGER);

		// Symbol declaration
		NTSymbol S = new NTSymbol("S");
		NTSymbol Instruction = new NTSymbol("Instruction");
		NTSymbol InstructionList = new NTSymbol("InstructionList");
		NTSymbol VariableDeclaration = new NTSymbol("VariableDeclaration");
		NTSymbol VariableAffectation = new NTSymbol("VariableAffectation");
		NTSymbol Type = new NTSymbol("Type");
		NTSymbol Expression = new NTSymbol("Expression");

		// Rules
		InstructionList
		.addRule(Rule.pattern(Instruction).result(arr -> new InstructionList().add((Instruction) arr[0])))
		.addRule(Rule.pattern(Instruction, InstructionList).result(arr -> ((InstructionList) arr[1]).add((Instruction) arr[0])));


		Instruction
		.addRule(Rule.pattern(VariableDeclaration, Token.TK_SEMICOLON).result(arr -> arr[0]))
		.addRule(Rule.pattern(VariableAffectation, Token.TK_SEMICOLON).result(arr -> arr[0]))
		.addRule(Rule.pattern(Token.TK_LBRACE, InstructionList, Token.TK_RBRACE).result(arr -> new Body((InstructionList) arr[1])))
		.addRule(Rule.pattern(Token.TK_WHILE, Token.TK_LPAREN, Expression, Token.TK_RPAREN, Instruction).result(arr -> new WhileLoop((Expression) arr[2], (Instruction) arr[4])));


		VariableDeclaration
		.addRule(Rule.pattern(Type, Token.TK_IDENTIFIER, Token.TK_EQUAL, Expression).result(arr -> new VariableDeclaration((Type) arr[0], (String) arr[1], (Expression) arr[3])));

		VariableAffectation
		.addRule(Rule.pattern(Token.TK_IDENTIFIER, Token.TK_EQUAL, Expression).result(arr -> new VariableAffectation((String) arr[0], (Expression) arr[2])));
		
		Type
		.addRule(Rule.pattern(TK_INT).result(arr -> PrimaryType.INT));

		Expression
		.addRule(Rule.pattern(Expression, Token.TK_PLUS, Expression).result(arr -> new Operation(Operation.Operator.PLUS, (Expression) arr[0], (Expression) arr[2])))
		.addRule(Rule.pattern(Expression, Token.TK_MINUS, Expression).result(arr -> new Operation(Operation.Operator.MINUS, (Expression) arr[0], (Expression) arr[2])))
		.addRule(Rule.pattern(Expression, Token.TK_STAR, Expression).result(arr -> new Operation(Operation.Operator.TIMES, (Expression) arr[0], (Expression) arr[2])))
		.addRule(Rule.pattern(Expression, Token.TK_SLASH, Expression).result(arr -> new Operation(Operation.Operator.DIVIDE, (Expression) arr[0], (Expression) arr[2])))
		.addRule(Rule.pattern(Expression, Token.TK_PERCENT, Expression).result(arr -> new Operation(Operation.Operator.MODULO, (Expression) arr[0], (Expression) arr[2])))
		.addRule(Rule.pattern(Expression, Token.TK_EQUAL, Expression).result(arr -> new Operation(Operation.Operator.EQUAL, (Expression) arr[0], (Expression) arr[2])))
		.addRule(Rule.pattern(Expression, Token.TK_EXCLAMATION, Token.TK_EQUAL,Expression).result(arr -> new Operation(Operation.Operator.NOT_EQUAL, (Expression) arr[0], (Expression) arr[3])))
		.addRule(Rule.pattern(Expression, Token.TK_GREATER, Expression).result(arr -> new Operation(Operation.Operator.GREATER, (Expression) arr[0], (Expression) arr[2])))
		.addRule(Rule.pattern(Expression, Token.TK_GREATER_EQUAL, Expression).result(arr -> new Operation(Operation.Operator.GREATER_EQUAL, (Expression) arr[0], (Expression) arr[2])))
		.addRule(Rule.pattern(Expression, Token.TK_LESS, Expression).result(arr -> new Operation(Operation.Operator.LESS, (Expression) arr[0], (Expression) arr[2])))
		.addRule(Rule.pattern(Expression, Token.TK_LESS_EQUAL, Expression).result(arr -> new Operation(Operation.Operator.LESS_EQUAL, (Expression) arr[0], (Expression) arr[2])))
		.addRule(Rule.pattern(Expression, Token.TK_AMPERSAND, Token.TK_AMPERSAND, Expression).result(arr -> new Operation(Operation.Operator.AND, (Expression) arr[0], (Expression) arr[3])))
		.addRule(Rule.pattern(Expression, Token.TK_PIPE, Expression).result(arr -> new Operation(Operation.Operator.OR, (Expression) arr[0], (Expression) arr[2])))
		.addRule(Rule.pattern(Token.TK_EXCLAMATION, Expression).result(arr -> new Operation(Operation.Operator.NOT, (Expression) arr[1], null)))
		.addRule(Rule.pattern(Token.TK_LPAREN, Expression, Token.TK_RPAREN).result(arr -> arr[1]))
		.addRule(Rule.pattern(Token.TK_INTEGER).result(arr -> new IntValue((int) Integer.parseInt((String) arr[0]))))
		.addRule(Rule.pattern(Token.TK_IDENTIFIER).result(arr -> new VariableExpression((String) arr[0])));



		Parser parser = new Parser(InstructionList);


		List<TokenInstance> tokens = lexer.lex("int a  = 1; while (1) {a = a + 1; b = b + 2;}");
		Object o = parser.parse(tokens);

		
	}
}