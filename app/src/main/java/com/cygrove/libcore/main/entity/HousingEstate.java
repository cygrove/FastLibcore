package com.cygrove.libcore.main.entity;

import java.io.Serializable;

public class HousingEstate implements Serializable {
    /**
     * communityID : aae04c17-b824-4c97-8f2e-19cb93b2adab
     * communityName : 香港仔中心商場 ac
     */
    private String communityID;
    private String communityName;

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}