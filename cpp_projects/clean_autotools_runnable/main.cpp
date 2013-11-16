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
 * Program clean_autotools_runnable.
 * 
 * This file is the clean_autotools_runnable used by Netbeans plugin executables.
 * This file will delete all the following files:
 * 
 * aclocal.m4
 * Makefile
 * Makefile.in
 * config.h
 * configure
 * 
 * from all directories specified in the SUBDIRS parameter of the
 * Makfile.am
 * 
 * Also deletes the following directory:
 * autom4te.cache
 *
 * Created on November 12, 2013, 1:39 PM
 */

#include <cstdlib>
#include <iostream>
#include <fstream>
#include <stdio.h>
#include <string.h>

#include "pstream.h"

using namespace std;

#include "execute_stream.h"

void deleteFileFromDirectory(string sDefaultDir, string sFileName)
{
    string sCommand = "rm ";
    sCommand += sDefaultDir;
    sCommand += "/";
    sCommand += sFileName;
    
    cout << sCommand << endl;
    
    execute_stream(sCommand);
}

void deleteDirectoryFromDirectory(string sDefaultDir, string sDirName)
{
    string sCommand = "rm -rf ";
    sCommand += sDefaultDir;
    sCommand += "/";
    sCommand += sDirName;
    
    cout << sCommand << endl;
    
    execute_stream(sCommand);
}

/*
 * 
 */
int main(int argc, char** argv) {
    if (argc >= 1)
    {
        if (argc == 1) return 0;
    }
    
    char strLine[2048] = "";
    ifstream fMakefileAM;
    string sDefaultDir = argv[1];
    string sNextDir;
    string sMakefileAM = sDefaultDir;
    sMakefileAM += "/Makefile.am";
    
    cout << sDefaultDir << endl;
    
    fMakefileAM.open(sMakefileAM.c_str());
    if (fMakefileAM.good())
    {
        fMakefileAM.getline(strLine, 2048);
        while(strstr(strLine, "SUBDIRS") == NULL)
        {
            fMakefileAM.getline(strLine, 2048);
        }
        fMakefileAM.close();
    }
    
    string sDirs, sGather;
    int nLen = (int)strlen(strLine);
    int nIndex = 0 ;
    bool bFoundEquals = false;
    cout << strLine << endl;
    
    deleteFileFromDirectory(sDefaultDir, "aclocal.m4") ;
    deleteFileFromDirectory(sDefaultDir, "Makefile") ;
    deleteFileFromDirectory(sDefaultDir, "Makefile.in") ;
    deleteFileFromDirectory(sDefaultDir, "config.h") ;
    deleteFileFromDirectory(sDefaultDir, "configure") ;
    deleteDirectoryFromDirectory(sDefaultDir, "autom4te.cache");
    
    nIndex = 0 ;
    while (nIndex < nLen)
    {
        if (!bFoundEquals)
        {
            if (strLine[nIndex] == '=')
            {
                bFoundEquals = true;
            }
        }
        else
        {
            if ((strLine[nIndex] == ' ') || (nIndex == (nLen - 1)))
            {
                if (nIndex == (nLen - 1))
                {
                    sGather.append(1, strLine[nIndex]);
                }
                if (sGather.length() > 0)
                {
                    sDirs = sGather;
                    sGather = "" ;
                    sNextDir = sDefaultDir;
                    sNextDir += "/";
                    sNextDir += sDirs;
                    deleteFileFromDirectory(sNextDir, "Makefile") ;
                    deleteFileFromDirectory(sNextDir, "Makefile.in") ;
                }
            }
            else
            {
                sGather.append(1, strLine[nIndex]);
            }
        }
        
        nIndex++;
    }

    return 0;
}

