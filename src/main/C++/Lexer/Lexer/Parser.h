#pragma once
#include "Token.h"
#include "Lexer.h"
#include <list>
#include "Token.h"
#include <vector>
#include <deque>

class Parser {

public:



	std::vector<std::deque<Token>> list;
	std::string filename;
	std::deque<Token> tokens;

	void newLine();
	void foundCOMMENT();
	Parser(std::string filename);
	void grabLineCode();
	void foudnEND();
	void foundWHILE();
	void foundDO();
	void foundTHEN();
	void foundCOMMA();

	bool ScanQUEUE(std::string var, std::deque<Token> &deq);

	void printT();
	void foundIF();
	void foundID();
	void foundSM();
	void foundDIV();
	void foundSUB();
	void foundMUL();
};
