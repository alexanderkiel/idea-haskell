/* The following code was generated by JFlex 1.4.1 on 9/12/09 8:54 AM */

package net.alexanderkiel.idea_haskell_plugin.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.1
 * on 9/12/09 8:54 AM from the specification file
 * <tt>haskell.flex</tt>
 */
public final class HaskellFlexLexer implements FlexLexer {
  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int COMMENT2 = 4;
  public static final int COMMENT1 = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  1,  2,  2,  3, 3
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\100\1\16\1\13\1\100\1\14\1\12\15\100\5\0\1\15\1\41"+
    "\1\33\1\20\1\41\1\41\1\42\1\34\1\1\1\2\1\41\1\43"+
    "\1\3\1\17\1\44\1\41\1\74\7\55\2\54\1\32\1\4\1\45"+
    "\1\46\1\47\1\41\1\50\4\57\1\77\1\57\10\37\1\75\10\37"+
    "\1\76\2\37\1\5\1\40\1\6\1\51\1\35\1\7\1\60\1\56"+
    "\1\30\1\27\1\23\1\22\1\64\1\73\1\21\2\36\1\24\1\66"+
    "\1\26\1\65\1\67\1\36\1\62\1\25\1\61\1\31\1\63\1\71"+
    "\1\70\1\72\1\36\1\10\1\52\1\11\1\53\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\4\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\2\13\1\14\1\15\2\16\7\17"+
    "\1\20\2\1\1\21\1\22\1\23\2\16\1\24\1\25"+
    "\1\26\1\27\1\30\4\17\1\30\1\16\5\31\1\32"+
    "\1\16\1\33\1\34\1\35\6\17\1\36\2\17\1\37"+
    "\1\40\1\0\1\41\3\0\1\42\1\43\1\44\2\0"+
    "\2\17\1\45\2\17\6\0\1\46\1\47\1\50\1\0"+
    "\2\51\4\17\1\52\6\17\3\0\1\53\5\0\2\54"+
    "\4\17\2\30\6\0\3\17\1\55\3\17\1\56\1\17"+
    "\1\57\2\0\1\60\1\61\2\17\2\62\6\0\1\63"+
    "\5\17\1\64\1\17\1\65\2\0\1\66\1\67\1\17"+
    "\1\70\3\17\1\71\1\0\1\17\1\72\1\73\1\17"+
    "\1\74\1\75";

  private static int [] zzUnpackAction() {
    int [] result = new int[178];
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
    "\0\0\0\101\0\202\0\303\0\u0104\0\u0104\0\u0104\0\u0104"+
    "\0\u0104\0\u0104\0\u0104\0\u0104\0\u0145\0\u0104\0\u0186\0\u0104"+
    "\0\u01c7\0\u0104\0\u0208\0\u0249\0\u028a\0\u02cb\0\u030c\0\u034d"+
    "\0\u038e\0\u03cf\0\u0410\0\u0451\0\u0492\0\u04d3\0\u02cb\0\u0514"+
    "\0\u0249\0\u0555\0\u0596\0\u05d7\0\u0249\0\u0249\0\u0249\0\u0618"+
    "\0\u0659\0\u069a\0\u06db\0\u071c\0\u075d\0\u079e\0\u0104\0\u07df"+
    "\0\u0820\0\u0861\0\u08a2\0\u0104\0\u08e3\0\u0249\0\u02cb\0\u0924"+
    "\0\u0965\0\u09a6\0\u09e7\0\u0a28\0\u0a69\0\u0aaa\0\u02cb\0\u0aeb"+
    "\0\u0b2c\0\u0b6d\0\u0b6d\0\u0492\0\u0104\0\u0bae\0\u0bef\0\u0c30"+
    "\0\u0249\0\u0249\0\u0249\0\u0c71\0\u0cb2\0\u0cf3\0\u0d34\0\u02cb"+
    "\0\u0d75\0\u0db6\0\u0df7\0\u0e38\0\u0e79\0\u0eba\0\u0efb\0\u0f3c"+
    "\0\u0104\0\u0104\0\u0104\0\u0f7d\0\u0fbe\0\u0104\0\u0fff\0\u1040"+
    "\0\u1081\0\u10c2\0\u02cb\0\u1103\0\u1144\0\u1185\0\u11c6\0\u1207"+
    "\0\u1248\0\u1289\0\u12ca\0\u130b\0\u0104\0\u134c\0\u138d\0\u13ce"+
    "\0\u140f\0\u1450\0\u1450\0\u1491\0\u14d2\0\u1513\0\u1554\0\u1595"+
    "\0\u0df7\0\u0e38\0\u15d6\0\u1617\0\u1658\0\u1699\0\u16da\0\u171b"+
    "\0\u175c\0\u179d\0\u17de\0\u02cb\0\u181f\0\u1860\0\u18a1\0\u02cb"+
    "\0\u18e2\0\u02cb\0\u1923\0\u1964\0\u02cb\0\u02cb\0\u19a5\0\u19e6"+
    "\0\u1a27\0\u0104\0\u1a68\0\u1aa9\0\u1aea\0\u1b2b\0\u1b6c\0\u1bad"+
    "\0\u1bee\0\u1c2f\0\u1c70\0\u1cb1\0\u1cf2\0\u1d33\0\u02cb\0\u1d74"+
    "\0\u02cb\0\u1db5\0\u1df6\0\u02cb\0\u02cb\0\u1e37\0\u02cb\0\u1e78"+
    "\0\u1eb9\0\u1efa\0\u02cb\0\u1f3b\0\u1f7c\0\u02cb\0\u02cb\0\u1fbd"+
    "\0\u02cb\0\u02cb";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[178];
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
    "\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14"+
    "\1\15\1\16\1\17\2\20\1\21\1\22\1\23\1\24"+
    "\1\25\1\26\1\27\1\30\1\26\1\31\1\32\1\33"+
    "\1\26\1\34\1\35\1\36\1\37\1\26\1\40\1\41"+
    "\3\24\1\42\1\43\1\44\1\24\1\45\1\24\1\46"+
    "\1\47\2\50\1\26\1\40\1\26\1\51\3\26\1\52"+
    "\1\53\2\26\1\54\2\26\1\55\3\40\2\5\1\6"+
    "\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16"+
    "\1\17\2\20\1\21\1\22\1\23\1\56\1\25\1\26"+
    "\1\27\1\30\1\26\1\31\1\32\1\33\1\26\1\34"+
    "\1\35\1\36\1\37\1\26\1\40\1\41\3\24\1\42"+
    "\1\43\1\44\1\24\1\45\1\24\1\46\1\47\2\50"+
    "\1\26\1\40\1\26\1\51\3\26\1\52\1\53\2\26"+
    "\1\54\2\26\1\55\3\40\1\5\10\57\1\60\1\57"+
    "\1\17\2\20\2\57\1\61\71\57\1\62\2\57\1\5"+
    "\3\57\1\63\61\57\120\0\1\64\74\0\1\20\102\0"+
    "\1\21\102\0\1\65\1\24\11\0\1\24\5\0\7\24"+
    "\1\66\4\24\44\0\2\24\11\0\1\24\5\0\14\24"+
    "\46\0\1\26\1\67\3\26\1\70\3\26\2\0\4\26"+
    "\14\0\12\26\1\71\11\26\22\0\11\26\2\0\4\26"+
    "\14\0\24\26\22\0\3\26\1\72\5\26\2\0\4\26"+
    "\14\0\24\26\22\0\2\26\1\73\6\26\2\0\4\26"+
    "\14\0\24\26\22\0\2\26\1\74\6\26\2\0\4\26"+
    "\14\0\24\26\22\0\2\26\1\75\6\26\2\0\4\26"+
    "\14\0\4\26\1\76\4\26\1\77\12\26\22\0\3\26"+
    "\1\100\5\26\2\0\4\26\14\0\4\26\1\101\17\26"+
    "\20\0\2\102\11\0\1\103\5\0\14\102\26\0\11\104"+
    "\3\0\1\104\1\0\14\104\1\105\4\104\1\106\37\104"+
    "\2\0\11\107\3\0\1\107\1\0\15\107\1\0\3\107"+
    "\1\110\37\107\22\0\11\40\2\0\4\40\14\0\24\40"+
    "\20\0\2\24\11\0\1\24\5\0\4\24\1\111\7\24"+
    "\44\0\1\112\1\24\11\0\1\24\5\0\14\24\44\0"+
    "\2\24\11\0\1\24\5\0\7\24\1\113\4\24\50\0"+
    "\1\114\20\0\1\115\7\0\2\50\16\0\1\50\2\0"+
    "\1\114\22\0\11\26\2\0\4\26\14\0\16\26\1\116"+
    "\1\117\4\26\22\0\1\26\1\120\7\26\2\0\4\26"+
    "\14\0\24\26\22\0\11\26\2\0\4\26\14\0\11\26"+
    "\1\121\12\26\22\0\11\26\2\0\4\26\14\0\17\26"+
    "\1\122\4\26\24\0\1\114\20\0\1\115\7\0\2\50"+
    "\7\0\1\123\2\0\1\124\3\0\1\50\1\123\1\124"+
    "\1\114\20\0\2\24\1\125\1\0\1\126\3\0\1\127"+
    "\1\0\1\130\1\24\5\0\14\24\44\0\1\131\72\0"+
    "\1\132\106\0\1\133\72\0\1\64\70\0\11\134\1\135"+
    "\2\136\2\134\2\65\11\134\1\65\5\134\14\65\24\134"+
    "\22\0\1\26\1\137\2\26\1\140\4\26\2\0\4\26"+
    "\14\0\24\26\22\0\11\26\2\0\4\26\14\0\13\26"+
    "\1\141\10\26\22\0\4\26\1\142\4\26\2\0\4\26"+
    "\14\0\24\26\22\0\11\26\2\0\4\26\14\0\5\26"+
    "\1\143\16\26\22\0\11\26\2\0\4\26\14\0\15\26"+
    "\1\144\6\26\22\0\1\26\1\145\7\26\2\0\4\26"+
    "\14\0\6\26\1\146\15\26\22\0\11\26\2\0\4\26"+
    "\14\0\5\26\1\147\16\26\22\0\11\26\2\0\4\26"+
    "\14\0\4\26\1\150\17\26\22\0\4\26\1\151\4\26"+
    "\2\0\4\26\14\0\24\26\20\0\2\102\11\0\1\102"+
    "\5\0\14\102\37\0\3\104\1\0\1\104\3\0\1\104"+
    "\3\0\1\104\4\0\2\104\3\0\1\104\1\0\1\104"+
    "\6\0\1\152\2\0\3\104\1\0\4\104\1\0\1\153"+
    "\2\0\1\154\3\0\1\104\3\0\1\104\34\0\1\155"+
    "\56\0\3\107\1\0\1\107\3\0\1\107\3\0\1\107"+
    "\4\0\2\107\3\0\1\107\1\0\1\107\6\0\1\156"+
    "\2\0\2\157\1\107\1\0\4\107\1\0\1\160\2\0"+
    "\1\161\3\0\1\157\3\0\1\107\17\0\1\162\23\0"+
    "\1\162\10\0\2\163\16\0\1\163\60\0\2\164\16\0"+
    "\1\164\25\0\11\26\2\0\4\26\14\0\13\26\1\165"+
    "\10\26\22\0\2\26\1\166\6\26\2\0\4\26\14\0"+
    "\24\26\22\0\6\26\1\167\2\26\2\0\4\26\14\0"+
    "\24\26\22\0\2\26\1\170\6\26\2\0\4\26\14\0"+
    "\24\26\56\0\1\171\16\0\1\171\26\0\2\172\3\0"+
    "\2\172\23\0\5\172\13\0\1\172\2\0\1\172\23\0"+
    "\1\173\3\0\1\174\76\0\1\175\1\0\1\176\75\0"+
    "\1\177\103\0\1\200\53\0\11\134\1\135\2\136\63\134"+
    "\14\0\1\136\106\0\1\201\10\26\2\0\4\26\14\0"+
    "\24\26\22\0\11\26\2\0\4\26\14\0\5\26\1\202"+
    "\16\26\22\0\11\26\2\0\4\26\14\0\11\26\1\203"+
    "\12\26\22\0\2\26\1\204\6\26\2\0\4\26\14\0"+
    "\24\26\22\0\11\26\2\0\4\26\14\0\5\26\1\205"+
    "\16\26\22\0\11\26\2\0\4\26\14\0\4\26\1\206"+
    "\17\26\22\0\1\207\10\26\2\0\4\26\14\0\24\26"+
    "\22\0\11\26\2\0\4\26\14\0\4\26\1\210\17\26"+
    "\22\0\4\26\1\211\4\26\2\0\4\26\14\0\24\26"+
    "\22\0\2\26\1\212\6\26\2\0\4\26\14\0\24\26"+
    "\6\0\2\104\26\0\1\104\1\0\2\104\7\0\2\104"+
    "\5\0\1\104\15\0\3\104\56\0\1\104\16\0\1\104"+
    "\26\0\2\104\3\0\2\104\23\0\5\104\13\0\1\104"+
    "\2\0\1\104\6\0\2\107\26\0\1\107\1\0\2\107"+
    "\7\0\2\107\5\0\1\107\15\0\3\107\35\0\1\155"+
    "\17\0\2\157\16\0\1\157\61\0\1\213\16\0\1\213"+
    "\26\0\2\214\3\0\2\214\23\0\5\214\13\0\1\214"+
    "\2\0\1\214\55\0\2\163\16\0\1\163\27\0\1\114"+
    "\30\0\2\164\16\0\1\164\2\0\1\114\22\0\2\26"+
    "\1\215\6\26\2\0\4\26\14\0\24\26\22\0\5\26"+
    "\1\216\3\26\2\0\4\26\14\0\24\26\22\0\10\26"+
    "\1\217\2\0\4\26\14\0\24\26\22\0\11\26\2\0"+
    "\4\26\14\0\6\26\1\220\15\26\2\0\11\173\1\221"+
    "\2\222\63\173\31\0\1\223\71\0\1\224\3\0\1\225"+
    "\102\0\1\226\73\0\1\227\105\0\1\230\72\0\11\26"+
    "\2\0\4\26\14\0\14\26\1\231\7\26\22\0\11\26"+
    "\2\0\4\26\14\0\4\26\1\232\17\26\22\0\11\26"+
    "\2\0\4\26\14\0\6\26\1\233\15\26\22\0\11\26"+
    "\2\0\4\26\14\0\16\26\1\234\5\26\22\0\10\26"+
    "\1\235\2\0\4\26\14\0\24\26\22\0\11\26\2\0"+
    "\4\26\14\0\7\26\1\236\14\26\22\0\4\26\1\237"+
    "\4\26\2\0\4\26\14\0\24\26\35\0\1\155\20\0"+
    "\1\213\16\0\1\213\26\0\2\214\3\0\2\214\3\0"+
    "\1\155\17\0\5\214\13\0\1\214\2\0\1\214\22\0"+
    "\3\26\1\240\5\26\2\0\4\26\14\0\24\26\22\0"+
    "\2\26\1\241\6\26\2\0\4\26\14\0\24\26\14\0"+
    "\1\222\111\0\1\242\76\0\1\173\101\0\1\173\76\0"+
    "\1\224\100\0\1\243\102\0\1\224\76\0\3\26\1\244"+
    "\5\26\2\0\4\26\14\0\6\26\1\245\15\26\22\0"+
    "\5\26\1\246\3\26\2\0\4\26\14\0\24\26\22\0"+
    "\11\26\2\0\4\26\14\0\5\26\1\247\16\26\22\0"+
    "\11\26\2\0\4\26\14\0\13\26\1\250\10\26\22\0"+
    "\3\26\1\251\5\26\2\0\4\26\14\0\24\26\22\0"+
    "\1\252\10\26\2\0\4\26\14\0\24\26\22\0\2\26"+
    "\1\253\6\26\2\0\4\26\14\0\24\26\32\0\1\254"+
    "\75\0\1\225\73\0\7\26\1\255\1\26\2\0\4\26"+
    "\14\0\24\26\22\0\2\26\1\256\6\26\2\0\4\26"+
    "\14\0\24\26\22\0\11\26\2\0\4\26\14\0\5\26"+
    "\1\257\16\26\22\0\5\26\1\260\3\26\2\0\4\26"+
    "\14\0\24\26\30\0\1\225\72\0\2\26\1\261\6\26"+
    "\2\0\4\26\14\0\24\26\22\0\11\26\2\0\4\26"+
    "\14\0\10\26\1\262\13\26\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[8190];
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


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;
  private static final char[] EMPTY_BUFFER = new char[0];
  private static final int YYEOF = -1;
  private static java.io.Reader zzReader = null; // Fake

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\4\0\10\11\1\1\1\11\1\1\1\11\1\1\1\11"+
    "\34\1\1\11\4\1\1\11\17\1\1\0\1\11\3\0"+
    "\3\1\2\0\5\1\6\0\3\11\1\0\1\1\1\11"+
    "\13\1\3\0\1\11\5\0\10\1\6\0\12\1\2\0"+
    "\5\1\1\11\6\0\11\1\2\0\10\1\1\0\6\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[178];
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

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** this buffer may contains the current text array to be matched when it is cheap to acquire it */
  private char[] zzBufferArray;

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;  


  public HaskellFlexLexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public HaskellFlexLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 156) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart(){
    return zzStartRead;
  }

  public final int getTokenEnd(){
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end,int initialState){
    zzBuffer = buffer;
    zzBufferArray = com.intellij.util.text.CharArrayUtil.fromSequenceWithoutCopying(buffer);
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzPushbackPos = 0;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  // For Demetra compatibility
  public void reset(CharSequence buffer, int initialState){
    zzBuffer = buffer;
    zzBufferArray = null;
    zzCurrentPos = zzMarkedPos = zzStartRead = 0;
    zzPushbackPos = 0;
    zzAtEOF = false;
    zzAtBOL = true;
    zzEndRead = buffer.length();
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBufferArray != null ? zzBufferArray[zzStartRead+pos]:zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;
    char[] zzBufferArrayL = zzBufferArray;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      if (zzMarkedPosL > zzStartRead) {
        switch (zzBufferL.charAt(zzMarkedPosL-1)) {
        case '\n':
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          zzAtBOL = true;
          break;
        case '\r': 
          if (zzMarkedPosL < zzEndReadL)
            zzAtBOL = zzBufferL.charAt(zzMarkedPosL) != '\n';
          else if (zzAtEOF)
            zzAtBOL = false;
          else {
            boolean eof = zzRefill();
            zzMarkedPosL = zzMarkedPos;
            zzEndReadL = zzEndRead;
            zzBufferL = zzBuffer;
            if (eof) 
              zzAtBOL = false;
            else 
              zzAtBOL = zzBufferL.charAt(zzMarkedPosL) != '\n';
          }
          break;
        default:
          zzAtBOL = false;
        }
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      if (zzAtBOL)
        zzState = ZZ_LEXSTATE[zzLexicalState+1];
      else
        zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL.charAt(zzCurrentPosL++);
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
              zzInput = zzBufferL.charAt(zzCurrentPosL++);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 4: 
          { return HaskellElementTypes.COMMA;
          }
        case 62: break;
        case 18: 
          { return HaskellElementTypes.CON_ID;
          }
        case 63: break;
        case 27: 
          { return HaskellElementTypes.ARROW_OP;
          }
        case 64: break;
        case 8: 
          { return HaskellElementTypes.BACKQUOTE;
          }
        case 65: break;
        case 26: 
          { yybegin(COMMENT1); return HaskellElementTypes.NCOMMENT;
          }
        case 66: break;
        case 16: 
          { return HaskellElementTypes.COLON_OP;
          }
        case 67: break;
        case 22: 
          { return HaskellElementTypes.BAR_OP;
          }
        case 68: break;
        case 52: 
          { return HaskellElementTypes.CLASS_ID;
          }
        case 69: break;
        case 59: 
          { return HaskellElementTypes.DEFAULT_ID;
          }
        case 70: break;
        case 25: 
          { return HaskellElementTypes.NCOMMENT;
          }
        case 71: break;
        case 36: 
          { return HaskellElementTypes.CONTEXT_OP;
          }
        case 72: break;
        case 29: 
          { return HaskellElementTypes.IN_ID;
          }
        case 73: break;
        case 42: 
          { return HaskellElementTypes.LET_ID;
          }
        case 74: break;
        case 43: 
          { return HaskellElementTypes.CHAR;
          }
        case 75: break;
        case 9: 
          { return HaskellElementTypes.OPEN_BRACE;
          }
        case 76: break;
        case 10: 
          { return HaskellElementTypes.CLOSE_BRACE;
          }
        case 77: break;
        case 60: 
          { return HaskellElementTypes.INSTANCE_ID;
          }
        case 78: break;
        case 3: 
          { return HaskellElementTypes.CLOSE_PAR;
          }
        case 79: break;
        case 31: 
          { return HaskellElementTypes.CON_SYM;
          }
        case 80: break;
        case 51: 
          { return HaskellElementTypes.INFIX_ID;
          }
        case 81: break;
        case 6: 
          { return HaskellElementTypes.OPEN_BRACKET;
          }
        case 82: break;
        case 57: 
          { return HaskellElementTypes.MODULE_ID;
          }
        case 83: break;
        case 49: 
          { return HaskellElementTypes.THEN_ID;
          }
        case 84: break;
        case 46: 
          { return HaskellElementTypes.DATA_ID;
          }
        case 85: break;
        case 44: 
          { return HaskellElementTypes.FLOAT;
          }
        case 86: break;
        case 30: 
          { return HaskellElementTypes.DO_ID;
          }
        case 87: break;
        case 58: 
          { return HaskellElementTypes.NEWTYPE_ID;
          }
        case 88: break;
        case 50: 
          { return HaskellElementTypes.PREPROCESSOR;
          }
        case 89: break;
        case 28: 
          { return HaskellElementTypes.IF_ID;
          }
        case 90: break;
        case 20: 
          { return HaskellElementTypes.EQUAL_OP;
          }
        case 91: break;
        case 38: 
          { yybegin(COMMENT2); return HaskellElementTypes.NCOMMENT;
          }
        case 92: break;
        case 54: 
          { return HaskellElementTypes.INFIXL_ID;
          }
        case 93: break;
        case 37: 
          { return HaskellElementTypes.OF_ID;
          }
        case 94: break;
        case 17: 
          { return HaskellElementTypes.WILDCARD_ID;
          }
        case 95: break;
        case 61: 
          { return HaskellElementTypes.DERIVING_ID;
          }
        case 96: break;
        case 48: 
          { return HaskellElementTypes.TYPE_ID;
          }
        case 97: break;
        case 19: 
          { return HaskellElementTypes.BACKSLASH_OP;
          }
        case 98: break;
        case 56: 
          { return HaskellElementTypes.IMPORT_ID;
          }
        case 99: break;
        case 1: 
          { return HaskellElementTypes.BAD_CHARACTER;
          }
        case 100: break;
        case 23: 
          { return HaskellElementTypes.TILDE_OP;
          }
        case 101: break;
        case 53: 
          { return HaskellElementTypes.WHERE_ID;
          }
        case 102: break;
        case 7: 
          { return HaskellElementTypes.CLOSE_BRACKET;
          }
        case 103: break;
        case 39: 
          { yybegin(YYINITIAL); return HaskellElementTypes.NCOMMENT;
          }
        case 104: break;
        case 47: 
          { return HaskellElementTypes.CASE_ID;
          }
        case 105: break;
        case 35: 
          { return HaskellElementTypes.GENERATOR_OP;
          }
        case 106: break;
        case 15: 
          { return HaskellElementTypes.VAR_ID;
          }
        case 107: break;
        case 24: 
          { return HaskellElementTypes.INTEGER;
          }
        case 108: break;
        case 11: 
          { return HaskellElementTypes.NEWLINE;
          }
        case 109: break;
        case 12: 
          { return HaskellElementTypes.WHITE_SPACE;
          }
        case 110: break;
        case 13: 
          { return HaskellElementTypes.TAB;
          }
        case 111: break;
        case 32: 
          { return HaskellElementTypes.DOUBLE_COLON_OP;
          }
        case 112: break;
        case 14: 
          { return HaskellElementTypes.VAR_SYM;
          }
        case 113: break;
        case 34: 
          { return HaskellElementTypes.DOUBLE_DOT_OP;
          }
        case 114: break;
        case 55: 
          { return HaskellElementTypes.INFIXR_ID;
          }
        case 115: break;
        case 45: 
          { return HaskellElementTypes.ELSE_ID;
          }
        case 116: break;
        case 21: 
          { return HaskellElementTypes.AT_OP;
          }
        case 117: break;
        case 5: 
          { return HaskellElementTypes.SEMICOLON;
          }
        case 118: break;
        case 2: 
          { return HaskellElementTypes.OPEN_PAR;
          }
        case 119: break;
        case 40: 
          { throw new Error("Too deep nested comments");
          }
        case 120: break;
        case 41: 
          { return HaskellElementTypes.COMMENT;
          }
        case 121: break;
        case 33: 
          { return HaskellElementTypes.STRING;
          }
        case 122: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            return null;
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
