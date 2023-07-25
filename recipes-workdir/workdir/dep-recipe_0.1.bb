# AUTHOR: TALEL BELHAJSALEM <bhstalel@gmail.com>

DESCRIPTION = "This recipe produces a C shared library, used by other recipes for compilation."
SUMMARY = "${DESCRIPTION}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "git://github.com/bhstalel/yocto-hello-c-dependency.git;protocol=ssh;branch=main"
SRCREV = "6114bcd982672421739cd1d5d9ddfd6d32dc0fe0"

# No need for do_configure as well, as we already have a Makefile
do_configure[noexec] = "1"

# No need to explicitly specify do_compile, because by default it run make

# Since it is a git project, we need to change the S variable to WORKDIR/git
S = "${WORKDIR}/git"

# do_install;
#   This recipe provides a shared library, so we will install it under /lib
#   Also, this library provides a header file hellolib.h, so we need to install it under /usr/include
do_install(){
    install -d ${D}/lib
    install -d ${D}/usr/include

    install hellolib.h ${D}/usr/include
    install -m 0700 hellolib.so ${D}/lib
}

# To create the package for this recipe, we need to specify what files should be packaged
# Bitbake uses FILES variable to find them.
# Since we are installing a file under /lib, and /lib is not enabled by default in FILES variable, we need to add it
FILES:${PN} += "/lib"