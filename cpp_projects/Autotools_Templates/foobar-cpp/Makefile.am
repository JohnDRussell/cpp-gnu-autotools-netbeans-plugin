## Process this file with automake to produce Makefile.in
## Created by Netbeans

SUBDIRS = src

${[REPLACE-GNU-AUTOCREATOR]}docdir = ${prefix}/doc/${[REPLACE-GNU-AUTOCREATOR]}
${[REPLACE-GNU-AUTOCREATOR]}doc_DATA = \
	README\
	COPYING\
	AUTHORS\
	ChangeLog\
	INSTALL\
	NEWS


INTLTOOL_FILES = intltool-extract.in \
	intltool-merge.in \
	intltool-update.in

EXTRA_DIST = $(${[REPLACE-GNU-AUTOCREATOR]}doc_DATA) \
	$(INTLTOOL_FILES)

DISTCLEANFILES = intltool-extract \
	intltool-merge \
	intltool-update


# Remove doc directory on uninstall
uninstall-local:
	-rm -r $(${[REPLACE-GNU-AUTOCREATOR]}docdir)
