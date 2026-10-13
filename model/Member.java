package com.hcl.library.model;

public class Member {
    private final String memberId;
    private final String name;
    private final String phoneNumber;

    public Member(String memberId, String name, String phoneNumber) {
        this.memberId = memberId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
