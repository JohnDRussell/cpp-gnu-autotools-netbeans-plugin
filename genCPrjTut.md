[Back to MainPage](MainPage.md)

# Generic C Project Tutorial #

To create a new Autotools style deployable project follow this tutorial.

Start up Netbeans 7.4 and make sure the CppGnuAutotools plugin is properly installed See [Installing CppGnuAutotools Plugin](autotools_install.md).

Start by selecting New Project... from the File menu.  Then navigate
to the C/C++ section and<br />
select Generic C Autotools Project as shown next:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/NewCProject.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/NewCProject.png)
<br />
Give your new project a name:
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/wiz_page2.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/wiz_page2.png)
<br />
This name will be used throughout the project files such as the executable name in src/Makefile.am:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/MakfileAMview.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/MakfileAMview.png)
<br />
Once you complete the new project wizard you will be presented with
a new autotools project of the following files:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/CompletedNewProject.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/CompletedNewProject.png)
<br />
Now you need to run all the autotools programs to get your project outfitted with a Makefile.<br />
To begin run the programs in the order listed in the right-click project menu starting with Run Libtoolize:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/prj_menu.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/prj_menu.png)
<br />
A dialog will popup:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/libtoolize_dialog.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/libtoolize_dialog.png)
<br />
In this dialog you can check as many checkboxes as you need to.  With libtoolize on this new project, you don't need to check any of them.  Notice if you do check one, say "--verbose", then that text will automatically be filled in for you into the edit box.<br />
If you want to add an option that is not listed, then just type it manually into the options edit box of the dialog.<br />
When all your selections and options are filled in as desired press the <b>Execute</b> button.  If you decide you don't want to run libtoolize at this time then select the <b>Cancel</b> button and the action will be cancelled.<br />
Repeat this process for <b>Run ACLocal</b><br />
Then for <b>Run AutoHeader</b><br />
Then for <b>Run AutoMake</b><br />
Notice the dialog for AutoMake:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/automake_dialog.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/automake_dialog.png)
<br />
Automake has many more options than libtoolize.  Some have more, some have less.<br />
Before you run AutoMake on a new project like this you will need to make one selection.  Select the "--add-missing" option before you press the <b>Execute</b> button:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/automake_dialog_missingsel.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/automake_dialog_missingsel.png)
<br />
Then <b>Run AutoConf</b><br />
And finally run the configure script with <b>Run Configure</b><br />
<br />
If you are not happy with the settings you chose and would like to start over again, then simply select the clean autotools files and that will bring your project back to the way it was when you just created it.<br />
To run this if needed select <b>Run Clean Autotools</b><br />
<br />
Now your project has a makefile. What you should do now is
  * Close this project.
  * Close Netbeans.
  * Open Netbeans back up
  * Select File->New Project again.
<br />
This time select Category: C/C++ with Project type C/C++ Project with Existing Sources:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/ExistingSources.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/CTut/ExistingSources.png)
<br />
Navigate to the folder where your new project just created for Autotools.
Select the folder of your new autotools project. Then select ok.  In the New Project Wizard you will have a <b>Finish</b> button.  Select the <b>Finish</b> button now.  The new project importer will run ./configure again for you.  Now you have a normal C/C++ project.  You can now select <b>Build</b> and your new project will have an executable created for you.
<br />
Select <b>Run</b> and the output window will show you:<br />
Hello World<br />
<b><font color='green'>Run Successful!</font></b><br />
[Back to MainPage](MainPage.md)