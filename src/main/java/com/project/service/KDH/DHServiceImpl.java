package com.project.service.KDH;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.dto.Actorsdto;
import com.project.dto.VideolistView;
import com.project.dto.Videolistdto;
import com.project.entity.Member;
import com.project.entity.Videolist;
import com.project.mapper.KDH.KDHMapper;
import com.project.repository.KDH.member1Repository;
import com.project.repository.KDH.videolistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DHServiceImpl implements DHService {
    final videolistRepository videolistRepository;
    final member1Repository memberRepository;
    final KDHMapper kdhMapper;

    // 회원의 권한을 받아 검증후 비디오 추가 회차정보를 입력받으면 그횟수만큼 비디오횟차를 올려서 추가
    @Override
    public void videolistInsert(Member admin, Videolistdto obj) {
        admin.setRole("a");
        // 아이디를 입력받으면 로그인파트랑 연계
        // Member member=memberRepository.findById(admin.getId()).orElse(null);
        // if(member.getRole().equals("a")){
        int episodecount = obj.getEpisode().intValue();
        if (admin.getRole().equals("a")) {
            for (int i = 0; i < episodecount; i++) {
                obj.setEpisode(Long.valueOf(i + 1));
                kdhMapper.videolistInsert(obj);
            }
        }
    }

    // 비디오의 이름을 받아서 작품코드를 조회 episode가 1인것조회
    @Override
    public Videolist selectnofromtitle(String title) {
        // System.out.println(videolistRepository.findByTitleAndEpisode(title,
        // BigInteger.valueOf(1)).getVideocode());
        return videolistRepository.findByTitleAndEpisode(title, BigInteger.valueOf(1));
    }

    // 조회된 비디오 코드로 비디오 리스트 뷰에서 비디오하나 조회
    @Override
    public VideolistView selectvideoOne(BigInteger videocode) {
        // System.out.println(kdhMapper.selectVideoOne(videocode).toString());
        return kdhMapper.selectVideoOne(videocode);

    }

    

    // 회원의 권한 확인후 관리자 확인되면 작품의 이름똑같은 모든 비디오를 수정함
    @Override
    public void videolistUpdate(Member admin, Videolistdto obj, String nowtitle) {
        admin.setRole("a");
        // 아이디를 입력받으면 로그인파트랑 연계
        // Member member=memberRepository.findById(admin.getId()).orElse(null);
        // if(member.getRole().equals("a")){
        if (admin.getRole().equals("a")) {
            kdhMapper.videolistUpdate(obj, nowtitle);
        }
    }

    @Override
    public void videolistDelete(Member admin, String title) {
        admin.setRole("a");
        // 아이디를 입력받으면 로그인파트랑 연계
        // Member member=memberRepository.findById(admin.getId()).orElse(null);
        // if(member.getRole().equals("a")){
        if (admin.getRole().equals("a")) {
            videolistRepository.deleteByTitle(title);
        }
    }

    @Override
    public List<VideolistView> selectvideolist() {
        return kdhMapper.selectvideolist();
    }

    @Override
    public int addactorinvideo(Videolistdto videocode, Actorsdto no) {
    //    Long epi = videocode.getEpisode();
    //    for(int i=0; i<epi; i++){
        kdhMapper.castsInsertactor(videocode.getVideocode(), no.getActors_No());
    //    }
       return 1;
    }

    @Override
    public int removeactorinvideo(Videolistdto videocode, Actorsdto no) {
        return kdhMapper.castsDeleteactor(videocode.getVideocode(), no.getActors_No());
    }

    @Override
    public List<Actorsdto> selectactors() {
        return kdhMapper.selectActors();
    }

    @Override
    public int addactorlist(String name) {
        return kdhMapper.actorInsert(name);
    }

    @Override
    public List<Long> selectactorsinvideo(BigInteger videocode) {
        return kdhMapper.selectActorsinvideo(videocode);
    }

    @Override
    public Actorsdto selectnotoname(Long no) {
        return kdhMapper.selectnotoname(no);
    }

    @Override
    public int castsInsertactorchk(Long actors_no, Long videocode) {
        return kdhMapper.castsInsertactorchk(actors_no, videocode);
    }

    @Override
    public List<Videolist> selectvideofordelete(String title) {
      List<Videolist> list=videolistRepository.findByTitle(title);
      return list;
    }

}
