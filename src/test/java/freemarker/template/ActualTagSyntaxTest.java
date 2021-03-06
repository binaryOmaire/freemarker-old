/*
 * Copyright 2014 Attila Szegedi, Daniel Dekany, Jonathan Revusky
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

package freemarker.template;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ActualTagSyntaxTest {

    @Test
    public void testWithFtlHeader() throws IOException {
        testWithFtlHeader(Configuration.AUTO_DETECT_TAG_SYNTAX);
        testWithFtlHeader(Configuration.ANGLE_BRACKET_TAG_SYNTAX);
        testWithFtlHeader(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
    }
    
    private void testWithFtlHeader(int cfgTagSyntax) throws IOException {
        assertEquals(getActualTagSyntax("[#ftl]foo", cfgTagSyntax), Configuration.SQUARE_BRACKET_TAG_SYNTAX);
        assertEquals(getActualTagSyntax("<#ftl>foo", cfgTagSyntax), Configuration.ANGLE_BRACKET_TAG_SYNTAX);
    }
    
    @Test
    public void testUndecidable() throws IOException {
        assertEquals(getActualTagSyntax("foo", Configuration.AUTO_DETECT_TAG_SYNTAX), Configuration.ANGLE_BRACKET_TAG_SYNTAX);
        assertEquals(getActualTagSyntax("foo", Configuration.ANGLE_BRACKET_TAG_SYNTAX), Configuration.ANGLE_BRACKET_TAG_SYNTAX);
        assertEquals(getActualTagSyntax("foo", Configuration.SQUARE_BRACKET_TAG_SYNTAX), Configuration.SQUARE_BRACKET_TAG_SYNTAX);
    }

    @Test
    public void testDecidableWithoutFtlHeader() throws IOException {
        assertEquals(getActualTagSyntax("foo<#if true></#if>", Configuration.AUTO_DETECT_TAG_SYNTAX), Configuration.ANGLE_BRACKET_TAG_SYNTAX);
        assertEquals(getActualTagSyntax("foo<#if true></#if>", Configuration.ANGLE_BRACKET_TAG_SYNTAX), Configuration.ANGLE_BRACKET_TAG_SYNTAX);
        assertEquals(getActualTagSyntax("foo<#if true></#if>", Configuration.SQUARE_BRACKET_TAG_SYNTAX), Configuration.SQUARE_BRACKET_TAG_SYNTAX);
        
        assertEquals(getActualTagSyntax("foo[#if true][/#if]", Configuration.AUTO_DETECT_TAG_SYNTAX), Configuration.SQUARE_BRACKET_TAG_SYNTAX);
        assertEquals(getActualTagSyntax("foo[#if true][/#if]", Configuration.ANGLE_BRACKET_TAG_SYNTAX), Configuration.ANGLE_BRACKET_TAG_SYNTAX);
        assertEquals(getActualTagSyntax("foo[#if true][/#if]", Configuration.SQUARE_BRACKET_TAG_SYNTAX), Configuration.SQUARE_BRACKET_TAG_SYNTAX);
    }
    
    private int getActualTagSyntax(String ftl, int cfgTagSyntax) throws IOException {
        Configuration cfg = new Configuration();
        cfg.setTagSyntax(cfgTagSyntax);
        return new Template(null, ftl, cfg).getActualTagSyntax();
    }
    
}
