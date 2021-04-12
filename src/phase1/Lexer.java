// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: lexFile.jflex

package phase1;
import phase1.HtmlHighlighter.HtmlHighlighter;





// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
class Lexer {

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;
  public static final int STRING = 2;
  public static final int ZERO = 4;
  public static final int INTEGER = 6;
  public static final int REAL = 8;
  public static final int HEX = 10;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5, 5
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\37\u0100\1\u0200\267\u0100\10\u0300\u1020\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\12\0\1\1\2\2\1\3\22\0\1\4\1\5\1\6"+
    "\2\0\1\7\1\10\1\0\2\11\1\12\1\13\1\7"+
    "\1\14\1\15\1\16\1\17\11\20\1\0\1\7\1\21"+
    "\1\22\1\5\2\0\4\23\1\24\1\23\21\25\1\26"+
    "\2\25\1\7\1\27\2\7\1\30\1\0\1\31\1\32"+
    "\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\25"+
    "\1\42\1\43\1\25\1\44\1\45\1\46\1\25\1\47"+
    "\1\50\1\51\1\52\1\53\1\54\1\26\1\55\1\25"+
    "\1\7\1\56\1\7\7\0\1\2\u01a2\0\2\2\326\0"+
    "\u0100\2";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[1024];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\0\1\2\1\3\1\2\1\4\1\5"+
    "\1\6\1\7\1\10\5\7\1\11\1\12\1\4\20\13"+
    "\1\7\1\1\1\14\1\4\1\15\1\16\1\2\1\3"+
    "\1\4\1\2\1\0\1\17\6\13\1\20\15\13\2\0"+
    "\1\3\3\0\6\13\1\20\10\13\1\0\1\17\2\13"+
    "\1\20\11\13\1\20\11\13\1\20\6\13\1\20\2\13"+
    "\1\20\3\13\1\20\2\13\1\20\25\13";

  private static int [] zzUnpackAction() {
    int [] result = new int[151];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\57\0\136\0\215\0\274\0\353\0\u011a\0\u011a"+
    "\0\u011a\0\u0149\0\u011a\0\u011a\0\u0178\0\u01a7\0\u01d6\0\u0205"+
    "\0\u011a\0\u011a\0\u0149\0\u0234\0\u0263\0\u0292\0\u02c1\0\u02f0"+
    "\0\u031f\0\u034e\0\u037d\0\u03ac\0\u03db\0\u040a\0\u0439\0\u0468"+
    "\0\u0497\0\u04c6\0\u04f5\0\u0524\0\u0553\0\u011a\0\u0582\0\u011a"+
    "\0\u011a\0\u05b1\0\u05e0\0\u060f\0\u063e\0\u066d\0\u069c\0\u06cb"+
    "\0\u06fa\0\u0729\0\u0758\0\u0787\0\u07b6\0\u06cb\0\u07e5\0\u0814"+
    "\0\u0843\0\u0872\0\u08a1\0\u08d0\0\u08ff\0\u092e\0\u095d\0\u098c"+
    "\0\u09bb\0\u09ea\0\u0a19\0\u0582\0\u060f\0\u0a48\0\u0a48\0\u0a77"+
    "\0\u0aa6\0\u0ad5\0\u0b04\0\u0b33\0\u0b62\0\u0b91\0\u0bc0\0\u0ad5"+
    "\0\u0bef\0\u0c1e\0\u0c4d\0\u0c7c\0\u0cab\0\u0cda\0\u0d09\0\u0d38"+
    "\0\u0d67\0\u011a\0\u0d96\0\u0dc5\0\u0d96\0\u0df4\0\u0e23\0\u0e52"+
    "\0\u0e81\0\u0eb0\0\u0edf\0\u0f0e\0\u0f3d\0\u0f6c\0\u0f6c\0\u0f9b"+
    "\0\u0fca\0\u0ff9\0\u1028\0\u1057\0\u1086\0\u10b5\0\u10e4\0\u1113"+
    "\0\u10e4\0\u1142\0\u1171\0\u11a0\0\u11cf\0\u11fe\0\u122d\0\u11cf"+
    "\0\u125c\0\u128b\0\u128b\0\u12ba\0\u12e9\0\u1318\0\u1318\0\u1347"+
    "\0\u1376\0\u1376\0\u13a5\0\u13d4\0\u1403\0\u1432\0\u1461\0\u1490"+
    "\0\u14bf\0\u14ee\0\u151d\0\u154c\0\u157b\0\u15aa\0\u15d9\0\u1608"+
    "\0\u1637\0\u1666\0\u1695\0\u16c4\0\u16f3\0\u1722\0\u011a";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[151];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\7\1\10\2\7\1\11\1\12\1\13\1\14\1\15"+
    "\1\14\1\12\1\16\1\17\1\14\1\20\1\21\1\22"+
    "\1\17\1\23\4\24\2\7\1\25\1\26\1\27\1\24"+
    "\1\30\1\31\2\24\1\32\1\24\1\33\1\34\1\35"+
    "\1\36\1\37\1\40\1\41\1\24\1\42\1\43\1\24"+
    "\1\44\1\45\1\7\4\45\1\46\20\45\1\47\27\45"+
    "\15\7\1\50\1\7\2\22\5\7\1\51\45\7\1\50"+
    "\1\7\2\52\55\7\2\53\3\7\1\54\51\7\2\55"+
    "\2\7\2\55\4\7\6\55\20\7\101\0\1\14\44\0"+
    "\1\14\61\0\1\14\6\0\1\14\50\0\1\14\5\0"+
    "\1\14\46\0\1\56\3\0\1\57\3\0\1\14\53\0"+
    "\2\60\2\0\4\60\1\0\26\60\20\0\2\60\2\0"+
    "\4\60\1\0\17\60\1\61\6\60\20\0\2\60\2\0"+
    "\4\60\1\0\15\60\1\62\1\60\1\63\6\60\20\0"+
    "\2\60\2\0\4\60\1\0\15\60\1\64\10\60\20\0"+
    "\2\60\2\0\4\60\1\0\13\60\1\65\12\60\20\0"+
    "\2\60\2\0\4\60\1\0\11\60\1\66\3\60\1\67"+
    "\10\60\20\0\2\60\2\0\4\60\1\0\6\60\1\66"+
    "\5\60\1\70\11\60\20\0\2\60\2\0\4\60\1\0"+
    "\5\60\1\71\7\60\1\72\10\60\20\0\2\60\2\0"+
    "\4\60\1\0\5\60\1\73\20\60\20\0\2\60\2\0"+
    "\4\60\1\0\22\60\1\74\3\60\20\0\2\60\2\0"+
    "\4\60\1\0\22\60\1\75\3\60\20\0\2\60\2\0"+
    "\4\60\1\0\5\60\1\76\7\60\1\77\10\60\20\0"+
    "\2\60\2\0\4\60\1\0\21\60\1\100\4\60\20\0"+
    "\2\60\2\0\4\60\1\0\10\60\1\101\15\60\20\0"+
    "\2\60\2\0\4\60\1\0\15\60\1\102\10\60\20\0"+
    "\2\60\2\0\4\60\1\0\10\60\1\103\15\60\57\0"+
    "\1\14\1\45\1\0\4\45\1\0\20\45\1\104\31\45"+
    "\2\0\53\45\17\0\2\52\55\0\2\53\3\0\1\105"+
    "\43\0\1\106\1\0\2\107\2\0\2\106\35\0\1\107"+
    "\17\0\2\55\2\0\2\55\4\0\6\55\20\0\12\110"+
    "\1\111\44\110\1\57\1\0\1\57\1\0\53\57\17\0"+
    "\2\112\2\0\4\112\1\0\26\112\20\0\2\112\2\0"+
    "\4\112\1\0\17\112\1\113\6\112\20\0\2\112\2\0"+
    "\4\112\1\0\15\112\1\114\10\112\20\0\2\112\2\0"+
    "\4\112\1\0\5\112\1\115\20\112\20\0\2\112\2\0"+
    "\4\112\1\0\14\112\1\116\11\112\20\0\2\112\2\0"+
    "\4\112\1\0\20\112\1\117\5\112\20\0\2\112\2\0"+
    "\4\112\1\0\17\112\1\120\6\112\20\0\2\112\2\0"+
    "\4\112\1\0\1\121\20\112\1\120\4\112\20\0\2\112"+
    "\2\0\4\112\1\0\14\112\1\120\4\112\1\120\4\112"+
    "\20\0\2\112\2\0\4\112\1\0\15\112\1\122\10\112"+
    "\20\0\2\112\2\0\4\112\1\0\24\112\1\120\1\112"+
    "\20\0\2\112\2\0\4\112\1\0\21\112\1\123\4\112"+
    "\20\0\2\112\2\0\4\112\1\0\13\112\1\114\12\112"+
    "\20\0\2\112\2\0\4\112\1\0\1\112\1\114\17\112"+
    "\1\124\4\112\20\0\2\112\2\0\4\112\1\0\6\112"+
    "\1\120\17\112\20\0\2\112\2\0\4\112\1\0\17\112"+
    "\1\125\6\112\20\0\2\112\2\0\4\112\1\0\5\112"+
    "\1\126\20\112\20\0\2\112\2\0\4\112\1\0\11\112"+
    "\1\127\14\112\20\0\2\112\2\0\4\112\1\0\11\112"+
    "\1\130\14\112\12\0\1\106\5\0\2\106\36\0\12\110"+
    "\1\131\44\110\12\0\1\111\3\0\1\132\57\0\2\133"+
    "\2\0\4\133\1\0\26\133\20\0\2\133\2\0\4\133"+
    "\1\0\1\133\1\134\24\133\20\0\2\133\2\0\4\133"+
    "\1\0\13\133\1\135\12\133\20\0\2\133\2\0\4\133"+
    "\1\0\1\133\1\136\24\133\20\0\2\133\2\0\4\133"+
    "\1\0\21\133\1\137\4\133\20\0\2\133\2\0\4\133"+
    "\1\0\5\133\1\135\20\133\20\0\2\133\2\0\4\133"+
    "\1\0\11\133\1\140\6\133\1\141\5\133\20\0\2\133"+
    "\2\0\4\133\1\0\16\133\1\135\7\133\20\0\2\133"+
    "\2\0\4\133\1\0\1\142\25\133\20\0\2\133\2\0"+
    "\4\133\1\0\22\133\1\143\3\133\20\0\2\133\2\0"+
    "\4\133\1\0\11\133\1\144\14\133\20\0\2\133\2\0"+
    "\4\133\1\0\14\133\1\135\11\133\20\0\2\133\2\0"+
    "\4\133\1\0\4\133\1\135\21\133\20\0\2\133\2\0"+
    "\4\133\1\0\13\133\1\145\12\133\1\0\12\110\1\131"+
    "\3\110\1\132\40\110\17\0\2\146\2\0\4\146\1\0"+
    "\26\146\20\0\2\146\2\0\4\146\1\0\25\146\1\147"+
    "\20\0\2\146\2\0\4\146\1\0\12\146\1\147\13\146"+
    "\20\0\2\146\2\0\4\146\1\0\11\146\1\150\14\146"+
    "\20\0\2\146\2\0\4\146\1\0\14\146\1\151\11\146"+
    "\20\0\2\146\2\0\4\146\1\0\21\146\1\152\4\146"+
    "\20\0\2\146\2\0\4\146\1\0\11\146\1\153\6\146"+
    "\1\154\5\146\20\0\2\146\2\0\4\146\1\0\17\146"+
    "\1\155\6\146\20\0\2\146\2\0\4\146\1\0\14\146"+
    "\1\156\11\146\20\0\2\146\2\0\4\146\1\0\5\146"+
    "\1\147\20\146\20\0\2\157\2\0\4\157\1\0\26\157"+
    "\20\0\2\157\2\0\4\157\1\0\14\157\1\160\11\157"+
    "\20\0\2\157\2\0\4\157\1\0\21\157\1\161\4\157"+
    "\20\0\2\157\2\0\4\157\1\0\17\157\1\162\6\157"+
    "\20\0\2\157\2\0\4\157\1\0\14\157\1\163\11\157"+
    "\20\0\2\157\2\0\4\157\1\0\21\157\1\164\4\157"+
    "\20\0\2\157\2\0\4\157\1\0\14\157\1\161\11\157"+
    "\20\0\2\157\2\0\4\157\1\0\7\157\1\161\16\157"+
    "\20\0\2\165\2\0\4\165\1\0\26\165\20\0\2\165"+
    "\2\0\4\165\1\0\22\165\1\166\3\165\20\0\2\165"+
    "\2\0\4\165\1\0\11\165\1\167\14\165\20\0\2\165"+
    "\2\0\4\165\1\0\21\165\1\170\4\165\20\0\2\165"+
    "\2\0\4\165\1\0\17\165\1\171\6\165\20\0\2\172"+
    "\2\0\4\172\1\0\26\172\20\0\2\172\2\0\4\172"+
    "\1\0\5\172\1\173\20\172\20\0\2\172\2\0\4\172"+
    "\1\0\14\172\1\174\11\172\20\0\2\172\2\0\4\172"+
    "\1\0\11\172\1\175\14\172\20\0\2\176\2\0\4\176"+
    "\1\0\26\176\20\0\2\176\2\0\4\176\1\0\7\176"+
    "\1\177\16\176\20\0\2\176\2\0\4\176\1\0\14\176"+
    "\1\200\11\176\20\0\2\201\2\0\4\201\1\0\26\201"+
    "\20\0\2\201\2\0\4\201\1\0\7\201\1\202\16\201"+
    "\20\0\2\203\2\0\4\203\1\0\26\203\20\0\2\204"+
    "\2\0\4\204\1\0\26\204\20\0\2\205\2\0\4\205"+
    "\1\0\26\205\20\0\2\206\2\0\4\206\1\0\26\206"+
    "\20\0\2\207\2\0\4\207\1\0\26\207\20\0\2\210"+
    "\2\0\4\210\1\0\26\210\20\0\2\211\2\0\4\211"+
    "\1\0\26\211\20\0\2\212\2\0\4\212\1\0\26\212"+
    "\20\0\2\213\2\0\4\213\1\0\26\213\20\0\2\214"+
    "\2\0\4\214\1\0\26\214\20\0\2\215\2\0\4\215"+
    "\1\0\26\215\20\0\2\216\2\0\4\216\1\0\26\216"+
    "\20\0\2\217\2\0\4\217\1\0\26\217\20\0\2\220"+
    "\2\0\4\220\1\0\26\220\20\0\2\221\2\0\4\221"+
    "\1\0\26\221\20\0\2\222\2\0\4\222\1\0\26\222"+
    "\20\0\2\223\2\0\4\223\1\0\26\223\20\0\2\224"+
    "\2\0\4\224\1\0\26\224\20\0\2\225\2\0\4\225"+
    "\1\0\26\225\20\0\2\226\2\0\4\226\1\0\26\226"+
    "\20\0\2\227\2\0\4\227\1\0\26\227\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[5969];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\1\1\0\3\1\3\11\1\1\2\11\4\1"+
    "\2\11\23\1\1\11\1\1\2\11\4\1\1\0\25\1"+
    "\2\0\1\1\3\0\17\1\1\0\1\11\74\1\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[151];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  @SuppressWarnings("unused")
  private boolean zzEOFDone;

  /* user code: */
    public static HtmlHighlighter htmlHighlighter = new HtmlHighlighter();
    StringBuilder number = new StringBuilder(""); 


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException(
          "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE) {
      zzBuffer = new char[ZZ_BUFFERSIZE];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  public String next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is
        // (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { htmlHighlighter.stringsAndCharacters(yytext());
            }
            // fall through
          case 17: break;
          case 2:
            { number.append(yytext());
            htmlHighlighter.integerNumbers(number.toString());
            number.setLength(0);
            yybegin(YYINITIAL);
            }
            // fall through
          case 18: break;
          case 3:
            { number.append(yytext());
            htmlHighlighter.realNumbers(number.toString());
            number.setLength(0);
            yybegin(YYINITIAL);
            }
            // fall through
          case 19: break;
          case 4:
            { htmlHighlighter.undefinedToken(yytext());
            }
            // fall through
          case 20: break;
          case 5:
            { htmlHighlighter.newLine();
            }
            // fall through
          case 21: break;
          case 6:
            { htmlHighlighter.space();
            }
            // fall through
          case 22: break;
          case 7:
            { htmlHighlighter.operatorsAndPunctuations(yytext());
            }
            // fall through
          case 23: break;
          case 8:
            { htmlHighlighter.operatorsAndPunctuations("\"");
            yybegin(STRING);
            }
            // fall through
          case 24: break;
          case 9:
            { htmlHighlighter.integerNumbers(yytext());
            yybegin(ZERO);
            }
            // fall through
          case 25: break;
          case 10:
            { number.append(yytext());
            yybegin(INTEGER);
            }
            // fall through
          case 26: break;
          case 11:
            { htmlHighlighter.Identifiers(yytext());
            }
            // fall through
          case 27: break;
          case 12:
            { htmlHighlighter.operatorsAndPunctuations("\"");
            yybegin(YYINITIAL);
            }
            // fall through
          case 28: break;
          case 13:
            { number.append(yytext());
            yybegin(REAL);
            }
            // fall through
          case 29: break;
          case 14:
            { htmlHighlighter.specialCharacters(yytext());
            yybegin(HEX);
            }
            // fall through
          case 30: break;
          case 15:
            { htmlHighlighter.comments(yytext());
            }
            // fall through
          case 31: break;
          case 16:
            { htmlHighlighter.reservedKeyWords(yytext());
            }
            // fall through
          case 32: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
