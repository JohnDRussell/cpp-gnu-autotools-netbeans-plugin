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
 * File:   execute_stream.cpp
 * UML:    Program Main
 * Author: Derby Russell <jdrussell51@gmail.com>
 * 
 * Program to run process using pstreams.h.
 * 
 * This file is in the runnableCommon folder used by Netbeans plugin executables.
 *
 * Created on November 12, 2013, 1:28 PM
 */
#include <cstdlib>
#include <iostream>

#include "pstream.h"

using namespace std;

int command_builder(int argc, char **argv, const char * szCmdName, string &sReturn)
{
    if (argc >= 1)
    {
        if (argc == 1) return 0;
    }
    
    if (argc >= 2) {
        sReturn = "cd ";
        sReturn += argv[1];
        sReturn += ";";
        sReturn += szCmdName;
        if (argc >= 3) {
            sReturn += " ";
            sReturn += argv[2];
        }

        cout << "cd " << argv[1] << endl;

        if (argc >= 2) {
            cout << szCmdName << " " << argv[2] << endl;
        } else {
            cout << szCmdName << endl;
        }
        return 1;
    }
    return 0;
}

void execute_stream(string sCommand)
{
    const redi::pstreams::pmode mode = redi::pstreams::pstdout|redi::pstreams::pstderr;
    redi::ipstream child(sCommand, mode);
    char buf[1024];
    std::streamsize n;
    bool finished[2] = { false, false };
    while (!finished[0] || !finished[1])
    {
        if (!finished[0])
        {
            while ((n = child.err().readsome(buf, sizeof(buf))) > 0)
                std::cerr.write(buf, n).flush();
            if (child.eof())
            {
                finished[0] = true;
                if (!finished[1])
                    child.clear();
            }
        }

        if (!finished[1])
        {
            while ((n = child.out().readsome(buf, sizeof(buf))) > 0)
                std::cout.write(buf, n).flush();
            if (child.eof())
            {
                finished[1] = true;
                if (!finished[0])
                    child.clear();
            }
        }
    }
}

