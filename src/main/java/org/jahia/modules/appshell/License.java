/*
 * ==========================================================================================
 * =                   JAHIA'S DUAL LICENSING - IMPORTANT INFORMATION                       =
 * ==========================================================================================
 *
 *                                 http://www.jahia.com
 *
 *     Copyright (C) 2002-2020 Jahia Solutions Group SA. All rights reserved.
 *
 *     THIS FILE IS AVAILABLE UNDER TWO DIFFERENT LICENSES:
 *     1/GPL OR 2/JSEL
 *
 *     1/ GPL
 *     ==================================================================================
 *
 *     IF YOU DECIDE TO CHOOSE THE GPL LICENSE, YOU MUST COMPLY WITH THE FOLLOWING TERMS:
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     2/ JSEL - Commercial and Supported Versions of the program
 *     ===================================================================================
 *
 *     IF YOU DECIDE TO CHOOSE THE JSEL LICENSE, YOU MUST COMPLY WITH THE FOLLOWING TERMS:
 *
 *     Alternatively, commercial and supported versions of the program - also known as
 *     Enterprise Distributions - must be used in accordance with the terms and conditions
 *     contained in a separate written agreement between you and Jahia Solutions Group SA.
 *
 *     If you are unsure which license is appropriate for your use,
 *     please contact the sales department at sales@jahia.com.
 */
package org.jahia.modules.appshell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * License check
 */
public class License {
    private static Logger logger = LoggerFactory.getLogger(License.class);

    private License() {
    }

    /**
     * License check
     */
    public static boolean isAllowed(String feature) {
        try {
            Class<?> c;
            c = getLicenseClass();
            return (boolean) c.getMethod("isAllowed", String.class).invoke(null, feature);
        } catch (Exception e) {
            logger.error("Cannot check license", e);
        }
        return false;
    }

    private static Class<?> getLicenseClass() throws ClassNotFoundException {
        Class<?> c;
        try {
            // Use this class for >= 8.0.3.0
            c = Class.forName("org.jahia.security.spi.LicenseCheckUtil");
        } catch (ClassNotFoundException e) {
            // Otherwise this one before 8.0.3.0
            c = Class.forName("org.jahia.security.license.LicenseCheckerService$Stub");
        }
        return c;
    }
}
