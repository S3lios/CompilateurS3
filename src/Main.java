package src;

import java.util.List;

import src.expression.Expression;
import src.expression.IntValue;
import src.instruction.Instruction;
import src.instruction.InstructionList;
import src.instruction.VariableAffectation;
import src.instruction.VariableDeclaration;
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
			.addToken(Token.TK_EQUAL)
			.addToken(Token.TK_SEMICOLON)
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
		NTSymbol IntValue = new NTSymbol("IntValue");

		// Rules

		InstructionList
		.addRule(Rule.pattern(Instruction).result(arr -> new InstructionList().add((Instruction) arr[0])))
		.addRule(Rule.pattern(Instruction, InstructionList).result(arr -> ((InstructionList) arr[1]).add((Instruction) arr[0])));


		Instruction
		.addRule(Rule.pattern(VariableDeclaration, Token.TK_SEMICOLON).result(arr -> arr[0]))
		.addRule(Rule.pattern(VariableAffectation, Token.TK_SEMICOLON).result(arr -> arr[0]));

		VariableDeclaration
		.addRule(Rule.pattern(Type, Token.TK_IDENTIFIER, Token.TK_EQUAL, Expression).result(arr -> new VariableDeclaration((Type) arr[0], (String) arr[1], (Expression) arr[3])));

		VariableAffectation
		.addRule(Rule.pattern(Token.TK_IDENTIFIER, Token.TK_EQUAL, Expression).result(arr -> new VariableAffectation((String) arr[0], (Expression) arr[2])));
		
		Type
		.addRule(Rule.pattern(TK_INT).result(arr -> PrimaryType.INT));

		Expression
		.addRule(Rule.pattern(IntValue).result(arr -> new IntValue((int) arr[0])));

		IntValue
		.addRule(Rule.pattern(Token.TK_INTEGER).result(arr -> Integer.parseInt((String) arr[0])));


		Parser parser = new Parser(InstructionList);


		List<TokenInstance> tokens = lexer.lex("int a = 1; int b = 2; a = 3;");
		Object o = parser.parse(tokens);

		
	}
}