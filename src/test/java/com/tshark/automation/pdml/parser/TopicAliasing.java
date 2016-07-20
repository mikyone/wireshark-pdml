package com.tshark.automation.pdml.parser;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public final class TopicAliasing {

    public static final String ALIAS_PREFIX = "!";
    private static ConcurrentMap<String, String> theAliasToNameMap = new ConcurrentHashMap();
    private static ConcurrentMap<String, String> theNameToAliasMap = new ConcurrentHashMap();

    private TopicAliasing() {
    }

    public static final void mapTopicAlias(String topicName, int topicId) {
        String alias = "!" + Integer.toString(topicId, 36);
        if (theAliasToNameMap.putIfAbsent(alias, topicName) == null) {
            theNameToAliasMap.putIfAbsent(topicName, alias);
        }

    }

    public static final void unmapTopic(String topicName) {
        String alias = (String) theNameToAliasMap.remove(topicName);
        if (alias != null) {
            theAliasToNameMap.remove(alias);
        }

    }

    public static String getTopicLoadHeaderValue(String topicName) {
        String alias = (String) theNameToAliasMap.get(topicName);
        return alias != null ? topicName + alias : topicName;
    }

    public static String getAlias(String topicName) {
        return (String) theNameToAliasMap.get(topicName);
    }

    public static String getTopicName(String alias) {
        return (String) theAliasToNameMap.get(alias);
    }


    public static ConcurrentMap<String, String> getTheAliasToNameMap() {
        return theAliasToNameMap;
    }

    public static ConcurrentMap<String, String> getTheNameToAliasMap() {
        return theNameToAliasMap;
    }
}

