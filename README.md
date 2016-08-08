# TTime Language

## Feature

- Based on [CLite](http://myslu.stlawu.edu/~ehar/Spring10/364/clite_grammar.html)
- Support _Time_ as primitive variable
- Support calculation of _Time_ (like +, -)
- Support Array of variable
- Support Global Variable
- Support user-custom Function
- Support Function Overloading
- Support **for, while, switch** loop
- Support floating point variable(_float_)

## [EBNF](https://github.com/TTimeLanguage/Compiler/blob/isac/src/BNF/TTime_EBNF.pdf)


---

# TTime Language Compiler

It compiles **TTime Language** source code to [U-Code](http://pl.skuniv.ac.kr/Lecture/Compiler/cdt-9/sld022.htm)

## Entry Points

### 1. `CodeGenerator.CodeGenerator`
- code generator
- It returns [U-Code](http://pl.skuniv.ac.kr/Lecture/Compiler/cdt-9/sld022.htm) file

#### Parameter
1. full path of source code
2. full path of result file

### 2. `Lexer.Lexer`
- Just print tokens of given source code

#### Parameter
1. full path of source code

### 3. `Parser.Parser`
- Print AST of given source code

#### Parameter
1. full path of source code


### 4. `Semantic.TypeChecker`
- Print AST if given source code is valid, or print error message

#### Parameter
1. full path of source code

