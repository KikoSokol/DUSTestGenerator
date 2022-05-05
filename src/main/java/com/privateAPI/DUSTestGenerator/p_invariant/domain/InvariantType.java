package com.privateAPI.DUSTestGenerator.p_invariant.domain;

public enum InvariantType {
    P("P"),T("T");
    private final String type;

    InvariantType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
