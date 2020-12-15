// Generated from IsiLang.g4 by ANTLR 4.7.1
package br.com.professorisidro.isilanguage.parser;

	import br.com.professorisidro.isilanguage.datastructures.IsiSymbol;
	import br.com.professorisidro.isilanguage.datastructures.IsiVariable;
	import br.com.professorisidro.isilanguage.datastructures.IsiSymbolTable;
	import br.com.professorisidro.isilanguage.exceptions.IsiSemanticException;
	import br.com.professorisidro.isilanguage.ast.IsiProgram;
	import br.com.professorisidro.isilanguage.ast.AbstractCommand;
	import br.com.professorisidro.isilanguage.ast.CommandLeitura;
	import br.com.professorisidro.isilanguage.ast.CommandEscrita;
	import br.com.professorisidro.isilanguage.ast.CommandAtribuicao;
	import br.com.professorisidro.isilanguage.ast.CommandDecisao;
	import br.com.professorisidro.isilanguage.ast.CommandEnquanto;
	import br.com.professorisidro.isilanguage.ast.CommandComentario;
	import java.util.ArrayList;
	import java.util.Stack;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class IsiLangLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		ASP=10, AP=11, FP=12, SC=13, PF=14, OP=15, ATTR=16, VIR=17, ACH=18, FCH=19, 
		COMMENTA=20, COMMENTF=21, OPREL=22, ID=23, NUMBER=24, TEXT=25, LET=26, 
		WS=27;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"ASP", "AP", "FP", "SC", "PF", "OP", "ATTR", "VIR", "ACH", "FCH", "COMMENTA", 
		"COMMENTF", "OPREL", "ID", "NUMBER", "TEXT", "LET", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'programa'", "'fimprog.'", "'numero'", "'texto'", "'leia'", "'escreva'", 
		"'se'", "'senao'", "'enquanto'", "'\"'", "'('", "')'", "';'", "'.'", null, 
		"':='", "','", "'{'", "'}'", "'/*'", "'*/'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, "ASP", "AP", 
		"FP", "SC", "PF", "OP", "ATTR", "VIR", "ACH", "FCH", "COMMENTA", "COMMENTF", 
		"OPREL", "ID", "NUMBER", "TEXT", "LET", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


		private int _tipo;
		private String _varName;
		private String _varValue;
		private IsiSymbolTable symbolTable = new IsiSymbolTable();
		private IsiSymbol symbol;
		private IsiProgram program = new IsiProgram();
		private ArrayList<AbstractCommand> curThread;
		private Stack<ArrayList<AbstractCommand>> stack = new Stack<ArrayList<AbstractCommand>>();
		private String _readID;
		private String _writeID;
		private String _exprID;
		private String _exprContent;
		private String _exprDecision;
		private ArrayList<AbstractCommand> listaTrue;
		private ArrayList<AbstractCommand> listaFalse;
		private ArrayList<AbstractCommand> listaEnquanto;
		private Stack<String> exprDecisions = new Stack<String>();	
		private Stack<ArrayList<AbstractCommand>> listasTrue = new Stack<ArrayList<AbstractCommand>>();
		
		public void verificaID(String id, boolean atribuir){
			if (!symbolTable.exists(id)){
				throw new IsiSemanticException("Symbol "+id+" not declared");
			} else {
				IsiVariable var = (IsiVariable) symbolTable.get(id);
				if (!var.getAttributed() && !atribuir) {
					throw new IsiSemanticException("Symbol "+id+" not attributed");			
				} else {
					var.setAttributed(true);
				}
			}
			marcarUsado(id);		
		}

		public void verificaTipo(String id, int tipoExpr){
			IsiVariable var = (IsiVariable) symbolTable.get(id);
			if (tipoExpr != var.getType()) {
		       	throw new IsiSemanticException("Can't attribute to symbol "+id+". Expected type "+var.getType()+" but got type "+tipoExpr); 
			}
		}
		
		public void verificaUsado(String id) {
			IsiVariable var = (IsiVariable) symbolTable.get(id);
			if (var.getUsedCount() == 0) {
		       	System.out.println("WARNING: Symbol "+id+" was declared but not used"); 
			}	
		}
		
		public void marcarUsado(String id) {
			IsiVariable var = (IsiVariable) symbolTable.get(id);
			var.increaseUsedCount();
		}
		
		public void exibeComandos(){
			for (AbstractCommand c: program.getComandos()){
				System.out.println(c);
			}
		}
		
		public void generateCode(){
			program.generateTarget();
		}


	public IsiLangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "IsiLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\35\u00c4\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21"+
		"\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u009c\n\27\3\30\3\30\3\30"+
		"\7\30\u00a1\n\30\f\30\16\30\u00a4\13\30\3\31\6\31\u00a7\n\31\r\31\16\31"+
		"\u00a8\3\31\3\31\6\31\u00ad\n\31\r\31\16\31\u00ae\5\31\u00b1\n\31\3\32"+
		"\3\32\7\32\u00b5\n\32\f\32\16\32\u00b8\13\32\3\32\3\32\3\33\6\33\u00bd"+
		"\n\33\r\33\16\33\u00be\3\34\3\34\3\34\3\34\2\2\35\3\3\5\4\7\5\t\6\13\7"+
		"\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25"+
		")\26+\27-\30/\31\61\32\63\33\65\34\67\35\3\2\b\5\2,-//\61\61\4\2>>@@\3"+
		"\2\62;\3\2$$\4\2C\\c|\5\2\13\f\17\17\"\"\2\u00ce\2\3\3\2\2\2\2\5\3\2\2"+
		"\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"+
		"\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3"+
		"\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3"+
		"\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\39\3\2\2\2\5B\3\2\2\2\7K\3\2\2\2\tR\3"+
		"\2\2\2\13X\3\2\2\2\r]\3\2\2\2\17e\3\2\2\2\21h\3\2\2\2\23n\3\2\2\2\25w"+
		"\3\2\2\2\27y\3\2\2\2\31{\3\2\2\2\33}\3\2\2\2\35\177\3\2\2\2\37\u0081\3"+
		"\2\2\2!\u0083\3\2\2\2#\u0086\3\2\2\2%\u0088\3\2\2\2\'\u008a\3\2\2\2)\u008c"+
		"\3\2\2\2+\u008f\3\2\2\2-\u009b\3\2\2\2/\u009d\3\2\2\2\61\u00a6\3\2\2\2"+
		"\63\u00b2\3\2\2\2\65\u00bc\3\2\2\2\67\u00c0\3\2\2\29:\7r\2\2:;\7t\2\2"+
		";<\7q\2\2<=\7i\2\2=>\7t\2\2>?\7c\2\2?@\7o\2\2@A\7c\2\2A\4\3\2\2\2BC\7"+
		"h\2\2CD\7k\2\2DE\7o\2\2EF\7r\2\2FG\7t\2\2GH\7q\2\2HI\7i\2\2IJ\7\60\2\2"+
		"J\6\3\2\2\2KL\7p\2\2LM\7w\2\2MN\7o\2\2NO\7g\2\2OP\7t\2\2PQ\7q\2\2Q\b\3"+
		"\2\2\2RS\7v\2\2ST\7g\2\2TU\7z\2\2UV\7v\2\2VW\7q\2\2W\n\3\2\2\2XY\7n\2"+
		"\2YZ\7g\2\2Z[\7k\2\2[\\\7c\2\2\\\f\3\2\2\2]^\7g\2\2^_\7u\2\2_`\7e\2\2"+
		"`a\7t\2\2ab\7g\2\2bc\7x\2\2cd\7c\2\2d\16\3\2\2\2ef\7u\2\2fg\7g\2\2g\20"+
		"\3\2\2\2hi\7u\2\2ij\7g\2\2jk\7p\2\2kl\7c\2\2lm\7q\2\2m\22\3\2\2\2no\7"+
		"g\2\2op\7p\2\2pq\7s\2\2qr\7w\2\2rs\7c\2\2st\7p\2\2tu\7v\2\2uv\7q\2\2v"+
		"\24\3\2\2\2wx\7$\2\2x\26\3\2\2\2yz\7*\2\2z\30\3\2\2\2{|\7+\2\2|\32\3\2"+
		"\2\2}~\7=\2\2~\34\3\2\2\2\177\u0080\7\60\2\2\u0080\36\3\2\2\2\u0081\u0082"+
		"\t\2\2\2\u0082 \3\2\2\2\u0083\u0084\7<\2\2\u0084\u0085\7?\2\2\u0085\""+
		"\3\2\2\2\u0086\u0087\7.\2\2\u0087$\3\2\2\2\u0088\u0089\7}\2\2\u0089&\3"+
		"\2\2\2\u008a\u008b\7\177\2\2\u008b(\3\2\2\2\u008c\u008d\7\61\2\2\u008d"+
		"\u008e\7,\2\2\u008e*\3\2\2\2\u008f\u0090\7,\2\2\u0090\u0091\7\61\2\2\u0091"+
		",\3\2\2\2\u0092\u009c\t\3\2\2\u0093\u0094\7@\2\2\u0094\u009c\7?\2\2\u0095"+
		"\u0096\7>\2\2\u0096\u009c\7?\2\2\u0097\u0098\7?\2\2\u0098\u009c\7?\2\2"+
		"\u0099\u009a\7#\2\2\u009a\u009c\7?\2\2\u009b\u0092\3\2\2\2\u009b\u0093"+
		"\3\2\2\2\u009b\u0095\3\2\2\2\u009b\u0097\3\2\2\2\u009b\u0099\3\2\2\2\u009c"+
		".\3\2\2\2\u009d\u00a2\5\65\33\2\u009e\u00a1\5\65\33\2\u009f\u00a1\5\61"+
		"\31\2\u00a0\u009e\3\2\2\2\u00a0\u009f\3\2\2\2\u00a1\u00a4\3\2\2\2\u00a2"+
		"\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\60\3\2\2\2\u00a4\u00a2\3\2\2"+
		"\2\u00a5\u00a7\t\4\2\2\u00a6\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a6"+
		"\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00b0\3\2\2\2\u00aa\u00ac\7\60\2\2"+
		"\u00ab\u00ad\t\4\2\2\u00ac\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00ac"+
		"\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b1\3\2\2\2\u00b0\u00aa\3\2\2\2\u00b0"+
		"\u00b1\3\2\2\2\u00b1\62\3\2\2\2\u00b2\u00b6\5\25\13\2\u00b3\u00b5\n\5"+
		"\2\2\u00b4\u00b3\3\2\2\2\u00b5\u00b8\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b6"+
		"\u00b7\3\2\2\2\u00b7\u00b9\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b9\u00ba\5\25"+
		"\13\2\u00ba\64\3\2\2\2\u00bb\u00bd\t\6\2\2\u00bc\u00bb\3\2\2\2\u00bd\u00be"+
		"\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\66\3\2\2\2\u00c0"+
		"\u00c1\t\7\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c3\b\34\2\2\u00c38\3\2\2\2"+
		"\f\2\u009b\u00a0\u00a2\u00a8\u00ae\u00b0\u00b6\u00bc\u00be\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}