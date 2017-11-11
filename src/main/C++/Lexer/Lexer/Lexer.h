#pragma once
#include <string>
#include "Token.h"
#include <fstream>
class Lexer {

public:

	Lexer(std::string filename);
	Token nextToken();
	std::string filename;
	std::fstream fp;
	static int counter;
};