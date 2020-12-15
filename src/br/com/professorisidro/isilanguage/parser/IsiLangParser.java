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

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class IsiLangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		ASP=10, AP=11, FP=12, SC=13, PF=14, OP=15, ATTR=16, VIR=17, ACH=18, FCH=19, 
		COMMENTA=20, COMMENTF=21, OPREL=22, ID=23, NUMBER=24, TEXT=25, LET=26, 
		WS=27;
	public static final int
		RULE_prog = 0, RULE_decl = 1, RULE_declaravar = 2, RULE_tipo = 3, RULE_bloco = 4, 
		RULE_cmd = 5, RULE_cmdleitura = 6, RULE_cmdescrita = 7, RULE_cmdattrib = 8, 
		RULE_cmdselecao = 9, RULE_cmdenquanto = 10, RULE_cmdcomentario = 11, RULE_expr = 12, 
		RULE_termo = 13;
	public static final String[] ruleNames = {
		"prog", "decl", "declaravar", "tipo", "bloco", "cmd", "cmdleitura", "cmdescrita", 
		"cmdattrib", "cmdselecao", "cmdenquanto", "cmdcomentario", "expr", "termo"
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

	@Override
	public String getGrammarFileName() { return "IsiLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


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

	public IsiLangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public DeclContext decl() {
			return getRuleContext(DeclContext.class,0);
		}
		public BlocoContext bloco() {
			return getRuleContext(BlocoContext.class,0);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitProg(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			match(T__0);
			setState(29);
			decl();
			setState(30);
			bloco();
			setState(31);
			match(T__1);
			  program.setVarTable(symbolTable);
			           	  program.setComandos(stack.pop());
			           	  for (IsiSymbol sym: symbolTable.getAll()) {
							IsiVariable var = (IsiVariable) sym; 
			           	  	verificaUsado(var.getName());
			           	  }
			           
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclContext extends ParserRuleContext {
		public List<DeclaravarContext> declaravar() {
			return getRuleContexts(DeclaravarContext.class);
		}
		public DeclaravarContext declaravar(int i) {
			return getRuleContext(DeclaravarContext.class,i);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitDecl(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_decl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(34);
				declaravar();
				}
				}
				setState(37); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__2 || _la==T__3 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclaravarContext extends ParserRuleContext {
		public TipoContext tipo() {
			return getRuleContext(TipoContext.class,0);
		}
		public List<TerminalNode> ID() { return getTokens(IsiLangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(IsiLangParser.ID, i);
		}
		public TerminalNode PF() { return getToken(IsiLangParser.PF, 0); }
		public List<TerminalNode> VIR() { return getTokens(IsiLangParser.VIR); }
		public TerminalNode VIR(int i) {
			return getToken(IsiLangParser.VIR, i);
		}
		public DeclaravarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaravar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterDeclaravar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitDeclaravar(this);
		}
	}

	public final DeclaravarContext declaravar() throws RecognitionException {
		DeclaravarContext _localctx = new DeclaravarContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declaravar);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			tipo();
			setState(40);
			match(ID);

				                  _varName = _input.LT(-1).getText();
				                  _varValue = null;
				                  symbol = new IsiVariable(_varName, _tipo, _varValue);
				                  if (!symbolTable.exists(_varName)){
				                     symbolTable.add(symbol);	
				                  }
				                  else{
				                  	 throw new IsiSemanticException("Symbol "+_varName+" already declared");
				                  }
			                    
			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VIR) {
				{
				{
				setState(42);
				match(VIR);
				setState(43);
				match(ID);

					                  _varName = _input.LT(-1).getText();
					                  _varValue = null;
					                  symbol = new IsiVariable(_varName, _tipo, _varValue);
					                  if (!symbolTable.exists(_varName)){
					                     symbolTable.add(symbol);	
					                  }
					                  else{
					                  	 throw new IsiSemanticException("Symbol "+_varName+" already declared");
					                  }
				                    
				}
				}
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(50);
			match(PF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TipoContext extends ParserRuleContext {
		public TipoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tipo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterTipo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitTipo(this);
		}
	}

	public final TipoContext tipo() throws RecognitionException {
		TipoContext _localctx = new TipoContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_tipo);
		try {
			setState(56);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				match(T__2);
				 _tipo = IsiVariable.NUMBER; 
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(54);
				match(T__3);
				 _tipo = IsiVariable.TEXT;   
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlocoContext extends ParserRuleContext {
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public BlocoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bloco; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterBloco(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitBloco(this);
		}
	}

	public final BlocoContext bloco() throws RecognitionException {
		BlocoContext _localctx = new BlocoContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_bloco);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 curThread = new ArrayList<AbstractCommand>(); 
				        stack.push(curThread);  
			          
			setState(60); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(59);
				cmd();
				}
				}
				setState(62); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__8) | (1L << COMMENTA) | (1L << ID))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdContext extends ParserRuleContext {
		public CmdleituraContext cmdleitura() {
			return getRuleContext(CmdleituraContext.class,0);
		}
		public CmdescritaContext cmdescrita() {
			return getRuleContext(CmdescritaContext.class,0);
		}
		public CmdattribContext cmdattrib() {
			return getRuleContext(CmdattribContext.class,0);
		}
		public CmdselecaoContext cmdselecao() {
			return getRuleContext(CmdselecaoContext.class,0);
		}
		public CmdenquantoContext cmdenquanto() {
			return getRuleContext(CmdenquantoContext.class,0);
		}
		public CmdcomentarioContext cmdcomentario() {
			return getRuleContext(CmdcomentarioContext.class,0);
		}
		public CmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmd(this);
		}
	}

	public final CmdContext cmd() throws RecognitionException {
		CmdContext _localctx = new CmdContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_cmd);
		try {
			setState(70);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				cmdleitura();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				cmdescrita();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(66);
				cmdattrib();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 4);
				{
				setState(67);
				cmdselecao();
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 5);
				{
				setState(68);
				cmdenquanto();
				}
				break;
			case COMMENTA:
				enterOuterAlt(_localctx, 6);
				{
				setState(69);
				cmdcomentario();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdleituraContext extends ParserRuleContext {
		public TerminalNode AP() { return getToken(IsiLangParser.AP, 0); }
		public TerminalNode FP() { return getToken(IsiLangParser.FP, 0); }
		public TerminalNode PF() { return getToken(IsiLangParser.PF, 0); }
		public TerminalNode ID() { return getToken(IsiLangParser.ID, 0); }
		public TerminalNode TEXT() { return getToken(IsiLangParser.TEXT, 0); }
		public CmdleituraContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdleitura; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdleitura(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdleitura(this);
		}
	}

	public final CmdleituraContext cmdleitura() throws RecognitionException {
		CmdleituraContext _localctx = new CmdleituraContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_cmdleitura);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(T__4);
			setState(73);
			match(AP);
			setState(77);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(74);
				match(ID);
				 verificaID(_input.LT(-1).getText(), true); 
				}
				break;
			case TEXT:
				{
				setState(76);
				match(TEXT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			 _readID = _input.LT(-1).getText(); 
			setState(80);
			match(FP);
			setState(81);
			match(PF);

			              	IsiVariable var = (IsiVariable)symbolTable.get(_readID);
			              	CommandLeitura cmd = new CommandLeitura(_readID, var);
			              	stack.peek().add(cmd);
			              
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdescritaContext extends ParserRuleContext {
		public TerminalNode AP() { return getToken(IsiLangParser.AP, 0); }
		public TerminalNode FP() { return getToken(IsiLangParser.FP, 0); }
		public TerminalNode PF() { return getToken(IsiLangParser.PF, 0); }
		public TerminalNode ID() { return getToken(IsiLangParser.ID, 0); }
		public TerminalNode TEXT() { return getToken(IsiLangParser.TEXT, 0); }
		public CmdescritaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdescrita; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdescrita(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdescrita(this);
		}
	}

	public final CmdescritaContext cmdescrita() throws RecognitionException {
		CmdescritaContext _localctx = new CmdescritaContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_cmdescrita);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			match(T__5);
			setState(85);
			match(AP);
			setState(89);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(86);
				match(ID);
				 verificaID(_input.LT(-1).getText(), false);
				}
				break;
			case TEXT:
				{
				setState(88);
				match(TEXT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			 _writeID = _input.LT(-1).getText(); 
			setState(92);
			match(FP);
			setState(93);
			match(PF);

			               	  CommandEscrita cmd = new CommandEscrita(_writeID);
			               	  stack.peek().add(cmd);
			               
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdattribContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(IsiLangParser.ID, 0); }
		public TerminalNode ATTR() { return getToken(IsiLangParser.ATTR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode PF() { return getToken(IsiLangParser.PF, 0); }
		public CmdattribContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdattrib; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdattrib(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdattrib(this);
		}
	}

	public final CmdattribContext cmdattrib() throws RecognitionException {
		CmdattribContext _localctx = new CmdattribContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_cmdattrib);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(ID);
			 verificaID(_input.LT(-1).getText(), true);
			                    _exprID = _input.LT(-1).getText();
			                   
			setState(98);
			match(ATTR);
			 _exprContent = ""; 
			setState(100);
			expr();
			setState(101);
			match(PF);

			               	 CommandAtribuicao cmd = new CommandAtribuicao(_exprID, _exprContent);
			               	 stack.peek().add(cmd);
			               
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdselecaoContext extends ParserRuleContext {
		public TerminalNode AP() { return getToken(IsiLangParser.AP, 0); }
		public List<TerminalNode> ID() { return getTokens(IsiLangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(IsiLangParser.ID, i);
		}
		public TerminalNode OPREL() { return getToken(IsiLangParser.OPREL, 0); }
		public TerminalNode FP() { return getToken(IsiLangParser.FP, 0); }
		public List<TerminalNode> ACH() { return getTokens(IsiLangParser.ACH); }
		public TerminalNode ACH(int i) {
			return getToken(IsiLangParser.ACH, i);
		}
		public List<TerminalNode> FCH() { return getTokens(IsiLangParser.FCH); }
		public TerminalNode FCH(int i) {
			return getToken(IsiLangParser.FCH, i);
		}
		public TerminalNode NUMBER() { return getToken(IsiLangParser.NUMBER, 0); }
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public CmdselecaoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdselecao; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdselecao(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdselecao(this);
		}
	}

	public final CmdselecaoContext cmdselecao() throws RecognitionException {
		CmdselecaoContext _localctx = new CmdselecaoContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_cmdselecao);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(T__6);
			setState(105);
			match(AP);
			setState(106);
			match(ID);
			 
			                    		verificaID(_input.LT(-1).getText(), false);
			                    		_exprDecision = _input.LT(-1).getText(); 
			                    		exprDecisions.push(_exprDecision);
			                    	  
			setState(108);
			match(OPREL);
			 
			                    		_exprDecision = exprDecisions.pop();
			                    		_exprDecision += _input.LT(-1).getText(); 
			                    		exprDecisions.push(_exprDecision);
			                    	  
			setState(113);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(110);
				match(ID);
				 verificaID(_input.LT(-1).getText(), false); 
				}
				break;
			case NUMBER:
				{
				setState(112);
				match(NUMBER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}

			                    		_exprDecision = exprDecisions.pop();                    
			                    		_exprDecision += _input.LT(-1).getText(); 
			                    		exprDecisions.push(_exprDecision);
			                    		
			setState(116);
			match(FP);
			setState(117);
			match(ACH);
			 curThread = new ArrayList<AbstractCommand>(); 
			                      stack.push(curThread);
			                    
			setState(120); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(119);
				cmd();
				}
				}
				setState(122); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__8) | (1L << COMMENTA) | (1L << ID))) != 0) );
			setState(124);
			match(FCH);

			                       listaTrue = stack.pop();
			                       listasTrue.push(listaTrue);	
			                    
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(126);
				match(T__7);
				setState(127);
				match(ACH);

				                   	 	curThread = new ArrayList<AbstractCommand>();
				                   	 	stack.push(curThread);
				                   	 
				{
				setState(130); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(129);
					cmd();
					}
					}
					setState(132); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__8) | (1L << COMMENTA) | (1L << ID))) != 0) );
				}
				setState(134);
				match(FCH);

				                        _exprDecision = exprDecisions.pop();      
				                        listaTrue = listasTrue.pop();              	
				                   		listaFalse = stack.pop();
				                   		CommandDecisao cmd = new CommandDecisao(_exprDecision, listaTrue, listaFalse);
				                   		stack.peek().add(cmd);
				                   	
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdenquantoContext extends ParserRuleContext {
		public TerminalNode AP() { return getToken(IsiLangParser.AP, 0); }
		public List<TerminalNode> ID() { return getTokens(IsiLangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(IsiLangParser.ID, i);
		}
		public TerminalNode OPREL() { return getToken(IsiLangParser.OPREL, 0); }
		public TerminalNode FP() { return getToken(IsiLangParser.FP, 0); }
		public TerminalNode ACH() { return getToken(IsiLangParser.ACH, 0); }
		public TerminalNode FCH() { return getToken(IsiLangParser.FCH, 0); }
		public TerminalNode NUMBER() { return getToken(IsiLangParser.NUMBER, 0); }
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public CmdenquantoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdenquanto; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdenquanto(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdenquanto(this);
		}
	}

	public final CmdenquantoContext cmdenquanto() throws RecognitionException {
		CmdenquantoContext _localctx = new CmdenquantoContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_cmdenquanto);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(T__8);
			setState(140);
			match(AP);
			setState(141);
			match(ID);
			 
			                    			verificaID(_input.LT(-1).getText(), false);
			                    			_exprDecision = _input.LT(-1).getText(); 
			                    			exprDecisions.push(_exprDecision); 
			                    			
			setState(143);
			match(OPREL);
			 
			                    			_exprDecision = exprDecisions.pop();
			                    			_exprDecision += _input.LT(-1).getText(); 
											exprDecisions.push(_exprDecision);
			                    		
			setState(148);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(145);
				match(ID);
				 verificaID(_input.LT(-1).getText(), false); 
				}
				break;
			case NUMBER:
				{
				setState(147);
				match(NUMBER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}

			                    			_exprDecision = exprDecisions.pop();
			                    			_exprDecision += _input.LT(-1).getText(); 
			                    			exprDecisions.push(_exprDecision);
			                    		
			setState(151);
			match(FP);
			setState(152);
			match(ACH);
			 curThread = new ArrayList<AbstractCommand>(); 
					                      stack.push(curThread);
					                    
			setState(155); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(154);
				cmd();
				}
				}
				setState(157); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__8) | (1L << COMMENTA) | (1L << ID))) != 0) );
			setState(159);
			match(FCH);

										    listaEnquanto = stack.pop();
										    _exprDecision = exprDecisions.pop();
										    CommandEnquanto cmd = new CommandEnquanto(_exprDecision, listaEnquanto);
										    stack.peek().add(cmd);
										
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdcomentarioContext extends ParserRuleContext {
		public TerminalNode COMMENTA() { return getToken(IsiLangParser.COMMENTA, 0); }
		public TerminalNode TEXT() { return getToken(IsiLangParser.TEXT, 0); }
		public TerminalNode COMMENTF() { return getToken(IsiLangParser.COMMENTF, 0); }
		public CmdcomentarioContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdcomentario; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdcomentario(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdcomentario(this);
		}
	}

	public final CmdcomentarioContext cmdcomentario() throws RecognitionException {
		CmdcomentarioContext _localctx = new CmdcomentarioContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_cmdcomentario);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			match(COMMENTA);
			setState(163);
			match(TEXT);
			 _exprContent = _input.LT(-1).getText(); 
			setState(165);
			match(COMMENTF);

			               	 CommandComentario cmd = new CommandComentario( _exprContent);
			               	 stack.peek().add(cmd);
			                
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public List<TermoContext> termo() {
			return getRuleContexts(TermoContext.class);
		}
		public TermoContext termo(int i) {
			return getRuleContext(TermoContext.class,i);
		}
		public List<TerminalNode> OP() { return getTokens(IsiLangParser.OP); }
		public TerminalNode OP(int i) {
			return getToken(IsiLangParser.OP, i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			termo();
			setState(174);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OP) {
				{
				{
				setState(169);
				match(OP);
				 _exprContent += _input.LT(-1).getText();
				setState(171);
				termo();
				}
				}
				setState(176);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermoContext extends ParserRuleContext {
		public TerminalNode AP() { return getToken(IsiLangParser.AP, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode FP() { return getToken(IsiLangParser.FP, 0); }
		public TerminalNode ID() { return getToken(IsiLangParser.ID, 0); }
		public TerminalNode NUMBER() { return getToken(IsiLangParser.NUMBER, 0); }
		public TerminalNode TEXT() { return getToken(IsiLangParser.TEXT, 0); }
		public TermoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_termo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterTermo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitTermo(this);
		}
	}

	public final TermoContext termo() throws RecognitionException {
		TermoContext _localctx = new TermoContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_termo);
		try {
			setState(189);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AP:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(177);
				match(AP);
				 _exprContent += "("; 
				setState(179);
				expr();
				setState(180);
				match(FP);
				 _exprContent += ")"; 
				}
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(183);
				match(ID);
				 verificaID(_input.LT(-1).getText(), false);
								   IsiVariable var = (IsiVariable) symbolTable.get(_input.LT(-1).getText());
							       verificaTipo(_exprID, var.getType());
					               _exprContent += _input.LT(-1).getText();
				                 
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 3);
				{
				setState(185);
				match(NUMBER);
				 verificaTipo(_exprID, IsiVariable.NUMBER);
								       _exprContent += _input.LT(-1).getText();
				                     
				}
				break;
			case TEXT:
				enterOuterAlt(_localctx, 4);
				{
				setState(187);
				match(TEXT);
				 verificaTipo(_exprID, IsiVariable.TEXT);
				              		   _exprContent += _input.LT(-1).getText();
				            	     
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\35\u00c2\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\2\3\2\3\2\3\3"+
		"\6\3&\n\3\r\3\16\3\'\3\4\3\4\3\4\3\4\3\4\3\4\7\4\60\n\4\f\4\16\4\63\13"+
		"\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5;\n\5\3\6\3\6\6\6?\n\6\r\6\16\6@\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\5\7I\n\7\3\b\3\b\3\b\3\b\3\b\5\bP\n\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\t\3\t\3\t\3\t\3\t\5\t\\\n\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13t\n"+
		"\13\3\13\3\13\3\13\3\13\3\13\6\13{\n\13\r\13\16\13|\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\6\13\u0085\n\13\r\13\16\13\u0086\3\13\3\13\3\13\5\13\u008c"+
		"\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u0097\n\f\3\f\3\f\3\f\3"+
		"\f\3\f\6\f\u009e\n\f\r\f\16\f\u009f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\16\3\16\3\16\3\16\7\16\u00af\n\16\f\16\16\16\u00b2\13\16\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00c0\n\17\3\17"+
		"\2\2\20\2\4\6\b\n\f\16\20\22\24\26\30\32\34\2\2\2\u00c8\2\36\3\2\2\2\4"+
		"%\3\2\2\2\6)\3\2\2\2\b:\3\2\2\2\n<\3\2\2\2\fH\3\2\2\2\16J\3\2\2\2\20V"+
		"\3\2\2\2\22b\3\2\2\2\24j\3\2\2\2\26\u008d\3\2\2\2\30\u00a4\3\2\2\2\32"+
		"\u00aa\3\2\2\2\34\u00bf\3\2\2\2\36\37\7\3\2\2\37 \5\4\3\2 !\5\n\6\2!\""+
		"\7\4\2\2\"#\b\2\1\2#\3\3\2\2\2$&\5\6\4\2%$\3\2\2\2&\'\3\2\2\2\'%\3\2\2"+
		"\2\'(\3\2\2\2(\5\3\2\2\2)*\5\b\5\2*+\7\31\2\2+\61\b\4\1\2,-\7\23\2\2-"+
		".\7\31\2\2.\60\b\4\1\2/,\3\2\2\2\60\63\3\2\2\2\61/\3\2\2\2\61\62\3\2\2"+
		"\2\62\64\3\2\2\2\63\61\3\2\2\2\64\65\7\20\2\2\65\7\3\2\2\2\66\67\7\5\2"+
		"\2\67;\b\5\1\289\7\6\2\29;\b\5\1\2:\66\3\2\2\2:8\3\2\2\2;\t\3\2\2\2<>"+
		"\b\6\1\2=?\5\f\7\2>=\3\2\2\2?@\3\2\2\2@>\3\2\2\2@A\3\2\2\2A\13\3\2\2\2"+
		"BI\5\16\b\2CI\5\20\t\2DI\5\22\n\2EI\5\24\13\2FI\5\26\f\2GI\5\30\r\2HB"+
		"\3\2\2\2HC\3\2\2\2HD\3\2\2\2HE\3\2\2\2HF\3\2\2\2HG\3\2\2\2I\r\3\2\2\2"+
		"JK\7\7\2\2KO\7\r\2\2LM\7\31\2\2MP\b\b\1\2NP\7\33\2\2OL\3\2\2\2ON\3\2\2"+
		"\2PQ\3\2\2\2QR\b\b\1\2RS\7\16\2\2ST\7\20\2\2TU\b\b\1\2U\17\3\2\2\2VW\7"+
		"\b\2\2W[\7\r\2\2XY\7\31\2\2Y\\\b\t\1\2Z\\\7\33\2\2[X\3\2\2\2[Z\3\2\2\2"+
		"\\]\3\2\2\2]^\b\t\1\2^_\7\16\2\2_`\7\20\2\2`a\b\t\1\2a\21\3\2\2\2bc\7"+
		"\31\2\2cd\b\n\1\2de\7\22\2\2ef\b\n\1\2fg\5\32\16\2gh\7\20\2\2hi\b\n\1"+
		"\2i\23\3\2\2\2jk\7\t\2\2kl\7\r\2\2lm\7\31\2\2mn\b\13\1\2no\7\30\2\2os"+
		"\b\13\1\2pq\7\31\2\2qt\b\13\1\2rt\7\32\2\2sp\3\2\2\2sr\3\2\2\2tu\3\2\2"+
		"\2uv\b\13\1\2vw\7\16\2\2wx\7\24\2\2xz\b\13\1\2y{\5\f\7\2zy\3\2\2\2{|\3"+
		"\2\2\2|z\3\2\2\2|}\3\2\2\2}~\3\2\2\2~\177\7\25\2\2\177\u008b\b\13\1\2"+
		"\u0080\u0081\7\n\2\2\u0081\u0082\7\24\2\2\u0082\u0084\b\13\1\2\u0083\u0085"+
		"\5\f\7\2\u0084\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0084\3\2\2\2\u0086"+
		"\u0087\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0089\7\25\2\2\u0089\u008a\b"+
		"\13\1\2\u008a\u008c\3\2\2\2\u008b\u0080\3\2\2\2\u008b\u008c\3\2\2\2\u008c"+
		"\25\3\2\2\2\u008d\u008e\7\13\2\2\u008e\u008f\7\r\2\2\u008f\u0090\7\31"+
		"\2\2\u0090\u0091\b\f\1\2\u0091\u0092\7\30\2\2\u0092\u0096\b\f\1\2\u0093"+
		"\u0094\7\31\2\2\u0094\u0097\b\f\1\2\u0095\u0097\7\32\2\2\u0096\u0093\3"+
		"\2\2\2\u0096\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u0099\b\f\1\2\u0099"+
		"\u009a\7\16\2\2\u009a\u009b\7\24\2\2\u009b\u009d\b\f\1\2\u009c\u009e\5"+
		"\f\7\2\u009d\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u009d\3\2\2\2\u009f"+
		"\u00a0\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a2\7\25\2\2\u00a2\u00a3\b"+
		"\f\1\2\u00a3\27\3\2\2\2\u00a4\u00a5\7\26\2\2\u00a5\u00a6\7\33\2\2\u00a6"+
		"\u00a7\b\r\1\2\u00a7\u00a8\7\27\2\2\u00a8\u00a9\b\r\1\2\u00a9\31\3\2\2"+
		"\2\u00aa\u00b0\5\34\17\2\u00ab\u00ac\7\21\2\2\u00ac\u00ad\b\16\1\2\u00ad"+
		"\u00af\5\34\17\2\u00ae\u00ab\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0\u00ae\3"+
		"\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\33\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3"+
		"\u00b4\7\r\2\2\u00b4\u00b5\b\17\1\2\u00b5\u00b6\5\32\16\2\u00b6\u00b7"+
		"\7\16\2\2\u00b7\u00b8\b\17\1\2\u00b8\u00c0\3\2\2\2\u00b9\u00ba\7\31\2"+
		"\2\u00ba\u00c0\b\17\1\2\u00bb\u00bc\7\32\2\2\u00bc\u00c0\b\17\1\2\u00bd"+
		"\u00be\7\33\2\2\u00be\u00c0\b\17\1\2\u00bf\u00b3\3\2\2\2\u00bf\u00b9\3"+
		"\2\2\2\u00bf\u00bb\3\2\2\2\u00bf\u00bd\3\2\2\2\u00c0\35\3\2\2\2\21\'\61"+
		":@HO[s|\u0086\u008b\u0096\u009f\u00b0\u00bf";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}