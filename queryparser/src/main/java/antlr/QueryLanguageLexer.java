package antlr;// Generated from QueryLanguage.g4 by ANTLR 4.8

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QueryLanguageLexer extends Lexer {
    public static final int
            EQ = 1, LBR = 2, RBR = 3, COMMA = 4, COLON = 5, SEMICOLON = 6, ARROW_LEFT = 7, ARROW_RIGHT = 8,
            PTRN_STAR = 9, PTRN_PLUS = 10, PTRN_ALT = 11, PTRN_OPTION = 12, KW_CONNECT = 13,
            KW_TO = 14, KW_LIST = 15, KW_GRAPHS = 16, KW_SELECT = 17, KW_FROM = 18, KW_WHERE = 19,
            KW_COUNT = 20, KW_EXISTS = 21, KW_ID = 22, LOWERCASE_WORD = 23, INTEGER = 24, NONTERMINAL = 25,
            STRING_DESC = 26, WS = 27;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\35\u00b1\b\1\4\2" +
                    "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                    "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                    "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
                    "\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3" +
                    "\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3" +
                    "\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20" +
                    "\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22" +
                    "\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24" +
                    "\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26" +
                    "\3\27\3\27\3\27\3\30\6\30\u0090\n\30\r\30\16\30\u0091\3\31\6\31\u0095" +
                    "\n\31\r\31\16\31\u0096\3\32\3\32\7\32\u009b\n\32\f\32\16\32\u009e\13\32" +
                    "\3\33\3\33\3\33\3\33\7\33\u00a4\n\33\f\33\16\33\u00a7\13\33\3\33\3\33" +
                    "\3\34\6\34\u00ac\n\34\r\34\16\34\u00ad\3\34\3\34\2\2\35\3\3\5\4\7\5\t" +
                    "\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23" +
                    "%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\35\3\2\b\3\2c|\3\2\62;" +
                    "\3\2C\\\4\2C\\c|\5\2\f\f\17\17$$\5\2\13\f\17\17\"\"\2\u00b6\2\3\3\2\2" +
                    "\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3" +
                    "\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2" +
                    "\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2" +
                    "\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2" +
                    "\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\39\3\2\2\2\5;\3\2\2\2\7=\3" +
                    "\2\2\2\t?\3\2\2\2\13A\3\2\2\2\rC\3\2\2\2\17E\3\2\2\2\21I\3\2\2\2\23M\3" +
                    "\2\2\2\25O\3\2\2\2\27Q\3\2\2\2\31S\3\2\2\2\33U\3\2\2\2\35]\3\2\2\2\37" +
                    "`\3\2\2\2!e\3\2\2\2#l\3\2\2\2%s\3\2\2\2\'x\3\2\2\2)~\3\2\2\2+\u0084\3" +
                    "\2\2\2-\u008b\3\2\2\2/\u008f\3\2\2\2\61\u0094\3\2\2\2\63\u0098\3\2\2\2" +
                    "\65\u009f\3\2\2\2\67\u00ab\3\2\2\29:\7?\2\2:\4\3\2\2\2;<\7*\2\2<\6\3\2" +
                    "\2\2=>\7+\2\2>\b\3\2\2\2?@\7.\2\2@\n\3\2\2\2AB\7<\2\2B\f\3\2\2\2CD\7=" +
                    "\2\2D\16\3\2\2\2EF\7/\2\2FG\7/\2\2GH\7~\2\2H\20\3\2\2\2IJ\7~\2\2JK\7/" +
                    "\2\2KL\7@\2\2L\22\3\2\2\2MN\7,\2\2N\24\3\2\2\2OP\7-\2\2P\26\3\2\2\2QR" +
                    "\7~\2\2R\30\3\2\2\2ST\7A\2\2T\32\3\2\2\2UV\7E\2\2VW\7Q\2\2WX\7P\2\2XY" +
                    "\7P\2\2YZ\7G\2\2Z[\7E\2\2[\\\7V\2\2\\\34\3\2\2\2]^\7V\2\2^_\7Q\2\2_\36" +
                    "\3\2\2\2`a\7N\2\2ab\7K\2\2bc\7U\2\2cd\7V\2\2d \3\2\2\2ef\7I\2\2fg\7T\2" +
                    "\2gh\7C\2\2hi\7R\2\2ij\7J\2\2jk\7U\2\2k\"\3\2\2\2lm\7U\2\2mn\7G\2\2no" +
                    "\7N\2\2op\7G\2\2pq\7E\2\2qr\7V\2\2r$\3\2\2\2st\7H\2\2tu\7T\2\2uv\7Q\2" +
                    "\2vw\7O\2\2w&\3\2\2\2xy\7Y\2\2yz\7J\2\2z{\7G\2\2{|\7T\2\2|}\7G\2\2}(\3" +
                    "\2\2\2~\177\7E\2\2\177\u0080\7Q\2\2\u0080\u0081\7W\2\2\u0081\u0082\7P" +
                    "\2\2\u0082\u0083\7V\2\2\u0083*\3\2\2\2\u0084\u0085\7G\2\2\u0085\u0086" +
                    "\7Z\2\2\u0086\u0087\7K\2\2\u0087\u0088\7U\2\2\u0088\u0089\7V\2\2\u0089" +
                    "\u008a\7U\2\2\u008a,\3\2\2\2\u008b\u008c\7k\2\2\u008c\u008d\7f\2\2\u008d" +
                    ".\3\2\2\2\u008e\u0090\t\2\2\2\u008f\u008e\3\2\2\2\u0090\u0091\3\2\2\2" +
                    "\u0091\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\60\3\2\2\2\u0093\u0095" +
                    "\t\3\2\2\u0094\u0093\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0094\3\2\2\2\u0096" +
                    "\u0097\3\2\2\2\u0097\62\3\2\2\2\u0098\u009c\t\4\2\2\u0099\u009b\t\5\2" +
                    "\2\u009a\u0099\3\2\2\2\u009b\u009e\3\2\2\2\u009c\u009a\3\2\2\2\u009c\u009d" +
                    "\3\2\2\2\u009d\64\3\2\2\2\u009e\u009c\3\2\2\2\u009f\u00a5\7$\2\2\u00a0" +
                    "\u00a4\n\6\2\2\u00a1\u00a2\7^\2\2\u00a2\u00a4\7$\2\2\u00a3\u00a0\3\2\2" +
                    "\2\u00a3\u00a1\3\2\2\2\u00a4\u00a7\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a5\u00a6" +
                    "\3\2\2\2\u00a6\u00a8\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a8\u00a9\7$\2\2\u00a9" +
                    "\66\3\2\2\2\u00aa\u00ac\t\7\2\2\u00ab\u00aa\3\2\2\2\u00ac\u00ad\3\2\2" +
                    "\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b0" +
                    "\b\34\2\2\u00b08\3\2\2\2\t\2\u0091\u0096\u009c\u00a3\u00a5\u00ad\3\b\2" +
                    "\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };
    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

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

    public QueryLanguageLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "EQ", "LBR", "RBR", "COMMA", "COLON", "SEMICOLON", "ARROW_LEFT", "ARROW_RIGHT",
                "PTRN_STAR", "PTRN_PLUS", "PTRN_ALT", "PTRN_OPTION", "KW_CONNECT", "KW_TO",
                "KW_LIST", "KW_GRAPHS", "KW_SELECT", "KW_FROM", "KW_WHERE", "KW_COUNT",
                "KW_EXISTS", "KW_ID", "LOWERCASE_WORD", "INTEGER", "NONTERMINAL", "STRING_DESC",
                "WS"
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
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }
}