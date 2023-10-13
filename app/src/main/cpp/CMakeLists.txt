# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)

# Declares and names the project.

project("pit")

add_definitions(-DSYSTEM_NAME=\"pit\" -DSYSTEM_VERSION=\"1.0\" -DSYS_CPU=1 -DSYS_SIZE=1 -DSYS_OS=4 -DSOEXT=\".so\"
        -DLUAPLAT=linux -DSYSTEM_OS=\"Android\" -DANDROID -DLINUX -DGPSLIB=GPSLib68K
        -DOSNAME=\"Pumpkin\" -DCRDATE=1632487228 -DARMEMU
        -DUSE_DL_PREFIX=1 -DHAVE_MMAP=0 -DMORECORE_CONTIGUOUS=0 -DMORECORE_CANNOT_TRIM=1 -DMORECORE_FAILURE=0 -DDL_DEBUG=1 -DMORECORE=heap_morecore
        -g
        )

include_directories(PumpkinOS/src/libpit)
include_directories(PumpkinOS/src/lua/src)
include_directories(PumpkinOS/src/font)
include_directories(PumpkinOS/src/libpumpkin)
include_directories(PumpkinOS/src/libpumpkin/dlmalloc)
include_directories(PumpkinOS/src/libpumpkin/emulation)
include_directories(PumpkinOS/src/libpumpkin/emulation/arm)
include_directories(PumpkinOS/src/libpumpkin/emulation/darm)
include_directories(PumpkinOS/src/PalmOS)
include_directories(PumpkinOS/src/PalmOS/Core)
include_directories(PumpkinOS/src/PalmOS/Core/Hardware)
include_directories(PumpkinOS/src/PalmOS/Core/System)
include_directories(PumpkinOS/src/PalmOS/Core/UI)
include_directories(PumpkinOS/src/PalmOS/Dynamic)
include_directories(PumpkinOS/src/PalmOS/Extensions/Bluetooth)
include_directories(PumpkinOS/src/PalmOS/Extensions/ExpansionMgr)
include_directories(PumpkinOS/src/PalmOS/Libraries)
include_directories(PumpkinOS/src/PalmOS/Libraries/CPMLib)
include_directories(PumpkinOS/src/PalmOS/Libraries/INet)
include_directories(PumpkinOS/src/PalmOS/Libraries/PalmOSGlue)
include_directories(PumpkinOS/src/PalmOS/Libraries/Pdi)
include_directories(PumpkinOS/src/PalmOS/Libraries/SSL)
include_directories(PumpkinOS/src/PalmOS/Garmin)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        pit

        # Sets the library as a shared library.
        SHARED

        PumpkinOS/src/libpit/bmp.c
        PumpkinOS/src/libpit/builtin.c
        PumpkinOS/src/libpit/bytes.c
        PumpkinOS/src/libpit/ctelnet.c
        PumpkinOS/src/libpit/debug.c
        PumpkinOS/src/libpit/endianness.c
        PumpkinOS/src/libpit/filter.c
        PumpkinOS/src/libpit/findargs.c
        PumpkinOS/src/libpit/gps.c
        PumpkinOS/src/libpit/graphic.c
        PumpkinOS/src/libpit/httpc.c
        PumpkinOS/src/libpit/httpd.c
        PumpkinOS/src/libpit/io.c
        PumpkinOS/src/libpit/list.c
        PumpkinOS/src/libpit/loadfile.c
        PumpkinOS/src/libpit/login.c
        PumpkinOS/src/libpit/main.c
        PumpkinOS/src/libpit/media.c
        PumpkinOS/src/libpit/mutex.c
        PumpkinOS/src/libpit/pterm.c
        PumpkinOS/src/libpit/ptr.c
        PumpkinOS/src/libpit/rgb.c
        PumpkinOS/src/libpit/script.c
        PumpkinOS/src/libpit/sig.c
        PumpkinOS/src/libpit/sock.c
        PumpkinOS/src/libpit/surface.c
        PumpkinOS/src/libpit/sys.c
        PumpkinOS/src/libpit/telnet.c
        PumpkinOS/src/libpit/template.c
        PumpkinOS/src/libpit/threadudp.c
        PumpkinOS/src/libpit/timeutc.c
        PumpkinOS/src/libpit/ts.c
        PumpkinOS/src/libpit/util.c
        PumpkinOS/src/libpit/vfont.c
        PumpkinOS/src/libpit/vfs.c
        PumpkinOS/src/libpit/vfslocal.c
        PumpkinOS/src/libpit/xalloc.c
        PumpkinOS/src/libpit/yuv.c
        )

add_library( # Sets the name of the library.
        scriptlua

        # Sets the library as a shared library.
        SHARED

        PumpkinOS/src/lua/lapi.c
        PumpkinOS/src/lua/lauxlib.c
        PumpkinOS/src/lua/lbaselib.c
        PumpkinOS/src/lua/lbitlib.c
        PumpkinOS/src/lua/lcode.c
        PumpkinOS/src/lua/lcorolib.c
        PumpkinOS/src/lua/lctype.c
        PumpkinOS/src/lua/ldblib.c
        PumpkinOS/src/lua/ldebug.c
        PumpkinOS/src/lua/ldo.c
        PumpkinOS/src/lua/ldump.c
        PumpkinOS/src/lua/lfunc.c
        PumpkinOS/src/lua/lgc.c
        PumpkinOS/src/lua/linit.c
        PumpkinOS/src/lua/liolib.c
        PumpkinOS/src/lua/llex.c
        PumpkinOS/src/lua/lmathlib.c
        PumpkinOS/src/lua/lmem.c
        PumpkinOS/src/lua/loadlib.c
        PumpkinOS/src/lua/lobject.c
        PumpkinOS/src/lua/lopcodes.c
        PumpkinOS/src/lua/loslib.c
        PumpkinOS/src/lua/lparser.c
        PumpkinOS/src/lua/lstate.c
        PumpkinOS/src/lua/lstring.c
        PumpkinOS/src/lua/lstrlib.c
        PumpkinOS/src/lua/ltable.c
        PumpkinOS/src/lua/ltablib.c
        PumpkinOS/src/lua/ltm.c
        PumpkinOS/src/lua/lundump.c
        PumpkinOS/src/lua/lutf8lib.c
        PumpkinOS/src/lua/lvm.c
        PumpkinOS/src/lua/lzio.c
        PumpkinOS/src/lua/script_lua.c
        )

add_library( # Sets the name of the library.
        pumpkin

        # Sets the library as a shared library.
        SHARED

        PumpkinOS/src/libpumpkin/AboutBox.c
        PumpkinOS/src/libpumpkin/AddressSortLib.c
        PumpkinOS/src/libpumpkin/AlarmMgr.c
        PumpkinOS/src/libpumpkin/AppRegistry.c
        PumpkinOS/src/libpumpkin/AttentionMgr.c
        PumpkinOS/src/libpumpkin/Bitmap.c
        PumpkinOS/src/libpumpkin/BmpGlue.c
        PumpkinOS/src/libpumpkin/BtLib.c
        PumpkinOS/src/libpumpkin/CPMLib68KInterface.c
        PumpkinOS/src/libpumpkin/Category.c
        PumpkinOS/src/libpumpkin/Chat.c
        PumpkinOS/src/libpumpkin/Clipboard.c
        PumpkinOS/src/libpumpkin/ConnectionMgr.c
        PumpkinOS/src/libpumpkin/ConsoleMgr.c
        PumpkinOS/src/libpumpkin/Control.c
        PumpkinOS/src/libpumpkin/Crc.c
        PumpkinOS/src/libpumpkin/CtlGlue.c
        PumpkinOS/src/libpumpkin/DLServer.c
        PumpkinOS/src/libpumpkin/DateGlue.c
        PumpkinOS/src/libpumpkin/DateTime.c
        PumpkinOS/src/libpumpkin/Day.c
        PumpkinOS/src/libpumpkin/DebugMgr.c
        PumpkinOS/src/libpumpkin/Encrypt.c
        PumpkinOS/src/libpumpkin/ErrorBase.c
        PumpkinOS/src/libpumpkin/Event.c
        PumpkinOS/src/libpumpkin/ExgLib.c
        PumpkinOS/src/libpumpkin/ExgMgr.c
        PumpkinOS/src/libpumpkin/ExpansionMgr.c
        PumpkinOS/src/libpumpkin/FSLib.c
        PumpkinOS/src/libpumpkin/FatalAlert.c
        PumpkinOS/src/libpumpkin/FeatureMgr.c
        PumpkinOS/src/libpumpkin/Field.c
        PumpkinOS/src/libpumpkin/FileStream.c
        PumpkinOS/src/libpumpkin/Find.c
        PumpkinOS/src/libpumpkin/FixedMath.c
        PumpkinOS/src/libpumpkin/FloatMgr.c
        PumpkinOS/src/libpumpkin/FntGlue.c
        PumpkinOS/src/libpumpkin/Font.c
        PumpkinOS/src/libpumpkin/FontSelect.c
        PumpkinOS/src/libpumpkin/Form.c
        PumpkinOS/src/libpumpkin/FrmGlue.c
        PumpkinOS/src/libpumpkin/GPDLib.c
        PumpkinOS/src/libpumpkin/GPSLib68K.c
        PumpkinOS/src/libpumpkin/Graffiti.c
        PumpkinOS/src/libpumpkin/GraffitiReference.c
        PumpkinOS/src/libpumpkin/GraffitiShift.c
        PumpkinOS/src/libpumpkin/HAL.c
        PumpkinOS/src/libpumpkin/HostControl.c
        PumpkinOS/src/libpumpkin/IMCUtils.c
        PumpkinOS/src/libpumpkin/INetMgr.c
        PumpkinOS/src/libpumpkin/InsPoint.c
        PumpkinOS/src/libpumpkin/IntlMgr.c
        PumpkinOS/src/libpumpkin/IrLib.c
        PumpkinOS/src/libpumpkin/KeyMgr.c
        PumpkinOS/src/libpumpkin/Keyboard.c
        PumpkinOS/src/libpumpkin/Launcher.c
        PumpkinOS/src/libpumpkin/List.c
        PumpkinOS/src/libpumpkin/LocaleMgr.c
        PumpkinOS/src/libpumpkin/Localize.c
        PumpkinOS/src/libpumpkin/LstGlue.c
        PumpkinOS/src/libpumpkin/Lz77Mgr.c
        PumpkinOS/src/libpumpkin/Menu.c
        PumpkinOS/src/libpumpkin/ModemMgr.c
        PumpkinOS/src/libpumpkin/NetBitUtils.c
        PumpkinOS/src/libpumpkin/NetMgr.c
        PumpkinOS/src/libpumpkin/OverlayMgr.c
        PumpkinOS/src/libpumpkin/Password.c
        PumpkinOS/src/libpumpkin/PceNativeCall.c
        PumpkinOS/src/libpumpkin/PdiLib.c
        PumpkinOS/src/libpumpkin/PenInputMgr.c
        PumpkinOS/src/libpumpkin/PenMgr.c
        PumpkinOS/src/libpumpkin/PhoneLookup.c
        PumpkinOS/src/libpumpkin/Preferences.c
        PumpkinOS/src/libpumpkin/PrivateRecords.c
        PumpkinOS/src/libpumpkin/Progress.c
        PumpkinOS/src/libpumpkin/Rect.c
        PumpkinOS/src/libpumpkin/ScrollBar.c
        PumpkinOS/src/libpumpkin/SelDay.c
        PumpkinOS/src/libpumpkin/SelTime.c
        PumpkinOS/src/libpumpkin/SelTimeZone.c
        PumpkinOS/src/libpumpkin/SerialLinkMgr.c
        PumpkinOS/src/libpumpkin/SerialMgr.c
        PumpkinOS/src/libpumpkin/SerialMgrOld.c
        PumpkinOS/src/libpumpkin/SerialSdrv.c
        PumpkinOS/src/libpumpkin/SerialVdrv.c
        PumpkinOS/src/libpumpkin/SlotDrvrLib.c
        PumpkinOS/src/libpumpkin/SoundMgr.c
        PumpkinOS/src/libpumpkin/SslLib.c
        PumpkinOS/src/libpumpkin/StringMgr.c
        PumpkinOS/src/libpumpkin/SysEvtMgr.c
        PumpkinOS/src/libpumpkin/SysUtils.c
        PumpkinOS/src/libpumpkin/SystemMgr.c
        PumpkinOS/src/libpumpkin/Table.c
        PumpkinOS/src/libpumpkin/TblGlue.c
        PumpkinOS/src/libpumpkin/TelephonyMgr.c
        PumpkinOS/src/libpumpkin/TextMgr.c
        PumpkinOS/src/libpumpkin/TextServicesMgr.c
        PumpkinOS/src/libpumpkin/TimeMgr.c
        PumpkinOS/src/libpumpkin/TxtGlue.c
        PumpkinOS/src/libpumpkin/UDAMgr.c
        PumpkinOS/src/libpumpkin/UIColor.c
        PumpkinOS/src/libpumpkin/UIControls.c
        PumpkinOS/src/libpumpkin/UIResources.c
        PumpkinOS/src/libpumpkin/VFSMgr.c
        PumpkinOS/src/libpumpkin/Window.c
        PumpkinOS/src/libpumpkin/calibrate.c
        PumpkinOS/src/libpumpkin/dbg.c
        PumpkinOS/src/libpumpkin/dia.c
        PumpkinOS/src/libpumpkin/dlheap.c
        PumpkinOS/src/libpumpkin/dlmalloc/dlmalloc.c
        PumpkinOS/src/libpumpkin/edit.c
        PumpkinOS/src/libpumpkin/fill.c
        PumpkinOS/src/libpumpkin/grail.c
        PumpkinOS/src/libpumpkin/language.c
        PumpkinOS/src/libpumpkin/palette.c
        PumpkinOS/src/libpumpkin/peditor.c
        PumpkinOS/src/libpumpkin/pumpkin.c
        PumpkinOS/src/libpumpkin/rcpexport.c
        PumpkinOS/src/libpumpkin/script.c
        PumpkinOS/src/libpumpkin/storage.c
        PumpkinOS/src/libpumpkin/syntax.c
        PumpkinOS/src/libpumpkin/wav.c
        PumpkinOS/src/libpumpkin/wman.c
        PumpkinOS/src/libpumpkin/emulation/accessortrap.c
        PumpkinOS/src/libpumpkin/emulation/arm/CPU.c
        PumpkinOS/src/libpumpkin/emulation/arm/MMU.c
        PumpkinOS/src/libpumpkin/emulation/arm/RAM.c
        PumpkinOS/src/libpumpkin/emulation/arm/armem.c
        PumpkinOS/src/libpumpkin/emulation/arm/armemu.c
        PumpkinOS/src/libpumpkin/emulation/arm/cp15.c
        PumpkinOS/src/libpumpkin/emulation/arm/icache.c
        PumpkinOS/src/libpumpkin/emulation/arm/pxa_IC.c
        PumpkinOS/src/libpumpkin/emulation/darm/armv7-tbl.c
        PumpkinOS/src/libpumpkin/emulation/darm/armv7.c
        PumpkinOS/src/libpumpkin/emulation/darm/darm-tbl.c
        PumpkinOS/src/libpumpkin/emulation/darm/darm.c
        PumpkinOS/src/libpumpkin/emulation/darm/thumb-tbl.c
        PumpkinOS/src/libpumpkin/emulation/darm/thumb.c
        PumpkinOS/src/libpumpkin/emulation/darm/thumb2-decoder.c
        PumpkinOS/src/libpumpkin/emulation/darm/thumb2-tbl.c
        PumpkinOS/src/libpumpkin/emulation/darm/thumb2.c
        PumpkinOS/src/libpumpkin/emulation/disasm.c
        PumpkinOS/src/libpumpkin/emulation/emupalmos.c
        PumpkinOS/src/libpumpkin/emulation/flpemtrap.c
        PumpkinOS/src/libpumpkin/emulation/flptrap.c
        PumpkinOS/src/libpumpkin/emulation/fstrap.c
        PumpkinOS/src/libpumpkin/emulation/gpdlibtrap.c
        PumpkinOS/src/libpumpkin/emulation/hdtrap.c
        PumpkinOS/src/libpumpkin/emulation/intltrap.c
        PumpkinOS/src/libpumpkin/emulation/m68kcpu.c
        PumpkinOS/src/libpumpkin/emulation/m68kdasm.c
        PumpkinOS/src/libpumpkin/emulation/m68kops.c
        PumpkinOS/src/libpumpkin/emulation/netlibtrap.c
        PumpkinOS/src/libpumpkin/emulation/omtrap.c
        PumpkinOS/src/libpumpkin/emulation/pinstrap.c
        PumpkinOS/src/libpumpkin/emulation/serialtrap.c
        PumpkinOS/src/libpumpkin/emulation/softfloat/softfloat.c
        PumpkinOS/src/libpumpkin/emulation/systrap.c
        PumpkinOS/src/libpumpkin/emulation/trapnames.c
        PumpkinOS/src/Launcher/editbin.c
        PumpkinOS/src/Launcher/editbmp.c
        PumpkinOS/src/Launcher/editform.c
        PumpkinOS/src/Launcher/editstr.c
        PumpkinOS/src/Launcher/Launcher.c
)

add_library( # Sets the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp
        window.c
        pitapp.c
        PumpkinOS/src/libos/libos.c
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
