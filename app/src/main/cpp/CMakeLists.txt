# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)

# Declares and names the project.

project("pit")

add_definitions(-DSYSTEM_NAME=\"pit\" -DSYSTEM_VERSION=\"1.0\" -DSYS_CPU=2 -DSYS_SIZE=2 -DSYS_OS=1 -DSOEXT=\".so\"
        -DLUAPLAT=linux -DSYSTEM_OS=\"Android\" -DANDROID -DLINUX -DGPSLIB=GPSLib68K
        -DOSNAME=\"Pumpkin\" -DCRDATE=1632487228 -DARMEMU
        -DUSE_DL_PREFIX=1 -DHAVE_MMAP=0 -DMORECORE_CONTIGUOUS=0 -DMORECORE_CANNOT_TRIM=1 -DMORECORE_FAILURE=0 -DDL_DEBUG=1 -DMORECORE=heap_morecore
        -g
        )

include_directories(pumpkin/libpit)
include_directories(pumpkin/lua/src)
include_directories(pumpkin/font)
include_directories(pumpkin/libpumpkin)
include_directories(pumpkin/libpumpkin/dlmalloc)
include_directories(pumpkin/libpumpkin/emulation)
include_directories(pumpkin/libpumpkin/emulation/arm)
include_directories(pumpkin/libpumpkin/emulation/darm)
include_directories(pumpkin/PalmOS)
include_directories(pumpkin/PalmOS/Core)
include_directories(pumpkin/PalmOS/Core/Hardware)
include_directories(pumpkin/PalmOS/Core/System)
include_directories(pumpkin/PalmOS/Core/UI)
include_directories(pumpkin/PalmOS/Dynamic)
include_directories(pumpkin/PalmOS/Extensions/Bluetooth)
include_directories(pumpkin/PalmOS/Extensions/ExpansionMgr)
include_directories(pumpkin/PalmOS/Libraries)
include_directories(pumpkin/PalmOS/Libraries/CPMLib)
include_directories(pumpkin/PalmOS/Libraries/INet)
include_directories(pumpkin/PalmOS/Libraries/PalmOSGlue)
include_directories(pumpkin/PalmOS/Libraries/Pdi)
include_directories(pumpkin/PalmOS/Libraries/SSL)
include_directories(pumpkin/PalmOS/Garmin)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        pit

        # Sets the library as a shared library.
        SHARED

        pumpkin/libpit/bmp.c
        pumpkin/libpit/builtin.c
        pumpkin/libpit/bytes.c
        pumpkin/libpit/ctelnet.c
        pumpkin/libpit/debug.c
        pumpkin/libpit/endianness.c
        pumpkin/libpit/filter.c
        pumpkin/libpit/findargs.c
        pumpkin/libpit/gps.c
        pumpkin/libpit/graphic.c
        pumpkin/libpit/httpc.c
        pumpkin/libpit/httpd.c
        pumpkin/libpit/io.c
        pumpkin/libpit/list.c
        pumpkin/libpit/loadfile.c
        pumpkin/libpit/login.c
        pumpkin/libpit/main.c
        pumpkin/libpit/media.c
        pumpkin/libpit/mutex.c
        pumpkin/libpit/pterm.c
        pumpkin/libpit/ptr.c
        pumpkin/libpit/script.c
        pumpkin/libpit/sig.c
        pumpkin/libpit/sock.c
        pumpkin/libpit/surface.c
        pumpkin/libpit/sys.c
        pumpkin/libpit/telnet.c
        pumpkin/libpit/template.c
        pumpkin/libpit/threadudp.c
        pumpkin/libpit/timeutc.c
        pumpkin/libpit/ts.c
        pumpkin/libpit/util.c
        pumpkin/libpit/vfont.c
        pumpkin/libpit/vfs.c
        pumpkin/libpit/vfslocal.c
        pumpkin/libpit/xalloc.c
        pumpkin/libpit/yuv.c
        )

add_library( # Sets the name of the library.
        scriptlua

        # Sets the library as a shared library.
        SHARED

        pumpkin/lua/src/lapi.c
        pumpkin/lua/src/lauxlib.c
        pumpkin/lua/src/lbaselib.c
        pumpkin/lua/src/lbitlib.c
        pumpkin/lua/src/lcode.c
        pumpkin/lua/src/lcorolib.c
        pumpkin/lua/src/lctype.c
        pumpkin/lua/src/ldblib.c
        pumpkin/lua/src/ldebug.c
        pumpkin/lua/src/ldo.c
        pumpkin/lua/src/ldump.c
        pumpkin/lua/src/lfunc.c
        pumpkin/lua/src/lgc.c
        pumpkin/lua/src/linit.c
        pumpkin/lua/src/liolib.c
        pumpkin/lua/src/llex.c
        pumpkin/lua/src/lmathlib.c
        pumpkin/lua/src/lmem.c
        pumpkin/lua/src/loadlib.c
        pumpkin/lua/src/lobject.c
        pumpkin/lua/src/lopcodes.c
        pumpkin/lua/src/loslib.c
        pumpkin/lua/src/lparser.c
        pumpkin/lua/src/lstate.c
        pumpkin/lua/src/lstring.c
        pumpkin/lua/src/lstrlib.c
        pumpkin/lua/src/ltable.c
        pumpkin/lua/src/ltablib.c
        pumpkin/lua/src/ltm.c
        pumpkin/lua/src/lundump.c
        pumpkin/lua/src/lutf8lib.c
        pumpkin/lua/src/lvm.c
        pumpkin/lua/src/lzio.c
        pumpkin/lua/src/script_lua.c
        )

add_library( # Sets the name of the library.
        pumpkin

        # Sets the library as a shared library.
        SHARED

        pumpkin/libpumpkin/AboutBox.c
        pumpkin/libpumpkin/AddressSortLib.c
        pumpkin/libpumpkin/AlarmMgr.c
        pumpkin/libpumpkin/AppRegistry.c
        pumpkin/libpumpkin/AttentionMgr.c
        pumpkin/libpumpkin/Bitmap.c
        pumpkin/libpumpkin/BmpGlue.c
        pumpkin/libpumpkin/BtLib.c
        pumpkin/libpumpkin/CPMLib68KInterface.c
        pumpkin/libpumpkin/Category.c
        pumpkin/libpumpkin/Chat.c
        pumpkin/libpumpkin/Clipboard.c
        pumpkin/libpumpkin/ConnectionMgr.c
        pumpkin/libpumpkin/ConsoleMgr.c
        pumpkin/libpumpkin/Control.c
        pumpkin/libpumpkin/Crc.c
        pumpkin/libpumpkin/CtlGlue.c
        pumpkin/libpumpkin/DLServer.c
        pumpkin/libpumpkin/DateGlue.c
        pumpkin/libpumpkin/DateTime.c
        pumpkin/libpumpkin/Day.c
        pumpkin/libpumpkin/DebugMgr.c
        pumpkin/libpumpkin/Encrypt.c
        pumpkin/libpumpkin/ErrorBase.c
        pumpkin/libpumpkin/Event.c
        pumpkin/libpumpkin/ExgLib.c
        pumpkin/libpumpkin/ExgMgr.c
        pumpkin/libpumpkin/ExpansionMgr.c
        pumpkin/libpumpkin/FSLib.c
        pumpkin/libpumpkin/FatalAlert.c
        pumpkin/libpumpkin/FeatureMgr.c
        pumpkin/libpumpkin/Field.c
        pumpkin/libpumpkin/FileStream.c
        pumpkin/libpumpkin/Find.c
        pumpkin/libpumpkin/FixedMath.c
        pumpkin/libpumpkin/FloatMgr.c
        pumpkin/libpumpkin/FntGlue.c
        pumpkin/libpumpkin/Font.c
        pumpkin/libpumpkin/FontSelect.c
        pumpkin/libpumpkin/Form.c
        pumpkin/libpumpkin/FrmGlue.c
        pumpkin/libpumpkin/GPDLib.c
        pumpkin/libpumpkin/GPSLib68K.c
        pumpkin/libpumpkin/Graffiti.c
        pumpkin/libpumpkin/GraffitiReference.c
        pumpkin/libpumpkin/GraffitiShift.c
        pumpkin/libpumpkin/HAL.c
        pumpkin/libpumpkin/HostControl.c
        pumpkin/libpumpkin/IMCUtils.c
        pumpkin/libpumpkin/INetMgr.c
        pumpkin/libpumpkin/InsPoint.c
        pumpkin/libpumpkin/IntlMgr.c
        pumpkin/libpumpkin/IrLib.c
        pumpkin/libpumpkin/KeyMgr.c
        pumpkin/libpumpkin/Keyboard.c
        pumpkin/libpumpkin/Launcher.c
        pumpkin/libpumpkin/List.c
        pumpkin/libpumpkin/LocaleMgr.c
        pumpkin/libpumpkin/Localize.c
        pumpkin/libpumpkin/LstGlue.c
        pumpkin/libpumpkin/Lz77Mgr.c
        pumpkin/libpumpkin/Menu.c
        pumpkin/libpumpkin/ModemMgr.c
        pumpkin/libpumpkin/NetBitUtils.c
        pumpkin/libpumpkin/NetMgr.c
        pumpkin/libpumpkin/OverlayMgr.c
        pumpkin/libpumpkin/Password.c
        pumpkin/libpumpkin/PceNativeCall.c
        pumpkin/libpumpkin/PdiLib.c
        pumpkin/libpumpkin/PenInputMgr.c
        pumpkin/libpumpkin/PenMgr.c
        pumpkin/libpumpkin/PhoneLookup.c
        pumpkin/libpumpkin/Preferences.c
        pumpkin/libpumpkin/PrivateRecords.c
        pumpkin/libpumpkin/Progress.c
        pumpkin/libpumpkin/Rect.c
        pumpkin/libpumpkin/ScrollBar.c
        pumpkin/libpumpkin/SelDay.c
        pumpkin/libpumpkin/SelTime.c
        pumpkin/libpumpkin/SelTimeZone.c
        pumpkin/libpumpkin/SerialLinkMgr.c
        pumpkin/libpumpkin/SerialMgr.c
        pumpkin/libpumpkin/SerialMgrOld.c
        pumpkin/libpumpkin/SerialSdrv.c
        pumpkin/libpumpkin/SerialVdrv.c
        pumpkin/libpumpkin/SlotDrvrLib.c
        pumpkin/libpumpkin/SoundMgr.c
        pumpkin/libpumpkin/SslLib.c
        pumpkin/libpumpkin/StringMgr.c
        pumpkin/libpumpkin/SysEvtMgr.c
        pumpkin/libpumpkin/SysUtils.c
        pumpkin/libpumpkin/SystemMgr.c
        pumpkin/libpumpkin/Table.c
        pumpkin/libpumpkin/TblGlue.c
        pumpkin/libpumpkin/TelephonyMgr.c
        pumpkin/libpumpkin/TextMgr.c
        pumpkin/libpumpkin/TextServicesMgr.c
        pumpkin/libpumpkin/TimeMgr.c
        pumpkin/libpumpkin/TxtGlue.c
        pumpkin/libpumpkin/UDAMgr.c
        pumpkin/libpumpkin/UIColor.c
        pumpkin/libpumpkin/UIControls.c
        pumpkin/libpumpkin/UIResources.c
        pumpkin/libpumpkin/VFSMgr.c
        pumpkin/libpumpkin/Window.c
        pumpkin/libpumpkin/dbg.c
        pumpkin/libpumpkin/dia.c
        pumpkin/libpumpkin/dlheap.c
        pumpkin/libpumpkin/dlmalloc/dlmalloc.c
        pumpkin/libpumpkin/edit.c
        pumpkin/libpumpkin/fill.c
        pumpkin/libpumpkin/grail.c
        pumpkin/libpumpkin/language.c
        pumpkin/libpumpkin/peditor.c
        pumpkin/libpumpkin/pumpkin.c
        pumpkin/libpumpkin/rcpexport.c
        pumpkin/libpumpkin/rgb.c
        pumpkin/libpumpkin/script.c
        pumpkin/libpumpkin/storage.c
        pumpkin/libpumpkin/syntax.c
        pumpkin/libpumpkin/wav.c
        pumpkin/libpumpkin/wman.c
        pumpkin/libpumpkin/emulation/accessortrap.c
        pumpkin/libpumpkin/emulation/arm/CPU.c
        pumpkin/libpumpkin/emulation/arm/MMU.c
        pumpkin/libpumpkin/emulation/arm/RAM.c
        pumpkin/libpumpkin/emulation/arm/armem.c
        pumpkin/libpumpkin/emulation/arm/armemu.c
        pumpkin/libpumpkin/emulation/arm/cp15.c
        pumpkin/libpumpkin/emulation/arm/icache.c
        pumpkin/libpumpkin/emulation/arm/pxa_IC.c
        pumpkin/libpumpkin/emulation/darm/armv7-tbl.c
        pumpkin/libpumpkin/emulation/darm/armv7.c
        pumpkin/libpumpkin/emulation/darm/darm-tbl.c
        pumpkin/libpumpkin/emulation/darm/darm.c
        pumpkin/libpumpkin/emulation/darm/thumb-tbl.c
        pumpkin/libpumpkin/emulation/darm/thumb.c
        pumpkin/libpumpkin/emulation/darm/thumb2-decoder.c
        pumpkin/libpumpkin/emulation/darm/thumb2-tbl.c
        pumpkin/libpumpkin/emulation/darm/thumb2.c
        pumpkin/libpumpkin/emulation/disasm.c
        pumpkin/libpumpkin/emulation/emupalmos.c
        pumpkin/libpumpkin/emulation/flpemtrap.c
        pumpkin/libpumpkin/emulation/flptrap.c
        pumpkin/libpumpkin/emulation/fstrap.c
        pumpkin/libpumpkin/emulation/gpdlibtrap.c
        pumpkin/libpumpkin/emulation/hdtrap.c
        pumpkin/libpumpkin/emulation/intltrap.c
        pumpkin/libpumpkin/emulation/m68kcpu.c
        pumpkin/libpumpkin/emulation/m68kdasm.c
        pumpkin/libpumpkin/emulation/m68kops.c
        pumpkin/libpumpkin/emulation/netlibtrap.c
        pumpkin/libpumpkin/emulation/omtrap.c
        pumpkin/libpumpkin/emulation/pinstrap.c
        pumpkin/libpumpkin/emulation/serialtrap.c
        pumpkin/libpumpkin/emulation/softfloat/softfloat.c
        pumpkin/libpumpkin/emulation/systrap.c
        pumpkin/libpumpkin/emulation/trapnames.c
        pumpkin/Launcher/editbin.c
        pumpkin/Launcher/editbmp.c
        pumpkin/Launcher/editform.c
        pumpkin/Launcher/editstr.c
        pumpkin/Launcher/Launcher.c
)

add_library( # Sets the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp
        window.c
        pitapp.c
        pumpkin/libos/libos.c
        )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        native-lib
        pit
        scriptlua
        pumpkin

        -ljnigraphics

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})

target_link_libraries(
        scriptlua
        pit
)

target_link_libraries(
        pit
        ${log-lib}
)

target_link_libraries(
        pumpkin
        pit
)
