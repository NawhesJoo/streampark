package com.project.repository.JeongRepositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Paychk;

@Repository
public interface PaychkRepository extends JpaRepository<Paychk, BigInteger> {

    Paychk findTop1ByOrderByRegdateDesc();
    
    Paychk findTop1ByMember_idAndTypeOrderByRegdateDesc(String id, String type);

    List<Paychk> findByMember_id(String id);

    List<Paychk> findByMember_idAndType(String id, String type);

}
