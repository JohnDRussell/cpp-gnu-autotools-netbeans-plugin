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
 * File:   execute_stream.h
 * UML:    Program Main
 * Author: Derby Russell <jdrussell51@gmail.com>
 * 
 * Program to run process using pstreams.h.
 * 
 * This file is in the runnableCommon folder used by Netbeans plugin executables.
 *
 * Created on November 12, 2013, 1:30 PM
 */

#ifndef EXECUTE_STREAM_H
#define	EXECUTE_STREAM_H

int command_builder(int argc, char **argv, const char * szCmdName, string &sReturn);
void execute_stream(string sCommand, const char * szCmdName, bool bDisplayMessage = true);

#endif	/* EXECUTE_STREAM_H */

