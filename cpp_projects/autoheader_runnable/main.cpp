/**
 * Copyright (C) 2013 Derby Russell <jdrussell51@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * File:   main.cpp
 * UML:    Program Main
 * Author: Derby Russell <jdrussell51@gmail.com>
 * 
 * Program autoheader_runnable.
 * 
 * This file is the autoheader_runnable used by Netbeans plugin executables.
 *
 * Created on November 15, 2013, 1:18 AM
 */

#include <cstdlib>
#include <iostream>

#include "pstream.h"

using namespace std;

#include "execute_stream.h"

/*
 * 
 */
int main(int argc, char** argv) {
    string sCommand = "";
    if (command_builder(argc, argv, "autoheader", sCommand) == 0) return 0;
    
    execute_stream(sCommand);

    return 0;
}

