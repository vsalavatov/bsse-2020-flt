package antlr;// Generated from QueryLanguage.g4 by ANTLR 4.8

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QueryLanguageParser extends Parser {
    public static final int
            EQ = 1, LBR = 2, RBR = 3, COMMA = 4, COLON = 5, SEMICOLON = 6, ARROW_LEFT = 7, ARROW_RIGHT = 8,
            PTRN_STAR = 9, PTRN_PLUS = 10, PTRN_ALT = 11, PTRN_OPTION = 12, KW_CONNECT = 13,
            KW_TO = 14, KW_LIST = 15, KW_GRAPHS = 16, KW_SELECT = 17, KW_FROM = 18, KW_WHERE = 19,
            KW_COUNT = 20, KW_EXISTS = 21, KW_ID = 22, LOWERCASE_WORD = 23, INTEGER = 24, NONTERMINAL = 25,
            STRING_DESC = 26, WS = 27;
    public static final int
            RULE_script = 0, RULE_statement = 1, RULE_stmt_connect = 2, RULE_stmt_list = 3,
            RULE_stmt_select = 4, RULE_stmt_pattern_decl = 5, RULE_object_expr = 6,
            RULE_from_expr = 7, RULE_where_expr = 8, RULE_vertices_expr = 9, RULE_count_expr = 10,
            RULE_exists_expr = 11, RULE_vertices_desc = 12, RULE_pair_vertices_desc = 13,
            RULE_single_vertex_desc = 14, RULE_path_desc = 15, RULE_vertex_cond = 16,
            RULE_pattern = 17, RULE_terminal = 18, RULE_epsilon = 19, RULE_vertex_name = 20;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\35\u00a0\4\2\t\2" +
                    "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\2\7\2\60\n\2\f\2\16" +
                    "\2\63\13\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3;\n\3\3\4\3\4\3\4\3\4\3\5\3\5\3" +
                    "\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\5\bP\n\b\3\t\3\t\3" +
                    "\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\5\16b\n\16" +
                    "\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21" +
                    "\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22{\n\22\3\23\3\23\3\23" +
                    "\5\23\u0080\n\23\3\23\5\23\u0083\n\23\3\23\3\23\3\23\3\23\5\23\u0089\n" +
                    "\23\5\23\u008b\n\23\3\23\3\23\3\23\3\23\3\23\7\23\u0092\n\23\f\23\16\23" +
                    "\u0095\13\23\3\24\3\24\5\24\u0099\n\24\3\25\3\25\3\25\3\26\3\26\3\26\2" +
                    "\3$\27\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*\2\3\4\2\13\f\16\16" +
                    "\2\u0099\2\61\3\2\2\2\4:\3\2\2\2\6<\3\2\2\2\b@\3\2\2\2\nC\3\2\2\2\fH\3" +
                    "\2\2\2\16O\3\2\2\2\20Q\3\2\2\2\22T\3\2\2\2\24W\3\2\2\2\26Y\3\2\2\2\30" +
                    "\\\3\2\2\2\32a\3\2\2\2\34c\3\2\2\2\36i\3\2\2\2 k\3\2\2\2\"z\3\2\2\2$\u008a" +
                    "\3\2\2\2&\u0098\3\2\2\2(\u009a\3\2\2\2*\u009d\3\2\2\2,-\5\4\3\2-.\7\b" +
                    "\2\2.\60\3\2\2\2/,\3\2\2\2\60\63\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2\62" +
                    "\64\3\2\2\2\63\61\3\2\2\2\64\65\7\2\2\3\65\3\3\2\2\2\66;\5\6\4\2\67;\5" +
                    "\b\5\28;\5\n\6\29;\5\f\7\2:\66\3\2\2\2:\67\3\2\2\2:8\3\2\2\2:9\3\2\2\2" +
                    ";\5\3\2\2\2<=\7\17\2\2=>\7\20\2\2>?\7\34\2\2?\7\3\2\2\2@A\7\21\2\2AB\7" +
                    "\22\2\2B\t\3\2\2\2CD\7\23\2\2DE\5\16\b\2EF\5\20\t\2FG\5\22\n\2G\13\3\2" +
                    "\2\2HI\7\33\2\2IJ\7\3\2\2JK\5$\23\2K\r\3\2\2\2LP\5\24\13\2MP\5\26\f\2" +
                    "NP\5\30\r\2OL\3\2\2\2OM\3\2\2\2ON\3\2\2\2P\17\3\2\2\2QR\7\24\2\2RS\7\34" +
                    "\2\2S\21\3\2\2\2TU\7\25\2\2UV\5 \21\2V\23\3\2\2\2WX\5\32\16\2X\25\3\2" +
                    "\2\2YZ\7\26\2\2Z[\5\32\16\2[\27\3\2\2\2\\]\7\27\2\2]^\5\32\16\2^\31\3" +
                    "\2\2\2_b\5\34\17\2`b\5\36\20\2a_\3\2\2\2a`\3\2\2\2b\33\3\2\2\2cd\7\4\2" +
                    "\2de\5*\26\2ef\7\6\2\2fg\5*\26\2gh\7\5\2\2h\35\3\2\2\2ij\5*\26\2j\37\3" +
                    "\2\2\2kl\5\"\22\2lm\7\t\2\2mn\5$\23\2no\7\n\2\2op\5\"\22\2p!\3\2\2\2q" +
                    "{\5*\26\2rs\7\4\2\2st\5*\26\2tu\7\7\2\2uv\7\30\2\2vw\7\3\2\2wx\7\32\2" +
                    "\2xy\7\5\2\2y{\3\2\2\2zq\3\2\2\2zr\3\2\2\2{#\3\2\2\2|\177\b\23\1\2}\u0080" +
                    "\5&\24\2~\u0080\7\33\2\2\177}\3\2\2\2\177~\3\2\2\2\u0080\u0082\3\2\2\2" +
                    "\u0081\u0083\t\2\2\2\u0082\u0081\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u008b" +
                    "\3\2\2\2\u0084\u0085\7\4\2\2\u0085\u0086\5$\23\2\u0086\u0088\7\5\2\2\u0087" +
                    "\u0089\t\2\2\2\u0088\u0087\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008b\3\2" +
                    "\2\2\u008a|\3\2\2\2\u008a\u0084\3\2\2\2\u008b\u0093\3\2\2\2\u008c\u008d" +
                    "\f\4\2\2\u008d\u008e\7\r\2\2\u008e\u0092\5$\23\5\u008f\u0090\f\3\2\2\u0090" +
                    "\u0092\5$\23\4\u0091\u008c\3\2\2\2\u0091\u008f\3\2\2\2\u0092\u0095\3\2" +
                    "\2\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094%\3\2\2\2\u0095\u0093" +
                    "\3\2\2\2\u0096\u0099\7\31\2\2\u0097\u0099\5(\25\2\u0098\u0096\3\2\2\2" +
                    "\u0098\u0097\3\2\2\2\u0099\'\3\2\2\2\u009a\u009b\7\4\2\2\u009b\u009c\7" +
                    "\5\2\2\u009c)\3\2\2\2\u009d\u009e\7\31\2\2\u009e+\3\2\2\2\16\61:Oaz\177" +
                    "\u0082\u0088\u008a\u0091\u0093\u0098";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    static {
        RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION);
    }

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

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public QueryLanguageParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "script", "statement", "stmt_connect", "stmt_list", "stmt_select", "stmt_pattern_decl",
                "object_expr", "from_expr", "where_expr", "vertices_expr", "count_expr",
                "exists_expr", "vertices_desc", "pair_vertices_desc", "single_vertex_desc",
                "path_desc", "vertex_cond", "pattern", "terminal", "epsilon", "vertex_name"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'='", "'('", "')'", "','", "':'", "';'", "'--|'", "'|->'", "'*'",
                "'+'", "'|'", "'?'", "'CONNECT'", "'TO'", "'LIST'", "'GRAPHS'", "'SELECT'",
                "'FROM'", "'WHERE'", "'COUNT'", "'EXISTS'", "'id'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "EQ", "LBR", "RBR", "COMMA", "COLON", "SEMICOLON", "ARROW_LEFT",
                "ARROW_RIGHT", "PTRN_STAR", "PTRN_PLUS", "PTRN_ALT", "PTRN_OPTION", "KW_CONNECT",
                "KW_TO", "KW_LIST", "KW_GRAPHS", "KW_SELECT", "KW_FROM", "KW_WHERE",
                "KW_COUNT", "KW_EXISTS", "KW_ID", "LOWERCASE_WORD", "INTEGER", "NONTERMINAL",
                "STRING_DESC", "WS"
        };
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
    public String getGrammarFileName() {
        return "QueryLanguage.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public final ScriptContext script() throws RecognitionException {
        ScriptContext _localctx = new ScriptContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_script);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(47);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_CONNECT) | (1L << KW_LIST) | (1L << KW_SELECT) | (1L << NONTERMINAL))) != 0)) {
                    {
                        {
                            setState(42);
                            statement();
                            setState(43);
                            match(SEMICOLON);
                        }
                    }
                    setState(49);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(50);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final StatementContext statement() throws RecognitionException {
        StatementContext _localctx = new StatementContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_statement);
        try {
            setState(56);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case KW_CONNECT:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(52);
                    stmt_connect();
                }
                break;
                case KW_LIST:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(53);
                    stmt_list();
                }
                break;
                case KW_SELECT:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(54);
                    stmt_select();
                }
                break;
                case NONTERMINAL:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(55);
                    stmt_pattern_decl();
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Stmt_connectContext stmt_connect() throws RecognitionException {
        Stmt_connectContext _localctx = new Stmt_connectContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_stmt_connect);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(58);
                match(KW_CONNECT);
                setState(59);
                match(KW_TO);
                setState(60);
                match(STRING_DESC);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Stmt_listContext stmt_list() throws RecognitionException {
        Stmt_listContext _localctx = new Stmt_listContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_stmt_list);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(62);
                match(KW_LIST);
                setState(63);
                match(KW_GRAPHS);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Stmt_selectContext stmt_select() throws RecognitionException {
        Stmt_selectContext _localctx = new Stmt_selectContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_stmt_select);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(65);
                match(KW_SELECT);
                setState(66);
                object_expr();
                setState(67);
                from_expr();
                setState(68);
                where_expr();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Stmt_pattern_declContext stmt_pattern_decl() throws RecognitionException {
        Stmt_pattern_declContext _localctx = new Stmt_pattern_declContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_stmt_pattern_decl);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(70);
                match(NONTERMINAL);
                setState(71);
                match(EQ);
                setState(72);
                pattern(0);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Object_exprContext object_expr() throws RecognitionException {
        Object_exprContext _localctx = new Object_exprContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_object_expr);
        try {
            setState(77);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case LBR:
                case LOWERCASE_WORD:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(74);
                    vertices_expr();
                }
                break;
                case KW_COUNT:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(75);
                    count_expr();
                }
                break;
                case KW_EXISTS:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(76);
                    exists_expr();
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final From_exprContext from_expr() throws RecognitionException {
        From_exprContext _localctx = new From_exprContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_from_expr);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(79);
                match(KW_FROM);
                setState(80);
                match(STRING_DESC);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Where_exprContext where_expr() throws RecognitionException {
        Where_exprContext _localctx = new Where_exprContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_where_expr);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(82);
                match(KW_WHERE);
                setState(83);
                path_desc();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Vertices_exprContext vertices_expr() throws RecognitionException {
        Vertices_exprContext _localctx = new Vertices_exprContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_vertices_expr);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(85);
                vertices_desc();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Count_exprContext count_expr() throws RecognitionException {
        Count_exprContext _localctx = new Count_exprContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_count_expr);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(87);
                match(KW_COUNT);
                setState(88);
                vertices_desc();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Exists_exprContext exists_expr() throws RecognitionException {
        Exists_exprContext _localctx = new Exists_exprContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_exists_expr);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(90);
                match(KW_EXISTS);
                setState(91);
                vertices_desc();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Vertices_descContext vertices_desc() throws RecognitionException {
        Vertices_descContext _localctx = new Vertices_descContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_vertices_desc);
        try {
            setState(95);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case LBR:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(93);
                    pair_vertices_desc();
                }
                break;
                case LOWERCASE_WORD:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(94);
                    single_vertex_desc();
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Pair_vertices_descContext pair_vertices_desc() throws RecognitionException {
        Pair_vertices_descContext _localctx = new Pair_vertices_descContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_pair_vertices_desc);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(97);
                match(LBR);
                setState(98);
                vertex_name();
                setState(99);
                match(COMMA);
                setState(100);
                vertex_name();
                setState(101);
                match(RBR);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Single_vertex_descContext single_vertex_desc() throws RecognitionException {
        Single_vertex_descContext _localctx = new Single_vertex_descContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_single_vertex_desc);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(103);
                vertex_name();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Path_descContext path_desc() throws RecognitionException {
        Path_descContext _localctx = new Path_descContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_path_desc);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(105);
                vertex_cond();
                setState(106);
                match(ARROW_LEFT);
                setState(107);
                pattern(0);
                setState(108);
                match(ARROW_RIGHT);
                setState(109);
                vertex_cond();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Vertex_condContext vertex_cond() throws RecognitionException {
        Vertex_condContext _localctx = new Vertex_condContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_vertex_cond);
        try {
            setState(120);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case LOWERCASE_WORD:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(111);
                    vertex_name();
                }
                break;
                case LBR:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(112);
                    match(LBR);
                    setState(113);
                    vertex_name();
                    setState(114);
                    match(COLON);
                    setState(115);
                    match(KW_ID);
                    setState(116);
                    match(EQ);
                    setState(117);
                    match(INTEGER);
                    setState(118);
                    match(RBR);
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final PatternContext pattern() throws RecognitionException {
        return pattern(0);
    }

    private PatternContext pattern(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        PatternContext _localctx = new PatternContext(_ctx, _parentState);
        PatternContext _prevctx = _localctx;
        int _startState = 34;
        enterRecursionRule(_localctx, 34, RULE_pattern, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(136);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 8, _ctx)) {
                    case 1: {
                        setState(125);
                        _errHandler.sync(this);
                        switch (_input.LA(1)) {
                            case LBR:
                            case LOWERCASE_WORD: {
                                setState(123);
                                terminal();
                            }
                            break;
                            case NONTERMINAL: {
                                setState(124);
                                match(NONTERMINAL);
                            }
                            break;
                            default:
                                throw new NoViableAltException(this);
                        }
                        setState(128);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
                            case 1: {
                                setState(127);
                                _la = _input.LA(1);
                                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PTRN_STAR) | (1L << PTRN_PLUS) | (1L << PTRN_OPTION))) != 0))) {
                                    _errHandler.recoverInline(this);
                                } else {
                                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                    _errHandler.reportMatch(this);
                                    consume();
                                }
                            }
                            break;
                        }
                    }
                    break;
                    case 2: {
                        setState(130);
                        match(LBR);
                        setState(131);
                        pattern(0);
                        setState(132);
                        match(RBR);
                        setState(134);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 7, _ctx)) {
                            case 1: {
                                setState(133);
                                _la = _input.LA(1);
                                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PTRN_STAR) | (1L << PTRN_PLUS) | (1L << PTRN_OPTION))) != 0))) {
                                    _errHandler.recoverInline(this);
                                } else {
                                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                    _errHandler.reportMatch(this);
                                    consume();
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(145);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
                while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(143);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
                                case 1: {
                                    _localctx = new PatternContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_pattern);
                                    setState(138);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(139);
                                    match(PTRN_ALT);
                                    setState(140);
                                    pattern(3);
                                }
                                break;
                                case 2: {
                                    _localctx = new PatternContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_pattern);
                                    setState(141);
                                    if (!(precpred(_ctx, 1)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                                    setState(142);
                                    pattern(2);
                                }
                                break;
                            }
                        }
                    }
                    setState(147);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    public final TerminalContext terminal() throws RecognitionException {
        TerminalContext _localctx = new TerminalContext(_ctx, getState());
        enterRule(_localctx, 36, RULE_terminal);
        try {
            setState(150);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case LOWERCASE_WORD:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(148);
                    match(LOWERCASE_WORD);
                }
                break;
                case LBR:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(149);
                    epsilon();
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final EpsilonContext epsilon() throws RecognitionException {
        EpsilonContext _localctx = new EpsilonContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_epsilon);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(152);
                match(LBR);
                setState(153);
                match(RBR);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final Vertex_nameContext vertex_name() throws RecognitionException {
        Vertex_nameContext _localctx = new Vertex_nameContext(_ctx, getState());
        enterRule(_localctx, 40, RULE_vertex_name);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(155);
                match(LOWERCASE_WORD);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 17:
                return pattern_sempred((PatternContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean pattern_sempred(PatternContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return precpred(_ctx, 2);
            case 1:
                return precpred(_ctx, 1);
        }
        return true;
    }

    public static class ScriptContext extends ParserRuleContext {
        public ScriptContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode EOF() {
            return getToken(QueryLanguageParser.EOF, 0);
        }

        public List<StatementContext> statement() {
            return getRuleContexts(StatementContext.class);
        }

        public StatementContext statement(int i) {
            return getRuleContext(StatementContext.class, i);
        }

        public List<TerminalNode> SEMICOLON() {
            return getTokens(QueryLanguageParser.SEMICOLON);
        }

        public TerminalNode SEMICOLON(int i) {
            return getToken(QueryLanguageParser.SEMICOLON, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_script;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterScript(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitScript(this);
        }
    }

    public static class StatementContext extends ParserRuleContext {
        public StatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public Stmt_connectContext stmt_connect() {
            return getRuleContext(Stmt_connectContext.class, 0);
        }

        public Stmt_listContext stmt_list() {
            return getRuleContext(Stmt_listContext.class, 0);
        }

        public Stmt_selectContext stmt_select() {
            return getRuleContext(Stmt_selectContext.class, 0);
        }

        public Stmt_pattern_declContext stmt_pattern_decl() {
            return getRuleContext(Stmt_pattern_declContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_statement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterStatement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitStatement(this);
        }
    }

    public static class Stmt_connectContext extends ParserRuleContext {
        public Stmt_connectContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode KW_CONNECT() {
            return getToken(QueryLanguageParser.KW_CONNECT, 0);
        }

        public TerminalNode KW_TO() {
            return getToken(QueryLanguageParser.KW_TO, 0);
        }

        public TerminalNode STRING_DESC() {
            return getToken(QueryLanguageParser.STRING_DESC, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_stmt_connect;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterStmt_connect(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitStmt_connect(this);
        }
    }

    public static class Stmt_listContext extends ParserRuleContext {
        public Stmt_listContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode KW_LIST() {
            return getToken(QueryLanguageParser.KW_LIST, 0);
        }

        public TerminalNode KW_GRAPHS() {
            return getToken(QueryLanguageParser.KW_GRAPHS, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_stmt_list;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterStmt_list(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitStmt_list(this);
        }
    }

    public static class Stmt_selectContext extends ParserRuleContext {
        public Stmt_selectContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode KW_SELECT() {
            return getToken(QueryLanguageParser.KW_SELECT, 0);
        }

        public Object_exprContext object_expr() {
            return getRuleContext(Object_exprContext.class, 0);
        }

        public From_exprContext from_expr() {
            return getRuleContext(From_exprContext.class, 0);
        }

        public Where_exprContext where_expr() {
            return getRuleContext(Where_exprContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_stmt_select;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterStmt_select(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitStmt_select(this);
        }
    }

    public static class Stmt_pattern_declContext extends ParserRuleContext {
        public Stmt_pattern_declContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode NONTERMINAL() {
            return getToken(QueryLanguageParser.NONTERMINAL, 0);
        }

        public TerminalNode EQ() {
            return getToken(QueryLanguageParser.EQ, 0);
        }

        public PatternContext pattern() {
            return getRuleContext(PatternContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_stmt_pattern_decl;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener)
                ((QueryLanguageListener) listener).enterStmt_pattern_decl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener)
                ((QueryLanguageListener) listener).exitStmt_pattern_decl(this);
        }
    }

    public static class Object_exprContext extends ParserRuleContext {
        public Object_exprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public Vertices_exprContext vertices_expr() {
            return getRuleContext(Vertices_exprContext.class, 0);
        }

        public Count_exprContext count_expr() {
            return getRuleContext(Count_exprContext.class, 0);
        }

        public Exists_exprContext exists_expr() {
            return getRuleContext(Exists_exprContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_object_expr;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterObject_expr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitObject_expr(this);
        }
    }

    public static class From_exprContext extends ParserRuleContext {
        public From_exprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode KW_FROM() {
            return getToken(QueryLanguageParser.KW_FROM, 0);
        }

        public TerminalNode STRING_DESC() {
            return getToken(QueryLanguageParser.STRING_DESC, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_from_expr;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterFrom_expr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitFrom_expr(this);
        }
    }

    public static class Where_exprContext extends ParserRuleContext {
        public Where_exprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode KW_WHERE() {
            return getToken(QueryLanguageParser.KW_WHERE, 0);
        }

        public Path_descContext path_desc() {
            return getRuleContext(Path_descContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_where_expr;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterWhere_expr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitWhere_expr(this);
        }
    }

    public static class Vertices_exprContext extends ParserRuleContext {
        public Vertices_exprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public Vertices_descContext vertices_desc() {
            return getRuleContext(Vertices_descContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_vertices_expr;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterVertices_expr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitVertices_expr(this);
        }
    }

    public static class Count_exprContext extends ParserRuleContext {
        public Count_exprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode KW_COUNT() {
            return getToken(QueryLanguageParser.KW_COUNT, 0);
        }

        public Vertices_descContext vertices_desc() {
            return getRuleContext(Vertices_descContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_count_expr;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterCount_expr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitCount_expr(this);
        }
    }

    public static class Exists_exprContext extends ParserRuleContext {
        public Exists_exprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode KW_EXISTS() {
            return getToken(QueryLanguageParser.KW_EXISTS, 0);
        }

        public Vertices_descContext vertices_desc() {
            return getRuleContext(Vertices_descContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_exists_expr;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterExists_expr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitExists_expr(this);
        }
    }

    public static class Vertices_descContext extends ParserRuleContext {
        public Vertices_descContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public Pair_vertices_descContext pair_vertices_desc() {
            return getRuleContext(Pair_vertices_descContext.class, 0);
        }

        public Single_vertex_descContext single_vertex_desc() {
            return getRuleContext(Single_vertex_descContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_vertices_desc;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterVertices_desc(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitVertices_desc(this);
        }
    }

    public static class Pair_vertices_descContext extends ParserRuleContext {
        public Pair_vertices_descContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode LBR() {
            return getToken(QueryLanguageParser.LBR, 0);
        }

        public List<Vertex_nameContext> vertex_name() {
            return getRuleContexts(Vertex_nameContext.class);
        }

        public Vertex_nameContext vertex_name(int i) {
            return getRuleContext(Vertex_nameContext.class, i);
        }

        public TerminalNode COMMA() {
            return getToken(QueryLanguageParser.COMMA, 0);
        }

        public TerminalNode RBR() {
            return getToken(QueryLanguageParser.RBR, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_pair_vertices_desc;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener)
                ((QueryLanguageListener) listener).enterPair_vertices_desc(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener)
                ((QueryLanguageListener) listener).exitPair_vertices_desc(this);
        }
    }

    public static class Single_vertex_descContext extends ParserRuleContext {
        public Single_vertex_descContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public Vertex_nameContext vertex_name() {
            return getRuleContext(Vertex_nameContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_single_vertex_desc;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener)
                ((QueryLanguageListener) listener).enterSingle_vertex_desc(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener)
                ((QueryLanguageListener) listener).exitSingle_vertex_desc(this);
        }
    }

    public static class Path_descContext extends ParserRuleContext {
        public Path_descContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<Vertex_condContext> vertex_cond() {
            return getRuleContexts(Vertex_condContext.class);
        }

        public Vertex_condContext vertex_cond(int i) {
            return getRuleContext(Vertex_condContext.class, i);
        }

        public TerminalNode ARROW_LEFT() {
            return getToken(QueryLanguageParser.ARROW_LEFT, 0);
        }

        public PatternContext pattern() {
            return getRuleContext(PatternContext.class, 0);
        }

        public TerminalNode ARROW_RIGHT() {
            return getToken(QueryLanguageParser.ARROW_RIGHT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_path_desc;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterPath_desc(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitPath_desc(this);
        }
    }

    public static class Vertex_condContext extends ParserRuleContext {
        public Vertex_condContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public Vertex_nameContext vertex_name() {
            return getRuleContext(Vertex_nameContext.class, 0);
        }

        public TerminalNode LBR() {
            return getToken(QueryLanguageParser.LBR, 0);
        }

        public TerminalNode COLON() {
            return getToken(QueryLanguageParser.COLON, 0);
        }

        public TerminalNode KW_ID() {
            return getToken(QueryLanguageParser.KW_ID, 0);
        }

        public TerminalNode EQ() {
            return getToken(QueryLanguageParser.EQ, 0);
        }

        public TerminalNode INTEGER() {
            return getToken(QueryLanguageParser.INTEGER, 0);
        }

        public TerminalNode RBR() {
            return getToken(QueryLanguageParser.RBR, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_vertex_cond;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterVertex_cond(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitVertex_cond(this);
        }
    }

    public static class PatternContext extends ParserRuleContext {
        public PatternContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalContext terminal() {
            return getRuleContext(TerminalContext.class, 0);
        }

        public TerminalNode NONTERMINAL() {
            return getToken(QueryLanguageParser.NONTERMINAL, 0);
        }

        public TerminalNode PTRN_STAR() {
            return getToken(QueryLanguageParser.PTRN_STAR, 0);
        }

        public TerminalNode PTRN_PLUS() {
            return getToken(QueryLanguageParser.PTRN_PLUS, 0);
        }

        public TerminalNode PTRN_OPTION() {
            return getToken(QueryLanguageParser.PTRN_OPTION, 0);
        }

        public TerminalNode LBR() {
            return getToken(QueryLanguageParser.LBR, 0);
        }

        public List<PatternContext> pattern() {
            return getRuleContexts(PatternContext.class);
        }

        public PatternContext pattern(int i) {
            return getRuleContext(PatternContext.class, i);
        }

        public TerminalNode RBR() {
            return getToken(QueryLanguageParser.RBR, 0);
        }

        public TerminalNode PTRN_ALT() {
            return getToken(QueryLanguageParser.PTRN_ALT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_pattern;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterPattern(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitPattern(this);
        }
    }

    public static class TerminalContext extends ParserRuleContext {
        public TerminalContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode LOWERCASE_WORD() {
            return getToken(QueryLanguageParser.LOWERCASE_WORD, 0);
        }

        public EpsilonContext epsilon() {
            return getRuleContext(EpsilonContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_terminal;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterTerminal(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitTerminal(this);
        }
    }

    public static class EpsilonContext extends ParserRuleContext {
        public EpsilonContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode LBR() {
            return getToken(QueryLanguageParser.LBR, 0);
        }

        public TerminalNode RBR() {
            return getToken(QueryLanguageParser.RBR, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_epsilon;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterEpsilon(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitEpsilon(this);
        }
    }

    public static class Vertex_nameContext extends ParserRuleContext {
        public Vertex_nameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode LOWERCASE_WORD() {
            return getToken(QueryLanguageParser.LOWERCASE_WORD, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_vertex_name;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).enterVertex_name(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof QueryLanguageListener) ((QueryLanguageListener) listener).exitVertex_name(this);
        }
    }
}