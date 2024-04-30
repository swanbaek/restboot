package com.multicamp.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.multicamp.domain.PagingVO;
import com.multicamp.domain.PostEntity;
import com.multicamp.domain.PostJpaVO;
import com.multicamp.domain.PostVO;
import com.multicamp.domain.ResponseVO;
import com.multicamp.service.PostJpaService;
//참고: https://github.com/fsoftwareengineer/todo-application-revision2
@RequestMapping("/api")
@RestController
public class PostReactJpaController {
	Logger log=LoggerFactory.getLogger(getClass());
	
	@Inject
	private PostJpaService postService;
	
	@PostMapping(value="/postAdd", produces="application/json")
	public ResponseEntity<?> postCreate(@AuthenticationPrincipal String nickname,
			@ModelAttribute PostJpaVO vo, HttpSession ses){
		log.info("vo==={}, nickname=={}",vo,nickname);
		try {
		//[0] 파일 업로드 처리
		ServletContext ctx=ses.getServletContext();
		String upDir=ctx.getRealPath("/upload");
		//System.out.println("vo=="+vo+", upDir: "+upDir+"mfilename: "+vo.getMfilename());
		MultipartFile mfile=vo.getMfilename();
		if(mfile!=null&&!mfile.isEmpty()) {
			try {
				mfile.transferTo(new File(upDir,mfile.getOriginalFilename()));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		vo.setFilename(mfile.getOriginalFilename());
		vo.setName(nickname);
		//[1] VO를 Entity로 변환한다
		PostEntity entity=PostJpaVO.toEntity(vo);
		//[2] id를 0으로 초기화한다. 생성 당시에는 id가 없으므로
		entity.setId(0);
		//[3] Authentication Bearer Token을 통해 받은 nickname을 넘긴다
		entity.setName(nickname);
		
		//[4] 서비스를 이용해 post entity를 생성한다
		List<PostEntity> postList=this.postService.create(entity);
		log.info("entity==={}",entity);
		//[5] java stream을 이용해 엔티티리스트를 PostJpaVO 리스트로 변환하자
		List<PostJpaVO> vos=postList.stream().map(PostJpaVO::new).collect(Collectors.toList());
		
		//[6] 변환된 리스트를 이용해 ResponseVO를 초기화한다
		ResponseVO<PostJpaVO> resVo=ResponseVO.<PostJpaVO>builder().data(vos).build();
		//[7] ResponseEntity 반환
		return ResponseEntity.ok().body(resVo);
		}catch(Exception e) {
			//[8] 예외 발생시 응답 처리
			log.error("error: {} ", e);
			ResponseVO<PostJpaVO> resVo=ResponseVO.<PostJpaVO>builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(resVo);
		}
	}

	@GetMapping(value="/postList", produces="application/json")
	public Map<String,Object> getPostList(@ModelAttribute PagingVO pvo, HttpSession ses){
		System.out.println("pvo=="+pvo);
		int totalCount=this.postService.getPostCount(pvo);
		pvo.setTotalCount(totalCount);
		pvo.init(ses);
		List<PostEntity> postList=this.postService.listPosts(pvo);
		Map<String,Object> map=new HashMap<>();
		map.put("totalCount", totalCount);
		map.put("posts", postList);
		return map;
	}
}
