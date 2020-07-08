/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.php.analysis.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.modules.php.analysis.commands.PHPStan;
import org.netbeans.modules.php.api.util.FileUtils;
import org.netbeans.modules.php.api.util.UiUtils;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.NbBundle;

public final class AnalysisUiUtils {

    private static final String PHPSTAN_LAST_FOLDER_SUFFIX = ".phpstan"; // NOI18N
    private static final String PHPSTAN_CONFIGURATION_LAST_FOLDER_SUFFIX = ".phpstan.config"; // NOI18N

    private AnalysisUiUtils() {
    }

    @CheckForNull
    @NbBundle.Messages("AnalysisUiUtils.browse.phpstan.title=Select PHPStan")
    public static File browsePHPStan() {
        return browse(PHPSTAN_LAST_FOLDER_SUFFIX, Bundle.AnalysisUiUtils_browse_phpstan_title());
    }

    @CheckForNull
    @NbBundle.Messages("AnalysisUiUtils.browse.phpstan.configuration.title=Select PHPStan Configuration File")
    public static File browsePHPStanConfiguration() {
        return browse(PHPSTAN_CONFIGURATION_LAST_FOLDER_SUFFIX, Bundle.AnalysisUiUtils_browse_phpstan_configuration_title());
    }

    @CheckForNull
    private static File browse(String lastFolderSuffix, String title) {
        File file = new FileChooserBuilder(AnalysisUiUtils.class.getName() + lastFolderSuffix)
                .setFilesOnly(true)
                .setTitle(title)
                .showOpenDialog();
        return file;
    }

    @CheckForNull
    @NbBundle.Messages({
        "AnalysisUiUtils.search.phpstan.title=PHPStan scripts",
        "AnalysisUiUtils.search.phpstan.scripts=P&HPStan scripts:",
        "AnalysisUiUtils.search.phpstan.pleaseWaitPart=PHPStan scripts",
        "AnalysisUiUtils.search.phpstan.notFound=No PHPStan scripts found."
    })
    public static String searchPHPStan() {
        SearchParameter param = new SearchParameter()
                .setFilenames(Arrays.asList(PHPStan.NAME, PHPStan.LONG_NAME))
                .setWindowTitle(Bundle.AnalysisUiUtils_search_phpstan_title())
                .setListTitle(Bundle.AnalysisUiUtils_search_phpstan_scripts())
                .setPleaseWaitPart(Bundle.AnalysisUiUtils_search_phpstan_pleaseWaitPart())
                .setNoItemsFound(Bundle.AnalysisUiUtils_search_phpstan_notFound());
        return search(param);
    }

    @CheckForNull
    private static String search(SearchParameter param) {
        return UiUtils.SearchWindow.search(new UiUtils.SearchWindow.SearchWindowSupport() {

            @Override
            public List<String> detect() {
                return FileUtils.findFileOnUsersPath(param.getFilenames().toArray(new String[param.getFilenames().size()]));
            }

            @Override
            public String getWindowTitle() {
                return param.getWindowTitle();
            }

            @Override
            public String getListTitle() {
                return param.getListTitle();
            }

            @Override
            public String getPleaseWaitPart() {
                return param.getPleaseWaitPart();
            }

            @Override
            public String getNoItemsFound() {
                return param.getNoItemsFound();
            }
        });
    }

    //~ Inner class
    private static final class SearchParameter {

        private final List<String> filenames = new ArrayList<>();
        private String windowTitle;
        private String listTitle;
        private String pleaseWaitPart;
        private String noItemsFound;

        public List<String> getFilenames() {
            return Collections.unmodifiableList(filenames);
        }

        public String getWindowTitle() {
            return windowTitle;
        }

        public String getListTitle() {
            return listTitle;
        }

        public String getPleaseWaitPart() {
            return pleaseWaitPart;
        }

        public String getNoItemsFound() {
            return noItemsFound;
        }

        SearchParameter setFilenames(List<String> filenames) {
            this.filenames.clear();
            this.filenames.addAll(filenames);
            return this;
        }

        SearchParameter setWindowTitle(String windowTitle) {
            this.windowTitle = windowTitle;
            return this;
        }

        SearchParameter setListTitle(String listTitle) {
            this.listTitle = listTitle;
            return this;
        }

        SearchParameter setPleaseWaitPart(String pleaseWaitPart) {
            this.pleaseWaitPart = pleaseWaitPart;
            return this;
        }

        SearchParameter setNoItemsFound(String noItemsFound) {
            this.noItemsFound = noItemsFound;
            return this;
        }

    }
}
