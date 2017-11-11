/*

Professor: Jose M Garrido
Class: Concepts of Programming Langugaes
Groupd Members: Juan E Tenorio Arzola, Andrew Shatz, Thomas Nguyen
Project: 2nd Deliverable

*/
#include "Lexer.h"
#include "Token.h"
#include <string>
#include <stdio.h>
#include <ctype.h>
#include <stdbool.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <sstream>


int Lexer::counter = 1;

Lexer::Lexer(std::string filename) {
	this->filename = filename;
	fp.open(filename);
	
}

Token Lexer::nextToken() {
	
	int state = 1;
	int digit;
	std::string alphaBuffer;
	char ch;
	ch = fp.get();
	bool skipped = false;
	while (true)
	{

		if (ch == EOF && !skipped) {

			skipped = true;
		}
		else if (skipped) {

			fp.close();


		}


		switch (state)
		{

		case 1:

			switch (ch)
			{
			case '(':

				return Token("LP", "(", counter);

			case ')':

				return Token("RP", ")", counter);

			case '{':

				return Token("LC", "{", counter);

			case '}':

				return Token("RC", "}", counter);

			case '[':
				return Token("LB", "[", counter);

			case ']':
				return Token("RB", "]", counter);


			case '\n':
				ch = fp.get(); // spaces can be ignored
				counter++;
				continue;
			case '\b':
			case '\f':
			case '\r':
			case '\t':
			case ' ':
				ch = fp.get(); // spaces can be ignored
				
				continue;

			case '.':

				return Token("PD", ".", counter);

			case '+':

				return Token("SM", "+",counter);

			case '=':

				ch = fp.get();
				state = 6;
				continue;

			case '-':

				ch = fp.get();
				state = 7;
				continue;

			case '*':

				return Token("MUL", "*",counter);

			case '/':

				return Token("DIV", "/",counter);

			default:
				state = 2;
				continue;
			}

		case 2:

			if (isdigit(ch)) {

				digit = ch - '0';
				state = 3;
				ch = fp.get();
			}
			else {
				state = 4;
			}

			continue;

		case 3:

			if (isdigit(ch)) {
				

				digit *= 10;
				digit += ch - '0';
				

				ch = fp.get();
			}

			else {

				return Token("NUM", std::to_string(digit),counter);

			}

			continue;


		case 4:

			if (isalpha(ch) || ch == '_') {

				alphaBuffer = "";
				alphaBuffer += ch;
				state = 5;
				ch = fp.get();
			}

			else {
				alphaBuffer = "";
				alphaBuffer += ch;
				return Token("INVALID: ", alphaBuffer,counter);
			}

			continue;

		case 5:

			if (isalpha(ch) || isdigit(ch) || ch == '_') {

				alphaBuffer += ch;
				ch = fp.get();
			}

			else {

				state = 1;

				fp.unget();

				if (alphaBuffer == "then" || alphaBuffer == "type" || alphaBuffer == "local" || alphaBuffer == "end" ||
					alphaBuffer == "if" || alphaBuffer == "while" || alphaBuffer == "not" || alphaBuffer == "do") {
					return Token("KW", alphaBuffer,counter);
				}

				return Token("ID", alphaBuffer,counter);
			}

			continue;

		case 6:

			if (ch == '=') {
				
				return Token("IEQ", "==",counter);
			}

			else {
				return Token("EQ", "=",counter);
			}

		case 7:

			if (ch == '-') {
				alphaBuffer = "--";
				ch = fp.get();
				state = 8;
				
			}

			else {
				return Token("SUB", "-",counter);
			}
			continue;

		case 8:

			if (ch != '\n') {
				
				alphaBuffer += ch;
				
				ch = fp.get();
			}
			else {
				return Token("COM", alphaBuffer,counter);
			}

			continue;

		case 9:

			if (ch == '\n') {
				state = 1;
			}
			continue;

		}

	}

}

