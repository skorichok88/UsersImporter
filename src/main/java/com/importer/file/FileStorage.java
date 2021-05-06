package com.importer.file;

import java.io.IOException;

public interface FileStorage {
    Object getStorageData();
    void saveDataToStorage(Object data);
}
