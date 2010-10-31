/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.lupic.parser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.IOException;

import org.jboss.lupic.parser.listener.ParserListenerAdapter;
import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.Pattern;
import org.jboss.lupic.suite.VisualSuite;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestSimpleConfiguration extends AbstractVisualSuiteDefinitionTest {
    @Test
    public void testSimpleParse() {
        try {
            startWriter();
            parse();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testGoThroughAllPhases() {
        try {
            AssertedListener assertedListener = new AssertedListener();
            parser.registerListener(assertedListener);

            startWriter();
            parse();

            assertEquals(assertedListener.state, 5);
        } catch (SAXException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }

    public class AssertedListener extends ParserListenerAdapter {

        int state = 0;

        @Override
        public void onSuiteStarted(VisualSuite visualSuite) {
            assertEquals(state, 0);
            nextState();
        }

        @Override
        public void onConfigurationParsed(VisualSuite visualSuite) {
            assertEquals(state, 1);
            nextState();
        }

        @Override
        public void onPatternParsed(Configuration configuration, Pattern pattern) {
            assertEquals(state, 2);
            nextState();
        }

        @Override
        public void onTestParsed(org.jboss.lupic.suite.Test test) {
            assertEquals(state, 3);
            nextState();
        }

        @Override
        public void onSuiteParsed(VisualSuite visualSuite) {
            assertEquals(state, 4);
            nextState();
        }

        private void nextState() {
            state += 1;
        }
    }
}