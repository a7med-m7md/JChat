package model;

import java.io.Serializable;

public class GroupMember implements Serializable {
    private String userMobile;
    private long groupId;

    public GroupMember(String userMobile, long groupId) {
        this.userMobile = userMobile;
        this.groupId = groupId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}