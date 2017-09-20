package com.dongweima.data.unit.test.db.util;

public interface FileProcess<T> {

  T readFile(String path);

  T readFileWithResourcePath(String path);
}
