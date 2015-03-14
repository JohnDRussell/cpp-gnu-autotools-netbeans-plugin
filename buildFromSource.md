[return to MainPage](MainPage.md)

# Building from source #

First download all the sources.<br />
You can download all with the zip option:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/DwnZip.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/DwnZip.png)
<br />

Or you can clone with git.
<br />
<br />

If you downloaded the zip, then unzip to a location on your computer:<br />

![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/Unzip.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/Unzip.png)
<br />

Start up Netbeans 7.4.  To edit the Netbeans module code, just open up the root of the download by first selecting Open Project...:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/OpenPrj.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/OpenPrj.png)
<br />

Then select the root of the download:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/SelectCpp.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/SelectCpp.png)
<br />

Once opened you will see the Netbeans Module sources:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/PrjOpen.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/PrjOpen.png)
<br />

Just make your changes and select build or clean and build.
<br />

You should uninstall your current version of this plugin before running.
To uninstall select Plugins:
<br />

![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/SelPlug.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/SelPlug.png)
<br />

Then select the Installed tab and select this plugin.  Then select Uninstall:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/Uninstall.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/Uninstall.png)
<br />

If you want to test your changes, then select Run. <br />
Run will start another instance of Netbeans 7.4 with the new plugin code temporarily installed in the new instance of Netbeans only.
<br />

# C++ Executables #
<br />

This plugin uses 7 executables to accomplish the running of the autotools files.  This is because they are perl scripts.  The HandleFilePermissions.java Class in the CppGnuAutoTools uses the java.io.Process.  This Process does not handle perl script very well.  So, I created executables to be called by java.io.Process.
<br />

To build these exectuables, Close the CppGnuAutoTools project and open the C++ Projects.  To do this, select Open Project...<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/OpenPrj.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/OpenPrj.png)
<br />

Then navigate to the root of the download.  DO NOT double click the root project, that will just open CppGnuAutoTools netbeans module again.<br />
Instead, left click once on the little arrow next to the root name CppGnuAutoTools.  This will expose the inner directories.<br />

![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/SelectCppPrj.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/SelectCppPrj.png)
<br />

Double Click on the sub-folder labelled cpp\_projects.  In this folder you will see the seven C++ executable component projects, one common directory, and the images for tutorials and this build instruction.  Select one of the C++ executable projects, double click and it will open and appear as:<br />

![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/CppOpened.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/CppOpened.png)
<br />

Then simply make your desired changes and select build.<br />
Note: The Makefile of each project contains a .build-post statement that will place the newly compiled executable into the correct folder of the CppGnuAutoTools project, ready to create a fresh nbm file.<br />
<br />
# Creating the NBM file #
<br />
Once all your changes are complete, you will need to recreate the nbm file used to install your plugin.  To do that close all projects and reopen the CppGnuAutoTools Netbeans plugin project as shown earlier on this page.  Then right click on the Main Project CppGnuAutoTools and select Create NBM:<br />
![https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/CreateNBM.png](https://cpp-gnu-autotools-netbeans-plugin.googlecode.com/git/cpp_projects/images/BldSrc/CreateNBM.png)
<br />
<br />
# Install Fresh Build #
<br />
To install the plugin again after building, follow the steps outlined in the [Installation](autotools_install.md) page.  The only difference is select your newly built nbm file instead of downloading.
<br />
[return to MainPage](MainPage.md)