#include "Token.h"
#include "Parser.h"
#include "Lexer.h"
#include <string>
#include <iostream>
#include <fstream>

/*

Professor: Jose M Garrido
Class: Concepts of Programming Langugaes 
Groupd Members: Juan E Tenorio Arzola, Andrew Shatz, Thomas Nguyen
Project: 2nd Deliverable

*/


int main(int arc, char* filename[] ) {


	if (arc > 1) {
		Parser par(filename[1]);
		std::cout << "ERRORS:\n";
		par.grabLineCode();

		std::cout << "\n\n";
		std::cout << "------------OUTPUT------------\n\n" << std::endl;
		par.printT();
	}
	else {
		Parser par("Sample.lua");
		std::cout << "ERRORS:\n";
		par.grabLineCode();

		std::cout << "\n\n";
		std::cout << "------------OUTPUT------------\n\n" << std::endl;
		par.printT();
	}

	int exit;
	std::cin >> exit;
	return 0;
}