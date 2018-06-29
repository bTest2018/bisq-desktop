/*
 * This file is part of Bitsquare.
 *
 * Bitsquare is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bitsquare is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bitsquare. If not, see <http://www.gnu.org/licenses/>.
 */

package io.bitsquare.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.files.AppDirectory;

public class ConfigLoader {
    private static final Logger log = LoggerFactory.getLogger(ConfigLoader.class);
    private static final String configFilePath = AppDirectory.dir() + "/bitsquare.conf";

    public static Properties loadConfig() {
        InputStream inputStream = null;

        // load default properties from class path
        Properties defaultProperties = new Properties();
        try {
            InputStream is = ConfigLoader.class.getResourceAsStream("/bitsquare.properties");
            defaultProperties.load(is);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

        // load properties file from config file path
        Properties properties = new Properties(defaultProperties);
        if (new File(configFilePath).exists()) {
            try {
                inputStream = new FileInputStream(configFilePath);
                properties.load(inputStream);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        }
        return properties;
    }
}