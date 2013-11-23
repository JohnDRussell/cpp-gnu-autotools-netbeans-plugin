dnl Process this file with autoconf to produce a configure script.
dnl Created by Netbeans application wizard.

AC_INIT([${[REPLACE-GNU-AUTOCREATOR]}], [${[REPLACE-GNU-VERSION-AUTOCREATOR]}])

AC_CONFIG_HEADERS([config.h])

AC_CONFIG_SRCDIR([src/main.c])
AM_INIT_AUTOMAKE

AM_SILENT_RULES([yes])

AC_PROG_CC




dnl ***************************************************************************
dnl Internationalization
dnl ***************************************************************************
IT_PROG_INTLTOOL([0.35.0])

GETTEXT_PACKAGE=${[REPLACE-GNU-AUTOCREATOR]}
AC_SUBST(GETTEXT_PACKAGE)
AC_DEFINE_UNQUOTED(GETTEXT_PACKAGE,"$GETTEXT_PACKAGE", [GETTEXT package name])

AM_GLIB_GNU_GETTEXT



LT_INIT






AC_OUTPUT([
Makefile
src/Makefile
])
