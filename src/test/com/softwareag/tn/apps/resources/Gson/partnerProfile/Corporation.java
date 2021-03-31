package test.com.softwareag.tn.apps.resources.Gson.partnerProfile;

public class Corporation {
    private CorporationDetails corporation;
    private Identity[] identities;

    public void setCorporation(CorporationDetails corporation){
        this.corporation=corporation;
    }

    public void setIdentity(Identity[] identity) {
        this.identities = identity;
    }
}
