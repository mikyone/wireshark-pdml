package com.tshark.automation.pdml.parser;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TryThat {
    @Test
    public void testClassTest(){
        TopicAliasing.mapTopicAlias("aze",10);
        Assert.assertEquals("!a", TopicAliasing.getAlias("aze"));

        TopicAliasing.mapTopicAlias("aze",20);
        Assert.assertEquals("!a", TopicAliasing.getAlias("aze"));

        Assert.assertEquals(2, TopicAliasing.getTheAliasToNameMap().size());
        Assert.assertEquals(1, TopicAliasing.getTheNameToAliasMap().size());

        TopicAliasing.unmapTopic("aze");

        Assert.assertEquals(1, TopicAliasing.getTheAliasToNameMap().size());
        Assert.assertEquals(0, TopicAliasing.getTheNameToAliasMap().size());
    }

    @Test
    public void testClassTestAssumption2(){
        TopicAliasing.mapTopicAlias("aze1",10);
        Assert.assertEquals("!a", TopicAliasing.getAlias("aze1"));

        TopicAliasing.mapTopicAlias("aze2",10);
        Assert.assertEquals("!a", TopicAliasing.getAlias("aze1"));

        Assert.assertEquals(1, TopicAliasing.getTheAliasToNameMap().size());
        Assert.assertEquals(1, TopicAliasing.getTheNameToAliasMap().size());

        TopicAliasing.unmapTopic("aze1");

        Assert.assertEquals(0, TopicAliasing.getTheAliasToNameMap().size());
        Assert.assertEquals(0, TopicAliasing.getTheNameToAliasMap().size());
    }
}
