package net.alexanderkiel.idea_haskell_plugin.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes;

%%

%class HaskellFlexLexer
%implements FlexLexer
%final
%public
%unicode
%function advance
%type IElementType
//%debug

Special      = {OpenPar} | {ClosePar} | {Comma} | {Semicolon} | {OpenBracket} | {CloseBracket} | {Backquote} | {OpenBrace} | {CloseBrace}
OpenPar      = "("
ClosePar     = ")"
Comma        = ","
Semicolon    = ";"
OpenBracket  = "["
CloseBracket = "]"
Backquote    = "`"
OpenBrace    = "{"
CloseBrace   = "}"

WhiteSpace   = {WhiteChar}+
WhiteChar    = {Space} //| {VerticalTab} | {UniWhite}
Newline      = {Return} {LineFeed} | {Return} | {LineFeed} | {FormFeed}
Return       = \r
LineFeed     = \n
//VerticalTab = \v
FormFeed     = \f
Space        = " "
Tab          = \t

Comment      = {Dashes} {AnyWithoutSymbol}? {Any}* {Newline}
Dashes       = "--" "-"*
OpenComment  = "{-"
CloseComment = "-}"

Preprocessor = "#" {PreId} {Any}* {Newline}
PreId        = "if" | "else" | "elif" | "endif" | "ifdef" | "include" | "define" | "undef"

Any = {Graphic} | {Space} | {Tab}
AnyWithoutSymbol = {GraphicWithoutSymbol} | {Space} | {Tab}
Graphic = {GraphicWithoutSymbol} | {Symbol}
GraphicWithoutSymbol = {Small} | {Large} | {Digit} | {Special} | ":" | "\"" | "'"

Small = {AsciiSmall} | "_"
AsciiSmall = [a-z]

Large = {AsciiLarge}
AsciiLarge = [A-Z]

Symbol = {AsciiSymbol} //TODO | {UniSymbol}
SymbolWithOutBackslash = {AsciiSymbolWithOutBackslash}
AsciiSymbol = {AsciiSymbolWithOutBackslash} | "\\"
AsciiSymbolWithOutBackslash = "!" | "#" | "$" | "%" | "&" | "*" | "+" | "." | "/" | "<" | "=" | ">" | "?" | "@" | "^" | "|" | "-" | "~"

Digit         = {AsciiDigit}
AsciiDigit    = [0-9]
Octit         = [0-7]
Hexit         = {Digit} | [A-Fa-f]

VarId         = {Small} ({Small} | {Large} | {Digit} | "'")*
ConId         = {Large} ({Small} | {Large} | {Digit} | "'")*

ReservedId    = {CaseId} | {ClassId} | {DataId} | {DefaultId} | {DerivingId} | {DoId} | {ElseId} | {IfId} | {ImportId} | {InId} | {InfixId} | {InfixlId} | {InfixrId} | {InstanceId} | {LetId} | {ModuleId} | {NewtypeId} | {OfId} | {ThenId} | {TypeId} | {WhereId} | {WildcardId}
CaseId        = "case"
ClassId       = "class"
DataId        = "data"
DefaultId     = "default"
DerivingId    = "deriving"
DoId          = "do"
ElseId        = "else"
IfId          = "if"
ImportId      = "import"
InId          = "in"
InfixId       = "infix"
InfixlId      = "infixl"
InfixrId      = "infixr"
InstanceId    = "instance"
LetId         = "let"
ModuleId      = "module"
NewtypeId     = "newtype"
OfId          = "of"
ThenId        = "then"
TypeId        = "type"
WhereId       = "where"
WildcardId    = "_"

VarSym        = {Symbol} ({Symbol} | ":")*
ConSym        = ":" ({Symbol} | ":")*
ReservedOp    = {DoubleDotOp} | {ColonOp} | {DoubleColonOp} | {EqualOp} | {BackslashOp} | {BarOp} | {GeneratorOp} | {ArrowOp} | {AtOp} | {TildeOp} | {ContextOp}
DoubleDotOp   = ".."
ColonOp       = ":"
DoubleColonOp = "::"
EqualOp       = "="
BackslashOp   = "\\"
BarOp         = "|"
GeneratorOp   = "<-"
ArrowOp       = "->"
AtOp          = "@"
TildeOp       = "~"
ContextOp     = "=>"

Decimal       = {Digit}+
Octal         = {Octit}+
Hexadecimal   = {Hexit}+

Integer       = {Decimal} | "0o" {Octal} | "0O" {Octal} | "0x" {Hexadecimal} | "0X" {Hexadecimal}
Float         = {Decimal} "." {Decimal} {Exponent}? | {Decimal} {Exponent}?
Exponent      = ("e" | "E") ("+" | "-")? {Decimal}

Char          = "'" ({CharGraphic} | {Space} | {Escape}) "'" //TODO: not really original
CharGraphic   = {Small} | {Large} | {SymbolWithOutBackslash} | {Digit} | {Special} | ":" | "\""
String        = "\"" ({StringGraphic} | {Space} | {Escape})* "\"" //TODO: not really original
StringGraphic = {Small} | {Large} | {SymbolWithOutBackslash} | {Digit} | {Special} | ":" | "'"
Escape        = "\\" ({Charesc} | {Ascii} | {Decimal} | "o" {Octal} | "x" {Hexadecimal})
Charesc       = "a" | "b" | "f" | "n" | "r" | "t" | "v" | "\\" | "\"" | "'" | "&"
Ascii         = ("^" {Cntrl}) | [\0-\32]
Cntrl         = {AsciiLarge} | "@" | "[" | "\\" | "]" | "^" | "_"

%state COMMENT1, COMMENT2

%%

<YYINITIAL> {
    ^{Preprocessor} { return HaskellElementTypes.PREPROCESSOR; }

    {Integer}       { return HaskellElementTypes.INTEGER; }
    {Float}         { return HaskellElementTypes.FLOAT; }
    {Char}          { return HaskellElementTypes.CHAR; }
    {String}        { return HaskellElementTypes.STRING; }

    {OpenPar}       { return HaskellElementTypes.OPEN_PAR; }
    {ClosePar}      { return HaskellElementTypes.CLOSE_PAR; }
    {Comma}         { return HaskellElementTypes.COMMA; }
    {Semicolon}     { return HaskellElementTypes.SEMICOLON; }
    {OpenBracket}   { return HaskellElementTypes.OPEN_BRACKET; }
    {CloseBracket}  { return HaskellElementTypes.CLOSE_BRACKET; }
    {Backquote}     { return HaskellElementTypes.BACKQUOTE; }
    {OpenBrace}     { return HaskellElementTypes.OPEN_BRACE; }
    {CloseBrace}    { return HaskellElementTypes.CLOSE_BRACE; }

    {CaseId}        { return HaskellElementTypes.CASE_ID; }
    {ClassId}       { return HaskellElementTypes.CLASS_ID; }
    {DataId}        { return HaskellElementTypes.DATA_ID; }
    {DefaultId}     { return HaskellElementTypes.DEFAULT_ID; }
    {DerivingId}    { return HaskellElementTypes.DERIVING_ID; }
    {DoId}          { return HaskellElementTypes.DO_ID; }
    {ElseId}        { return HaskellElementTypes.ELSE_ID; }
    {IfId}          { return HaskellElementTypes.IF_ID; }
    {ImportId}      { return HaskellElementTypes.IMPORT_ID; }
    {InId}          { return HaskellElementTypes.IN_ID; }
    {InfixId}       { return HaskellElementTypes.INFIX_ID; }
    {InfixlId}      { return HaskellElementTypes.INFIXL_ID; }
    {InfixrId}      { return HaskellElementTypes.INFIXR_ID; }
    {InstanceId}    { return HaskellElementTypes.INSTANCE_ID; }
    {LetId}         { return HaskellElementTypes.LET_ID; }
    {ModuleId}      { return HaskellElementTypes.MODULE_ID; }
    {NewtypeId}     { return HaskellElementTypes.NEWTYPE_ID; }
    {OfId}          { return HaskellElementTypes.OF_ID; }
    {ThenId}        { return HaskellElementTypes.THEN_ID; }
    {TypeId}        { return HaskellElementTypes.TYPE_ID; }
    {WhereId}       { return HaskellElementTypes.WHERE_ID; }
    {WildcardId}    { return HaskellElementTypes.WILDCARD_ID; }

    {DoubleDotOp}   { return HaskellElementTypes.DOUBLE_DOT_OP; }
    {ColonOp}       { return HaskellElementTypes.COLON_OP; }
    {DoubleColonOp} { return HaskellElementTypes.DOUBLE_COLON_OP; }
    {EqualOp}       { return HaskellElementTypes.EQUAL_OP; }
    {BackslashOp}   { return HaskellElementTypes.BACKSLASH_OP; }
    {BarOp}         { return HaskellElementTypes.BAR_OP; }
    {GeneratorOp}   { return HaskellElementTypes.GENERATOR_OP; }
    {ArrowOp}       { return HaskellElementTypes.ARROW_OP; }
    {AtOp}          { return HaskellElementTypes.AT_OP; }
    {TildeOp}       { return HaskellElementTypes.TILDE_OP; }
    {ContextOp}     { return HaskellElementTypes.CONTEXT_OP; }

    {VarSym}        { return HaskellElementTypes.VAR_SYM; }
    {ConSym}        { return HaskellElementTypes.CON_SYM; }

    {VarId}         { return HaskellElementTypes.VAR_ID; }
    {ConId}         { return HaskellElementTypes.CON_ID; }

    {Comment}       { return HaskellElementTypes.COMMENT; }
    {OpenComment}   { yybegin(COMMENT1); return HaskellElementTypes.NCOMMENT; }

    {WhiteSpace}    { return HaskellElementTypes.WHITE_SPACE; }
    {Newline}       { return HaskellElementTypes.NEWLINE; }
    {Tab}           { return HaskellElementTypes.TAB; }
}

<COMMENT1> {
    {OpenComment}   { yybegin(COMMENT2); return HaskellElementTypes.NCOMMENT; }
    {CloseComment}  { yybegin(YYINITIAL); return HaskellElementTypes.NCOMMENT; }
    {Newline}       { return HaskellElementTypes.NEWLINE; }
    .               { return HaskellElementTypes.NCOMMENT; }
}

<COMMENT2> {
    {OpenComment}   { throw new Error("Too deep nested comments"); }
    {CloseComment}  { yybegin(COMMENT1); return HaskellElementTypes.NCOMMENT; }
    .               { return HaskellElementTypes.NCOMMENT; }
}


. | \n              { return HaskellElementTypes.BAD_CHARACTER; }