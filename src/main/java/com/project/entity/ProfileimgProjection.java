package com.project.entity;

import java.math.BigInteger;

public interface ProfileimgProjection {
        
    byte[] getImagedata();

    String getImagetype();

    BigInteger getImagesize(); //이미지사이즈


}
