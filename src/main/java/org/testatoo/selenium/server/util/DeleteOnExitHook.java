/**
 * Copyright (C) 2008 Ovea <dev@testatoo.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.testatoo.selenium.server.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

final class DeleteOnExitHook implements Runnable {

    private static final DeleteOnExitHook instance = new DeleteOnExitHook();
    private final LinkedHashSet<File> files = new LinkedHashSet<File>();
    private boolean shutdownInProgress;

    private DeleteOnExitHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this, "DeleteOnExitHookThread"));
    }

    public void run() {
        shutdownInProgress = true;
        LinkedHashSet<File> theFiles = files;
        ArrayList<File> toBeDeleted = new ArrayList<File>(theFiles);
        // reverse the list to maintain previous jdk deletion order.
        // Last in first deleted.
        Collections.reverse(toBeDeleted);
        for (File file : toBeDeleted) {
            deleteSubFolders(file);
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }

    void deleteSubFolders(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                deleteSubFolders(f);
            }
            //noinspection ResultOfMethodCallIgnored
            f.delete();
        }
    }

    public DeleteOnExitHook add(File file) {
        if (!shutdownInProgress) {
            files.add(file);
        }
        return this;
    }

    public static DeleteOnExitHook instance() {
        return instance;
    }
}
