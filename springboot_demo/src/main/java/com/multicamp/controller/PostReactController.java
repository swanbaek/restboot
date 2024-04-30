package com.multicamp.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.multicamp.domain.PagingVO;
import com.multicamp.domain.PostVO;
import com.multicamp.service.PostService;

import lombok.extern.slf4j.Slf4j;

//@RequestMapping("/api_old")
@RestController
@Slf4j
public class PostReactController {
	@Inject
	private PostService postService;
	
	@PostMapping(value="/postAdd", produces="application/json")
	public ModelMap postCreate(@ModelAttribute PostVO vo,HttpSession ses) {
		ServletContext ctx=ses.getServletContext();
		String upDir=ctx.getRealPath("/upload");
		//System.out.println("vo=="+vo+", upDir: "+upDir+"mfilename: "+vo.getMfilename());
		MultipartFile mfile=vo.getMfilename();
		if(mfile!=null&&!mfile.isEmpty()) {
			try {
				mfile.transferTo(new File(upDir,mfile.getOriginalFilename()));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		vo.setFilename(mfile.getOriginalFilename());
		int n=postService.createPost(vo);
		ModelMap map=new ModelMap();
		String str=(n>0)?"ok":"fail";
		map.put("result", str);
		return map;
	}//---------------------
	
	@GetMapping(value="/postList", produces="application/json")
	public Map<String,Object> getPostList(@ModelAttribute PagingVO pvo, HttpSession ses){
		System.out.println("pvo=="+pvo);
		int totalCount=this.postService.getPostCount(pvo);
		pvo.setTotalCount(totalCount);
		pvo.init(ses);
		List<PostVO> postList=this.postService.listPosts(pvo);
		Map<String,Object> map=new HashMap<>();
		map.put("totalCount", totalCount);
		map.put("posts", postList);
		return map;
	}
	
	@PostMapping(value="/postEdit",produces="application/json")
	public ModelMap updatePost(@ModelAttribute PostVO vo,HttpSession ses) {
		int n=this.postService.updatePost(vo);
		ModelMap map=new ModelMap();
		String str=(n>0)?"ok":"fail";
		map.put("result", str);
		return map;
	}
	
	@DeleteMapping(value="/postDelete/{id}", produces="application/json")
	public ModelMap deletePost(@PathVariable("id") int id) {
		System.out.println("===delete======================"+id);
		log.info("delete id={}",id);
		int n=this.postService.deletePost(id);
		ModelMap map=new ModelMap();
		String str=(n>0)?"ok":"fail";
		map.put("result", str);
		return map;
	}
	

} 
