package de.codecentric.centerdevice.platform;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class NativeAdapter {

  private static final String LIB_NAME = "libNSMenuFX.dylib";
  private static final String LIB_PREFIX = "nsmenufx-";
  private static final String LIB_SUFFIX = ".dylib";

  static {
    try(InputStream lib = NativeAdapter.class.getClassLoader().getResourceAsStream(LIB_NAME)) {
      System.load(writeLibToTempFile(lib).toAbsolutePath().toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Path writeLibToTempFile(InputStream lib) throws IOException {
    Path tempFilePath = Files.createTempFile(LIB_PREFIX, LIB_SUFFIX);
    tempFilePath.toFile().deleteOnExit();
    Files.copy(lib, tempFilePath, StandardCopyOption.REPLACE_EXISTING);
    return tempFilePath;
  }

  public native void hide();

  public native void unhideAllApplications();

  public native void hideOtherApplications();
}