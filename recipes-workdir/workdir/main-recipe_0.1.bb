# AUTHOR: BELHAJ SALEM TALEL <bhstalel@gmail.com>

DESCRIPTION = "\
    This is the main recipe for the article, \
    We will discover the working directory of this recipe. \
    This recipe is a simple Hello World C program that depends on a shared library. \
"

SUMMARY = "${DESCRIPTION}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Define the source of the recipe that Bitbake will run do_fetch on.
# The source of this recipe is "git"
# For Git source, we need to define:
#   - The URL
#   - The fetch protocol
#   - The branch
#   - The commit
# SRC_URI = "git://URL;protocol=PROTOCOL;branch=BRANCH"
# SRCREV = "COMMIT"
SRC_URI = "git://github.com/bhstalel/yocto-hello-c-makefile.git;protocol=ssh;branch=main"
SRCREV = "e6909f4e90d70352b7250fa2f27dae8a1b33e2cb"

# After do_fetch , there is:
#   -> do_unpack    (Done automatically by Bitbake from DL_DIR to WORKDIR)
#   -> do_patch     (If there are patches, no patches in our case)
#   -> do_configure (To configure the project; something you want to do before compiling;
#                    like: cmake; no need in our example)
#   -> do_compile   (The compilation process; by default it will run make;
#                    it is perfect in our case, because we have a Makefile)
#   -> do_install   (Specify what and where files should be in the final rootfs; also to create the recipe package)
#   -> ...

# Regarding the do_unpack; for git source; the files will be unpacked under WORKDIR/git
# the rest of tasks after do_unpack will work by default in the S variable which is by default: ${WORKDIR}/${PN}-${PV}
# This is not the case here, we need to modify it to ${WORKDIR}/git
S = "${WORKDIR}/git"

# Since, no need for do_configure; we can disable it
do_configure[noexec] = "1"

# This recipe depends on hellolib.so library, and the library is provided by dep-recipe recipe.
# The dependency happens at compile and run times, so we should use:
DEPENDS += "dep-recipe"
RDEPENDS:${PN} += "dep-recipe"
# We will cover in other article the use of PACKAGECONFIG which resolves this automatically.

# do_install;
#   The compilation of this example will produce a binary called: hello
#   So; we can install wherever we want; usually to: /usr/bin
#   The target is the variable D; it is empty by default; So we need to create everything.
do_install(){
    # Create the /usr/bin folder: ${bindir} = /usr/bin
    install -d ${D}${bindir}

    # Install the binary
    install -m 0700 hello ${D}${bindir}
}