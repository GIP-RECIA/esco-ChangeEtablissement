package org.esco.portlet.changeetab.dao.bean;

import org.esco.portlet.changeetab.model.Etablissement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jgribonvald on 28/07/16.
 */
public class LdapEtablissementDisplayNameFormater implements IEtablissementFormatter, InitializingBean {

    /** Logger. */
    private final Logger LOG = LoggerFactory.getLogger(LdapEtablissementDisplayNameFormater.class);

    private String groupsRegex;
    private Pattern groupsRegexPattern;
    private Map<Integer,String> indexListReplacement;

    public Map<Integer,String> getIndexListReplacement() {
        return indexListReplacement;
    }

    public void setIndexListReplacement(final Map<Integer,String> indexListReplacement) {
        this.indexListReplacement = indexListReplacement;
    }

    public String getGroupsRegex() {
        return groupsRegex;
    }

    public void setGroupsRegex(final String groupsRegex) {
        this.groupsRegex = groupsRegex;
        this.groupsRegexPattern = Pattern.compile(groupsRegex);
    }

    public LdapEtablissementDisplayNameFormater() {
        super();
    }

    @Override
    public Etablissement format(Etablissement input) {
        if (input != null) {
            // setting as new displayName the origin displayName formatted
            input.setDisplayName(format(input.getDisplayName()));
        }
        return input;
    }

    private String format(String input) {
        if (input != null && !input.isEmpty()) {
            Matcher group = this.groupsRegexPattern.matcher(input);
            if (group.find()) {
                StringBuilder displayName = new StringBuilder(input);
                for (Map.Entry<Integer, String> entry: this.indexListReplacement.entrySet()) {
                    displayName.replace(group.start(entry.getKey()), group.end(entry.getKey()), entry.getValue());
                }
//                for (int i = 0; i < group.groupCount(); i++) {
//                    if (this.indexListReplacement.containsKey(i)) {
//                        displayName += group.group(this.indexListReplacement.get(i));
//                    }
//
//                }
                LOG.debug("Matcher found groups displayName, and applied replacement value is : {}", displayName);
                return displayName.toString();
            }

        }
        return input;
    }
    public String replaceGroup(String regex, String source, int groupToReplace, int groupOccurrence, String replacement) {
        Matcher m = Pattern.compile(regex).matcher(source);
        for (int i = 0; i < groupOccurrence; i++)
            if (!m.find()) return source; // pattern not met, may also throw an exception here
        return new StringBuilder(source).replace(m.start(groupToReplace), m.end(groupToReplace), replacement).toString();
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.groupsRegexPattern, "Attribut groupsRegex wasn't initialized, a regular expression with groups should be passed");
        Assert.isTrue(!this.indexListReplacement.isEmpty(), "Attribut indexListToKeep wasn't initialized, you should list all regexp group where you want to make replacemnt");
    }
}
