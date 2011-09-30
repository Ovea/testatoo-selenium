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

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
final class MainWithHub {
    public static void main(String... args) throws Exception {
        Selenium s = new DefaultSelenium("localhost", 4444, "*firefox", "http://www.amazon.ca/");
        s.start();
        s.open("/");
        for (String s1 : s.getAllButtons()) {
            System.out.println(s1);
        }
        Thread.sleep(10000);
    }
}
