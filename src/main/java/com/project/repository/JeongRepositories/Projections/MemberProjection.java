package com.project.repository.JeongRepositories.Projections;

import java.math.BigInteger;

public interface MemberProjection {
    
    String getId();

    String getName();

    String getPhone();

    String getBirth();

    BigInteger getToken();

    String getGender();

    String getRole();

    String getEmail();

    BigInteger getMembershipchk();
    
}
