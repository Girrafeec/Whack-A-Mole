package com.girrafeecstud.whacamole;

import android.widget.ImageView;
import android.widget.TextView;

public class RuleItem {

    private int ruleImageId;

    private String ruleDescription;

    public RuleItem(int ruleImageId, String ruleDescription){
        this.ruleImageId = ruleImageId;
        this.ruleDescription = ruleDescription;
    }

    public int getRuleImageId(){
        return this.ruleImageId;
    }

    public String getRuleDescription(){
        return this.ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public void setRuleImageId(int ruleImageId){
        this.ruleImageId = ruleImageId;
    }
}
